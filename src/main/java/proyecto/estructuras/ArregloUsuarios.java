/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.estructuras;

import proyecto.modelo.Usuario;

/**
 * Gestiona los usuarios del sistema mediante un arreglo de capacidad fija, evitando nombres de usuario duplicados.
 */
public class ArregloUsuarios {
    private final Usuario[] datos;
    private int cantidad;

    /**
     * Inicializa una nueva instancia de arreglo usuarios.
     *
     * @param capacidad capacidad máxima de la estructura
     */
    public ArregloUsuarios(int capacidad) { datos = new Usuario[capacidad]; }

    /**
     * Inserta un elemento en la estructura de datos.
     *
     * @param usuario usuario que se desea procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public boolean insertar(Usuario usuario) {
        if (cantidad >= datos.length || buscarPorUsuario(usuario.getUsuario()) != null) return false;
        datos[cantidad++] = usuario;
        return true;
    }

    /**
     * Busca un usuario por su nombre de acceso sin distinguir mayúsculas y minúsculas.
     *
     * @param nombreUsuario nombre de acceso del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario buscarPorUsuario(String nombreUsuario) {
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].getUsuario().equalsIgnoreCase(nombreUsuario)) return datos[i];
        }
        return null;
    }

    /**
     * Busca un registro por su identificador.
     *
     * @param id identificador del registro
     * @return usuario encontrado o null si no existe
     */
    public Usuario buscarPorId(int id) {
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
                // Se compacta el arreglo después de retirar el usuario.
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
     * @return arreglo compacto de usuarios
     */
    public Usuario[] toArray() {
        Usuario[] copia = new Usuario[cantidad];
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
