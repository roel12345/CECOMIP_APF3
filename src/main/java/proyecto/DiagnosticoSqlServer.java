package proyecto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import proyecto.dao.SqlServerConexion;
/** Verifica la conexión sin modificar registros ni imprimir credenciales. */
public final class DiagnosticoSqlServer {
    private DiagnosticoSqlServer() { }
    public static void main(String[] args) throws Exception {
        try (Connection c = SqlServerConexion.abrir();
             PreparedStatement p = c.prepareStatement("SELECT DB_NAME(), (SELECT COUNT(*) FROM dbo.usuarios), (SELECT COUNT(*) FROM dbo.clientes), (SELECT COUNT(*) FROM dbo.producciones), (SELECT COUNT(*) FROM dbo.ventas)");
             ResultSet r = p.executeQuery()) {
            if (r.next()) System.out.printf("Conexión correcta a %s. Usuarios=%d, clientes=%d, producciones=%d, ventas=%d%n",
                r.getString(1), r.getInt(2), r.getInt(3), r.getInt(4), r.getInt(5));
        }
    }
}
