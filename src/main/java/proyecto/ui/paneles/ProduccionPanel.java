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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyecto.modelo.ProduccionOro;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Módulo gráfico para registrar producciones, validar la siguiente producción en cola y eliminar registros.
 */
public class ProduccionPanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final JTextField txtFecha = UiUtil.campo(12);
    private final JTextField txtPeso = UiUtil.campo(12);
    private final JTextField txtPureza = UiUtil.campo(10);
    private final JComboBox<String> cboTurno = UiUtil.combo("MAÑANA", "TARDE", "NOCHE");
    private final JTextField txtObservacion = UiUtil.campo(26);
    private final JLabel lblCola = new JLabel();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Lote", "Fecha", "Peso bruto (g)", "Pureza (%)", "Oro fino (g)", "Turno", "Estado", "Observación"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public ProduccionPanel(AppContext contexto) {
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
        JLabel titulo = new JLabel("CONTROL DE PRODUCCIÓN DE ORO");
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
        agregar(formulario, c, 0, 0, "Fecha (AAAA-MM-DD):", txtFecha);
        /**
         * Ejecuta la operación agregar.
         *
         * @param gramos:" valor de gramos:"
         */
        agregar(formulario, c, 2, 0, "Peso bruto en gramos:", txtPeso);
        agregar(formulario, c, 0, 1, "Pureza (%):", txtPureza);
        /**
         * Ejecuta la operación agregar.
         */
        agregar(formulario, c, 2, 1, "Turno:", cboTurno);
        /**
         * Ejecuta la operación agregar.
         */
        agregar(formulario, c, 0, 2, "Observación:", txtObservacion);
        c.gridx = 2; c.gridy = 2; c.gridwidth = 2; c.weightx = 1;
        lblCola.setFont(Tema.SUBTITULO);
        lblCola.setForeground(Tema.AZUL_OSCURO);
        formulario.add(lblCola, c);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        JButton btnNuevo = UiUtil.botonSecundario("Limpiar");
        JButton btnRegistrar = UiUtil.botonRegistrar("Registrar lote");
        JButton btnValidar = UiUtil.botonSecundario("Validar siguiente de la cola");
        JButton btnEliminar = UiUtil.botonPeligro("Eliminar lote");
        botones.add(btnNuevo); botones.add(btnRegistrar); botones.add(btnValidar); botones.add(btnEliminar);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 4;
        formulario.add(botones, c);

        btnNuevo.addActionListener(e -> limpiar());
        btnRegistrar.addActionListener(e -> registrar());
        btnValidar.addActionListener(e -> validarSiguiente());
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
     * Valida el formulario y registra una nueva producción pendiente.
     */
    private void registrar() {
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            double peso = Double.parseDouble(txtPeso.getText().trim());
            double pureza = Double.parseDouble(txtPureza.getText().trim());
            if (peso <= 0 || pureza <= 0 || pureza > 100) {
                UiUtil.error(this, "El peso debe ser mayor a cero y la pureza debe estar entre 0 y 100.");
                return;
            }
            ProduccionOro p = new ProduccionOro(contexto.getProducciones().siguienteId(), fecha, peso, pureza,
                    (String) cboTurno.getSelectedItem(), txtObservacion.getText().trim(), "PENDIENTE");
            if (!contexto.registrarProduccion(p)) {
                UiUtil.error(this, "No se pudo registrar: el arreglo de producción alcanzó su capacidad.");
                return;
            }
            UiUtil.info(this, "Lote registrado y encolado para validación (FIFO).");
            /**
             * Vacía completamente la estructura de datos.
             */
            limpiar();
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        } catch (Exception e) {
            UiUtil.error(this, "Revise la fecha, el peso y la pureza. Use fecha AAAA-MM-DD y números válidos.");
        }
    }

    /**
     * Solicita la validación del siguiente registro pendiente de la cola.
     */
    private void validarSiguiente() {
        ProduccionOro p = contexto.validarSiguienteProduccion();
        if (p == null) UiUtil.info(this, "No existen lotes pendientes en la cola.");
        else UiUtil.info(this, "Se validó el lote N.° " + p.getId() + ". Ahora forma parte del stock disponible.");
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
    }

    /**
     * Elimina la producción seleccionada después de solicitar confirmación.
     */
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            UiUtil.error(this, "Seleccione un lote de producción.");
            return;
        }
        int id = ((Number) modelo.getValueAt(fila, 0)).intValue();
        if (UiUtil.confirmar(this, "¿Eliminar el lote N.° " + id + " del arreglo de objetos?")) {
            contexto.eliminarProduccion(id);
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
        txtObservacion.setText("");
        cboTurno.setSelectedIndex(0);
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        modelo.setRowCount(0);
        for (ProduccionOro p : contexto.getProducciones().toArray()) {
            modelo.addRow(new Object[]{p.getId(), p.getFecha(), String.format("%.2f", p.getPesoGramos()),
                String.format("%.2f", p.getPureza()), String.format("%.2f", p.getOroFinoGramos()),
                p.getTurno(), p.getEstado(), p.getObservacion()});
        }
        lblCola.setText("Lotes pendientes en cola: " + contexto.getColaProduccion().getCantidad());
    }
}
