
package Vista;

import Controlador.RuletaController;
import Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Ventana grafica del juego de la ruleta.
 *
 * Permite seleccionar un tipo de apuesta,
 * definir un monto y ejecutar una ronda.
 */
public class VentanaRuleta {

    // Relaciona cada opcion con su clase de apuesta.
    // Evita utilizar un switch para crear los objetos.
    private static final Map<String, IntFunction<ApuestaBase>>
            FABRICAS = Map.of(
            "ROJO", ApuestaRojo::new,
            "NEGRO", ApuestaNegro::new,
            "PAR", ApuestaPar::new,
            "IMPAR", ApuestaImpar::new
    );

    // Datos y controlador de la ventana
    private final String nombreUsuario;
    private final RuletaController controlador;
    private final Runnable alVolver;

    // Ventana principal
    private final JFrame frame =
            new JFrame("RULETA - Casino Black Cat");

    // Componentes graficos
    private JComboBox<String> comboTipoApuesta;
    private JComboBox<String> comboColor;
    private JComboBox<String> comboParidad;

    private JSpinner spinnerMonto;

    private JLabel lblSaldo;
    private JLabel lblResultado;

    /**
     * Constructor de la ventana.
     */
    public VentanaRuleta(String nombreUsuario,
                         RuletaController controlador,
                         Runnable alVolver) {

        this.nombreUsuario = nombreUsuario;
        this.controlador = controlador;
        this.alVolver = alVolver;

        configurarVentana();
        construirInterfaz();
        actualizarSelectores();
    }

    /**
     * Configura las propiedades de la ventana.
     */
    private void configurarVentana() {

        frame.setSize(530, 325);

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Regresa al menu al cerrar la ventana
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
     * Construye los componentes Swing.
     */
    private void construirInterfaz() {

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 5, 5)
        );

        // Selector del grupo de apuesta
        comboTipoApuesta = new JComboBox<>(
                new String[]{"Color", "Paridad"}
        );

        // Selector de color
        comboColor = new JComboBox<>(
                new String[]{"ROJO", "NEGRO"}
        );

        // Selector de paridad
        comboParidad = new JComboBox<>(
                new String[]{"PAR", "IMPAR"}
        );

        // Selector del monto
        spinnerMonto = new JSpinner(
                new SpinnerNumberModel(
                        100, 1, 100000, 10
                )
        );

        spinnerMonto.setEditor(
                new JSpinner.NumberEditor(
                        spinnerMonto, "#"
                )
        );

        // Boton para jugar
        JButton btnGirar = new JButton("Girar");

        // Etiquetas de informacion
        lblSaldo = new JLabel(
                "Saldo: $" + controlador.getSaldo()
        );

        lblResultado = new JLabel(" ");

        // Eventos de los componentes
        comboTipoApuesta.addActionListener(
                e -> actualizarSelectores()
        );

        btnGirar.addActionListener(
                e -> jugarRonda()
        );

        // Agrega componentes al panel
        panel.add(new JLabel("Tipo de apuesta:"));
        panel.add(comboTipoApuesta);

        panel.add(new JLabel("Seleccione color:"));
        panel.add(comboColor);

        panel.add(new JLabel("Seleccione paridad:"));
        panel.add(comboParidad);

        panel.add(new JLabel("Monto:"));
        panel.add(spinnerMonto);

        panel.add(btnGirar);
        panel.add(lblSaldo);

        // Boton para volver al menu
        JButton btnVolver = new JButton("Volver");

        btnVolver.addActionListener(
                e -> frame.dispose()
        );

        JPanel sur = new JPanel(
                new BorderLayout(5, 5)
        );

        sur.add(lblResultado, BorderLayout.CENTER);
        sur.add(btnVolver, BorderLayout.EAST);

        // Distribucion de la ventana
        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(sur, BorderLayout.SOUTH);
    }

    /**
     * Habilita el selector correspondiente
     * al tipo de apuesta elegido.
     */
    private void actualizarSelectores() {

        boolean esColor = "Color".equals(
                comboTipoApuesta.getSelectedItem()
        );

        comboColor.setEnabled(esColor);
        comboParidad.setEnabled(!esColor);
    }

    /**
     * Ejecuta una ronda de ruleta.
     */
    private void jugarRonda() {

        try {

            // Obtiene el monto de la interfaz
            int monto = (int) spinnerMonto.getValue();

            // Construye la apuesta seleccionada
            ApuestaBase apuesta =
                    obtenerApuestaSeleccionada(monto);

            // Solicita al controlador ejecutar la ronda
            Resultado resultado =
                    controlador.realizarApuesta(apuesta);

            // Actualiza la informacion mostrada
            actualizarInterfaz(resultado);

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "Apuesta invalida",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Crea una apuesta concreta utilizando
     * el tipo seleccionado por el usuario.
     */
    private ApuestaBase obtenerApuestaSeleccionada(int monto) {

        boolean esColor = "Color".equals(
                comboTipoApuesta.getSelectedItem()
        );

        String etiqueta = esColor
                ? (String) comboColor.getSelectedItem()
                : (String) comboParidad.getSelectedItem();

        // Busca el constructor correspondiente
        IntFunction<ApuestaBase> fabricar =
                FABRICAS.get(etiqueta);

        if (fabricar == null) {
            throw new IllegalArgumentException(
                    "Selecciona un tipo de apuesta valido"
            );
        }

        // Crea el objeto de apuesta
        return fabricar.apply(monto);
    }

    /**
     * Muestra el resultado de la ronda
     * y el nuevo saldo del usuario.
     */
    private void actualizarInterfaz(Resultado resultado) {

        String estado = resultado.isAcierto()
                ? "GANASTE"
                : "PERDISTE";

        lblResultado.setText(
                String.format(
                        "Numero %d (%s) | Apuesta=%s | Monto=$%d | %s",
                        resultado.getNumero(),
                        resultado.getColor(),
                        resultado.getApuesta().getEtiqueta(),
                        resultado.getMonto(),
                        estado
                )
        );

        lblSaldo.setText(
                "Saldo: $" + resultado.getSaldoActual()
        );
    }

    /**
     * Muestra la ventana centrada en pantalla.
     */
    public void mostrarVentana() {

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
