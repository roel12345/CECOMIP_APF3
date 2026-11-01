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
 * Entidad de dominio que representa a un cliente comprador de oro.
 */
public class Cliente {
    private int id;
    private String documento;
    private String nombreRazonSocial;
    private String telefono;
    private String correo;
    private String direccion;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param id identificador del registro
     * @param documento valor de documento
     * @param nombreRazonSocial valor de nombre razon social
     * @param telefono valor de telefono
     * @param correo valor de correo
     * @param direccion valor de direccion
     */
    public Cliente(int id, String documento, String nombreRazonSocial, String telefono, String correo, String direccion) {
        this.id = id;
        this.documento = documento;
        this.nombreRazonSocial = nombreRazonSocial;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    /**
     * Devuelve id.
     *
     * @return valor entero solicitado
     */
    public int getId() { return id; }
    /**
     * Devuelve documento.
     *
     * @return texto almacenado
     */
    public String getDocumento() { return documento; }
    /**
     * Devuelve nombre razon social.
     *
     * @return texto almacenado
     */
    public String getNombreRazonSocial() { return nombreRazonSocial; }
    /**
     * Devuelve telefono.
     *
     * @return texto almacenado
     */
    public String getTelefono() { return telefono; }
    /**
     * Devuelve correo.
     *
     * @return texto almacenado
     */
    public String getCorreo() { return correo; }
    /**
     * Devuelve direccion.
     *
     * @return texto almacenado
     */
    public String getDireccion() { return direccion; }

    /**
     * Actualiza documento.
     *
     * @param documento documento del cliente
     */
    public void setDocumento(String documento) { this.documento = documento; }
    /**
     * Actualiza nombre razon social.
     *
     * @param nombreRazonSocial nombre o razón social
     */
    public void setNombreRazonSocial(String nombreRazonSocial) { this.nombreRazonSocial = nombreRazonSocial; }
    /**
     * Actualiza telefono.
     *
     * @param telefono teléfono del cliente
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }
    /**
     * Actualiza correo.
     *
     * @param correo correo electrónico
     */
    public void setCorreo(String correo) { this.correo = correo; }
    /**
     * Actualiza direccion.
     *
     * @param direccion dirección del cliente
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    /**
     * Ejecuta la operación to string.
     *
     * @return texto resultante
     */
    public String toString() {
        return documento + " - " + nombreRazonSocial;
    }
}
