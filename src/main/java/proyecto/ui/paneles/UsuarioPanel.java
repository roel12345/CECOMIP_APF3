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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import proyecto.modelo.Usuario;
import proyecto.servicio.AppContext;
import proyecto.ui.MainFrame;
import proyecto.ui.RegistroUsuarioDialog;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Módulo administrativo para consultar usuarios, cambiar su estado y eliminar cuentas.
 */
public class UsuarioPanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final Usuario usuarioActual;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre completo", "Usuario", "Rol", "Estado"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     * @param usuarioActual usuario que inició sesión
     */
    public UsuarioPanel(AppContext contexto, Usuario usuarioActual) {
        this.contexto = contexto;
        this.usuarioActual = usuarioActual;
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
        JLabel titulo = new JLabel("ADMINISTRACIÓN DE USUARIOS");
        titulo.setFont(Tema.TITULO);
        titulo.setForeground(Tema.AZUL_OSCURO);
        /**
         * Ejecuta la operación add.
         */
        add(titulo, BorderLayout.NORTH);

        JPanel card = UiUtil.tarjeta();
        card.setLayout(new BorderLayout(0, 12));
        JLabel aviso = new JLabel("Los administradores pueden crear, activar, desactivar y eliminar usuarios del sistema.");
        aviso.setFont(Tema.NORMAL);
        aviso.setForeground(Tema.TEXTO);
        card.add(aviso, BorderLayout.NORTH);
        card.add(UiUtil.scrollTabla(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        JButton btnNuevo = UiUtil.botonRegistrar("Nuevo usuario");
        JButton btnEstado = UiUtil.botonModificar("Activar / Desactivar");
        JButton btnEliminar = UiUtil.botonPeligro("Eliminar");
        botones.add(btnNuevo); botones.add(btnEstado); botones.add(btnEliminar);
        card.add(botones, BorderLayout.SOUTH);
        /**
         * Ejecuta la operación add.
         */
        add(card, BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> {
            if (!esAdministrador()) return;
            RegistroUsuarioDialog d = new RegistroUsuarioDialog(javax.swing.SwingUtilities.getWindowAncestor(this), contexto, true);
            d.setVisible(true);
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        });
        btnEstado.addActionListener(e -> cambiarEstado());
        btnEliminar.addActionListener(e -> eliminar());
    }

    /**
     * Indica si el usuario que inició sesión posee rol de administrador.
     *
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    private boolean esAdministrador() {
        if (!"ADMINISTRADOR".equals(usuarioActual.getRol())) {
            UiUtil.error(this, "Solo un administrador puede gestionar usuarios.");
            return false;
        }
        return true;
    }

    /**
     * Obtiene el usuario correspondiente a la fila seleccionada.
     *
     * @return usuario encontrado o null si no existe
     */
    private Usuario seleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            UiUtil.error(this, "Seleccione un usuario.");
            return null;
        }
        int id = ((Number) modelo.getValueAt(fila, 0)).intValue();
        return contexto.getUsuarios().buscarPorId(id);
    }

    /**
     * Activa o desactiva la cuenta seleccionada.
     */
    private void cambiarEstado() {
        if (!esAdministrador()) return;
        Usuario u = seleccionado();
        if (u == null) return;
        if (u.getId() == usuarioActual.getId()) {
            UiUtil.error(this, "No puede desactivar su propia cuenta durante la sesión.");
            return;
        }
        u.setActivo(!u.isActivo());
        contexto.registrarOperacion("Estado del usuario " + u.getUsuario() + " cambiado a " + (u.isActivo() ? "ACTIVO" : "INACTIVO"));
        contexto.guardarTodo();
        /**
         * Recarga el contenido del panel con la información actual del sistema.
         */
        actualizarDatos();
    }

    /**
     * Elimina la cuenta seleccionada, evitando borrar al usuario en sesión.
     */
    private void eliminar() {
        if (!esAdministrador()) return;
        Usuario u = seleccionado();
        if (u == null) return;
        if (u.getId() == usuarioActual.getId()) {
            UiUtil.error(this, "No puede eliminar el usuario con el que inició sesión.");
            return;
        }
        if (UiUtil.confirmar(this, "¿Eliminar al usuario " + u.getUsuario() + "?")) {
            contexto.getUsuarios().eliminar(u.getId());
            contexto.registrarOperacion("Usuario eliminado: " + u.getUsuario());
            contexto.guardarTodo();
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        }
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        modelo.setRowCount(0);
        for (Usuario u : contexto.getUsuarios().toArray()) {
            modelo.addRow(new Object[]{u.getId(), u.getNombreCompleto(), u.getUsuario(), u.getRol(), u.isActivo() ? "ACTIVO" : "INACTIVO"});
        }
    }
}
