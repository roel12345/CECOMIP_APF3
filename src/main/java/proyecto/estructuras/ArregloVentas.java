/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.estructuras;

import proyecto.modelo.VentaOro;

/**
 * Gestiona las ventas de oro mediante un arreglo de capacidad fija y operaciones secuenciales básicas.
 */
public class ArregloVentas {
    private final VentaOro[] datos;
    private int cantidad;

    /**
     * Inicializa una nueva instancia de arreglo ventas.
     *
     * @param capacidad capacidad máxima de la estructura
     */
    public ArregloVentas(int capacidad) { datos = new VentaOro[capacidad]; }

    /**
     * Inserta un elemento en la estructura de datos.
     *
     * @param venta venta que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean insertar(VentaOro venta) {
        if (cantidad >= datos.length) return false;
        datos[cantidad++] = venta;
        return true;
    }

    /**
     * Busca y selecciona un registro según los criterios ingresados.
     *
     * @param id identificador del registro
     * @return venta encontrada o null si no existe
     */
    public VentaOro buscar(int id) {
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
                // Se compacta el arreglo después de retirar la venta.
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
     * @return arreglo compacto de ventas
     */
    public VentaOro[] toArray() {
        VentaOro[] copia = new VentaOro[cantidad];
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
