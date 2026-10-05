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
    private JLabel lblNombre;
    private JLabel lblSaldo;

    public VentanaMenu(SessionController session, RuletaController controlador) {
        this.session = session;
        this.controlador = controlador;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void construirInterfaz() {
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 5, 5));
        panelBotones.setPreferredSize(new Dimension(160, 0));

        JButton btnJugar = new JButton("Jugar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnPerfil = new JButton("Perfil");
        JButton btnSalir = new JButton("Salir");
        lblNombre = new JLabel();
        lblSaldo = new JLabel();
        actualizarNombre();
        actualizarSaldo();

        btnJugar.addActionListener(e -> abrirVentanaRuleta());
        btnHistorial.addActionListener(e -> abrirVentanaHistorial());
        btnPerfil.addActionListener(e -> abrirVentanaPerfil());
        btnSalir.addActionListener(e -> cerrarSesion());

        panelBotones.add(lblNombre);
        panelBotones.add(lblSaldo);
        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnPerfil);
        panelBotones.add(btnSalir);

        JTextArea areaInfo = new JTextArea(
                "Bienvenido/a al menu principal.\n" +
                        "A la izquierda tienes:\n" +
                        "- Jugar: abre la ventana de juego.\n" +
                        "- Historial: muestra las rondas jugadas.\n" +
                        "- Perfil: cambia tu nombre y recarga saldo.\n" +
                        "- Salir: cierra sesion y vuelve al login."
        );
        areaInfo.setEditable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panelBotones, BorderLayout.WEST);
        frame.add(areaInfo, BorderLayout.CENTER);
    }

    // Los datos se piden de nuevo cada vez que se muestra el menú
    private void actualizarNombre() {
        lblNombre.setText(session.getNombreUsuario());
    }

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

    private void abrirVentanaPerfil() {
        frame.setVisible(false);
        VentanaPerfil ventanaPerfil =
                new VentanaPerfil(session, controlador, this::mostrarVentana);
        ventanaPerfil.mostrarVentana();
    }

    private void cerrarSesion() {
        session.cerrarSesion();
        frame.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin(session, controlador);
        ventanaLogin.mostrarVentana();
    }

    public void mostrarVentana() {
        actualizarNombre(); // al volver del perfil, el nombre se refresca
        actualizarSaldo();  // y también el saldo, tras jugar o recargar
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}