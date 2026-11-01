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
 * Implementa una pila LIFO para conservar el historial reciente de operaciones realizadas por los usuarios.
 */
public class PilaOperaciones {
    private NodoOperacion cima;
    private int cantidad;

    /**
     * Agrega una operación en la cima de la pila.
     *
     * @param operacion operación que se desea registrar
     */
    public void apilar(Operacion operacion) {
        // El nuevo nodo apunta a la antigua cima, cumpliendo el comportamiento LIFO.
        cima = new NodoOperacion(operacion, cima);
        cantidad++;
    }

    /**
     * Retira y devuelve la operación ubicada en la cima.
     *
     * @return operación encontrada o null si la pila está vacía
     */
    public Operacion desapilar() {
        if (cima == null) return null;
        Operacion dato = cima.dato;
        cima = cima.siguiente;
        cantidad--;
        return dato;
    }

    /**
     * Consulta el elemento de la cima sin retirarlo.
     *
     * @return operación almacenada
     */
    public Operacion verCima() { return cima == null ? null : cima.dato; }

    /**
     * Copia los elementos almacenados a un arreglo compacto.
     *
     * @return arreglo de operaciones desde la cima hacia la base
     */
    public Operacion[] toArray() {
        Operacion[] resultado = new Operacion[cantidad];
        NodoOperacion actual = cima;
        int i = 0;
        while (actual != null) {
            resultado[i++] = actual.dato;
            actual = actual.siguiente;
        }
        return resultado;
    }

    /**
     * Devuelve la cantidad de elementos almacenados.
     *
     * @return valor entero solicitado
     */
    public int getCantidad() { return cantidad; }
}
