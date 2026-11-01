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
 * Resultado consolidado del reporte anual de producción y ventas.
 * Mantiene los cálculos separados de la interfaz gráfica para que los mismos
 * datos puedan mostrarse en pantalla y exportarse a PDF o Excel.
 */
public class ReporteAnual {
    private final int anio;
    private final double[][] meses;
    private final LocalDate fechaMayorProduccion;
    private final double mayorProduccionDiaria;
    private final int indiceMesMayorProduccion;
    private final double totalProduccion;
    private final double totalVendido;
    private final double totalIngresos;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param anio año que se desea consolidar
     * @param meses matriz con los valores mensuales
     * @param fechaMayorProduccion fecha con mayor producción diaria
     * @param mayorProduccionDiaria cantidad máxima producida en un día
     * @param indiceMesMayorProduccion índice del mes con mayor producción
     * @param totalProduccion total anual producido
     * @param totalVendido total anual vendido
     * @param totalIngresos ingreso total anual
     */
    public ReporteAnual(int anio, double[][] meses, LocalDate fechaMayorProduccion,
            double mayorProduccionDiaria, int indiceMesMayorProduccion,
            double totalProduccion, double totalVendido, double totalIngresos) {
        this.anio = anio;
        this.meses = copiarMatriz(meses);
        this.fechaMayorProduccion = fechaMayorProduccion;
        this.mayorProduccionDiaria = mayorProduccionDiaria;
        this.indiceMesMayorProduccion = indiceMesMayorProduccion;
        this.totalProduccion = totalProduccion;
        this.totalVendido = totalVendido;
        this.totalIngresos = totalIngresos;
    }

    /**
     * Crea una copia defensiva de la matriz recibida.
     *
     * @param origen archivo o estructura de origen
     * @return copia independiente de la matriz
     */
    private double[][] copiarMatriz(double[][] origen) {
        double[][] copia = new double[origen.length][];
        for (int i = 0; i < origen.length; i++) {
            copia[i] = new double[origen[i].length];
            System.arraycopy(origen[i], 0, copia[i], 0, origen[i].length);
        }
        return copia;
    }

    /**
     * Devuelve anio.
     *
     * @return valor entero solicitado
     */
    public int getAnio() { return anio; }
    /**
     * Devuelve produccion mes.
     *
     * @param mes índice del mes, donde enero corresponde a 0
     * @return valor decimal calculado
     */
    public double getProduccionMes(int mes) { return meses[mes][0]; }
    /**
     * Devuelve vendido mes.
     *
     * @param mes índice del mes, donde enero corresponde a 0
     * @return valor decimal calculado
     */
    public double getVendidoMes(int mes) { return meses[mes][1]; }
    /**
     * Devuelve ingresos mes.
     *
     * @param mes índice del mes, donde enero corresponde a 0
     * @return valor decimal calculado
     */
    public double getIngresosMes(int mes) { return meses[mes][2]; }
    /**
     * Devuelve saldo mes.
     *
     * @param mes índice del mes, donde enero corresponde a 0
     * @return valor decimal calculado
     */
    public double getSaldoMes(int mes) { return getProduccionMes(mes) - getVendidoMes(mes); }
    /**
     * Devuelve fecha mayor produccion.
     *
     * @return fecha almacenada
     */
    public LocalDate getFechaMayorProduccion() { return fechaMayorProduccion; }
    /**
     * Devuelve mayor produccion diaria.
     *
     * @return valor decimal calculado
     */
    public double getMayorProduccionDiaria() { return mayorProduccionDiaria; }
    /**
     * Devuelve indice mes mayor produccion.
     *
     * @return valor entero solicitado
     */
    public int getIndiceMesMayorProduccion() { return indiceMesMayorProduccion; }
    /**
     * Devuelve total produccion.
     *
     * @return valor decimal calculado
     */
    public double getTotalProduccion() { return totalProduccion; }
    /**
     * Devuelve total vendido.
     *
     * @return valor decimal calculado
     */
    public double getTotalVendido() { return totalVendido; }
    /**
     * Calcula el saldo anual de oro fino.
     *
     * @return valor decimal calculado
     */
    public double getSaldoAnual() { return totalProduccion - totalVendido; }
    /**
     * Devuelve total ingresos.
     *
     * @return valor decimal calculado
     */
    public double getTotalIngresos() { return totalIngresos; }
    /**
     * Determina si el reporte contiene valores distintos de cero.
     *
     * @return verdadero si se cumple la condición; falso en caso contrario
     */
    public boolean tieneDatos() { return totalProduccion > 0 || totalVendido > 0 || totalIngresos > 0; }
}
