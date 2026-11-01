/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.ui.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Componente visual reutilizable para mostrar un indicador destacado en el panel principal.
 */
public class TarjetaResumen extends JPanel {
    private final JLabel valor = new JLabel("0");

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param titulo título mostrado al usuario
     * @param acento color de acento de la tarjeta
     */
    public TarjetaResumen(String titulo, Color acento) {
        setLayout(new BorderLayout(8, 8));
        /**
         * Actualiza background.
         */
        setBackground(Color.WHITE);
        setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, acento),
                new EmptyBorder(14, 16, 14, 16)));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Tema.NORMAL);
        lblTitulo.setForeground(new Color(100, 108, 124));
        valor.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 24));
        valor.setForeground(Tema.TEXTO);
        /**
         * Ejecuta la operación add.
         */
        add(lblTitulo, BorderLayout.NORTH);
        /**
         * Ejecuta la operación add.
         */
        add(valor, BorderLayout.CENTER);
    }

    /**
     * Actualiza el texto principal mostrado por la tarjeta.
     *
     * @param texto texto que se desea mostrar
     */
    public void setValor(String texto) { valor.setText(texto); }
}
