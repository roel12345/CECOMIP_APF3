/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Codifica y decodifica textos para almacenarlos de forma segura dentro de archivos TSV.
 */
public final class TextoUtil {
    /**
     * Inicializa una nueva instancia de texto util.
     */
    private TextoUtil() {}

    /**
     * Codifica un texto en Base64 para evitar conflictos con tabulaciones y saltos de línea.
     *
     * @param texto texto que se desea procesar
     * @return texto resultante
     */
    public static String codificar(String texto) {
        String seguro = texto == null ? "" : texto;
        return Base64.getEncoder().encodeToString(seguro.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodifica un texto previamente almacenado en Base64.
     *
     * @param texto texto que se desea procesar
     * @return texto resultante
     */
    public static String decodificar(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return new String(Base64.getDecoder().decode(texto), StandardCharsets.UTF_8);
    }
}
