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
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import proyecto.modelo.Operacion;
import proyecto.modelo.ProduccionOro;
import proyecto.modelo.VentaOro;
import proyecto.servicio.AppContext;
import proyecto.ui.componentes.Tema;
import proyecto.ui.componentes.UiUtil;

/**
 * Panel educativo que muestra el contenido actual de las estructuras de datos utilizadas por la aplicación.
 */
public class EstructurasPanel extends JPanel implements Actualizable {
    private final AppContext contexto;
    private final JTextArea areaPila = area();
    private final JTextArea areaCola = area();
    private final JTextArea areaArbol = area();

    /**
     * Inicializa una nueva instancia de la clase y sus dependencias.
     *
     * @param contexto contexto general de la aplicación
     */
    public EstructurasPanel(AppContext contexto) {
        this.contexto = contexto;
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
     * Crea un área de texto de solo lectura para mostrar una estructura de datos.
     *
     * @return resultado de la operación
     */
    private static JTextArea area() {
        JTextArea a = new JTextArea();
        a.setEditable(false);
        a.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        return a;
    }

    /**
     * Construye y distribuye los componentes visuales del panel.
     */
    private void construir() {
        setLayout(new BorderLayout(0, 14));
        /**
         * Actualiza background.
         */
        setBackground(Tema.FONDO);
        setBorder(new EmptyBorder(18, 24, 22, 24));
        JLabel titulo = new JLabel("ESTRUCTURAS DE DATOS APLICADAS AL SISTEMA");
        titulo.setFont(Tema.TITULO);
        titulo.setForeground(Tema.AZUL_OSCURO);
        /**
         * Ejecuta la operación add.
         */
        add(titulo, BorderLayout.NORTH);

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 14, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(crearCard("PILA - Operaciones recientes (LIFO)", areaPila));
        tarjetas.add(crearCard("COLA - Lotes pendientes (FIFO)", areaCola));
        tarjetas.add(crearCard("ÁRBOL ABB - Ventas ordenadas por ID", areaArbol));
        /**
         * Ejecuta la operación add.
         */
        add(tarjetas, BorderLayout.CENTER);

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pie.setOpaque(false);
        JButton btnDesapilar = UiUtil.botonSecundario("Desapilar última operación");
        JButton btnProcesar = UiUtil.boton("Validar siguiente lote");
        JButton btnActualizar = UiUtil.botonSecundario("Actualizar vistas");
        pie.add(btnDesapilar); pie.add(btnProcesar); pie.add(btnActualizar);
        /**
         * Ejecuta la operación add.
         */
        add(pie, BorderLayout.SOUTH);

        btnDesapilar.addActionListener(e -> {
            Operacion op = contexto.getPilaOperaciones().desapilar();
            UiUtil.info(this, op == null ? "La pila está vacía." : "Operación retirada de la cima:\n" + op);
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        });
        btnProcesar.addActionListener(e -> {
            ProduccionOro p = contexto.validarSiguienteProduccion();
            UiUtil.info(this, p == null ? "La cola está vacía." : "Se atendió el lote N.° " + p.getId());
            /**
             * Recarga el contenido del panel con la información actual del sistema.
             */
            actualizarDatos();
        });
        btnActualizar.addActionListener(e -> actualizarDatos());
    }

    /**
     * Crea una tarjeta visual que contiene el título y el área de una estructura.
     *
     * @param titulo título mostrado al usuario
     * @param area valor de area
     * @return panel configurado
     */
    private JPanel crearCard(String titulo, JTextArea area) {
        JPanel card = UiUtil.tarjeta();
        card.setLayout(new BorderLayout(0, 10));
        JLabel l = new JLabel(titulo);
        l.setFont(Tema.SUBTITULO);
        l.setForeground(Tema.AZUL_OSCURO);
        card.add(l, BorderLayout.NORTH);
        card.add(new JScrollPane(area), BorderLayout.CENTER);
        return card;
    }

    @Override
    /**
     * Recarga tablas, indicadores o estructuras usando la información actual del contexto.
     */
    public void actualizarDatos() {
        StringBuilder pila = new StringBuilder();
        Operacion[] ops = contexto.getPilaOperaciones().toArray();
        if (ops.length == 0) pila.append("Pila vacía");
        else {
            for (int i = 0; i < ops.length; i++) pila.append(i == 0 ? "CIMA -> " : "         ").append(ops[i]).append('\n');
        }
        areaPila.setText(pila.toString());

        StringBuilder cola = new StringBuilder();
        ProduccionOro[] pendientes = contexto.getColaProduccion().toArray();
        if (pendientes.length == 0) cola.append("Cola vacía");
        else {
            for (int i = 0; i < pendientes.length; i++) {
                ProduccionOro p = pendientes[i];
                cola.append(i == 0 ? "FRENTE -> " : "           ")
                        .append("Lote ").append(p.getId()).append(" | ").append(p.getFecha())
                        .append(" | ").append(String.format("%.2f g", p.getOroFinoGramos())).append('\n');
            }
        }
        areaCola.setText(cola.toString());

        StringBuilder arbol = new StringBuilder();
        VentaOro[] ordenadas = contexto.getArbolVentas().inOrden(contexto.getVentas().getCantidad());
        if (ordenadas.length == 0) arbol.append("Árbol vacío");
        else {
            arbol.append("Recorrido INORDEN (menor a mayor):\n\n");
            for (VentaOro v : ordenadas) {
                if (v != null) arbol.append("ID ").append(v.getId()).append(" | ").append(v.getFecha()).append(" | ")
                        .append(v.getClienteNombre()).append(" | S/ ").append(String.format("%,.2f", v.getTotal())).append('\n');
            }
        }
        areaArbol.setText(arbol.toString());
    }
}
