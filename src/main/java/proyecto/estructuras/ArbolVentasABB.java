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
 * Implementa un árbol binario de búsqueda (ABB) para almacenar ventas ordenadas por su identificador. Permite insertar, buscar, eliminar y recorrer los registros en orden ascendente.
 */
public class ArbolVentasABB {
    private NodoArbolVenta raiz;

    /**
     * Inserta el elemento indicado en la estructura.
     *
     * @param venta venta que se desea insertar
     */
    public void insertar(VentaOro venta) { raiz = insertarRec(raiz, venta); }

    /**
     * Inserta recursivamente una venta respetando el orden del árbol.
     *
     * @param nodo nodo actual del recorrido
     * @param venta venta que se desea procesar
     * @return resultado de la operación
     */
    private NodoArbolVenta insertarRec(NodoArbolVenta nodo, VentaOro venta) {
        // Caso base: se encontró la posición libre donde se crea el nuevo nodo.
        if (nodo == null) return new NodoArbolVenta(venta);
        if (venta.getId() < nodo.dato.getId()) nodo.izquierda = insertarRec(nodo.izquierda, venta);
        else if (venta.getId() > nodo.dato.getId()) nodo.derecha = insertarRec(nodo.derecha, venta);
        return nodo;
    }

    /**
     * Busca y selecciona un registro según los criterios ingresados.
     *
     * @param id identificador del registro
     * @return venta encontrada o null si no existe
     */
    public VentaOro buscar(int id) {
        NodoArbolVenta actual = raiz;
        // Búsqueda iterativa: en cada paso se descarta uno de los subárboles.
        while (actual != null) {
            if (id == actual.dato.getId()) return actual.dato;
            actual = id < actual.dato.getId() ? actual.izquierda : actual.derecha;
        }
        return null;
    }

    /**
     * Elimina el elemento identificado por el valor recibido.
     *
     * @param id identificador del registro
     */
    public void eliminar(int id) { raiz = eliminarRec(raiz, id); }

    /**
     * Elimina recursivamente un nodo del árbol y conserva la propiedad de orden.
     *
     * @param nodo nodo actual del recorrido
     * @param id identificador del registro
     * @return resultado de la operación
     */
    private NodoArbolVenta eliminarRec(NodoArbolVenta nodo, int id) {
        if (nodo == null) return null;
        if (id < nodo.dato.getId()) nodo.izquierda = eliminarRec(nodo.izquierda, id);
        else if (id > nodo.dato.getId()) nodo.derecha = eliminarRec(nodo.derecha, id);
        else {
            if (nodo.izquierda == null) return nodo.derecha;
            if (nodo.derecha == null) return nodo.izquierda;
            // Si tiene dos hijos, se reemplaza por el sucesor inorden del subárbol derecho.
            NodoArbolVenta sucesor = minimo(nodo.derecha);
            nodo.dato = sucesor.dato;
            nodo.derecha = eliminarRec(nodo.derecha, sucesor.dato.getId());
        }
        return nodo;
    }

    /**
     * Obtiene el nodo con el menor identificador dentro del subárbol indicado.
     *
     * @param nodo nodo actual del recorrido
     * @return resultado de la operación
     */
    private NodoArbolVenta minimo(NodoArbolVenta nodo) {
        while (nodo.izquierda != null) nodo = nodo.izquierda;
        return nodo;
    }

    /**
     * Devuelve las ventas ordenadas de menor a mayor identificador.
     *
     * @param cantidadEsperada cantidad de posiciones reservadas para el resultado
     * @return arreglo compacto de ventas
     */
    public VentaOro[] inOrden(int cantidadEsperada) {
        VentaOro[] resultado = new VentaOro[cantidadEsperada];
        int[] indice = {0};
        // El recorrido inorden produce los identificadores en orden ascendente.
        /**
         * Ejecuta la operación in orden rec.
         */
        inOrdenRec(raiz, resultado, indice);
        return resultado;
    }

    /**
     * Realiza el recorrido inorden recursivo del árbol.
     *
     * @param nodo nodo actual del recorrido
     * @param resultado arreglo donde se almacenan los elementos recorridos
     * @param indice posición mutable utilizada durante el recorrido recursivo
     */
    private void inOrdenRec(NodoArbolVenta nodo, VentaOro[] resultado, int[] indice) {
        if (nodo == null) return;
        /**
         * Ejecuta la operación in orden rec.
         */
        inOrdenRec(nodo.izquierda, resultado, indice);
        resultado[indice[0]++] = nodo.dato;
        /**
         * Ejecuta la operación in orden rec.
         */
        inOrdenRec(nodo.derecha, resultado, indice);
    }

    /**
     * Vacía completamente la estructura de datos.
     */
    public void limpiar() { raiz = null; }
}
