
package Vista;

import Controlador.EstadisticasController;
import Modelo.TipoApuesta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class VentanaEstadisticas {

    // Controlador encargado de consultar las estadísticas
    private final EstadisticasController estadisticasController;

    // Acción que permite regresar al menú principal
    private final Runnable alVolver;

    // Ventana principal
    private final JFrame frame =
            new JFrame("ESTADISTICAS - Casino Black Cat");

    // Etiquetas que muestran los indicadores
    private JLabel lblTotalJugadas;
    private JLabel lblVictorias;
    private JLabel lblPorcentaje;
    private JLabel lblRachaMaxima;
    private JLabel lblTipoMasJugado;

    // Constructor de la ventana
    public VentanaEstadisticas(
            EstadisticasController estadisticasController,
            Runnable alVolver) {

        if (estadisticasController == null) {
            throw new IllegalArgumentException(
                    "El controlador es obligatorio"
            );
        }

        this.estadisticasController = estadisticasController;
        this.alVolver = alVolver;

        configurarVentana();
        construirInterfaz();
    }

    // Configura el tamaño y el cierre de la ventana
    private void configurarVentana() {

        frame.setSize(480, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Al cerrar la ventana, regresa al menú
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (alVolver != null) {
                    alVolver.run();
                }
            }
        });
    }

    // Construye los componentes gráficos
    private void construirInterfaz() {

        JLabel titulo = new JLabel(
                "Estadísticas del jugador",
                SwingConstants.CENTER
        );

        titulo.setFont(
                titulo.getFont().deriveFont(Font.BOLD, 18f)
        );

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 12, 12)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // Inicializa las etiquetas
        lblTotalJugadas = new JLabel();
        lblVictorias = new JLabel();
        lblPorcentaje = new JLabel();
        lblRachaMaxima = new JLabel();
        lblTipoMasJugado = new JLabel();

        // Agrega los indicadores
        panel.add(new JLabel("Total de jugadas:"));
        panel.add(lblTotalJugadas);

        panel.add(new JLabel("Victorias:"));
        panel.add(lblVictorias);

        panel.add(new JLabel("Porcentaje de victorias:"));
        panel.add(lblPorcentaje);

        panel.add(new JLabel("Racha máxima:"));
        panel.add(lblRachaMaxima);

        panel.add(new JLabel("Tipo de apuesta más jugado:"));
        panel.add(lblTipoMasJugado);

        // Botón para regresar al menú
        JButton btnVolver = new JButton("Volver");

        btnVolver.addActionListener(e -> frame.dispose());

        JPanel pie = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        pie.add(btnVolver);

        // Distribuye los componentes
        frame.setLayout(new BorderLayout());

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(pie, BorderLayout.SOUTH);
    }

    // Actualiza los indicadores utilizando el controlador
    private void actualizarEstadisticas() {

        lblTotalJugadas.setText(
                String.valueOf(
                        estadisticasController.obtenerTotalJugadas()
                )
        );

        lblVictorias.setText(
                String.valueOf(
                        estadisticasController.obtenerVictorias()
                )
        );

        lblPorcentaje.setText(
                String.format(
                        Locale.forLanguageTag("es-CL"),
                        "%.2f %%",
                        estadisticasController.obtenerPorcentajeVictorias()
                )
        );

        lblRachaMaxima.setText(
                String.valueOf(
                        estadisticasController.obtenerRachaMaxima()
                )
        );

        TipoApuesta tipo =
                estadisticasController.obtenerTipoMasJugado();

        lblTipoMasJugado.setText(
                tipo == null ? "Sin jugadas" : tipo.name()
        );
    }

    // Muestra la ventana centrada en pantalla
    public void mostrarVentana() {

        actualizarEstadisticas();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
