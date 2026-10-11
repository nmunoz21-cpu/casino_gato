
package Vista;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    // Controladores compartidos entre las ventanas
    private final SessionController session;
    private final RuletaController controlador;
    private final ResultadoController resultadoController;

    // Componentes principales de la interfaz
    private final JFrame frame =
            new JFrame("RULETA - Casino Black Cat");

    private JLabel lblNombre;
    private JLabel lblSaldo;

    // Recibe los controladores necesarios para el menú
    public VentanaMenu(SessionController session,
                       RuletaController controlador,
                       ResultadoController resultadoController) {

        this.session = session;
        this.controlador = controlador;
        this.resultadoController = resultadoController;

        configurarVentana();
        construirInterfaz();
    }

    // Configura el tamaño y cierre de la ventana
    private void configurarVentana() {
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Construye los botones y componentes del menú
    private void construirInterfaz() {

        JPanel panelBotones =
                new JPanel(new GridLayout(6, 1, 5, 5));

        panelBotones.setPreferredSize(new Dimension(160, 0));

        JButton btnJugar = new JButton("Jugar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnPerfil = new JButton("Perfil");
        JButton btnSalir = new JButton("Salir");

        lblNombre = new JLabel();
        lblSaldo = new JLabel();

        actualizarNombre();
        actualizarSaldo();

        // Asigna las acciones de cada botón
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
                        "- Historial: muestra tus rondas jugadas.\n" +
                        "- Perfil: cambia tu nombre y recarga saldo.\n" +
                        "- Salir: cierra sesion y vuelve al login."
        );

        areaInfo.setEditable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panelBotones, BorderLayout.WEST);
        frame.add(areaInfo, BorderLayout.CENTER);
    }

    // Actualiza el nombre del usuario conectado
    private void actualizarNombre() {
        lblNombre.setText(session.getNombreUsuario());
    }

    // Actualiza el saldo mostrado en el menú
    private void actualizarSaldo() {
        lblSaldo.setText("Saldo: $" + controlador.getSaldo());
    }

    // Abre la ventana para realizar apuestas
    private void abrirVentanaRuleta() {
        frame.setVisible(false);

        VentanaRuleta ventanaRuleta = new VentanaRuleta(
                session.getNombreUsuario(),
                controlador,
                this::mostrarVentana
        );

        ventanaRuleta.mostrarVentana();
    }

    // Abre el historial personal mediante ResultadoController
    private void abrirVentanaHistorial() {
        frame.setVisible(false);

        VentanaHistorial ventanaHistorial =
                new VentanaHistorial(
                        resultadoController,
                        this::mostrarVentana
                );

        ventanaHistorial.mostrarVentana();
    }

    // Abre la ventana para modificar el perfil
    private void abrirVentanaPerfil() {
        frame.setVisible(false);

        VentanaPerfil ventanaPerfil = new VentanaPerfil(
                session,
                controlador,
                this::mostrarVentana
        );

        ventanaPerfil.mostrarVentana();
    }

    // Cierra la sesión y regresa al inicio de sesión
    private void cerrarSesion() {
        session.cerrarSesion();
        frame.dispose();

        VentanaLogin ventanaLogin =
                new VentanaLogin(session, controlador);

        ventanaLogin.mostrarVentana();
    }

    // Actualiza los datos y muestra nuevamente el menú
    public void mostrarVentana() {
        actualizarNombre();
        actualizarSaldo();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
