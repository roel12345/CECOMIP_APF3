/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Formulario modal para registrar usuarios, editable desde NetBeans GUI Builder.
 */
public class RegistroUsuarioDialog extends JDialog {

    private final AppContext contexto;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param padre ventana o componente padre
     * @param contexto contexto general de la aplicación
     * @param permitirAdministrador indica si se permite asignar el rol administrador
     */
    public RegistroUsuarioDialog(Window padre, AppContext contexto, boolean permitirAdministrador) {
        /**
         * Ejecuta la operación super.
         *
         * @param usuario" valor de usuario"
         */
        super(padre, "Registrar usuario", Dialog.ModalityType.APPLICATION_MODAL);
        this.contexto = contexto;
        /**
         * Ejecuta la operación init components.
         */
        initComponents();
        /**
         * Ejecuta la operación configurar estilos.
         */
        configurarEstilos();
        if (!permitirAdministrador) {
            cboRol.removeItem("ADMINISTRADOR");
        }
        /**
         * Actualiza size.
         */
        setSize(470, 390);
        setMinimumSize(new Dimension(430, 360));
        /**
         * Actualiza location relative to.
         */
        setLocationRelativeTo(padre);
    }

    /**
     * Aplica colores, fuentes, bordes y dimensiones a los controles del formulario.
     */
    private void configurarEstilos() {
        pnlPrincipal.setBackground(Color.WHITE);
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        pnlFormulario.setOpaque(false);
        pnlPie.setOpaque(false);

        lblTitulo.setFont(Tema.TITULO);
        lblTitulo.setForeground(Tema.AZUL_OSCURO);
        /**
         * Ejecuta la operación configurar etiqueta.
         */
        configurarEtiqueta(lblNombre);
        /**
         * Ejecuta la operación configurar etiqueta.
         */
        configurarEtiqueta(lblUsuario);
        /**
         * Ejecuta la operación configurar etiqueta.
         */
        configurarEtiqueta(lblClave);
        /**
         * Ejecuta la operación configurar etiqueta.
         */
        configurarEtiqueta(lblConfirmar);
        /**
         * Ejecuta la operación configurar etiqueta.
         */
        configurarEtiqueta(lblRol);

        /**
         * Ejecuta la operación configurar campo.
         */
        configurarCampo(txtNombre);
        /**
         * Ejecuta la operación configurar campo.
         */
        configurarCampo(txtUsuario);
        /**
         * Ejecuta la operación configurar campo.
         */
        configurarCampo(txtClave);
        /**
         * Ejecuta la operación configurar campo.
         */
        configurarCampo(txtConfirmar);
        cboRol.setFont(Tema.NORMAL);

        btnGuardar.setFont(Tema.NORMAL.deriveFont(Font.BOLD));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(Tema.VERDE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    /**
     * Aplica el estilo estándar a una etiqueta del formulario.
     *
     * @param etiqueta texto o componente de etiqueta
     */
    private void configurarEtiqueta(javax.swing.JLabel etiqueta) {
        etiqueta.setFont(Tema.NORMAL);
        etiqueta.setForeground(Tema.TEXTO);
    }

    /**
     * Aplica el estilo estándar a un campo de texto.
     *
     * @param campo control asociado a la etiqueta
     */
    private void configurarCampo(javax.swing.JTextField campo) {
        campo.setFont(Tema.NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblClave = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        lblConfirmar = new javax.swing.JLabel();
        txtConfirmar = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<String>();
        pnlPie = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar usuario");

        pnlPrincipal.setLayout(new java.awt.BorderLayout(0, 18));

        lblTitulo.setText("CREAR CUENTA DE USUARIO");
        pnlPrincipal.add(lblTitulo, java.awt.BorderLayout.NORTH);

        pnlFormulario.setLayout(new java.awt.GridBagLayout());

        lblNombre.setText("Nombre completo:");
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.35;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(lblNombre, gridBagConstraints);

        txtNombre.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(txtNombre, gridBagConstraints);

        lblUsuario.setText("Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.35;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(lblUsuario, gridBagConstraints);

        txtUsuario.setColumns(16);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(txtUsuario, gridBagConstraints);

        lblClave.setText("Contraseña:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.35;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(lblClave, gridBagConstraints);

        txtClave.setColumns(16);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(txtClave, gridBagConstraints);

        lblConfirmar.setText("Confirmar contraseña:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.35;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(lblConfirmar, gridBagConstraints);

        txtConfirmar.setColumns(16);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(txtConfirmar, gridBagConstraints);

        lblRol.setText("Rol:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.35;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(lblRol, gridBagConstraints);

        cboRol.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "OPERADOR", "ADMINISTRADOR" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 7, 4);
        pnlFormulario.add(cboRol, gridBagConstraints);

        pnlPrincipal.add(pnlFormulario, java.awt.BorderLayout.CENTER);

        pnlPie.setLayout(new java.awt.BorderLayout());
        btnGuardar.setText("REGISTRAR USUARIO");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlPie.add(btnGuardar, java.awt.BorderLayout.EAST);
        pnlPrincipal.add(pnlPie, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlPrincipal, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        /**
         * Ejecuta la operación registrar.
         */
        registrar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Valida los campos y solicita al contexto el registro del nuevo usuario.
     */
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        String rol = (String) cboRol.getSelectedItem();

        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            UiUtil.error(this, "Complete todos los campos obligatorios.");
            return;
        }
        if (usuario.contains(" ")) {
            UiUtil.error(this, "El nombre de usuario no debe contener espacios.");
            return;
        }
        if (clave.length() < 10) {
            UiUtil.error(this, "La contraseña debe tener como mínimo 10 caracteres.");
            return;
        }
        if (!clave.equals(confirmar)) {
            UiUtil.error(this, "Las contraseñas no coinciden.");
            return;
        }
        if (!contexto.registrarUsuario(nombre, usuario, clave, rol)) {
            UiUtil.error(this, "El nombre de usuario ya existe o se alcanzó la capacidad máxima.");
            return;
        }
        UiUtil.info(this, "Usuario registrado correctamente.");
        /**
         * Ejecuta la operación dispose.
         */
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblConfirmar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JPasswordField txtConfirmar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
