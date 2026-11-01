/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.time.LocalDate;
import proyecto.estructuras.MatrizProduccionVentas;
import proyecto.modelo.ProduccionOro;
import proyecto.modelo.ReporteAnual;

/** Realiza todos los cálculos del módulo de reportes. */
public final class ServicioReportes {
    /**
     * Inicializa una nueva instancia de servicio reportes.
     */
    private ServicioReportes() {}

    /**
     * Genera el reporte anual consolidado a partir del contexto de la aplicación.
     *
     * @param contexto contexto general de la aplicación
     * @param anio año que se desea consolidar
     * @return reporte anual consolidado
     */
    public static ReporteAnual generar(AppContext contexto, int anio) {
        MatrizProduccionVentas matriz = contexto.getMatriz();
        matriz.construir(anio, contexto.getProducciones().toArray(), contexto.getVentas().toArray());
        double[][] meses = matriz.copia();

        double totalProduccion = 0;
        double totalVendido = 0;
        double totalIngresos = 0;
        double mayorMes = -1;
        int indiceMayorMes = -1;

        for (int mes = 0; mes < 12; mes++) {
            totalProduccion += meses[mes][0];
            totalVendido += meses[mes][1];
            totalIngresos += meses[mes][2];
            if (meses[mes][0] > mayorMes) {
                mayorMes = meses[mes][0];
                indiceMayorMes = mes;
            }
        }

        // Arreglo estático por día del año para encontrar la fecha con mayor producción.
        double[] produccionDiaria = new double[366];
        LocalDate[] fechas = new LocalDate[366];
        for (ProduccionOro produccion : contexto.getProducciones().toArray()) {
            if (produccion.getFecha().getYear() == anio && "VALIDADO".equals(produccion.getEstado())) {
                int indice = produccion.getFecha().getDayOfYear() - 1;
                produccionDiaria[indice] += produccion.getOroFinoGramos();
                fechas[indice] = produccion.getFecha();
            }
        }

        double mayorDia = 0;
        LocalDate fechaMayor = null;
        for (int i = 0; i < produccionDiaria.length; i++) {
            if (produccionDiaria[i] > mayorDia) {
                mayorDia = produccionDiaria[i];
                fechaMayor = fechas[i];
            }
        }

        if (mayorMes <= 0) indiceMayorMes = -1;
        return new ReporteAnual(anio, meses, fechaMayor, mayorDia, indiceMayorMes,
                totalProduccion, totalVendido, totalIngresos);
    }
}
