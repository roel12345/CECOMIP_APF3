/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entidad que representa una acción registrada en el historial del sistema.
 */
public class Operacion {
    private final LocalDateTime fechaHora;
    private final String descripcion;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param fechaHora fecha y hora de la operación
     * @param descripcion descripción mostrada en el selector
     */
    public Operacion(LocalDateTime fechaHora, String descripcion) {
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve fecha hora.
     *
     * @return fecha y hora almacenadas
     */
    public LocalDateTime getFechaHora() { return fechaHora; }
    /**
     * Devuelve descripcion.
     *
     * @return texto almacenado
     */
    public String getDescripcion() { return descripcion; }

    @Override
    /**
     * Ejecuta la operación to string.
     *
     * @return texto resultante
     */
    public String toString() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - " + descripcion;
    }
}
