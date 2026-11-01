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
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import proyecto.modelo.ReporteAnual;
import proyecto.servicio.AppContext;
import proyecto.servicio.ExportadorReportes;
import proyecto.servicio.ServicioReportes;
import proyecto.ui.componentes.GraficoBarrasMensual;
import proyecto.ui.componentes.TarjetaResumen;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Módulo gráfico para consultar indicadores anuales y exportarlos a PDF o Excel.
 */
public class ReportePanel extends JPanel implements Actualizable {
    private static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private final AppContext contexto;
    private final JSpinner spAnio = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 2000, 2100, 1));
    private final TarjetaResumen tDia = new TarjetaResumen("Día con mayor producción", Tema.DORADO);
    private final TarjetaResumen tMes = new TarjetaResumen("Mes con mayor producción", Tema.AZUL);
    private final TarjetaResumen tAnual = new TarjetaResumen("Producción anual", Tema.VERDE);
    private final TarjetaResumen tIngresos = new TarjetaResumen("Ventas anuales", Tema.AZUL_MEDIO);
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Mes", "Producción fina (g)", "Oro vendido (g)", "Saldo mensual (g)", "Ingresos (S/)"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private final GraficoBarrasMensual grafico = new GraficoBarrasMensual();
    private ReporteAnual reporteActual;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public ReportePanel(AppContext contexto) {
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
        setLayout(new BorderLayout(0, 14));
        /**
         * Actualiza background.
         */
        setBackground(Tema.FONDO);
        setBorder(new EmptyBorder(18, 24, 22, 24));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        JLabel titulo = new JLabel("REPORTES DE PRODUCCIÓN: DIARIO, MENSUAL Y ANUAL");
        titulo.setFont(Tema.TITULO);
        titulo.setForeground(Tema.AZUL_OSCURO);

        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filtro.setOpaque(false);
        filtro.add(UiUtil.etiqueta("Año del reporte:"));
        spAnio.setPreferredSize(new java.awt.Dimension(90, 36));
        filtro.add(spAnio);

        JButton btnGenerar = UiUtil.boton("Generar reporte");
        JButton btnPdf = UiUtil.botonPeligro("Exportar PDF");
        JButton btnExcel = UiUtil.botonRegistrar("Exportar Excel");
        filtro.add(btnGenerar);
        filtro.add(btnPdf);
        filtro.add(btnExcel);
        cabecera.add(titulo, BorderLayout.WEST);
        cabecera.add(filtro, BorderLayout.EAST);
        /**
         * Ejecuta la operación add.
         */
        add(cabecera, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 14));
        contenido.setOpaque(false);
        JPanel tarjetas = new JPanel(new GridLayout(1, 4, 12, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(tDia);
        tarjetas.add(tMes);
        tarjetas.add(tAnual);
        tarjetas.add(tIngresos);
        contenido.add(tarjetas, BorderLayout.NORTH);

        JPanel detalle = new JPanel(new GridLayout(1, 2, 14, 0));
        detalle.setOpaque(false);
        JPanel tablaCard = UiUtil.tarjeta();
        tablaCard.setLayout(new BorderLayout());
        tablaCard.add(UiUtil.scrollTabla(tabla), BorderLayout.CENTER);
        JPanel graficoCard = UiUtil.tarjeta();
        graficoCard.setLayout(new BorderLayout());
        graficoCard.add(grafico, BorderLayout.CENTER);
        detalle.add(tablaCard);
        detalle.add(graficoCard);
        contenido.add(detalle, BorderLayout.CENTER);
        /**
         * Ejecuta la operación add.
         */
        add(contenido, BorderLayout.CENTER);

        btnGenerar.addActionListener(e -> actualizarDatos());
        btnPdf.addActionListener(e -> exportarPdf());
        btnExcel.addActionListener(e -> exportarExcel());
        spAnio.addChangeListener(e -> actualizarDatos());
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        int anio = ((Number) spAnio.getValue()).intValue();
        reporteActual = ServicioReportes.generar(contexto, anio);
        modelo.setRowCount(0);
        double[] barras = new double[12];

        for (int i = 0; i < 12; i++) {
            double produccion = reporteActual.getProduccionMes(i);
            double vendido = reporteActual.getVendidoMes(i);
            double ingresos = reporteActual.getIngresosMes(i);
            barras[i] = produccion;
            modelo.addRow(new Object[]{
                MESES[i],
                String.format(Locale.US, "%,.2f", produccion),
                String.format(Locale.US, "%,.2f", vendido),
                String.format(Locale.US, "%,.2f", reporteActual.getSaldoMes(i)),
                String.format(Locale.US, "%,.2f", ingresos)
            });
        }

        tDia.setValor(reporteActual.getFechaMayorProduccion() == null
                ? "Sin datos"
                : reporteActual.getFechaMayorProduccion() + " | "
                + String.format(Locale.US, "%,.2f g", reporteActual.getMayorProduccionDiaria()));
        tMes.setValor(reporteActual.getIndiceMesMayorProduccion() < 0
                ? "Sin datos"
                : MESES[reporteActual.getIndiceMesMayorProduccion()] + " | "
                + String.format(Locale.US, "%,.2f g",
                        reporteActual.getProduccionMes(reporteActual.getIndiceMesMayorProduccion())));
        tAnual.setValor(String.format(Locale.US, "%,.2f g", reporteActual.getTotalProduccion()));
        tIngresos.setValor(String.format(Locale.US, "S/ %,.2f", reporteActual.getTotalIngresos()));
        grafico.setDatos(barras, "Producción mensual de oro fino - " + anio);
    }

    /**
     * Permite seleccionar una ubicación y exporta el reporte actual a PDF.
     */
    private void exportarPdf() {
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
        JFileChooser selector = crearSelector("Guardar reporte en PDF", "pdf", "Documento PDF (*.pdf)");
        selector.setSelectedFile(new File("Reporte_CECOMIP_" + reporteActual.getAnio() + ".pdf"));
        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File destino = completarExtension(selector.getSelectedFile(), ".pdf");
        if (!confirmarReemplazo(destino)) return;
        try {
            ExportadorReportes.exportarPdf(reporteActual, destino);
            contexto.registrarOperacion("Reporte PDF exportado: " + destino.getName());
            /**
             * Ejecuta la operación mostrar resultado.
             *
             * @param correctamente." valor de correctamente."
             */
            mostrarResultado(destino, "El reporte PDF se generó correctamente.");
        } catch (Exception ex) {
            UiUtil.error(this, "No se pudo exportar el PDF.\n" + ex.getMessage());
        }
    }

    /**
     * Permite seleccionar una ubicación y exporta el reporte actual a Excel.
     */
    private void exportarExcel() {
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
        JFileChooser selector = crearSelector("Guardar reporte en Excel", "xlsx", "Libro de Excel (*.xlsx)");
        selector.setSelectedFile(new File("Reporte_CECOMIP_" + reporteActual.getAnio() + ".xlsx"));
        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File destino = completarExtension(selector.getSelectedFile(), ".xlsx");
        if (!confirmarReemplazo(destino)) return;
        try {
            ExportadorReportes.exportarExcel(reporteActual, destino);
            contexto.registrarOperacion("Reporte Excel exportado: " + destino.getName());
            /**
             * Ejecuta la operación mostrar resultado.
             *
             * @param correctamente." valor de correctamente."
             */
            mostrarResultado(destino, "El reporte de Excel se generó correctamente.");
        } catch (Exception ex) {
            UiUtil.error(this, "No se pudo exportar el archivo de Excel.\n" + ex.getMessage());
        }
    }

    /**
     * Crea un selector de archivos configurado para la extensión indicada.
     *
     * @param titulo título mostrado al usuario
     * @param extension extensión esperada sin punto inicial
     * @param descripcion descripción mostrada en el selector
     * @return selector de archivos configurado
     */
    private JFileChooser crearSelector(String titulo, String extension, String descripcion) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(titulo);
        selector.setAcceptAllFileFilterUsed(false);
        selector.setFileFilter(new FileNameExtensionFilter(descripcion, extension));
        return selector;
    }

    /**
     * Agrega la extensión esperada cuando el usuario no la escribió.
     *
     * @param archivo archivo que se desea validar o procesar
     * @param extension extensión esperada sin punto inicial
     * @return archivo resultante
     */
    private File completarExtension(File archivo, String extension) {
        if (archivo.getName().toLowerCase(Locale.ROOT).endsWith(extension)) return archivo;
        return new File(archivo.getParentFile(), archivo.getName() + extension);
    }

    /**
     * Solicita confirmación cuando el archivo de destino ya existe.
     *
     * @param archivo archivo que se desea validar o procesar
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    private boolean confirmarReemplazo(File archivo) {
        return !archivo.exists() || UiUtil.confirmar(this,
                "El archivo ya existe. ¿Desea reemplazarlo?\n" + archivo.getAbsolutePath());
    }

    /**
     * Informa que la exportación terminó y ofrece abrir el archivo.
     *
     * @param archivo archivo que se desea validar o procesar
     * @param mensaje mensaje que se mostrará
     */
    private void mostrarResultado(File archivo, String mensaje) {
        UiUtil.info(this, mensaje + "\n\nUbicación:\n" + archivo.getAbsolutePath());
        if (!Desktop.isDesktopSupported()) return;
        try {
            Desktop.getDesktop().open(archivo);
        } catch (IOException ex) {
            // El archivo ya fue creado; simplemente no se pudo abrir automáticamente.
        }
    }
}
