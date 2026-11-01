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
 * Implementa una lista simplemente enlazada para administrar los clientes registrados en el sistema.
 */
public class ListaClientes {
    private NodoCliente inicio;
    private int cantidad;

    /**
     * Agrega un cliente al final de la lista enlazada.
     *
     * @param cliente cliente que se desea procesar
     */
    public void insertarFinal(Cliente cliente) {
        NodoCliente nuevo = new NodoCliente(cliente);
        if (inicio == null) inicio = nuevo;
        else {
            NodoCliente actual = inicio;
            // Se recorre la lista hasta localizar el último nodo.
            while (actual.siguiente != null) actual = actual.siguiente;
            actual.siguiente = nuevo;
        }
        cantidad++;
    }

    /**
     * Busca un registro por su identificador.
     *
     * @param id identificador del registro
     * @return cliente encontrado o null si no existe
     */
    public Cliente buscarPorId(int id) {
        NodoCliente actual = inicio;
        while (actual != null) {
            if (actual.dato.getId() == id) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Busca un cliente por su número de documento.
     *
     * @param documento valor de documento
     * @return cliente encontrado o null si no existe
     */
    public Cliente buscarPorDocumento(String documento) {
        NodoCliente actual = inicio;
        while (actual != null) {
            if (actual.dato.getDocumento().equalsIgnoreCase(documento)) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Elimina el registro que coincide con el identificador indicado.
     *
     * @param id identificador del registro
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean eliminar(int id) {
        if (inicio == null) return false;
        if (inicio.dato.getId() == id) {
            inicio = inicio.siguiente;
            cantidad--;
            return true;
        }
        NodoCliente actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.getId() == id) {
                // El nodo anterior enlaza directamente con el sucesor del nodo eliminado.
                actual.siguiente = actual.siguiente.siguiente;
                cantidad--;
                return true;
            }
            actual = actual.siguiente;
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
        NodoCliente actual = inicio;
        while (actual != null) {
            max = Math.max(max, actual.dato.getId());
            actual = actual.siguiente;
        }
        return max + 1;
    }

    /**
     * Copia los elementos almacenados a un arreglo compacto.
     *
     * @return arreglo compacto de clientes
     */
    public Cliente[] toArray() {
        Cliente[] resultado = new Cliente[cantidad];
        int i = 0;
        NodoCliente actual = inicio;
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
