package Ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final String nombreUsuario;
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
                        "- Historial: abre la ventana de historial (se implementara aparte).\n" +
                        "- Salir: cierra sesion y vuelve al login."
        );
        areaInfo.setEditable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panelBotones, BorderLayout.WEST);
        frame.add(areaInfo, BorderLayout.CENTER);
    }

    private void abrirVentanaRuleta() {
        VentanaRuleta ventanaRuleta = new VentanaRuleta(nombreUsuario);
        ventanaRuleta.mostrarVentana();
    }

    private void abrirVentanaHistorial() {
        // Pendiente: se implementara en una proxima iteracion.
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