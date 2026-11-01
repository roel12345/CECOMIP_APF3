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
 * Implementa una cola FIFO de producciones pendientes de validación usando nodos enlazados.
 */
public class ColaProduccion {
    private NodoProduccion frente;
    private NodoProduccion fin;
    private int cantidad;

    /**
     * Agrega una producción al final de la cola.
     *
     * @param produccion producción que se desea procesar
     */
    public void encolar(ProduccionOro produccion) {
        // El nuevo nodo siempre se agrega al final para mantener el orden FIFO.
        NodoProduccion nuevo = new NodoProduccion(produccion);
        if (fin == null) frente = fin = nuevo;
        else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        cantidad++;
    }

    /**
     * Retira y devuelve la producción ubicada al frente de la cola.
     *
     * @return producción encontrada o procesada; null si no existe
     */
    public ProduccionOro desencolar() {
        if (frente == null) return null;
        ProduccionOro dato = frente.dato;
        // El segundo elemento pasa a ocupar el frente de la cola.
        frente = frente.siguiente;
        if (frente == null) fin = null;
        cantidad--;
        return dato;
    }

    /**
     * Consulta el primer elemento de la cola sin retirarlo.
     *
     * @return producción almacenada
     */
    public ProduccionOro verFrente() { return frente == null ? null : frente.dato; }

    /**
     * Copia los elementos almacenados a un arreglo compacto.
     *
     * @return arreglo compacto de producciones
     */
    public ProduccionOro[] toArray() {
        ProduccionOro[] resultado = new ProduccionOro[cantidad];
        NodoProduccion actual = frente;
        int i = 0;
        while (actual != null) {
            resultado[i++] = actual.dato;
            actual = actual.siguiente;
        }
        return resultado;
    }

    /**
     * Vacía completamente la estructura de datos.
     */
    public void limpiar() { frente = fin = null; cantidad = 0; }
    /**
     * Devuelve la cantidad de elementos almacenados.
     *
     * @return valor entero solicitado
     */
    public int getCantidad() { return cantidad; }
}
