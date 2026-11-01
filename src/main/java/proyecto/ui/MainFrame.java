/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import proyecto.modelo.Usuario;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.paneles.Actualizable;
import proyecto.ui.paneles.ClientePanel;
import proyecto.ui.paneles.DashboardPanel;
import proyecto.ui.paneles.EstructurasPanel;
import proyecto.ui.paneles.ProduccionPanel;
import proyecto.ui.paneles.ReportePanel;
import proyecto.ui.paneles.UsuarioPanel;
import proyecto.ui.paneles.VentaPanel;

/**
 * Ventana principal editable desde NetBeans GUI Builder.
 * Los módulos y la lógica del sistema se cargan después de initComponents().
 */
public class MainFrame extends JFrame {

    private final AppContext contexto;
    private final Usuario usuarioActual;

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     * @param usuarioActual usuario que inició sesión
     */
    public MainFrame(AppContext contexto, Usuario usuarioActual) {
        this.contexto = contexto;
        this.usuarioActual = usuarioActual;
        /**
         * Ejecuta la operación init components.
         */
        initComponents();
        /**
         * Ejecuta la operación configurar estilos.
         */
        configurarEstilos();
        /**
         * Ejecuta la operación cargar modulos.
         */
        cargarModulos();
        /**
         * Actualiza size.
         */
        setSize(1366, 768);
        setMinimumSize(new Dimension(1100, 680));
        /**
         * Actualiza location relative to.
         */
        setLocationRelativeTo(null);
    }

