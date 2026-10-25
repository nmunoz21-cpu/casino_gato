
package Vista;

import Controlador.EstadisticasController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

/**
 * Ventana que muestra las estadisticas
 * individuales del usuario.
 */
public class VentanaEstadisticas {

    // Controlador de estadisticas
    private final EstadisticasController estadisticasController;

    // Accion para regresar al menu
    private final Runnable alVolver;

    // Ventana principal
    private final JFrame frame =
            new JFrame("ESTADISTICAS - Casino Black Cat");

    // Etiquetas de los indicadores
    private JLabel lblTotalJugadas;
    private JLabel lblVictorias;
    private JLabel lblPorcentaje;
    private JLabel lblRachaMaxima;
    private JLabel lblTipoMasJugado;

    /**
     * Constructor de la ventana.
     */
    public VentanaEstadisticas(
            EstadisticasController estadisticasController,
            Runnable alVolver) {

        if (estadisticasController == null) {
            throw new IllegalArgumentException(
                    "El controlador es obligatorio"
            );
        }

        this.estadisticasController =
                estadisticasController;

        this.alVolver = alVolver;

        configurarVentana();
        construirInterfaz();
    }

    /**
     * Configura las propiedades de la ventana.
     */
    private void configurarVentana() {

        frame.setSize(480, 350);

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                if (alVolver != null) {
                    alVolver.run();
                }
            }
        });
    }

    /**
     * Construye los componentes graficos.
     */
    private void construirInterfaz() {

        JLabel titulo = new JLabel(
                "Estadísticas del jugador",
                SwingConstants.CENTER
        );

        titulo.setFont(
                titulo.getFont().deriveFont(
                        Font.BOLD, 18f
                )
        );

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 12, 12)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
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

        panel.add(new JLabel(
                "Tipo de apuesta más jugado:"
        ));
        panel.add(lblTipoMasJugado);

        // Boton para regresar al menu
        JButton btnVolver = new JButton("Volver");

        btnVolver.addActionListener(
                e -> frame.dispose()
        );

        JPanel pie = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        pie.add(btnVolver);

        // Distribucion de componentes
        frame.setLayout(new BorderLayout());

        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(pie, BorderLayout.SOUTH);
    }

    /**
     * Solicita las estadisticas al controlador
     * y actualiza los indicadores.
     */
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
                        estadisticasController
                                .obtenerPorcentajeVictorias()
                )
        );

        lblRachaMaxima.setText(
                String.valueOf(
                        estadisticasController.obtenerRachaMaxima()
                )
        );

        // Obtiene el tipo de apuesta mas frecuente
        String tipo =
                estadisticasController.obtenerTipoMasJugado();

        lblTipoMasJugado.setText(
                tipo == null ? "Sin jugadas" : tipo
        );
    }

    /**
     * Actualiza los indicadores y muestra
     * la ventana centrada en pantalla.
     */
    public void mostrarVentana() {

        actualizarEstadisticas();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
