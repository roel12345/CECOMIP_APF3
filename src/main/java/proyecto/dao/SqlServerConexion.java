package proyecto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/** Abre conexiones JDBC usando variables de entorno, nunca credenciales en Git. */
public final class SqlServerConexion {
    private SqlServerConexion() { }

    public static Connection abrir() throws SQLException {
        String url = valor("CECOMIP_SQL_URL", "jdbc:sqlserver://localhost:1433;databaseName=CECOMIP_APF3;encrypt=true;trustServerCertificate=false;loginTimeout=10");
        String user = valor("CECOMIP_SQL_USER", null);
        String password = valor("CECOMIP_SQL_PASSWORD", null);
        if (user == null || user.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalStateException("Configure CECOMIP_SQL_USER y CECOMIP_SQL_PASSWORD en las variables de entorno. Lea GUIA_SQL_SERVER.md");
        }
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        return DriverManager.getConnection(url, p);
    }

    /** Configuración: variables de entorno tienen prioridad sobre config-local.properties. */
    public static String valor(String key, String fallback) {
        String desdeEntorno = System.getenv(key);
        if (desdeEntorno != null && !desdeEntorno.trim().isEmpty()) return desdeEntorno.trim();
        Path archivo = Paths.get("config-local.properties");
        if (Files.isRegularFile(archivo)) {
            Properties p = new Properties();
            try (InputStream in = Files.newInputStream(archivo)) { p.load(in); }
            catch (IOException e) { throw new IllegalStateException("No se pudo leer config-local.properties", e); }
            String valor = p.getProperty(key);
            if (valor != null && !valor.trim().isEmpty()) return valor.trim();
        }
        return fallback;
    }
}
