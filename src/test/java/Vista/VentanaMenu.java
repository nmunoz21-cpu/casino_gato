
package Vista;

import Controlador.EstadisticasController;
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
    private final EstadisticasController estadisticasController;

    // Componentes principales de la interfaz
    private final JFrame frame =
            new JFrame("RULETA - Casino Black Cat");

    private JLabel lblNombre;
    private JLabel lblSaldo;

    // Recibe los controladores existentes
    public VentanaMenu(SessionController session,
                       RuletaController controlador,
                       ResultadoController resultadoController) {

        this.session = session;
        this.controlador = controlador;
        this.resultadoController = resultadoController;

        // Reutiliza la misma sesión para las estadísticas
        this.estadisticasController =
                new EstadisticasController(session);

        configurarVentana();
        construirInterfaz();
    }

    // Configura el tamaño y cierre de la ventana
    private void configurarVentana() {
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Construye los botones y componentes del menú
    private void construirInterfaz() {

        JPanel panelBotones =
                new JPanel(new GridLayout(7, 1, 5, 5));

        panelBotones.setPreferredSize(new Dimension(160, 0));

        JButton btnJugar = new JButton("Jugar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnPerfil = new JButton("Perfil");
        JButton btnEstadisticas = new JButton("Estadísticas");
        JButton btnSalir = new JButton("Salir");

        lblNombre = new JLabel();
        lblSaldo = new JLabel();

        actualizarNombre();
        actualizarSaldo();

        // Asigna las acciones a los botones
        btnJugar.addActionListener(e -> abrirVentanaRuleta());

        btnHistorial.addActionListener(
                e -> abrirVentanaHistorial()
        );

        btnPerfil.addActionListener(e -> abrirVentanaPerfil());

        btnEstadisticas.addActionListener(
                e -> abrirVentanaEstadisticas()
        );

        btnSalir.addActionListener(e -> cerrarSesion());

        // Agrega los componentes al menú
        panelBotones.add(lblNombre);
        panelBotones.add(lblSaldo);
        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnPerfil);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnSalir);

        JTextArea areaInfo = new JTextArea(
                "Bienvenido/a al menu principal.\n" +
                        "A la izquierda tienes:\n" +
                        "- Jugar: abre la ventana de juego.\n" +
                        "- Historial: muestra tus rondas jugadas.\n" +
                        "- Perfil: cambia tu nombre y recarga saldo.\n" +
                        "- Estadísticas: consulta tus resultados.\n" +
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

    // Abre el historial personal del usuario
    private void abrirVentanaHistorial() {

        frame.setVisible(false);

        VentanaHistorial ventanaHistorial =
                new VentanaHistorial(
                        resultadoController,
                        this::mostrarVentana
                );

        ventanaHistorial.mostrarVentana();
    }

    // Abre la ventana del perfil
    private void abrirVentanaPerfil() {

        frame.setVisible(false);

        VentanaPerfil ventanaPerfil = new VentanaPerfil(
                session,
                controlador,
                this::mostrarVentana
        );

        ventanaPerfil.mostrarVentana();
    }

    // Abre las estadísticas del usuario activo
    private void abrirVentanaEstadisticas() {

        frame.setVisible(false);

        VentanaEstadisticas ventanaEstadisticas =
                new VentanaEstadisticas(
                        estadisticasController,
                        this::mostrarVentana
                );

        ventanaEstadisticas.mostrarVentana();
    }

    // Cierra la sesión y regresa al login
    private void cerrarSesion() {

        session.cerrarSesion();
        frame.dispose();

        VentanaLogin ventanaLogin = new VentanaLogin(
                session,
                controlador,
                resultadoController
        );

        ventanaLogin.mostrarVentana();
    }

    // Actualiza los datos y muestra el menú
    public void mostrarVentana() {

        actualizarNombre();
        actualizarSaldo();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
