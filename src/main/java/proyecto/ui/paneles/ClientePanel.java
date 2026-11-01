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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyecto.modelo.Cliente;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Módulo gráfico para registrar, modificar, buscar y eliminar clientes.
 */
public class ClientePanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final JTextField txtDocumento = UiUtil.campo(13);
    private final JTextField txtNombre = UiUtil.campo(24);
    private final JTextField txtTelefono = UiUtil.campo(12);
    private final JTextField txtCorreo = UiUtil.campo(20);
    private final JTextField txtDireccion = UiUtil.campo(24);
    private final JTextField txtBuscar = UiUtil.campo(14);
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "DNI / RUC", "Cliente / Razón social", "Teléfono", "Correo", "Dirección"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private int idSeleccionado = 0;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public ClientePanel(AppContext contexto) {
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
        setLayout(new BorderLayout(0, 16));
        /**
         * Actualiza background.
         */
        setBackground(Tema.FONDO);
        setBorder(new EmptyBorder(20, 24, 24, 24));

        JLabel titulo = new JLabel("GESTIÓN DE CLIENTES");
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
        c.anchor = GridBagConstraints.WEST;

        /**
         * Ejecuta la operación agregar campo.
         *
         * @param RUC:" valor de ruc:"
         */
        agregarCampo(formulario, c, 0, 0, "DNI o RUC:", txtDocumento);
        /**
         * Ejecuta la operación agregar campo.
         *
         * @param social:" valor de social:"
         */
        agregarCampo(formulario, c, 2, 0, "Cliente / Razón social:", txtNombre);
        /**
         * Ejecuta la operación agregar campo.
         */
        agregarCampo(formulario, c, 0, 1, "Teléfono:", txtTelefono);
        /**
         * Ejecuta la operación agregar campo.
         */
        agregarCampo(formulario, c, 2, 1, "Correo:", txtCorreo);
        /**
         * Ejecuta la operación agregar campo.
         */
        agregarCampo(formulario, c, 0, 2, "Dirección:", txtDireccion);
        c.gridx = 2; c.gridy = 2; c.weightx = 0;
        formulario.add(UiUtil.etiqueta("Buscar por DNI/RUC:"), c);
        c.gridx = 3; c.weightx = 1;
        formulario.add(txtBuscar, c);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        JButton btnNuevo = UiUtil.botonSecundario("Limpiar");
        JButton btnRegistrar = UiUtil.botonRegistrar("Registrar");
        JButton btnModificar = UiUtil.botonModificar("Modificar");
        JButton btnEliminar = UiUtil.botonPeligro("Eliminar");
        JButton btnBuscar = UiUtil.botonSecundario("Buscar");
        botones.add(btnNuevo);
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 4; c.weightx = 1;
        formulario.add(botones, c);

        btnNuevo.addActionListener(e -> limpiar());
        btnRegistrar.addActionListener(e -> registrar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        txtBuscar.addActionListener(e -> buscar());

        centro.add(formulario, BorderLayout.NORTH);
        JScrollPane scroll = UiUtil.scrollTabla(tabla);
        centro.add(scroll, BorderLayout.CENTER);
        /**
         * Ejecuta la operación add.
         */
        add(centro, BorderLayout.CENTER);

        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    /**
     * Agrega una etiqueta y su campo asociado usando GridBagLayout.
     *
     * @param p panel donde se agregan los componentes
     * @param c restricciones de GridBagLayout
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param etiqueta texto o componente de etiqueta
     * @param campo control asociado a la etiqueta
     */
    private void agregarCampo(JPanel p, GridBagConstraints c, int x, int y, String etiqueta, java.awt.Component campo) {
        c.gridx = x; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        p.add(UiUtil.etiqueta(etiqueta), c);
        c.gridx = x + 1; c.weightx = 1;
        p.add(campo, c);
    }

    /**
     * Comprueba que los campos requeridos tengan contenido.
     *
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    private boolean validarCamposObligatorios() {
        if (txtDocumento.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
            UiUtil.error(this, "El documento y el nombre del cliente son obligatorios.");
            return false;
        }
        return true;
    }

    /**
     * Valida el formulario y registra un nuevo cliente.
     */
    private void registrar() {
        if (!validarCamposObligatorios()) return;
        if (idSeleccionado != 0) {
            UiUtil.error(this, "Pulse Limpiar antes de registrar un cliente nuevo.");
            return;
        }

        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        Cliente nuevo = new Cliente(contexto.getClientes().siguienteId(), documento, nombre,
                txtTelefono.getText().trim(), txtCorreo.getText().trim(), txtDireccion.getText().trim());
        if (!contexto.registrarCliente(nuevo)) {
            UiUtil.error(this, "Ya existe un cliente con ese DNI o RUC.");
            return;
        }
        UiUtil.info(this, "Cliente registrado en la lista enlazada.");
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
     * Valida y aplica los cambios al registro seleccionado.
     */
    private void modificar() {
        if (idSeleccionado == 0) {
            UiUtil.error(this, "Seleccione de la tabla el cliente que desea modificar.");
            return;
        }
        if (!validarCamposObligatorios()) return;

        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        Cliente actual = contexto.getClientes().buscarPorId(idSeleccionado);
        Cliente duplicado = contexto.getClientes().buscarPorDocumento(documento);
        if (duplicado != null && duplicado.getId() != idSeleccionado) {
            UiUtil.error(this, "El documento pertenece a otro cliente.");
            return;
        }
        if (actual == null) {
            UiUtil.error(this, "El cliente seleccionado ya no existe.");
            /**
             * Vacía completamente la estructura de datos.
             */
            limpiar();
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
            return;
        }

        actual.setDocumento(documento);
        actual.setNombreRazonSocial(nombre);
        actual.setTelefono(txtTelefono.getText().trim());
        actual.setCorreo(txtCorreo.getText().trim());
        actual.setDireccion(txtDireccion.getText().trim());
        contexto.registrarOperacion("Cliente modificado: " + nombre);
        contexto.guardarTodo();
        UiUtil.info(this, "Cliente actualizado correctamente.");
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
     * Elimina el cliente seleccionado después de solicitar confirmación.
     */
    private void eliminar() {
        if (idSeleccionado == 0) {
            UiUtil.error(this, "Seleccione un cliente de la tabla.");
            return;
        }
        if (UiUtil.confirmar(this, "¿Eliminar al cliente seleccionado de la lista enlazada?")) {
            contexto.eliminarCliente(idSeleccionado);
            /**
             * Vacía completamente la estructura de datos.
             */
            limpiar();
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        }
    }

    /**
     * Busca un cliente por documento o identificador y selecciona su fila.
     */
    private void buscar() {
        String documento = txtBuscar.getText().trim();
        Cliente c = contexto.getClientes().buscarPorDocumento(documento);
        if (c == null) {
            UiUtil.error(this, "No se encontró un cliente con ese documento.");
            return;
        }
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (((Number) modelo.getValueAt(i, 0)).intValue() == c.getId()) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                break;
            }
        }
    }

    /**
     * Carga en el formulario los datos de la fila seleccionada.
     */
    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        idSeleccionado = ((Number) modelo.getValueAt(fila, 0)).intValue();
        Cliente c = contexto.getClientes().buscarPorId(idSeleccionado);
        if (c != null) {
            txtDocumento.setText(c.getDocumento());
            txtNombre.setText(c.getNombreRazonSocial());
            txtTelefono.setText(c.getTelefono());
            txtCorreo.setText(c.getCorreo());
            txtDireccion.setText(c.getDireccion());
        }
    }

    /**
     * Restablece los controles del formulario a su estado inicial.
     */
    private void limpiar() {
        idSeleccionado = 0;
        txtDocumento.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        tabla.clearSelection();
        txtDocumento.requestFocusInWindow();
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        modelo.setRowCount(0);
        for (Cliente c : contexto.getClientes().toArray()) {
            modelo.addRow(new Object[]{c.getId(), c.getDocumento(), c.getNombreRazonSocial(), c.getTelefono(), c.getCorreo(), c.getDireccion()});
        }
    }
}
