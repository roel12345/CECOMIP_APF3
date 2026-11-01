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
 * Nodo interno del árbol binario de búsqueda de ventas. Mantiene el dato y las referencias a sus hijos izquierdo y derecho.
 */
class NodoArbolVenta {
    VentaOro dato;
    NodoArbolVenta izquierda;
    NodoArbolVenta derecha;
    /**
     * Inicializa una nueva instancia de nodo arbol venta.
     *
     * @param dato valor de dato
     */
    NodoArbolVenta(VentaOro dato) { this.dato = dato; }
}
