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
import proyecto.modelo.VentaOro;

/**
 * Consolida por mes la producción fina, el oro vendido y los ingresos en una matriz de 12 filas por 3 columnas.
 */
public class MatrizProduccionVentas {
    // Filas: 12 meses. Columnas: 0 producción fina, 1 venta fina, 2 ingresos.
    private final double[][] datos = new double[12][3];

    /**
     * Construye y distribuye los componentes visuales del panel.
     *
     * @param anio año que se desea consolidar
     * @param producciones arreglo de producciones disponibles
     * @param ventas arreglo de ventas disponibles
     */
    public void construir(int anio, ProduccionOro[] producciones, VentaOro[] ventas) {
        // Antes de consolidar un año se reinician todas las celdas de la matriz.
        for (int f = 0; f < 12; f++) for (int c = 0; c < 3; c++) datos[f][c] = 0;
        for (ProduccionOro p : producciones) {
            if (p.getFecha().getYear() == anio && "VALIDADO".equals(p.getEstado())) {
                datos[p.getFecha().getMonthValue() - 1][0] += p.getOroFinoGramos();
            }
        }
        for (VentaOro v : ventas) {
            if (v.getFecha().getYear() == anio) {
                datos[v.getFecha().getMonthValue() - 1][1] += v.getOroFinoGramos();
                datos[v.getFecha().getMonthValue() - 1][2] += v.getTotal();
            }
        }
    }

    /**
     * Ejecuta la operación get.
     *
     * @param fila índice de fila
     * @param columna índice de columna
     * @return valor decimal calculado
     */
    public double get(int fila, int columna) { return datos[fila][columna]; }
    /**
     * Devuelve una copia independiente de la matriz interna.
     *
     * @return copia independiente de la matriz
     */
    public double[][] copia() {
        double[][] copia = new double[12][3];
        for (int i = 0; i < 12; i++) System.arraycopy(datos[i], 0, copia[i], 0, 3);
        return copia;
    }
}
