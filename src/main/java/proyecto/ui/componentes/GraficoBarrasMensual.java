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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Componente Swing personalizado que dibuja un gráfico de barras con valores mensuales.
 */
public class GraficoBarrasMensual extends JPanel {
    private double[] valores = new double[12];
    private String titulo = "Producción mensual de oro fino";
    private static final String[] MESES = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     */
    public GraficoBarrasMensual() {
        /**
         * Actualiza background.
         */
        setBackground(Color.WHITE);
    }

    /**
     * Actualiza los valores que serán representados por el componente gráfico.
     *
     * @param valores valores que se mostrarán
     * @param titulo título mostrado al usuario
     */
    public void setDatos(double[] valores, String titulo) {
        this.valores = valores == null ? new double[12] : valores.clone();
        this.titulo = titulo;
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
        int w = getWidth();
        int h = getHeight();
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.SUBTITULO);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(titulo, Math.max(12, (w - fm.stringWidth(titulo)) / 2), 25);

        int left = 45, right = 20, top = 45, bottom = 45;
        int chartW = Math.max(1, w - left - right);
        int chartH = Math.max(1, h - top - bottom);
        double max = 1;
        for (double v : valores) max = Math.max(max, v);

        g2.setColor(new Color(225, 229, 238));
        for (int i = 0; i <= 4; i++) {
            int y = top + i * chartH / 4;
            g2.drawLine(left, y, left + chartW, y);
        }

        int gap = 8;
        int barW = Math.max(6, chartW / 12 - gap);
        g2.setFont(Tema.NORMAL);
        for (int i = 0; i < 12; i++) {
            int x = left + i * chartW / 12 + gap / 2;
            int bh = (int) Math.round((valores[i] / max) * (chartH - 10));
            int y = top + chartH - bh;
            g2.setColor(Tema.AZUL);
            g2.fillRoundRect(x, y, barW, bh, 6, 6);
            g2.setColor(Tema.TEXTO);
            g2.drawString(MESES[i], x, top + chartH + 18);
        }
        g2.dispose();
    }
}
