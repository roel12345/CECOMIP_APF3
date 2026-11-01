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
 * Nodo interno utilizado por la cola de producciones pendientes.
 */
class NodoProduccion {
    ProduccionOro dato;
    NodoProduccion siguiente;
    /**
     * Inicializa una nueva instancia de nodo produccion.
     *
     * @param dato valor de dato
     */
    NodoProduccion(ProduccionOro dato) { this.dato = dato; }
}
