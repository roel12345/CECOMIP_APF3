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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Componente Swing personalizado que representa la relación entre oro producido, vendido y disponible.
 */
public class GraficoCircularOro extends JPanel {
    private double producido;
    private double vendido;

    /**
     * Inicializa una nueva instancia de grafico circular oro.
     */
    public GraficoCircularOro() { setBackground(Color.WHITE); }

    /**
     * Actualiza los valores que serán representados por el componente gráfico.
     *
     * @param producido cantidad de oro fino producido
     * @param vendido cantidad de oro fino vendido
     */
    public void setDatos(double producido, double vendido) {
        this.producido = Math.max(0, producido);
        this.vendido = Math.max(0, vendido);
        /**
         * Ejecuta la operación repaint.
         */
        repaint();
    }

    @Override
    /**
     * Dibuja el contenido personalizado del componente.
     *
     * @param g valor de g
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.SUBTITULO);
        String titulo = "Oro fino producido y vendido";
        int tx = Math.max(10, (getWidth() - g2.getFontMetrics().stringWidth(titulo)) / 2);
        g2.drawString(titulo, tx, 25);
        int size = Math.min(getWidth() - 90, getHeight() - 90);
        size = Math.max(80, size);
        int x = (getWidth() - size) / 2;
        int y = 45;
        double total = Math.max(producido, 0.0001);
        int vendidoAng = (int) Math.round(Math.min(1, vendido / total) * 360);
        g2.setColor(new Color(223, 229, 238));
        g2.fillArc(x, y, size, size, 0, 360);
        g2.setColor(Tema.DORADO);
        g2.fillArc(x, y, size, size, 90, -vendidoAng);
        int inner = (int) (size * 0.58);
        int ix = x + (size - inner) / 2;
        int iy = y + (size - inner) / 2;
        g2.setColor(Color.WHITE);
        g2.fillOval(ix, iy, inner, inner);
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.NORMAL);
        String leyenda1 = String.format("Producido: %.2f g", producido);
        String leyenda2 = String.format("Vendido: %.2f g", vendido);
        g2.drawString(leyenda1, 20, getHeight() - 32);
        g2.drawString(leyenda2, 20, getHeight() - 12);
        g2.dispose();
    }
}
