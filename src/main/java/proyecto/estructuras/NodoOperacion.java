/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.estructuras;

import proyecto.modelo.Operacion;

/**
 * Nodo interno utilizado por la pila de operaciones del sistema.
 */
class NodoOperacion {
    Operacion dato;
    NodoOperacion siguiente;
    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param dato objeto almacenado en el nodo
     * @param siguiente referencia al siguiente nodo
     */
    NodoOperacion(Operacion dato, NodoOperacion siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }
}
