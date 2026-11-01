/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.estructuras;

import proyecto.modelo.Cliente;

/**
 * Nodo interno de la lista simplemente enlazada de clientes.
 */
class NodoCliente {
    Cliente dato;
    NodoCliente siguiente;
    /**
     * Inicializa una nueva instancia de nodo cliente.
     *
     * @param dato valor de dato
     */
    NodoCliente(Cliente dato) { this.dato = dato; }
}
