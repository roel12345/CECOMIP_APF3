/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import proyecto.servicio.AppContext;
import proyecto.ui.LoginFrame;

/**
 * Punto de entrada de la aplicación CECOMIP LTDA. Configura la apariencia de Swing, crea el contexto general del sistema y muestra el formulario de autenticación.
 */
public class Main {
    /**
     * Inicia la aplicación en el hilo de eventos de Swing.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> {
            try {
                AppContext contexto = new AppContext();
                new LoginFrame(contexto).setVisible(true);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null,
                    "No se pudo iniciar el sistema con SQL Server:\n" + e.getMessage()
                    + "\n\nConsulte GUIA_SQL_SERVER.md", "Error de conexión o configuración", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
