package Vista;

import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPerfil {

    private final SessionController session;
    private final RuletaController ruletaController;
    private final Runnable alVolver; // qué hacer al cerrar (reabrir el menú)

    private final JFrame frame = new JFrame("PERFIL - Casino Black Cat");
    private JLabel lblNombreActual;
    private JLabel lblSaldo;
    private JTextField txtNombre;
    private JSpinner spinnerMonto;

    public VentanaPerfil(SessionController session, RuletaController ruletaController,
                         Runnable alVolver) {
        this.session = session;
        this.ruletaController = ruletaController;
        this.alVolver = alVolver;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        frame.setSize(450, 360);
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
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Los datos se piden a los controladores, nunca al Modelo directamente
        lblNombreActual = new JLabel(session.getNombreUsuario());
        lblSaldo = new JLabel("$" + ruletaController.getSaldo());
        txtNombre = new JTextField();

        spinnerMonto = new JSpinner(new SpinnerNumberModel(100, 1, 100000, 10));
        spinnerMonto.setEditor(new JSpinner.NumberEditor(spinnerMonto, "#")); // sin separador de miles

        JButton btnGuardar = new JButton("Guardar nombre");
        JButton btnRecargar = new JButton("Recargar");
        JButton btnVolver = new JButton("Volver");

        btnGuardar.addActionListener(e -> guardarNombre());
        btnRecargar.addActionListener(e -> recargarSaldo());
        btnVolver.addActionListener(e -> frame.dispose());

        panel.add(new JLabel("Usuario:"));
        panel.add(new JLabel(session.getUsername())); // el username no se puede modificar
        panel.add(new JLabel("Nombre actual:"));
        panel.add(lblNombreActual);
        panel.add(new JLabel("Saldo:"));
        panel.add(lblSaldo);
        panel.add(new JLabel("Nuevo nombre:"));
        panel.add(txtNombre);
        panel.add(btnGuardar);
        panel.add(new JLabel(" "));
        panel.add(new JLabel("Monto a recargar:"));
        panel.add(spinnerMonto);
        panel.add(btnRecargar);
        panel.add(btnVolver);

        frame.add(panel);
    }

    // La regla (nombre no vacío) vive en Usuario.setNombre; aquí solo se muestra el error
    private void guardarNombre() {
        try {
            session.cambiarNombre(txtNombre.getText());
            lblNombreActual.setText(session.getNombreUsuario());
            txtNombre.setText("");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    "Nombre inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    // La regla (monto mayor a 0) vive en Ruleta.depositar
    private void recargarSaldo() {
        int monto = (int) spinnerMonto.getValue();
        try {
            ruletaController.depositar(monto);
            lblSaldo.setText("$" + ruletaController.getSaldo());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    "Monto inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}