/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import proyecto.estructuras.ArregloProducciones;
import proyecto.estructuras.ArregloUsuarios;
import proyecto.estructuras.ArregloVentas;
import proyecto.estructuras.ListaClientes;
import proyecto.modelo.Cliente;
import proyecto.modelo.ProduccionOro;
import proyecto.modelo.Usuario;
import proyecto.modelo.VentaOro;

/**
 * Implementa la persistencia local del sistema mediante archivos TSV codificados en UTF-8.
 */
public class RepositorioArchivos {
    private final Path carpetaDatos = Paths.get("datos");
    private final Path usuarios = carpetaDatos.resolve("usuarios.tsv");
    private final Path clientes = carpetaDatos.resolve("clientes.tsv");
    private final Path producciones = carpetaDatos.resolve("producciones.tsv");
    private final Path ventas = carpetaDatos.resolve("ventas.tsv");

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     */
    public RepositorioArchivos() {
        try {
            Files.createDirectories(carpetaDatos);
            Files.createDirectories(Paths.get("facturas"));
        } catch (IOException e) {
            /**
             * Ejecuta la operación illegal state exception.
             *
             * @param datos" valor de datos"
             * @return resultado de la operación
             */
            throw new IllegalStateException("No se pudieron crear las carpetas de datos", e);
        }
    }

    /**
     * Comprueba si existen archivos persistidos para cargar.
     *
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean existenDatos() {
        return Files.exists(usuarios) || Files.exists(clientes) || Files.exists(producciones) || Files.exists(ventas);
    }

    /**
     * Guarda los usuarios en el archivo TSV.
     *
     * @param arreglo estructura que se desea guardar
     */
    public void guardarUsuarios(ArregloUsuarios arreglo) { escribirUsuarios(arreglo.toArray()); }
    /**
     * Guarda los clientes en el archivo TSV.
     *
     * @param lista lista que se desea guardar
     */
    public void guardarClientes(ListaClientes lista) { escribirClientes(lista.toArray()); }
    /**
     * Guarda las producciones en el archivo TSV.
     *
     * @param arreglo estructura que se desea guardar
     */
    public void guardarProducciones(ArregloProducciones arreglo) { escribirProducciones(arreglo.toArray()); }
    /**
     * Guarda las ventas en el archivo TSV.
     *
     * @param arreglo estructura que se desea guardar
     */
    public void guardarVentas(ArregloVentas arreglo) { escribirVentas(arreglo.toArray()); }

    /**
     * Carga los usuarios desde el archivo TSV.
     *
     * @param destino archivo o estructura de destino
     */
    public void cargarUsuarios(ArregloUsuarios destino) {
        if (!Files.exists(usuarios)) return;
        try (BufferedReader br = Files.newBufferedReader(usuarios, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\t", -1);
                if (p.length >= 6) destino.insertar(new Usuario(Integer.parseInt(p[0]), TextoUtil.decodificar(p[1]),
                        TextoUtil.decodificar(p[2]), p[3], TextoUtil.decodificar(p[4]), Boolean.parseBoolean(p[5])));
            }
        } catch (Exception e) { throw new IllegalStateException("Error al cargar usuarios", e); }
    }

    /**
     * Carga los clientes desde el archivo TSV.
     *
     * @param destino archivo o estructura de destino
     */
    public void cargarClientes(ListaClientes destino) {
        if (!Files.exists(clientes)) return;
        try (BufferedReader br = Files.newBufferedReader(clientes, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\t", -1);
                if (p.length >= 6) destino.insertarFinal(new Cliente(Integer.parseInt(p[0]), TextoUtil.decodificar(p[1]),
                        TextoUtil.decodificar(p[2]), TextoUtil.decodificar(p[3]), TextoUtil.decodificar(p[4]), TextoUtil.decodificar(p[5])));
            }
        } catch (Exception e) { throw new IllegalStateException("Error al cargar clientes", e); }
    }

    /**
     * Carga las producciones desde el archivo TSV.
     *
     * @param destino archivo o estructura de destino
     */
    public void cargarProducciones(ArregloProducciones destino) {
        if (!Files.exists(producciones)) return;
        try (BufferedReader br = Files.newBufferedReader(producciones, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\t", -1);
                if (p.length >= 7) destino.insertar(new ProduccionOro(Integer.parseInt(p[0]), LocalDate.parse(p[1]),
                        Double.parseDouble(p[2]), Double.parseDouble(p[3]), TextoUtil.decodificar(p[4]),
                        TextoUtil.decodificar(p[5]), TextoUtil.decodificar(p[6])));
            }
        } catch (Exception e) { throw new IllegalStateException("Error al cargar producción", e); }
    }

