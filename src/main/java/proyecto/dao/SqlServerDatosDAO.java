package proyecto.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import proyecto.estructuras.ArregloUsuarios;
import proyecto.estructuras.ListaClientes;
import proyecto.estructuras.ArregloProducciones;
import proyecto.estructuras.ArregloVentas;
import proyecto.modelo.Cliente;
import proyecto.modelo.Usuario;
import proyecto.modelo.ProduccionOro;
import proyecto.modelo.VentaOro;

/** DAO JDBC para SQL Server. Consultas parametrizadas y escrituras transaccionales.
 * IDs provenientes del cliente Java por compatibilidad con la aplicación educativa,
 * por lo que no se recomienda abrir dos instancias que creen registros a la vez.
 */
public final class SqlServerDatosDAO implements DatosDAO {
    @FunctionalInterface private interface Trabajo { void ejecutar(Connection c) throws SQLException; }

    public SqlServerDatosDAO() {
        try (Connection c = SqlServerConexion.abrir()) {
            // No crear el esquema silenciosamente; el usuario debe ejecutar el script SQL.
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM dbo.usuarios")) {
                ps.executeQuery();
            }
        } catch (SQLException e) { throw error("conectar/verificar SQL Server", e); }
    }

    private static IllegalStateException error(String operacion, SQLException e) {
        return new IllegalStateException("Fallo al " + operacion + ": " + e.getMessage(), e);
    }
    private void tx(String nombre, Trabajo trabajo) {
        try (Connection c = SqlServerConexion.abrir()) {
            c.setAutoCommit(false);
            try { trabajo.ejecutar(c); c.commit(); }
            catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        } catch (SQLException e) { throw error(nombre, e); }
    }
    private void lectura(String nombre, Trabajo trabajo) {
        try (Connection c = SqlServerConexion.abrir()) { trabajo.ejecutar(c); }
        catch (SQLException e) { throw error(nombre, e); }
    }
    public boolean existenDatos() {
        final boolean[] datos = {false};
        lectura("consultar registros", c -> {
            try (PreparedStatement p = c.prepareStatement(
                    "SELECT (SELECT COUNT(*) FROM dbo.usuarios)+(SELECT COUNT(*) FROM dbo.clientes)" +
                    "+(SELECT COUNT(*) FROM dbo.producciones)+(SELECT COUNT(*) FROM dbo.ventas)")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) datos[0] = r.getInt(1) > 0; }
            }
        });
        return datos[0];
    }

    public void cargarUsuarios(ArregloUsuarios dst) {
        lectura("cargar usuarios", c -> {
            try (PreparedStatement p = c.prepareStatement("SELECT id,nombre_completo,usuario,clave_hash,rol,activo FROM dbo.usuarios ORDER BY id"); ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    if (!dst.insertar(new Usuario(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getBoolean(6))))
                        throw new IllegalStateException("Límite de usuarios en memoria o usuario duplicado");
                }
            }
        });
    }
    public void cargarClientes(ListaClientes dst) {
        lectura("cargar clientes", c -> {
            try (PreparedStatement p = c.prepareStatement("SELECT id,documento,nombre_razon_social,telefono,correo,direccion FROM dbo.clientes ORDER BY id"); ResultSet r = p.executeQuery()) {
                while (r.next()) dst.insertarFinal(new Cliente(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6)));
            }
        });
    }
    public void cargarProducciones(ArregloProducciones dst) {
        lectura("cargar producciones", c -> {
            try (PreparedStatement p = c.prepareStatement("SELECT id,fecha,peso_gramos,pureza,turno,observacion,estado FROM dbo.producciones ORDER BY id"); ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    if (!dst.insertar(new ProduccionOro(r.getInt(1), r.getDate(2).toLocalDate(), r.getDouble(3), r.getDouble(4), r.getString(5), r.getString(6), r.getString(7))))
                        throw new IllegalStateException("Límite de producciones en memoria");
                }
            }
        });
    }
    public void cargarVentas(ArregloVentas dst) {
        lectura("cargar ventas", c -> {
            try (PreparedStatement p = c.prepareStatement("SELECT id,cliente_id,cliente_nombre,fecha,peso_gramos,pureza,precio_gramo,total,ruta_factura FROM dbo.ventas ORDER BY id"); ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    if (!dst.insertar(new VentaOro(r.getInt(1), r.getInt(2), r.getString(3), r.getDate(4).toLocalDate(), r.getDouble(5), r.getDouble(6), r.getDouble(7), r.getDouble(8), r.getString(9))))
                        throw new IllegalStateException("Límite de ventas en memoria");
                }
            }
        });
    }

    // La versión vigente usa estructuras en memoria; cada guardado sincroniza su tabla.
    // No se eliminan todas las filas para reinsertarlas: se actualiza por ID y luego
    // se retiran solo las que el usuario haya eliminado localmente.
    private void usuarios(Connection c, ArregloUsuarios lista) throws SQLException {
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement upd = c.prepareStatement("UPDATE dbo.usuarios SET nombre_completo=?,usuario=?,clave_hash=?,rol=?,activo=? WHERE id=?");
             PreparedStatement ins = c.prepareStatement("INSERT INTO dbo.usuarios(id,nombre_completo,usuario,clave_hash,rol,activo) VALUES(?,?,?,?,?,?)")) {
            for (Usuario u : lista.toArray()) {
                ids.add(u.getId());
                upd.setString(1,u.getNombreCompleto()); upd.setString(2,u.getUsuario()); upd.setString(3,u.getClaveHash());
                upd.setString(4,u.getRol()); upd.setBoolean(5,u.isActivo()); upd.setInt(6,u.getId());
                if (upd.executeUpdate()==0) {
                    ins.setInt(1,u.getId());ins.setString(2,u.getNombreCompleto());ins.setString(3,u.getUsuario());
                    ins.setString(4,u.getClaveHash());ins.setString(5,u.getRol());ins.setBoolean(6,u.isActivo()); ins.executeUpdate();
                }
            }
        }
        depurar(c, "dbo.usuarios", ids);
    }
    private void clientes(Connection c, ListaClientes lista) throws SQLException {
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement upd = c.prepareStatement("UPDATE dbo.clientes SET documento=?,nombre_razon_social=?,telefono=?,correo=?,direccion=? WHERE id=?");
             PreparedStatement ins = c.prepareStatement("INSERT INTO dbo.clientes(id,documento,nombre_razon_social,telefono,correo,direccion) VALUES(?,?,?,?,?,?)")) {
            for (Cliente a : lista.toArray()) {
                ids.add(a.getId());
                upd.setString(1,a.getDocumento());upd.setString(2,a.getNombreRazonSocial());upd.setString(3,a.getTelefono());
                upd.setString(4,a.getCorreo());upd.setString(5,a.getDireccion());upd.setInt(6,a.getId());
                if (upd.executeUpdate()==0) {
                    ins.setInt(1,a.getId());ins.setString(2,a.getDocumento());ins.setString(3,a.getNombreRazonSocial());
                    ins.setString(4,a.getTelefono());ins.setString(5,a.getCorreo());ins.setString(6,a.getDireccion());ins.executeUpdate();
                }
            }
        }
        depurar(c, "dbo.clientes", ids);
    }
    private void producciones(Connection c, ArregloProducciones lista) throws SQLException {
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement upd = c.prepareStatement("UPDATE dbo.producciones SET fecha=?,peso_gramos=?,pureza=?,turno=?,observacion=?,estado=? WHERE id=?");
             PreparedStatement ins = c.prepareStatement("INSERT INTO dbo.producciones(id,fecha,peso_gramos,pureza,turno,observacion,estado) VALUES(?,?,?,?,?,?,?)")) {
            for (ProduccionOro a : lista.toArray()) {
                ids.add(a.getId());
                upd.setDate(1,Date.valueOf(a.getFecha()));upd.setDouble(2,a.getPesoGramos());upd.setDouble(3,a.getPureza());
                upd.setString(4,a.getTurno());upd.setString(5,a.getObservacion());upd.setString(6,a.getEstado());upd.setInt(7,a.getId());
                if (upd.executeUpdate()==0) {
                    ins.setInt(1,a.getId());ins.setDate(2,Date.valueOf(a.getFecha()));ins.setDouble(3,a.getPesoGramos());
                    ins.setDouble(4,a.getPureza());ins.setString(5,a.getTurno());ins.setString(6,a.getObservacion());
                    ins.setString(7,a.getEstado());ins.executeUpdate();
                }
            }
        }
        depurar(c, "dbo.producciones", ids);
    }
    private void ventas(Connection c, ArregloVentas lista) throws SQLException {
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement upd = c.prepareStatement("UPDATE dbo.ventas SET cliente_id=?,cliente_nombre=?,fecha=?,peso_gramos=?,pureza=?,precio_gramo=?,total=?,ruta_factura=? WHERE id=?");
             PreparedStatement ins = c.prepareStatement("INSERT INTO dbo.ventas(id,cliente_id,cliente_nombre,fecha,peso_gramos,pureza,precio_gramo,total,ruta_factura) VALUES(?,?,?,?,?,?,?,?,?)")) {
            for (VentaOro a : lista.toArray()) {
                ids.add(a.getId());
                upd.setInt(1,a.getClienteId());upd.setString(2,a.getClienteNombre());upd.setDate(3,Date.valueOf(a.getFecha()));
                upd.setDouble(4,a.getPesoGramos());upd.setDouble(5,a.getPureza());upd.setDouble(6,a.getPrecioGramo());
                upd.setDouble(7,a.getTotal());upd.setString(8,a.getRutaFactura());upd.setInt(9,a.getId());
                if (upd.executeUpdate()==0) {
                    ins.setInt(1,a.getId());ins.setInt(2,a.getClienteId());ins.setString(3,a.getClienteNombre());
                    ins.setDate(4,Date.valueOf(a.getFecha()));ins.setDouble(5,a.getPesoGramos());ins.setDouble(6,a.getPureza());
                    ins.setDouble(7,a.getPrecioGramo());ins.setDouble(8,a.getTotal());ins.setString(9,a.getRutaFactura());ins.executeUpdate();
                }
            }
        }
        depurar(c, "dbo.ventas", ids);
    }
    /** Tablas restringidas a constantes internas, nunca nombres ingresados por usuarios. */
    private void depurar(Connection c, String tabla, Set<Integer> ids) throws SQLException {
        Set<Integer> enSql = new HashSet<>();
        try (PreparedStatement q = c.prepareStatement("SELECT id FROM " + tabla); ResultSet r = q.executeQuery()) {
            while (r.next()) enSql.add(r.getInt(1));
        }
        try (PreparedStatement delete = c.prepareStatement("DELETE FROM " + tabla + " WHERE id=?")) {
            for (Integer id : enSql) if (!ids.contains(id)) { delete.setInt(1, id); delete.executeUpdate(); }
        }
    }
    public void guardarUsuarios(ArregloUsuarios a) { tx("guardar usuarios", c -> usuarios(c,a)); }
    public void guardarClientes(ListaClientes a) { tx("guardar clientes", c -> clientes(c,a)); }
    public void guardarProducciones(ArregloProducciones a) { tx("guardar producciones", c -> producciones(c,a)); }
    public void guardarVentas(ArregloVentas a) { tx("guardar ventas", c -> ventas(c,a)); }
    /** Persiste todos los módulos en una sola transacción para no dejar escrituras parciales. */
    @Override public void guardarTodo(ArregloUsuarios u, ListaClientes c, ArregloProducciones p, ArregloVentas v) {
        tx("guardar proyecto", cn -> { usuarios(cn,u); producciones(cn,p); clientes(cn,c); ventas(cn,v); });
    }
}
