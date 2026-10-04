package Vista;

import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    // Los controladores se crean en el Launcher y se comparten entre ventanas
    private final SessionController session;
    private final RuletaController controlador;
    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private JLabel lblSaldo;

    public VentanaMenu(SessionController session, RuletaController controlador) {
        this.session = session;
        this.controlador = controlador;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void construirInterfaz() {
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));

        JButton btnJugar = new JButton("Jugar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnSalir = new JButton("Salir");
        lblSaldo = new JLabel();
        actualizarSaldo();

        btnJugar.addActionListener(e -> abrirVentanaRuleta());
        btnHistorial.addActionListener(e -> abrirVentanaHistorial());
        btnSalir.addActionListener(e -> cerrarSesion());

        panelBotones.add(new JLabel(session.getNombreUsuario()));
        panelBotones.add(lblSaldo);
        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnSalir);

        JTextArea areaInfo = new JTextArea(
                "Bienvenido/a al menu principal, " + session.getNombreUsuario() + ".\n" +
                        "A la izquierda tienes:\n" +
                        "- Jugar: abre la ventana de juego.\n" +
                        "- Historial: muestra las rondas jugadas.\n" +
                        "- Salir: cierra sesion y vuelve al login."
        );
        areaInfo.setEditable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panelBotones, BorderLayout.WEST);
        frame.add(areaInfo, BorderLayout.CENTER);
    }

    // Consulta siempre el saldo mediante el getter del controlador
    private void actualizarSaldo() {
        lblSaldo.setText("Saldo: $" + controlador.getSaldo());
    }

    private void abrirVentanaRuleta() {
        frame.setVisible(false); // el menú se oculta mientras se juega
        VentanaRuleta ventanaRuleta = new VentanaRuleta(
                session.getNombreUsuario(), controlador, this::mostrarVentana);
        ventanaRuleta.mostrarVentana();
    }

    private void abrirVentanaHistorial() {
        frame.setVisible(false);
        VentanaHistorial ventanaHistorial =
                new VentanaHistorial(controlador.getHistorial(), this::mostrarVentana);
        ventanaHistorial.mostrarVentana();
    }

    private void cerrarSesion() {
        session.cerrarSesion();
        frame.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin(session, controlador);
        ventanaLogin.mostrarVentana();
    }

    public void mostrarVentana() {
        actualizarSaldo(); // al volver de jugar, el saldo se refresca
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}