    /**
     * Carga las ventas desde el archivo TSV.
     *
     * @param destino archivo o estructura de destino
     */
    public void cargarVentas(ArregloVentas destino) {
        if (!Files.exists(ventas)) return;
        try (BufferedReader br = Files.newBufferedReader(ventas, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\t", -1);
                if (p.length >= 9) destino.insertar(new VentaOro(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
                        TextoUtil.decodificar(p[2]), LocalDate.parse(p[3]), Double.parseDouble(p[4]),
                        Double.parseDouble(p[5]), Double.parseDouble(p[6]), Double.parseDouble(p[7]), TextoUtil.decodificar(p[8])));
            }
        } catch (Exception e) { throw new IllegalStateException("Error al cargar ventas", e); }
    }

    /**
     * Crea un escritor UTF-8 y asegura la existencia del directorio de datos.
     *
     * @param archivo archivo que se desea validar o procesar
     * @return escritor preparado para el archivo
     */
    private BufferedWriter nuevoEscritor(Path archivo) throws IOException {
        return Files.newBufferedWriter(archivo, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    /**
     * Serializa los usuarios en formato TSV.
     *
     * @param datos arreglo de entidades que se serializará
     */
    private void escribirUsuarios(Usuario[] datos) {
        try (BufferedWriter bw = nuevoEscritor(usuarios)) {
            for (Usuario u : datos) {
                bw.write(u.getId() + "\t" + TextoUtil.codificar(u.getNombreCompleto()) + "\t" + TextoUtil.codificar(u.getUsuario())
                        + "\t" + u.getClaveHash() + "\t" + TextoUtil.codificar(u.getRol()) + "\t" + u.isActivo());
                bw.newLine();
            }
        } catch (IOException e) { throw new IllegalStateException("Error al guardar usuarios", e); }
    }

    /**
     * Serializa los clientes en formato TSV.
     *
     * @param datos arreglo de entidades que se serializará
     */
    private void escribirClientes(Cliente[] datos) {
        try (BufferedWriter bw = nuevoEscritor(clientes)) {
            for (Cliente c : datos) {
                bw.write(c.getId() + "\t" + TextoUtil.codificar(c.getDocumento()) + "\t" + TextoUtil.codificar(c.getNombreRazonSocial())
                        + "\t" + TextoUtil.codificar(c.getTelefono()) + "\t" + TextoUtil.codificar(c.getCorreo()) + "\t" + TextoUtil.codificar(c.getDireccion()));
                bw.newLine();
            }
        } catch (IOException e) { throw new IllegalStateException("Error al guardar clientes", e); }
    }

    /**
     * Serializa las producciones en formato TSV.
     *
     * @param datos arreglo de entidades que se serializará
     */
    private void escribirProducciones(ProduccionOro[] datos) {
        try (BufferedWriter bw = nuevoEscritor(producciones)) {
            for (ProduccionOro p : datos) {
                bw.write(p.getId() + "\t" + p.getFecha() + "\t" + p.getPesoGramos() + "\t" + p.getPureza()
                        + "\t" + TextoUtil.codificar(p.getTurno()) + "\t" + TextoUtil.codificar(p.getObservacion()) + "\t" + TextoUtil.codificar(p.getEstado()));
                bw.newLine();
            }
        } catch (IOException e) { throw new IllegalStateException("Error al guardar producción", e); }
    }

    /**
     * Serializa las ventas en formato TSV.
     *
     * @param datos arreglo de entidades que se serializará
     */
    private void escribirVentas(VentaOro[] datos) {
        try (BufferedWriter bw = nuevoEscritor(ventas)) {
            for (VentaOro v : datos) {
                bw.write(v.getId() + "\t" + v.getClienteId() + "\t" + TextoUtil.codificar(v.getClienteNombre()) + "\t" + v.getFecha()
                        + "\t" + v.getPesoGramos() + "\t" + v.getPureza() + "\t" + v.getPrecioGramo() + "\t" + v.getTotal()
                        + "\t" + TextoUtil.codificar(v.getRutaFactura()));
                bw.newLine();
            }
        } catch (IOException e) { throw new IllegalStateException("Error al guardar ventas", e); }
    }
}
