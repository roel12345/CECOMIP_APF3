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
 * Entidad de dominio que representa un lote de producción de oro y calcula su contenido de oro fino.
 */
public class ProduccionOro {
    private int id;
    private LocalDate fecha;
    private double pesoGramos;
    private double pureza;
    private String turno;
    private String observacion;
    private String estado;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param id identificador del registro
     * @param fecha fecha del registro
     * @param pesoGramos peso total expresado en gramos
     * @param pureza porcentaje de pureza del oro
     * @param turno turno de producción
     * @param observacion observación asociada al registro
     * @param estado estado del registro
     */
    public ProduccionOro(int id, LocalDate fecha, double pesoGramos, double pureza, String turno, String observacion, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.pesoGramos = pesoGramos;
        this.pureza = pureza;
        this.turno = turno;
        this.observacion = observacion;
        this.estado = estado;
    }

    /**
     * Devuelve id.
     *
     * @return valor entero solicitado
     */
    public int getId() { return id; }
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
     * Devuelve turno.
     *
     * @return texto almacenado
     */
    public String getTurno() { return turno; }
    /**
     * Devuelve observacion.
     *
     * @return texto almacenado
     */
    public String getObservacion() { return observacion; }
    /**
     * Devuelve estado.
     *
     * @return texto almacenado
     */
    public String getEstado() { return estado; }
    /**
     * Calcula el contenido de oro fino según el peso y la pureza.
     *
     * @return valor decimal calculado
     */
    public double getOroFinoGramos() { return pesoGramos * pureza / 100.0; }

    /**
     * Actualiza fecha.
     *
     * @param fecha fecha del registro
     */
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    /**
     * Actualiza peso gramos.
     *
     * @param pesoGramos peso expresado en gramos
     */
    public void setPesoGramos(double pesoGramos) { this.pesoGramos = pesoGramos; }
    /**
     * Actualiza pureza.
     *
     * @param pureza porcentaje de pureza
     */
    public void setPureza(double pureza) { this.pureza = pureza; }
    /**
     * Actualiza turno.
     *
     * @param turno turno de producción
     */
    public void setTurno(String turno) { this.turno = turno; }
    /**
     * Actualiza observacion.
     *
     * @param observacion observación del registro
     */
    public void setObservacion(String observacion) { this.observacion = observacion; }
    /**
     * Actualiza estado.
     *
     * @param estado estado de la producción
     */
    public void setEstado(String estado) { this.estado = estado; }
}
