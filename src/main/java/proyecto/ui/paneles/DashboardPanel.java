/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui.paneles;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import proyecto.modelo.ProduccionOro;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.GraficoBarrasMensual;
import proyecto.ui.componentes.GraficoCircularOro;
import proyecto.ui.componentes.TarjetaResumen;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Panel de inicio que presenta indicadores globales y gráficos resumidos de producción y ventas.
 */
public class DashboardPanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final TarjetaResumen tProduccion = new TarjetaResumen("Producción fina acumulada", Tema.DORADO);
    private final TarjetaResumen tVendido = new TarjetaResumen("Oro fino vendido", Tema.AZUL);
    private final TarjetaResumen tSaldo = new TarjetaResumen("Saldo disponible", Tema.VERDE);
    private final TarjetaResumen tIngresos = new TarjetaResumen("Ingresos por ventas", Tema.AZUL_MEDIO);
    private final GraficoCircularOro circular = new GraficoCircularOro();
    private final GraficoBarrasMensual barras = new GraficoBarrasMensual();

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public DashboardPanel(AppContext contexto) {
        this.contexto = contexto;
        /**
         * Ejecuta la operación construir.
         */
        construir();
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
    }

    /**
     * Construye y distribuye los componentes visuales del panel.
     */
    private void construir() {
        setLayout(new BorderLayout(0, 18));
        /**
         * Actualiza background.
         */
        setBackground(Tema.FONDO);
        setBorder(new EmptyBorder(22, 26, 24, 26));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("DASHBOARD DE PRODUCCIÓN Y VENTAS");
        titulo.setFont(Tema.TITULO);
        titulo.setForeground(Tema.AZUL_OSCURO);
        JLabel subtitulo = new JLabel("Resumen general del control de oro de la empresa minera");
        subtitulo.setFont(Tema.NORMAL);
        subtitulo.setForeground(Tema.TEXTO);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.SOUTH);
        /**
         * Ejecuta la operación add.
         */
        add(encabezado, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 18));
        centro.setOpaque(false);
        JPanel tarjetas = new JPanel(new GridLayout(1, 4, 14, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(tProduccion);
        tarjetas.add(tVendido);
        tarjetas.add(tSaldo);
        tarjetas.add(tIngresos);
        centro.add(tarjetas, BorderLayout.NORTH);

        JPanel graficos = new JPanel(new GridLayout(1, 2, 18, 0));
        graficos.setOpaque(false);
        JPanel cardCircular = UiUtil.tarjeta();
        cardCircular.setLayout(new BorderLayout());
        cardCircular.add(circular, BorderLayout.CENTER);
        JPanel cardBarras = UiUtil.tarjeta();
        cardBarras.setLayout(new BorderLayout());
        cardBarras.add(barras, BorderLayout.CENTER);
        graficos.add(cardCircular);
        graficos.add(cardBarras);
        centro.add(graficos, BorderLayout.CENTER);
        /**
         * Ejecuta la operación add.
         */
        add(centro, BorderLayout.CENTER);
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        double producido = contexto.getProduccionFinaTotal();
        double vendido = contexto.getVendidoFinoTotal();
        double saldo = contexto.getSaldoOroFino();
        tProduccion.setValor(String.format("%,.2f g", producido));
        tVendido.setValor(String.format("%,.2f g", vendido));
        tSaldo.setValor(String.format("%,.2f g", saldo));
        tIngresos.setValor(String.format("S/ %,.2f", contexto.getIngresosTotal()));
        circular.setDatos(producido, vendido);

        double[] meses = new double[12];
        int anio = LocalDate.now().getYear();
        for (ProduccionOro p : contexto.getProducciones().toArray()) {
            if (p.getFecha().getYear() == anio && "VALIDADO".equals(p.getEstado())) {
                meses[p.getFecha().getMonthValue() - 1] += p.getOroFinoGramos();
            }
        }
        barras.setDatos(meses, "Producción mensual " + anio + " (gramos finos)");
    }
}
