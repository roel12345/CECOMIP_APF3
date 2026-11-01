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
import java.awt.Font;

/**
 * Centraliza la paleta de colores, fuentes y constantes visuales de la interfaz.
 */
public final class Tema {
    public static final Color AZUL_OSCURO = new Color(24, 34, 73);
    public static final Color AZUL_MEDIO = new Color(43, 58, 110);
    public static final Color AZUL = new Color(63, 130, 191);

    // Colores principales solicitados para la interfaz.
    public static final Color CELESTE = new Color(41, 169, 225);
    public static final Color CELESTE_HOVER = new Color(20, 132, 190);
    public static final Color MORADO = new Color(116, 67, 181);
    public static final Color MORADO_HOVER = new Color(94, 50, 154);
    public static final Color NARANJA = new Color(235, 132, 38);
    public static final Color NARANJA_HOVER = new Color(207, 103, 15);

    public static final Color DORADO = new Color(211, 161, 39);
    public static final Color FONDO = new Color(245, 247, 251);
    public static final Color BORDE = new Color(220, 225, 234);
    public static final Color TEXTO = new Color(47, 55, 70);
    public static final Color VERDE = new Color(38, 154, 104);
    public static final Color ROJO = new Color(207, 66, 66);
    public static final Color ROJO_HOVER = new Color(177, 43, 43);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    public static final Font SUBTITULO = new Font("SansSerif", Font.BOLD, 16);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 13);

    /**
     * Inicializa una nueva instancia de tema.
     */
    private Tema() {}
}
