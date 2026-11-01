/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import proyecto.estructuras.ArbolVentasABB;
import proyecto.estructuras.ArregloProducciones;
import proyecto.estructuras.ArregloUsuarios;
import proyecto.estructuras.ArregloVentas;
import proyecto.estructuras.ColaProduccion;
import proyecto.estructuras.ListaClientes;
import proyecto.estructuras.MatrizProduccionVentas;
import proyecto.estructuras.PilaOperaciones;
import proyecto.modelo.Cliente;
import proyecto.modelo.Operacion;
import proyecto.modelo.ProduccionOro;
import proyecto.modelo.Usuario;
import proyecto.modelo.VentaOro;

/**
 * Coordina las estructuras de datos, la persistencia y las operaciones principales del sistema. Funciona como capa de servicio compartida entre los formularios.
 */
public class AppContext {
    private final ArregloUsuarios usuarios = new ArregloUsuarios(100);
    private final ListaClientes clientes = new ListaClientes();
    private final ArregloProducciones producciones = new ArregloProducciones(1000);
    private final ArregloVentas ventas = new ArregloVentas(1000);
    private final ColaProduccion colaProduccion = new ColaProduccion();
    private final PilaOperaciones pilaOperaciones = new PilaOperaciones();
    private final ArbolVentasABB arbolVentas = new ArbolVentasABB();
    private final MatrizProduccionVentas matriz = new MatrizProduccionVentas();
    private final proyecto.dao.DatosDAO repositorio;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     */
    public AppContext() { this(crearRepositorio()); }

    /** Selección de persistencia: SQL Server es el modo predeterminado. */
    private static proyecto.dao.DatosDAO crearRepositorio() {
        String modo = System.getenv("CECOMIP_STORAGE");
        if ("tsv".equalsIgnoreCase(modo)) return new proyecto.dao.ArchivoDatosDAO();
        return new proyecto.dao.SqlServerDatosDAO();
    }

    /** Constructor inyectable para pruebas, respeta el principio de inversión de dependencias. */
    public AppContext(proyecto.dao.DatosDAO dao) {
        this.repositorio = dao;
        boolean teniaDatos = repositorio.existenDatos();
        repositorio.cargarUsuarios(usuarios);
        repositorio.cargarClientes(clientes);
        repositorio.cargarProducciones(producciones);
        repositorio.cargarVentas(ventas);
        boolean inicializacionNueva = false;
        if (usuarios.getCantidad() == 0) {
            inicializacionNueva = true;
            if (dao instanceof proyecto.dao.ArchivoDatosDAO) {
                usuarios.insertar(new Usuario(1, "Administrador de prueba", "admin", SeguridadUtil.hashClaveSegura("Cambiar123!"), "ADMINISTRADOR", true));
            } else {
                String clave = proyecto.dao.SqlServerConexion.valor("CECOMIP_ADMIN_PASSWORD", null);
                String nombre = proyecto.dao.SqlServerConexion.valor("CECOMIP_ADMIN_USER", null);
                if (clave == null || clave.length() < 10) {
                    throw new IllegalStateException("Base vacía: configure CECOMIP_ADMIN_PASSWORD (mínimo 10 caracteres) para crear el primer administrador.");
                }
                if (nombre == null || nombre.trim().isEmpty()) nombre = "admin";
                usuarios.insertar(new Usuario(1, "Administrador General", nombre, SeguridadUtil.hashClaveSegura(clave), "ADMINISTRADOR", true));
            }
        }
        // Nunca cargar datos ficticios en SQL Server. Solo en modo TSV académico.
        if (!teniaDatos && dao instanceof proyecto.dao.ArchivoDatosDAO) {
            cargarDatosDemostracion();
            inicializacionNueva = true;
        }
        reconstruirEstructuras();
        // Evita una sincronización de escritura solo por abrir SQL Server existente.
        if (inicializacionNueva) guardarTodo();
    }

    /**
     * Crea los datos iniciales cuando no existen archivos persistidos.
     */
    private void cargarDatosDemostracion() {
        Cliente c = new Cliente(1, "20548796321", "Comercializadora Andina S.A.C.", "987654321", "compras@andina.pe", "Lima, Perú");
        clientes.insertarFinal(c);
        LocalDate hoy = LocalDate.now();
        producciones.insertar(new ProduccionOro(1, hoy.minusDays(2), 1250, 92.5, "MAÑANA", "Lote de planta 01", "VALIDADO"));
        producciones.insertar(new ProduccionOro(2, hoy.minusDays(1), 1680, 94.0, "TARDE", "Lote de planta 02", "VALIDADO"));
        producciones.insertar(new ProduccionOro(3, hoy, 980, 91.8, "MAÑANA", "Pendiente de laboratorio", "PENDIENTE"));
        double total = 500 * 0.935 * 245.0;
        ventas.insertar(new VentaOro(1, c.getId(), c.getNombreRazonSocial(), hoy.minusDays(1), 500, 93.5, 245.0, total, ""));
    }

