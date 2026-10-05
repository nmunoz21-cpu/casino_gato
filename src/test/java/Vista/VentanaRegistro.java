package Vista;

import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

    // Los controladores se crean en el Launcher y se comparten entre ventanas
    private final SessionController session;
    private final RuletaController ruletaController;

    // --- Componentes de la interfaz gráfica ---
    private final JFrame frame = new JFrame("Registro - Casino Black Cat");

    private final JLabel lblNombre = new JLabel("Nombre Completo:");
    private final JTextField txtNombre = new JTextField();

    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Contraseña:");
    private final JPasswordField txtClave = new JPasswordField();

    private final JButton btnRegistrar = new JButton("Registrar");
    private final JButton btnVolver = new JButton("Volver");

    /**
     * Constructor que inicializa la ventana de registro.
     *
     * @param session          sesión compartida, donde se guardan los usuarios
     * @param ruletaController controlador de la ruleta, que se devuelve al login
     */
    public VentanaRegistro(SessionController session, RuletaController ruletaController) {
        this.session = session;
        this.ruletaController = ruletaController;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(380, 250);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Agregar componentes
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblClave);
        panel.add(txtClave);
        panel.add(btnRegistrar);
        panel.add(btnVolver);

        frame.add(panel);

        // Eventos
        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> volverLogin());
    }

    /**
     * Muestra la ventana de registro centrada en pantalla.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Cierra la ventana actual y vuelve a la ventana de Login,
     * entregándole los mismos controladores.
     */
    private void volverLogin() {
        frame.dispose();
        SwingUtilities.invokeLater(() ->
                new VentanaLogin(session, ruletaController).mostrarVentana());
    }

    /**
     * Envía los datos a la sesión. Las reglas (campos vacíos, usuario repetido)
     * las aplica el controlador; la Vista solo muestra el mensaje si algo falla.
     */
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String user = txtUsuario.getText().trim();
        String pass = new String(txtClave.getPassword()).trim();

        try {
            session.registrarUsuario(user, pass, nombre);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame,
                    ex.getMessage(),
                    "REGISTRO INVÁLIDO",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame,
                "¡REGISTRO EXITOSO!",
                "ÉXITO",
                JOptionPane.INFORMATION_MESSAGE);

        volverLogin();
    }
}