/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui.paneles;

/**
 * Contrato para los paneles que deben volver a cargar sus datos cuando se muestran en pantalla.
 */
public interface Actualizable {
    /**
     * Recarga el contenido del panel con la información actual del sistema.
     */
    void actualizarDatos();
}
