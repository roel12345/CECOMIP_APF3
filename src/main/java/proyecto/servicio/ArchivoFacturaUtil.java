/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Utilidad para copiar y organizar los archivos de factura asociados a cada venta.
 */
public final class ArchivoFacturaUtil {
    /**
     * Inicializa una nueva instancia de archivo factura util.
     */
    private ArchivoFacturaUtil() {}

    /**
     * Copia una factura al directorio administrado por la aplicación y devuelve su nueva ruta.
     *
     * @param origen archivo o estructura de origen
     * @param ventaId identificador de la venta asociada
     * @return texto resultante
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    public static String copiarFactura(File origen, int ventaId) throws IOException {
        if (origen == null) return "";
        String nombreLimpio = origen.getName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path carpeta = Paths.get("facturas");
        Files.createDirectories(carpeta);
        Path destino = carpeta.resolve("FACTURA_VENTA_" + ventaId + "_" + nombreLimpio);
        Files.copy(origen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        return destino.toAbsolutePath().toString();
    }
}
