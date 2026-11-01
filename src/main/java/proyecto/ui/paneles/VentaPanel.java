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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import proyecto.modelo.Cliente;
import proyecto.modelo.VentaOro;
import proyecto.servicio.AppContext;
import proyecto.servicio.ArchivoFacturaUtil;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Módulo gráfico para registrar ventas, calcular importes, adjuntar facturas y buscar registros mediante el ABB.
 */
public class VentaPanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final JComboBox<Cliente> cboCliente = new JComboBox<>();
    private final JTextField txtFecha = UiUtil.campo(12);
    private final JTextField txtPeso = UiUtil.campo(10);
    private final JTextField txtPureza = UiUtil.campo(10);
    private final JTextField txtPrecio = UiUtil.campo(10);
    private final JTextField txtTotal = UiUtil.campo(12);
    private final JTextField txtBuscarId = UiUtil.campo(8);
    private final JLabel lblFactura = new JLabel("Sin archivo seleccionado");
    private final JLabel lblStock = new JLabel();
    private File facturaSeleccionada;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Venta", "Fecha", "Cliente", "Peso (g)", "Pureza (%)", "Precio/g", "Total", "Factura"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public VentaPanel(AppContext contexto) {
        this.contexto = contexto;
        /**
         * Ejecuta la operación construir.
         */
        construir();
        /**
         * Vacía completamente la estructura de datos.
         */
        limpiar();
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
    }

    /**
     * Construye y distribuye los componentes visuales del panel.
     */
    private void construir() {
        setLayout(new BorderLayout(0, 16));
        /**
         * Actualiza background.
         */
        setBackground(Tema.FONDO);
        setBorder(new EmptyBorder(20, 24, 24, 24));
        JLabel titulo = new JLabel("REGISTRO DE VENTAS DE ORO");
        titulo.setFont(Tema.TITULO);
        titulo.setForeground(Tema.AZUL_OSCURO);
        /**
         * Ejecuta la operación add.
         */
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 14));
        centro.setOpaque(false);
        JPanel formulario = UiUtil.tarjeta();
        formulario.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 6, 5, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        cboCliente.setPreferredSize(new java.awt.Dimension(250, 34));
        txtTotal.setEditable(false);
        /**
         * Ejecuta la operación agregar.
         */
        agregar(formulario, c, 0, 0, "Cliente:", cboCliente);
        agregar(formulario, c, 2, 0, "Fecha (AAAA-MM-DD):", txtFecha);
        agregar(formulario, c, 0, 1, "Peso de oro (g):", txtPeso);
        agregar(formulario, c, 2, 1, "Pureza (%):", txtPureza);
        agregar(formulario, c, 0, 2, "Precio por gramo fino (S/):", txtPrecio);
        agregar(formulario, c, 2, 2, "Total de venta (S/):", txtTotal);

        c.gridx = 0; c.gridy = 3; c.weightx = 0;
        formulario.add(UiUtil.etiqueta("Factura (PDF/JPG/PNG):"), c);
        JButton btnFactura = UiUtil.botonSecundario("Seleccionar factura");
        c.gridx = 1; c.weightx = 0;
        formulario.add(btnFactura, c);
        c.gridx = 2; c.gridwidth = 2; c.weightx = 1;
        formulario.add(lblFactura, c);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.weightx = 1;
        lblStock.setFont(Tema.SUBTITULO);
        lblStock.setForeground(Tema.VERDE);
        formulario.add(lblStock, c);
        c.gridx = 2; c.gridwidth = 1; c.weightx = 0;
        formulario.add(UiUtil.etiqueta("Buscar venta por ID (ABB):"), c);
        c.gridx = 3; c.weightx = 1;
        formulario.add(txtBuscarId, c);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        JButton btnCalcular = UiUtil.botonSecundario("Calcular total");
        JButton btnRegistrar = UiUtil.botonRegistrar("Registrar venta");
        JButton btnBuscar = UiUtil.botonSecundario("Buscar en ABB");
        JButton btnAbrir = UiUtil.botonSecundario("Abrir factura");
        JButton btnEliminar = UiUtil.botonPeligro("Eliminar venta");
        botones.add(btnCalcular); botones.add(btnRegistrar); botones.add(btnBuscar); botones.add(btnAbrir); botones.add(btnEliminar);
        c.gridx = 0; c.gridy = 5; c.gridwidth = 4;
        formulario.add(botones, c);

        btnFactura.addActionListener(e -> seleccionarFactura());
        btnCalcular.addActionListener(e -> calcular());
        btnRegistrar.addActionListener(e -> registrar());
        btnBuscar.addActionListener(e -> buscarABB());
        txtBuscarId.addActionListener(e -> buscarABB());
        btnAbrir.addActionListener(e -> abrirFactura());
        btnEliminar.addActionListener(e -> eliminar());

        centro.add(formulario, BorderLayout.NORTH);
        centro.add(UiUtil.scrollTabla(tabla), BorderLayout.CENTER);
        /**
         * Ejecuta la operación add.
         */
        add(centro, BorderLayout.CENTER);
    }

    /**
     * Agrega una etiqueta y un control al formulario mediante GridBagLayout.
     *
     * @param p panel donde se agregan los componentes
     * @param c restricciones de GridBagLayout
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param etiqueta texto o componente de etiqueta
     * @param campo control asociado a la etiqueta
     */
    private void agregar(JPanel p, GridBagConstraints c, int x, int y, String etiqueta, java.awt.Component campo) {
        c.gridx = x; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        p.add(UiUtil.etiqueta(etiqueta), c);
        c.gridx = x + 1; c.weightx = 1;
        p.add(campo, c);
    }

    /**
     * Calcula el total de la venta a partir del peso, pureza y precio por gramo.
     *
     * @return valor decimal calculado
     */
    private double calcular() {
        try {
            double peso = Double.parseDouble(txtPeso.getText().trim());
            double pureza = Double.parseDouble(txtPureza.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (peso <= 0 || pureza <= 0 || pureza > 100 || precio <= 0) throw new NumberFormatException();
            double total = peso * (pureza / 100.0) * precio;
            txtTotal.setText(String.format("%.2f", total));
            return total;
        } catch (Exception e) {
            UiUtil.error(this, "Ingrese peso, pureza y precio válidos. La pureza debe estar entre 0 y 100.");
            return -1;
        }
    }

    /**
     * Permite seleccionar el archivo de factura que se asociará a la venta.
     */
    private void seleccionarFactura() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar factura de la venta");
        chooser.setFileFilter(new FileNameExtensionFilter("Factura PDF o imagen", "pdf", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            facturaSeleccionada = chooser.getSelectedFile();
            lblFactura.setText(facturaSeleccionada.getName());
        }
    }

    /**
     * Valida los datos, comprueba el saldo, copia la factura y registra una nueva venta.
     */
    private void registrar() {
        Cliente cliente = (Cliente) cboCliente.getSelectedItem();
        if (cliente == null) {
            UiUtil.error(this, "Primero debe registrar y seleccionar un cliente.");
            return;
        }
        // El importe se recalcula antes de registrar para evitar datos desactualizados.
        double total = calcular();
        if (total < 0) return;
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            double peso = Double.parseDouble(txtPeso.getText().trim());
            double pureza = Double.parseDouble(txtPureza.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            double requerido = peso * pureza / 100.0;
            if (requerido > contexto.getSaldoOroFino() + 0.0001) {
                UiUtil.error(this, String.format("Stock insuficiente. Disponible: %.2f g de oro fino; requerido: %.2f g.", contexto.getSaldoOroFino(), requerido));
                return;
            }
            int id = contexto.getVentas().siguienteId();
            String ruta = facturaSeleccionada == null ? "" : ArchivoFacturaUtil.copiarFactura(facturaSeleccionada, id);
            VentaOro venta = new VentaOro(id, cliente.getId(), cliente.getNombreRazonSocial(), fecha, peso, pureza, precio, total, ruta);
            if (!contexto.registrarVenta(venta)) {
                UiUtil.error(this, "No se pudo registrar la venta por falta de stock o capacidad del arreglo.");
                return;
            }
            UiUtil.info(this, "Venta registrada. La factura fue almacenada en la carpeta 'facturas'.");
            /**
             * Vacía completamente la estructura de datos.
             */
            limpiar();
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        } catch (Exception e) {
            UiUtil.error(this, "No se pudo registrar la venta: " + e.getMessage());
        }
    }

    /**
     * Busca una venta por identificador utilizando el árbol binario de búsqueda.
     */
    private void buscarABB() {
        try {
            int id = Integer.parseInt(txtBuscarId.getText().trim());
            VentaOro v = contexto.getArbolVentas().buscar(id);
            if (v == null) {
                UiUtil.error(this, "La venta no existe en el Árbol Binario de Búsqueda.");
                return;
            }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (((Number) modelo.getValueAt(i, 0)).intValue() == id) {
                    tabla.setRowSelectionInterval(i, i);
                    tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                    UiUtil.info(this, "Venta encontrada mediante el ABB: " + v.getClienteNombre());
                    break;
                }
            }
        } catch (Exception e) {
            UiUtil.error(this, "Ingrese un ID de venta válido.");
        }
    }

    /**
     * Abre con la aplicación predeterminada la factura de la venta seleccionada.
     */
    private void abrirFactura() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            UiUtil.error(this, "Seleccione una venta.");
            return;
        }
        int id = ((Number) modelo.getValueAt(fila, 0)).intValue();
        VentaOro v = contexto.getVentas().buscar(id);
        if (v == null || v.getRutaFactura().isEmpty()) {
            UiUtil.info(this, "La venta seleccionada no tiene una factura adjunta.");
            return;
        }
        try {
            Desktop.getDesktop().open(new File(v.getRutaFactura()));
        } catch (Exception e) {
            UiUtil.error(this, "No se pudo abrir la factura. Ruta: " + v.getRutaFactura());
        }
    }

    /**
     * Elimina la venta seleccionada después de solicitar confirmación.
     */
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            UiUtil.error(this, "Seleccione una venta de la tabla.");
            return;
        }
        int id = ((Number) modelo.getValueAt(fila, 0)).intValue();
        if (UiUtil.confirmar(this, "¿Eliminar la venta N.° " + id + " del arreglo y del ABB?")) {
            contexto.eliminarVenta(id);
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        }
    }

    /**
     * Restablece los controles del formulario a su estado inicial.
     */
    private void limpiar() {
        txtFecha.setText(LocalDate.now().toString());
        txtPeso.setText("");
        txtPureza.setText("");
        txtPrecio.setText("");
        txtTotal.setText("");
        txtBuscarId.setText("");
        facturaSeleccionada = null;
        lblFactura.setText("Sin archivo seleccionado");
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        Cliente seleccionado = (Cliente) cboCliente.getSelectedItem();
        cboCliente.removeAllItems();
        for (Cliente c : contexto.getClientes().toArray()) cboCliente.addItem(c);
        if (seleccionado != null) {
            for (int i = 0; i < cboCliente.getItemCount(); i++) {
                if (cboCliente.getItemAt(i).getId() == seleccionado.getId()) cboCliente.setSelectedIndex(i);
            }
        }
        modelo.setRowCount(0);
        for (VentaOro v : contexto.getVentas().toArray()) {
            modelo.addRow(new Object[]{v.getId(), v.getFecha(), v.getClienteNombre(), String.format("%.2f", v.getPesoGramos()),
                    String.format("%.2f", v.getPureza()), String.format("S/ %.2f", v.getPrecioGramo()),
                    String.format("S/ %,.2f", v.getTotal()), v.getRutaFactura().isEmpty() ? "NO" : "SÍ"});
        }
        lblStock.setText(String.format("Stock disponible: %,.2f g de oro fino", contexto.getSaldoOroFino()));
    }
}