    /**
     * Valida el usuario, la contraseña y el estado activo de la cuenta.
     *
     * @param nombreUsuario nombre de acceso del usuario
     * @param clave contraseña escrita por el usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario autenticar(String nombreUsuario, String clave) {
        Usuario u = usuarios.buscarPorUsuario(nombreUsuario.trim());
        if (u != null && u.isActivo() && SeguridadUtil.verificarClave(clave, u.getClaveHash())) {
            // Al autenticar, moderniza un hash SHA-256 legado sin pedir restablecer la clave.
            if (!u.getClaveHash().startsWith("pbkdf2$")) {
                u.setClaveHash(SeguridadUtil.hashClaveSegura(clave));
                repositorio.guardarUsuarios(usuarios);
            }
            return u;
        }
        return null;
    }

    /**
     * Valida y registra una nueva cuenta de usuario.
     *
     * @param nombre nombre o identificador lógico
     * @param nombreUsuario nombre de acceso del usuario
     * @param clave contraseña escrita por el usuario
     * @param rol rol asignado al usuario
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean registrarUsuario(String nombre, String nombreUsuario, String clave, String rol) {
        if (usuarios.buscarPorUsuario(nombreUsuario) != null) return false;
        boolean ok = usuarios.insertar(new Usuario(usuarios.siguienteId(), nombre, nombreUsuario,
                SeguridadUtil.hashClaveSegura(clave), rol, true));
        if (ok) {
            /**
             * Ejecuta la operación registrar operacion.
             *
             * @param nombreUsuario valor de nombre usuario
             */
            registrarOperacion("Se registró el usuario " + nombreUsuario);
            repositorio.guardarUsuarios(usuarios);
        }
        return ok;
    }

    /**
     * Registra un cliente y persiste los cambios.
     *
     * @param cliente cliente que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean registrarCliente(Cliente cliente) {
        if (clientes.buscarPorDocumento(cliente.getDocumento()) != null) return false;
        clientes.insertarFinal(cliente);
        registrarOperacion("Cliente registrado: " + cliente.getNombreRazonSocial());
        repositorio.guardarClientes(clientes);
        return true;
    }

    /**
     * Elimina un cliente cuando no está asociado a ventas.
     *
     * @param id identificador del registro
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean eliminarCliente(int id) {
        for (VentaOro v : ventas.toArray()) if (v.getClienteId() == id) return false;
        boolean ok = clientes.eliminar(id);
        if (ok) {
            /**
             * Ejecuta la operación registrar operacion.
             *
             * @param eliminado valor de eliminado
             * @param id identificador del registro
             */
            registrarOperacion("Cliente eliminado, ID " + id);
            repositorio.guardarClientes(clientes);
        }
        return ok;
    }

    /**
     * Registra una producción y la agrega a la cola de validación.
     *
     * @param produccion producción que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean registrarProduccion(ProduccionOro produccion) {
        boolean ok = producciones.insertar(produccion);
        if (ok) {
            colaProduccion.encolar(produccion);
            registrarOperacion("Lote de producción registrado: " + produccion.getId());
            repositorio.guardarProducciones(producciones);
        }
        return ok;
    }

    /**
     * Valida la producción pendiente ubicada al frente de la cola.
     *
     * @return producción encontrada o procesada; null si no existe
     */
    public ProduccionOro validarSiguienteProduccion() {
        ProduccionOro p = colaProduccion.desencolar();
        if (p != null) {
            p.setEstado("VALIDADO");
            registrarOperacion("Producción validada: lote " + p.getId());
            repositorio.guardarProducciones(producciones);
        }
        return p;
    }

    /**
     * Elimina una producción y reconstruye las estructuras dependientes.
     *
     * @param id identificador del registro
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean eliminarProduccion(int id) {
        boolean ok = producciones.eliminar(id);
        if (ok) {
            /**
             * Ejecuta la operación reconstruir cola.
             */
            reconstruirCola();
            /**
             * Ejecuta la operación registrar operacion.
             *
             * @param eliminada valor de eliminada
             * @param id identificador del registro
             */
            registrarOperacion("Producción eliminada, lote " + id);
            repositorio.guardarProducciones(producciones);
        }
        return ok;
    }

    /**
     * Registra una venta, actualiza el árbol de búsqueda y persiste los cambios.
     *
     * @param venta venta que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean registrarVenta(VentaOro venta) {
        if (venta.getOroFinoGramos() > getSaldoOroFino() + 0.0001) return false;
        boolean ok = ventas.insertar(venta);
        if (ok) {
            arbolVentas.insertar(venta);
            registrarOperacion("Venta registrada: N.° " + venta.getId());
            repositorio.guardarVentas(ventas);
        }
        return ok;
    }

    /**
     * Elimina una venta del arreglo y del árbol binario de búsqueda.
     *
     * @param id identificador del registro
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean eliminarVenta(int id) {
        boolean ok = ventas.eliminar(id);
        if (ok) {
            arbolVentas.eliminar(id);
            /**
             * Ejecuta la operación registrar operacion.
             *
             * @param id identificador del registro
             */
            registrarOperacion("Venta eliminada: N.° " + id);
            repositorio.guardarVentas(ventas);
        }
        return ok;
    }

    /**
     * Agrega una descripción al historial de operaciones.
     *
     * @param descripcion descripción mostrada en el selector
     */
    public void registrarOperacion(String descripcion) {
        pilaOperaciones.apilar(new Operacion(LocalDateTime.now(), descripcion));
    }

    /**
     * Calcula el total de oro fino producido y validado.
     *
     * @return valor decimal calculado
     */
    public double getProduccionFinaTotal() {
        double total = 0;
        for (ProduccionOro p : producciones.toArray()) if ("VALIDADO".equals(p.getEstado())) total += p.getOroFinoGramos();
        return total;
    }

    /**
     * Calcula el total de oro fino vendido.
     *
     * @return valor decimal calculado
     */
    public double getVendidoFinoTotal() {
        double total = 0;
        for (VentaOro v : ventas.toArray()) total += v.getOroFinoGramos();
        return total;
    }

    /**
     * Devuelve saldo oro fino.
     *
     * @return valor decimal calculado
     */
    public double getSaldoOroFino() { return getProduccionFinaTotal() - getVendidoFinoTotal(); }

    /**
     * Calcula el ingreso acumulado de todas las ventas.
     *
     * @return valor decimal calculado
     */
    public double getIngresosTotal() {
        double total = 0;
        for (VentaOro v : ventas.toArray()) total += v.getTotal();
        return total;
    }

    /**
     * Sincroniza las estructuras derivadas con los datos principales.
     */
    public void reconstruirEstructuras() {
        /**
         * Ejecuta la operación reconstruir cola.
         */
        reconstruirCola();
        arbolVentas.limpiar();
        for (VentaOro v : ventas.toArray()) arbolVentas.insertar(v);
    }

    /**
     * Reconstruye la cola usando las producciones que aún no están validadas.
     */
    private void reconstruirCola() {
        colaProduccion.limpiar();
        for (ProduccionOro p : producciones.toArray()) if ("PENDIENTE".equals(p.getEstado())) colaProduccion.encolar(p);
    }

    /**
     * Guarda usuarios, clientes, producciones y ventas en archivos locales.
     */
    public void guardarTodo() {
        repositorio.guardarTodo(usuarios, clientes, producciones, ventas);
    }

    /**
     * Devuelve usuarios.
     *
     * @return estructura de usuarios
     */
    public ArregloUsuarios getUsuarios() { return usuarios; }
    /**
     * Devuelve clientes.
     *
     * @return lista de clientes
     */
    public ListaClientes getClientes() { return clientes; }
    /**
     * Devuelve producciones.
     *
     * @return estructura de producciones
     */
    public ArregloProducciones getProducciones() { return producciones; }
    /**
     * Devuelve ventas.
     *
     * @return estructura de ventas
     */
    public ArregloVentas getVentas() { return ventas; }
    /**
     * Devuelve cola produccion.
     *
     * @return cola de producciones
     */
    public ColaProduccion getColaProduccion() { return colaProduccion; }
    /**
     * Devuelve pila operaciones.
     *
     * @return pila de operaciones
     */
    public PilaOperaciones getPilaOperaciones() { return pilaOperaciones; }
    /**
     * Devuelve arbol ventas.
     *
     * @return árbol de ventas
     */
    public ArbolVentasABB getArbolVentas() { return arbolVentas; }
    /**
     * Devuelve matriz.
     *
     * @return matriz mensual
     */
    public MatrizProduccionVentas getMatriz() { return matriz; }
}
