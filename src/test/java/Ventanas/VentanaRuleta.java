package ventanas;

import Logica.Ruleta;
import Logica.ResultadoRonda;
import javax.swing.*;
import java.awt.*;

public class VentanaRuleta {

    private final String nombreUsuario;
    private final Ruleta ruleta;

    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private JComboBox<String> comboTipoApuesta;
    private JComboBox<String> comboColor;
    private JComboBox<String> comboParidad;
    private JSpinner spinnerMonto;
    private JLabel lblSaldo;
    private JLabel lblResultado;

    public VentanaRuleta(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.ruleta = new Ruleta();
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void construirInterfaz() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        comboTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad"});
        comboColor = new JComboBox<>(new String[]{"Rojo", "Negro"});
        comboParidad = new JComboBox<>(new String[]{"Par", "Impar"});
        spinnerMonto = new JSpinner(new SpinnerNumberModel(100, 1, 100000, 1));
        JButton btnGirar = new JButton("Girar");
        lblSaldo = new JLabel("Saldo: " + ruleta.getSaldo());
        lblResultado = new JLabel(" ");

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

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(lblResultado, BorderLayout.SOUTH);
    }

    private void jugarRonda() {
        char tipo = obtenerTipoApuestaSeleccionado();
        int monto = (int) spinnerMonto.getValue();
        ResultadoRonda resultado = ruleta.jugar(monto, tipo);
        actualizarInterfaz(resultado);
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
        String color = resultado.isRojo() ? "Rojo" : "Negro";
        String estado = resultado.isAcierto() ? "GANASTE" : "PERDISTE";

        lblResultado.setText(String.format(
                "Numero %d (%s) | Apuesta=%c | Monto=$%d | %s",
                resultado.getNumero(), color, resultado.getTipoApuesta(),
                resultado.getMonto(), estado
        ));

        lblSaldo.setText("Saldo: " + resultado.getSaldoActual());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}