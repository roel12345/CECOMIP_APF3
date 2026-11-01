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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import proyecto.modelo.Usuario;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Formulario de acceso editable desde NetBeans GUI Builder.
 * La lógica de autenticación se mantiene separada del código visual.
 */
public class LoginFrame extends JFrame {

    private final AppContext contexto;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public LoginFrame(AppContext contexto) {
        this.contexto = contexto;
        /**
         * Ejecuta la operación init components.
         */
        initComponents();
        /**
         * Ejecuta la operación configurar estilos.
         */
        configurarEstilos();
        /**
         * Actualiza size.
         */
        setSize(980, 610);
        setMinimumSize(new Dimension(840, 540));
        /**
         * Actualiza location relative to.
         */
        setLocationRelativeTo(null);
    }

    /**
     * Aplica colores, fuentes, bordes y dimensiones a los controles del formulario.
     */
    private void configurarEstilos() {
        pnlRaiz.setBackground(Tema.FONDO);
        pnlMarca.setBackground(Tema.AZUL_OSCURO);
        pnlMarca.setPreferredSize(new Dimension(420, 600));
        pnlContenedor.setBackground(Tema.FONDO);

        lblLogo.setForeground(Tema.DORADO);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblProyecto.setForeground(new Color(190, 198, 224));
        lblProyecto.setFont(Tema.NORMAL);

        pnlTarjeta.setBackground(Color.WHITE);
        pnlTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE),
                BorderFactory.createEmptyBorder(32, 38, 32, 38)));
        pnlTarjeta.setPreferredSize(new Dimension(400, 350));

        lblTitulo.setFont(Tema.TITULO);
        lblTitulo.setForeground(Tema.AZUL_OSCURO);
        lblSubtitulo.setFont(Tema.NORMAL);
        lblSubtitulo.setForeground(new Color(105, 113, 130));
        lblUsuario.setFont(Tema.NORMAL);
        lblUsuario.setForeground(Tema.TEXTO);
        lblClave.setFont(Tema.NORMAL);
        lblClave.setForeground(Tema.TEXTO);
        lblAyuda.setForeground(new Color(120, 128, 145));
        lblAyuda.setFont(new Font("SansSerif", Font.ITALIC, 12));

        txtUsuario.setFont(Tema.NORMAL);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        txtClave.setFont(Tema.NORMAL);
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        btnIngresar.setFont(Tema.NORMAL.deriveFont(Font.BOLD));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBackground(Tema.CELESTE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlRaiz = new javax.swing.JPanel();
        pnlMarca = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblProyecto = new javax.swing.JLabel();
        pnlContenedor = new javax.swing.JPanel();
        pnlTarjeta = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblClave = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        lblAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CECOMIP LTDA - Acceso | 03_EDPROYECTO_GRUPO_12");

        pnlRaiz.setLayout(new java.awt.BorderLayout());

        pnlMarca.setLayout(new java.awt.GridBagLayout());

        lblLogo.setText("CECOMIP LTDA");
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(8, 24, 8, 24);
        pnlMarca.add(lblLogo, gridBagConstraints);

        lblDescripcion.setText("Sistema de control de producción y venta de oro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 24, 8, 24);
        pnlMarca.add(lblDescripcion, gridBagConstraints);

        lblProyecto.setText("03_EDPROYECTO_GRUPO_12");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 24, 8, 24);
        pnlMarca.add(lblProyecto, gridBagConstraints);

        pnlRaiz.add(pnlMarca, java.awt.BorderLayout.WEST);

        pnlContenedor.setLayout(new java.awt.GridBagLayout());
        pnlTarjeta.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setText("INICIAR SESIÓN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        pnlTarjeta.add(lblTitulo, gridBagConstraints);

        lblSubtitulo.setText("Ingrese sus credenciales para administrar el sistema");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        pnlTarjeta.add(lblSubtitulo, gridBagConstraints);

        lblUsuario.setText("Usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 4, 0);
        pnlTarjeta.add(lblUsuario, gridBagConstraints);

        txtUsuario.setColumns(18);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 8, 0);
        pnlTarjeta.add(txtUsuario, gridBagConstraints);

        lblClave.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 4, 0);
        pnlTarjeta.add(lblClave, gridBagConstraints);

        txtClave.setColumns(18);
        txtClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 16, 0);
        pnlTarjeta.add(txtClave, gridBagConstraints);

        btnIngresar.setText("INGRESAR AL SISTEMA");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 8, 0);
        pnlTarjeta.add(btnIngresar, gridBagConstraints);

        lblAyuda.setText("Acceso con usuario registrado en SQL Server");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        pnlTarjeta.add(lblAyuda, gridBagConstraints);

        pnlContenedor.add(pnlTarjeta, new java.awt.GridBagConstraints());
        pnlRaiz.add(pnlContenedor, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlRaiz, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        /**
         * Ejecuta la operación ingresar.
         */
        ingresar();
    }//GEN-LAST:event_btnIngresarActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void txtClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveActionPerformed
        /**
         * Ejecuta la operación ingresar.
         */
        ingresar();
    }//GEN-LAST:event_txtClaveActionPerformed

    /**
     * Procesa el intento de inicio de sesión y abre la ventana principal.
     */
    private void ingresar() {
        // La autenticación se delega a la capa de servicio para separar GUI y lógica.
        Usuario usuario = new proyecto.controlador.AutenticacionControlador(contexto).ingresar(txtUsuario.getText(), txtClave.getPassword());
        if (usuario == null) {
            UiUtil.error(this, "Usuario, contraseña o estado de cuenta incorrectos.");
            txtClave.setText("");
            return;
        }
        contexto.registrarOperacion("Inicio de sesión de " + usuario.getUsuario());
        new MainFrame(contexto, usuario).setVisible(true);
        /**
         * Ejecuta la operación dispose.
         */
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel lblAyuda;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblProyecto;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlMarca;
    private javax.swing.JPanel pnlRaiz;
    private javax.swing.JPanel pnlTarjeta;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
