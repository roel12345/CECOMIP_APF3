package proyecto;

import proyecto.dao.ArchivoDatosDAO;
import proyecto.dao.SqlServerDatosDAO;
import proyecto.estructuras.ArregloUsuarios;
import proyecto.estructuras.ArregloProducciones;
import proyecto.estructuras.ArregloVentas;
import proyecto.estructuras.ListaClientes;

/** Importa, una sola vez, los archivos datos/*.tsv originales a SQL Server vacío.
 * Se niega a sobrescribir una base que ya tenga registros. No importa facturas PDF.
 */
public final class MigradorTsvSqlServer {
    private MigradorTsvSqlServer() { }
    public static void main(String[] args) {
        ArchivoDatosDAO tsv = new ArchivoDatosDAO();
        if (!tsv.existenDatos()) throw new IllegalStateException("No hay datos/*.tsv en la carpeta de ejecución. Copie primero los archivos originales.");
        SqlServerDatosDAO sql = new SqlServerDatosDAO();
        if (sql.existenDatos()) throw new IllegalStateException("Migración cancelada: la base SQL ya contiene registros. Haga respaldo y revise los datos antes de migrar.");
        ArregloUsuarios u = new ArregloUsuarios(100);
        ListaClientes c = new ListaClientes();
        ArregloProducciones p = new ArregloProducciones(1000);
        ArregloVentas v = new ArregloVentas(1000);
        tsv.cargarUsuarios(u); tsv.cargarClientes(c);
        tsv.cargarProducciones(p); tsv.cargarVentas(v);
        if (u.getCantidad()==0 && c.toArray().length==0 && p.getCantidad()==0 && v.getCantidad()==0)
            throw new IllegalStateException("Archivos TSV vacíos; no se migró nada.");
        sql.guardarTodo(u,c,p,v);
        System.out.printf("Migración completada: usuarios=%d clientes=%d producciones=%d ventas=%d%n",
            u.getCantidad(), c.toArray().length, p.getCantidad(), v.getCantidad());
    }
}
