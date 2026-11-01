/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.estructuras;

import proyecto.modelo.ProduccionOro;

/**
 * Gestiona las producciones de oro mediante un arreglo de capacidad fija. Incluye operaciones de inserción, búsqueda, eliminación, generación de identificadores y conversión a arreglo compacto.
 */
public class ArregloProducciones {
    private final ProduccionOro[] datos;
    private int cantidad;

    /**
     * Inicializa una nueva instancia de arreglo producciones.
     *
     * @param capacidad capacidad máxima de la estructura
     */
    public ArregloProducciones(int capacidad) { datos = new ProduccionOro[capacidad]; }

    /**
     * Inserta un elemento en la estructura de datos.
     *
     * @param produccion producción que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean insertar(ProduccionOro produccion) {
        if (cantidad >= datos.length) return false;
        datos[cantidad++] = produccion;
        return true;
    }

    /**
     * Busca y selecciona un registro según los criterios ingresados.
     *
     * @param id identificador del registro
     * @return producción encontrada o procesada; null si no existe
     */
    public ProduccionOro buscar(int id) {
        for (int i = 0; i < cantidad; i++) if (datos[i].getId() == id) return datos[i];
        return null;
    }

    /**
     * Elimina el registro que coincide con el identificador indicado.
     *
     * @param id identificador del registro
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean eliminar(int id) {
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].getId() == id) {
                // Se desplazan los elementos para conservar el arreglo sin espacios intermedios.
                for (int j = i; j < cantidad - 1; j++) datos[j] = datos[j + 1];
                datos[--cantidad] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Calcula el siguiente identificador disponible.
     *
     * @return valor entero calculado
     */
    public int siguienteId() {
        int max = 0;
        for (int i = 0; i < cantidad; i++) max = Math.max(max, datos[i].getId());
        return max + 1;
    }

    /**
     * Copia los elementos almacenados a un arreglo compacto.
     *
     * @return arreglo compacto de producciones
     */
    public ProduccionOro[] toArray() {
        ProduccionOro[] copia = new ProduccionOro[cantidad];
        System.arraycopy(datos, 0, copia, 0, cantidad);
        return copia;
    }

    /**
     * Devuelve la cantidad de elementos almacenados.
     *
     * @return valor entero solicitado
     */
    public int getCantidad() { return cantidad; }
}
