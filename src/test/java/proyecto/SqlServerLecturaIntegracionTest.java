package proyecto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import proyecto.dao.SqlServerConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;

/** Sólo se ejecuta si el evaluador configura una instancia REAL con CECOMIP_SQL_TEST=1. */
class SqlServerLecturaIntegracionTest {
    @Test @EnabledIfEnvironmentVariable(named="CECOMIP_SQL_TEST",matches="1")
    void conectaYConsultaEsquema() throws Exception {
        try (Connection c=SqlServerConexion.abrir();
             PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM dbo.clientes");
             ResultSet rs=p.executeQuery()) {
            assertTrue(rs.next());
            assertTrue(rs.getInt(1)>=0);
        }
    }
}
