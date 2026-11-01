/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.modelo;

import java.time.LocalDate;

/**
 * Entidad de dominio que representa una venta de oro, el cliente asociado, los importes y la factura adjunta.
 */
public class VentaOro {
    private int id;
    private int clienteId;
    private String clienteNombre;
    private LocalDate fecha;
    private double pesoGramos;
    private double pureza;
    private double precioGramo;
    private double total;
    private String rutaFactura;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param id identificador del registro
     * @param clienteId identificador del cliente
     * @param clienteNombre nombre del cliente
     * @param fecha fecha del registro
     * @param pesoGramos peso total expresado en gramos
     * @param pureza porcentaje de pureza del oro
     * @param precioGramo precio aplicado por gramo
     * @param total importe total de la venta
     * @param rutaFactura ruta del archivo de factura
     */
    public VentaOro(int id, int clienteId, String clienteNombre, LocalDate fecha, double pesoGramos,
                    double pureza, double precioGramo, double total, String rutaFactura) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.fecha = fecha;
        this.pesoGramos = pesoGramos;
        this.pureza = pureza;
        this.precioGramo = precioGramo;
        this.total = total;
        this.rutaFactura = rutaFactura;
    }

    /**
     * Devuelve id.
     *
     * @return valor entero solicitado
     */
    public int getId() { return id; }
    /**
     * Devuelve cliente id.
     *
     * @return valor entero solicitado
     */
    public int getClienteId() { return clienteId; }
    /**
     * Devuelve cliente nombre.
     *
     * @return texto almacenado
     */
    public String getClienteNombre() { return clienteNombre; }
    /**
     * Devuelve fecha.
     *
     * @return fecha almacenada
     */
    public LocalDate getFecha() { return fecha; }
    /**
     * Devuelve peso gramos.
     *
     * @return valor decimal calculado
     */
    public double getPesoGramos() { return pesoGramos; }
    /**
     * Devuelve pureza.
     *
     * @return valor decimal calculado
     */
    public double getPureza() { return pureza; }
    /**
     * Devuelve precio gramo.
     *
     * @return valor decimal calculado
     */
    public double getPrecioGramo() { return precioGramo; }
    /**
     * Devuelve total.
     *
     * @return valor decimal calculado
     */
    public double getTotal() { return total; }
    /**
     * Devuelve ruta factura.
     *
     * @return texto almacenado
     */
    public String getRutaFactura() { return rutaFactura; }
    /**
     * Calcula el contenido de oro fino según el peso y la pureza.
     *
     * @return valor decimal calculado
     */
    public double getOroFinoGramos() { return pesoGramos * pureza / 100.0; }

    /**
     * Actualiza ruta factura.
     *
     * @param rutaFactura ruta del archivo de factura
     */
    public void setRutaFactura(String rutaFactura) { this.rutaFactura = rutaFactura; }
}
