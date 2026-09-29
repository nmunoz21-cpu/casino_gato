package Ventanas;

import Logica.Ruleta;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final String nombreUsuario;
    // Una sola Ruleta por sesión: conserva saldo e historial entre ventanas
    private final Ruleta ruleta = new Ruleta();
    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");

    public VentanaMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void construirInterfaz() {
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton btnJugar = new JButton("Jugar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnSalir = new JButton("Salir");

        btnJugar.addActionListener(e -> abrirVentanaRuleta());
        btnHistorial.addActionListener(e -> abrirVentanaHistorial());
        btnSalir.addActionListener(e -> cerrarSesion());

        panelBotones.add(new JLabel(nombreUsuario));
        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnSalir);

        JTextArea areaInfo = new JTextArea(
                "Bienvenido/a al menu principal, " + nombreUsuario + ".\n" +
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

    private void abrirVentanaRuleta() {
        frame.setVisible(false); // el menú se oculta mientras se juega
        VentanaRuleta ventanaRuleta =
                new VentanaRuleta(nombreUsuario, ruleta, this::mostrarVentana);
        ventanaRuleta.mostrarVentana();
    }

    private void abrirVentanaHistorial() {
        frame.setVisible(false);
        VentanaHistorial ventanaHistorial =
                new VentanaHistorial(ruleta.getHistorial(), this::mostrarVentana);
        ventanaHistorial.mostrarVentana();
    }

    private void cerrarSesion() {
        frame.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.mostrarVentana();
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}