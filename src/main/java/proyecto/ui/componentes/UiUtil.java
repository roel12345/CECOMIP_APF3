/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Agrupa fábricas y utilidades reutilizables para crear controles Swing con una apariencia uniforme.
 */
public final class UiUtil {
    /**
     * Inicializa una nueva instancia de ui util.
     */
    private UiUtil() {}

    /**
     * Construye un botón con colores, borde y comportamiento visual configurables.
     *
     * @param texto texto que se desea procesar
     * @param fondo color de fondo normal
     * @param hover color mostrado al pasar el puntero
     * @return botón configurado
     */
    private static JButton crearBoton(String texto, Color fondo, Color hover) {
        JButton b = new JButton(texto);
        b.setFont(Tema.NORMAL.deriveFont(java.awt.Font.BOLD));
        // Fuerza el pintado del fondo en cualquier Look & Feel de NetBeans/Java.
        b.setUI(new BasicButtonUI());
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setRolloverEnabled(false);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override
            /**
             * Ejecuta la operación mouse entered.
             *
             * @param e evento de entrada o salida del puntero
             */
            public void mouseEntered(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(hover);
            }

            @Override
            /**
             * Ejecuta la operación mouse exited.
             *
             * @param e evento de entrada o salida del puntero
             */
            public void mouseExited(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(fondo);
            }
        });
        return b;
    }

    /** Botón general o secundario de color celeste. */
    public static JButton boton(String texto) {
        /**
         * Ejecuta la operación crear boton.
         *
         * @return resultado de la operación
         */
        return crearBoton(texto, Tema.CELESTE, Tema.CELESTE_HOVER);
    }

    /** Botón secundario de color celeste. */
    public static JButton botonSecundario(String texto) {
        /**
         * Ejecuta la operación crear boton.
         *
         * @return resultado de la operación
         */
        return crearBoton(texto, Tema.CELESTE, Tema.CELESTE_HOVER);
    }

    /** Botón para registrar o guardar un nuevo registro. */
    public static JButton botonRegistrar(String texto) {
        /**
         * Ejecuta la operación crear boton.
         *
         * @return resultado de la operación
         */
        return crearBoton(texto, Tema.MORADO, Tema.MORADO_HOVER);
    }

    /** Botón para modificar o cambiar información existente. */
    public static JButton botonModificar(String texto) {
        /**
         * Ejecuta la operación crear boton.
         *
         * @return resultado de la operación
         */
        return crearBoton(texto, Tema.NARANJA, Tema.NARANJA_HOVER);
    }

    /** Botón para eliminar información. */
    public static JButton botonPeligro(String texto) {
        /**
         * Ejecuta la operación crear boton.
         *
         * @return resultado de la operación
         */
        return crearBoton(texto, Tema.ROJO, Tema.ROJO_HOVER);
    }

    /**
     * Crea un campo de texto con el estilo general de la aplicación.
     *
     * @param columnas cantidad de columnas visibles del campo
     * @return campo de texto configurado
     */
    public static JTextField campo(int columnas) {
        JTextField c = new JTextField(columnas);
        c.setFont(Tema.NORMAL);
        c.setPreferredSize(new Dimension(c.getPreferredSize().width, 34));
        return c;
    }

    /**
     * Crea una lista desplegable con los valores indicados.
     *
     * @param valores valores que se mostrarán
     * @return lista desplegable configurada
     */
    public static JComboBox<String> combo(String... valores) {
        JComboBox<String> c = new JComboBox<>(valores);
        c.setFont(Tema.NORMAL);
        c.setPreferredSize(new Dimension(170, 34));
        return c;
    }

    /**
     * Crea una etiqueta con el estilo general de la aplicación.
     *
     * @param texto texto que se desea procesar
     * @return etiqueta configurada
     */
    public static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.NORMAL);
        l.setForeground(Tema.TEXTO);
        return l;
    }

    /**
     * Crea un panel blanco con borde y separación interna.
     *
     * @return panel configurado
     */
    public static JPanel tarjeta() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE),
                new EmptyBorder(16, 16, 16, 16)));
        return p;
    }

    /**
     * Configura una tabla y la devuelve dentro de un panel desplazable.
     *
     * @param tabla valor de tabla
     * @return panel desplazable que contiene la tabla
     */
    public static JScrollPane scrollTabla(JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setFont(Tema.NORMAL);
        tabla.setGridColor(Tema.BORDE);
        tabla.setSelectionBackground(new Color(217, 231, 246));
        tabla.setSelectionForeground(Tema.TEXTO);
        tabla.setFillsViewportHeight(true);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setReorderingAllowed(false);
        encabezado.setBackground(Tema.CELESTE);
        encabezado.setForeground(Color.WHITE);
        encabezado.setOpaque(true);
        encabezado.setPreferredSize(new Dimension(encabezado.getPreferredSize().width, 36));

        /*
         * Se crea un renderer independiente del Look & Feel de Windows.
         * No se reutiliza el renderer del sistema porque algunos estilos vuelven
         * a pintar el fondo de blanco y hacen invisibles los títulos de las columnas.
         */
        DefaultTableCellRenderer rendererEncabezado = new DefaultTableCellRenderer() {
            @Override
            /**
             * Configura colores, alineación y borde para la celda que será mostrada.
             *
             * @param table tabla que solicita el renderizado
             * @param value valor de value
             * @param isSelected indica si la celda está seleccionada
             * @param hasFocus indica si la celda posee el foco
             * @param row fila de la celda
             * @param column columna de la celda
             * @return componente preparado para renderizar la celda
             */
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel etiqueta = (JLabel) super.getTableCellRendererComponent(
                        table, value, false, false, row, column);
                etiqueta.setText(value == null ? "" : value.toString());
                etiqueta.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));
                etiqueta.setHorizontalAlignment(JLabel.CENTER);
                etiqueta.setVerticalAlignment(JLabel.CENTER);
                etiqueta.setForeground(Color.WHITE);
                etiqueta.setBackground(Tema.CELESTE);
                etiqueta.setOpaque(true);
                etiqueta.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
                return etiqueta;
            }
        };
        encabezado.setDefaultRenderer(rendererEncabezado);

        DefaultTableCellRenderer rendererCeldas = new DefaultTableCellRenderer();
        rendererCeldas.setHorizontalAlignment(JLabel.CENTER);
        tabla.setDefaultRenderer(Object.class, rendererCeldas);

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBorder(BorderFactory.createLineBorder(Tema.BORDE));
        return sp;
    }

    /**
     * Muestra un cuadro de diálogo de error.
     *
     * @param padre ventana o componente padre
     * @param mensaje mensaje que se mostrará
     */
    public static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo informativo.
     *
     * @param padre ventana o componente padre
     * @param mensaje mensaje que se mostrará
     */
    public static void info(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Solicita confirmación antes de ejecutar una operación.
     *
     * @param padre ventana o componente padre
     * @param mensaje mensaje que se mostrará
     * @return verdadero si la operación se realizó correctamente; falso en caso contrario
     */
    public static boolean confirmar(Component padre, String mensaje) {
        return JOptionPane.showConfirmDialog(padre, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Crea un borde vacío con los márgenes especificados.
     *
     * @param top margen superior
     * @param left margen izquierdo
     * @param bottom margen inferior
     * @param right margen derecho
     * @return borde vacío con los márgenes solicitados
     */
    public static EmptyBorder margen(int top, int left, int bottom, int right) {
        return new EmptyBorder(new Insets(top, left, bottom, right));
    }
}
