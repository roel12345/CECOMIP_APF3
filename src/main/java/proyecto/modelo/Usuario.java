/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.modelo;

/**
 * Entidad que representa una cuenta de acceso, su rol y su estado dentro del sistema.
 */
public class Usuario {
    private int id;
    private String nombreCompleto;
    private String usuario;
    private String claveHash;
    private String rol;
    private boolean activo;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param id identificador del registro
     * @param nombreCompleto nombre completo de la persona
     * @param usuario usuario que se desea procesar
     * @param claveHash contraseña transformada mediante hash
     * @param rol rol asignado al usuario
     * @param activo estado de habilitación de la cuenta
     */
    public Usuario(int id, String nombreCompleto, String usuario, String claveHash, String rol, boolean activo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.claveHash = claveHash;
        this.rol = rol;
        this.activo = activo;
    }

    /**
     * Devuelve id.
     *
     * @return valor entero solicitado
     */
    public int getId() { return id; }
    /**
     * Devuelve nombre completo.
     *
     * @return texto almacenado
     */
    public String getNombreCompleto() { return nombreCompleto; }
    /**
     * Devuelve usuario.
     *
     * @return texto almacenado
     */
    public String getUsuario() { return usuario; }
    /**
     * Devuelve clave hash.
     *
     * @return texto almacenado
     */
    public String getClaveHash() { return claveHash; }
    /**
     * Devuelve rol.
     *
     * @return texto almacenado
     */
    public String getRol() { return rol; }
    /**
     * Indica si activo.
     *
     * @return verdadero si se cumple la condición; falso en caso contrario
     */
    public boolean isActivo() { return activo; }

    /**
     * Actualiza nombre completo.
     *
     * @param nombreCompleto nombre completo del usuario
     */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    /**
     * Actualiza clave hash.
     *
     * @param claveHash contraseña almacenada como hash
     */
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }
    /**
     * Actualiza rol.
     *
     * @param rol rol del usuario
     */
    public void setRol(String rol) { this.rol = rol; }
    /**
     * Actualiza activo.
     *
     * @param activo estado de la cuenta
     */
    public void setActivo(boolean activo) { this.activo = activo; }
}