    /**
     * Aplica colores, fuentes, bordes y dimensiones a los controles del formulario.
     */
    private void configurarEstilos() {
        pnlSidebar.setPreferredSize(new Dimension(250, 0));
        pnlSidebar.setBackground(Tema.AZUL_OSCURO);
        pnlMarca.setOpaque(false);
        pnlMarca.setBorder(new EmptyBorder(20, 18, 18, 18));
        pnlMenu.setOpaque(false);
        pnlMenu.setBorder(new EmptyBorder(0, 10, 10, 10));

        lblLogo.setForeground(Tema.DORADO);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 23));
        lblProyecto.setForeground(Color.WHITE);
        lblProyecto.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblPanel.setForeground(new Color(190, 198, 224));
        lblPanel.setFont(new Font("SansSerif", Font.BOLD, 12));

        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnInicio);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnClientes);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnProduccion);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnVentas);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnReportes);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnEstructuras);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnUsuarios);
        /**
         * Ejecuta la operación configurar boton menu.
         */
        configurarBotonMenu(btnCerrarSesion);

        pnlBarraSuperior.setPreferredSize(new Dimension(0, 66));
        pnlBarraSuperior.setBackground(Color.WHITE);
        pnlBarraSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDE));
        lblSeccion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSeccion.setForeground(Tema.TEXTO);
        lblSeccion.setBorder(new EmptyBorder(0, 26, 0, 0));
        lblUsuarioActual.setText(usuarioActual.getNombreCompleto() + "  |  " + usuarioActual.getRol());
        lblUsuarioActual.setFont(Tema.NORMAL);
        lblUsuarioActual.setForeground(Tema.TEXTO);
        lblUsuarioActual.setBorder(new EmptyBorder(0, 0, 0, 24));
        pnlContenido.setBackground(Tema.FONDO);
    }

    /**
     * Configura la apariencia y el efecto visual de un botón del menú lateral.
     *
     * @param boton valor de boton
     */
    private void configurarBotonMenu(final JButton boton) {
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setUI(new BasicButtonUI());
        boton.setForeground(Color.WHITE);
        boton.setBackground(Tema.CELESTE);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);
        boton.setRolloverEnabled(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            /**
             * Ejecuta la operación mouse entered.
             *
             * @param e evento de entrada o salida del puntero
             */
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(Tema.CELESTE_HOVER);
            }

            @Override
            /**
             * Ejecuta la operación mouse exited.
             *
             * @param e evento de entrada o salida del puntero
             */
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(Tema.CELESTE);
            }
        });
    }

    /**
     * Crea y registra los paneles funcionales dentro del contenedor principal.
     */
    private void cargarModulos() {
        pnlContenido.add(new DashboardPanel(contexto), "INICIO");
        pnlContenido.add(new ClientePanel(contexto), "CLIENTES");
        pnlContenido.add(new ProduccionPanel(contexto), "PRODUCCION");
        pnlContenido.add(new VentaPanel(contexto), "VENTAS");
        pnlContenido.add(new ReportePanel(contexto), "REPORTES");
        pnlContenido.add(new EstructurasPanel(contexto), "ESTRUCTURAS");
        pnlContenido.add(new UsuarioPanel(contexto, usuarioActual), "USUARIOS");
        /**
         * Ejecuta la operación mostrar.
         *
         * @param PRINCIPAL" valor de principal"
         */
        mostrar("INICIO", "PANEL PRINCIPAL");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlSidebar = new javax.swing.JPanel();
        pnlMarca = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblProyecto = new javax.swing.JLabel();
        lblPanel = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btnInicio = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProduccion = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnEstructuras = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        pnlPrincipal = new javax.swing.JPanel();
        pnlBarraSuperior = new javax.swing.JPanel();
        lblSeccion = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        pnlContenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CECOMIP LTDA - Control de Producción de Oro | 03_EDPROYECTO_GRUPO_12");
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlSidebar.setLayout(new java.awt.BorderLayout());

        pnlMarca.setLayout(new java.awt.GridLayout(3, 1));
        lblLogo.setText("CECOMIP LTDA");
        pnlMarca.add(lblLogo);
        lblProyecto.setText("03_EDPROYECTO_GRUPO_12");
        pnlMarca.add(lblProyecto);
        lblPanel.setText("PANEL DE ADMINISTRACIÓN");
        pnlMarca.add(lblPanel);
        pnlSidebar.add(pnlMarca, java.awt.BorderLayout.NORTH);

        pnlMenu.setLayout(new java.awt.GridLayout(8, 1, 0, 5));

        btnInicio.setText("Inicio");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        pnlMenu.add(btnInicio);

        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        pnlMenu.add(btnClientes);

        btnProduccion.setText("Producción de oro");
        btnProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduccionActionPerformed(evt);
            }
        });
        pnlMenu.add(btnProduccion);

        btnVentas.setText("Ventas y facturas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        pnlMenu.add(btnVentas);

        btnReportes.setText("Reportes");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        pnlMenu.add(btnReportes);

        btnEstructuras.setText("Estructuras AED");
        btnEstructuras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstructurasActionPerformed(evt);
            }
        });
        pnlMenu.add(btnEstructuras);

        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        pnlMenu.add(btnUsuarios);

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        pnlMenu.add(btnCerrarSesion);

        pnlSidebar.add(pnlMenu, java.awt.BorderLayout.CENTER);
        getContentPane().add(pnlSidebar, java.awt.BorderLayout.WEST);

        pnlPrincipal.setLayout(new java.awt.BorderLayout());
        pnlBarraSuperior.setLayout(new java.awt.BorderLayout());

        lblSeccion.setText("PANEL PRINCIPAL");
        pnlBarraSuperior.add(lblSeccion, java.awt.BorderLayout.WEST);

        lblUsuarioActual.setText("USUARIO | ROL");
        pnlBarraSuperior.add(lblUsuarioActual, java.awt.BorderLayout.EAST);

        pnlPrincipal.add(pnlBarraSuperior, java.awt.BorderLayout.NORTH);

        pnlContenido.setLayout(new java.awt.CardLayout());
        pnlPrincipal.add(pnlContenido, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlPrincipal, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        /**
         * Ejecuta la operación mostrar.
         */
        mostrar("INICIO", "INICIO");
    }//GEN-LAST:event_btnInicioActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        /**
         * Ejecuta la operación mostrar.
         */
        mostrar("CLIENTES", "CLIENTES");
    }//GEN-LAST:event_btnClientesActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduccionActionPerformed
        /**
         * Ejecuta la operación mostrar.
         *
         * @param ORO" valor de oro"
         */
        mostrar("PRODUCCION", "PRODUCCIÓN DE ORO");
    }//GEN-LAST:event_btnProduccionActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        /**
         * Ejecuta la operación mostrar.
         *
         * @param FACTURAS" valor de facturas"
         */
        mostrar("VENTAS", "VENTAS Y FACTURAS");
    }//GEN-LAST:event_btnVentasActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        /**
         * Ejecuta la operación mostrar.
         */
        mostrar("REPORTES", "REPORTES");
    }//GEN-LAST:event_btnReportesActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnEstructurasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstructurasActionPerformed
        /**
         * Ejecuta la operación mostrar.
         *
         * @param AED" valor de aed"
         */
        mostrar("ESTRUCTURAS", "ESTRUCTURAS AED");
    }//GEN-LAST:event_btnEstructurasActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        /**
         * Ejecuta la operación mostrar.
         */
        mostrar("USUARIOS", "USUARIOS");
    }//GEN-LAST:event_btnUsuariosActionPerformed

    /**
     * Atiende el evento generado por el control y delega la operación correspondiente.
     *
     * @param evt evento generado por la interfaz
     */
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        new LoginFrame(contexto).setVisible(true);
        /**
         * Ejecuta la operación dispose.
         */
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    /**
     * Muestra el módulo solicitado y actualiza su información antes de visualizarlo.
     *
     * @param nombre nombre o identificador lógico
     * @param titulo título mostrado al usuario
     */
    private void mostrar(String nombre, String titulo) {
        lblSeccion.setText(titulo);
        // CardLayout permite cambiar de módulo sin crear nuevas ventanas.
        CardLayout layout = (CardLayout) pnlContenido.getLayout();
        layout.show(pnlContenido, nombre);
        for (Component componente : pnlContenido.getComponents()) {
            if (componente.isVisible() && componente instanceof Actualizable) {
                ((Actualizable) componente).actualizarDatos();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEstructuras;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnProduccion;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPanel;
    private javax.swing.JLabel lblProyecto;
    private javax.swing.JLabel lblSeccion;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JPanel pnlBarraSuperior;
    private javax.swing.JPanel pnlContenido;
    private javax.swing.JPanel pnlMarca;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlSidebar;
    // End of variables declaration//GEN-END:variables
}
