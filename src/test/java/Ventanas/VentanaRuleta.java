package Ventanas;

import Logica.ResultadoRonda;
import Logica.Ruleta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaRuleta {

    private final String nombreUsuario;
    private final Ruleta ruleta;
    private final Runnable alVolver; // qué hacer al cerrar (normalmente reabrir el menú)

    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private JComboBox<String> comboTipoApuesta;
    private JComboBox<String> comboColor;
    private JComboBox<String> comboParidad;
    private JSpinner spinnerMonto;
    private JLabel lblSaldo;
    private JLabel lblResultado;

    /**
     * @param ruleta   la misma instancia del menú, para conservar saldo e historial
     * @param alVolver acción al cerrar la ventana (puede ser null)
     */
    public VentanaRuleta(String nombreUsuario, Ruleta ruleta, Runnable alVolver) {
        this.nombreUsuario = nombreUsuario;
        this.ruleta = ruleta;
        this.alVolver = alVolver;
        configurarVentana();
        construirInterfaz();
        actualizarSelectores();
    }

    private void configurarVentana() {
        frame.setSize(500, 320);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Tanto el botón Volver como la X terminan aquí
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (alVolver != null) {
                    alVolver.run();
                }
            }
        });
    }

    private void construirInterfaz() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        comboTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad"});
        comboColor = new JComboBox<>(new String[]{"Rojo", "Negro"});
        comboParidad = new JComboBox<>(new String[]{"Par", "Impar"});

        spinnerMonto = new JSpinner(new SpinnerNumberModel(100, 1, 100000, 10));
        spinnerMonto.setEditor(new JSpinner.NumberEditor(spinnerMonto, "#")); // sin separador de miles

        JButton btnGirar = new JButton("Girar");
        lblSaldo = new JLabel("Saldo: $" + ruleta.getSaldo());
        lblResultado = new JLabel(" ");

        comboTipoApuesta.addActionListener(e -> actualizarSelectores());
        btnGirar.addActionListener(e -> jugarRonda());

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

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> frame.dispose());

        JPanel sur = new JPanel(new BorderLayout(5, 5));
        sur.add(lblResultado, BorderLayout.CENTER);
        sur.add(btnVolver, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(sur, BorderLayout.SOUTH);
    }

    /** Solo se puede elegir el selector que corresponde al tipo de apuesta. */
    private void actualizarSelectores() {
        boolean esColor = "Color".equals(comboTipoApuesta.getSelectedItem());
        comboColor.setEnabled(esColor);
        comboParidad.setEnabled(!esColor);
    }

    private void jugarRonda() {
        char tipo = obtenerTipoApuestaSeleccionado();
        int monto = (int) spinnerMonto.getValue();
        try {
            ResultadoRonda resultado = ruleta.jugar(monto, tipo);
            actualizarInterfaz(resultado);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    "Apuesta inválida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private char obtenerTipoApuestaSeleccionado() {
        String tipoApuesta = (String) comboTipoApuesta.getSelectedItem();
        if ("Color".equals(tipoApuesta)) {
            String color = (String) comboColor.getSelectedItem();
            return "Rojo".equals(color) ? 'R' : 'N';
        } else {
            String paridad = (String) comboParidad.getSelectedItem();
            return "Par".equals(paridad) ? 'P' : 'I';
        }
    }

    private void actualizarInterfaz(ResultadoRonda resultado) {
        String estado = resultado.isAcierto() ? "GANASTE" : "PERDISTE";

        lblResultado.setText(String.format(
                "Numero %d (%s) | Apuesta=%c | Monto=$%d | %s",
                resultado.getNumero(), resultado.getColor(), resultado.getTipoApuesta(),
                resultado.getMonto(), estado
        ));

        lblSaldo.setText("Saldo: $" + resultado.getSaldoActual());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}