package Vista;

import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin {

    // Los controladores se crean en el Launcher y se comparten entre ventanas
    private final SessionController session;
    private final RuletaController ruletaController;

    // --- Componentes de la interfaz gráfica ---
    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrarse");

    /**
     * Constructor que inicializa la ventana de inicio de sesión.
     * Configura sus componentes y eventos.
     *
     * @param session          sesión compartida con el resto de las ventanas
     * @param ruletaController controlador de la ruleta, que se entrega al menú
     */
    public VentanaLogin(SessionController session, RuletaController ruletaController) {
        this.session = session;
        this.ruletaController = ruletaController;

        // Configuración del tamaño y layout de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Agregar componentes al panel
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblClave);
        panel.add(txtClave);
        panel.add(btnIngresar);
        panel.add(btnRegistrar);

        frame.add(panel);

        // Eventos de botones
        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    /**
     * Muestra la ventana en pantalla, centrada.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Gestiona el inicio de sesión al presionar el botón.
     * La Vista solo envía los datos; la validación la hace la sesión.
     */
    private void login() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        if (session.iniciarSesion(u, p)) {
            JOptionPane.showMessageDialog(frame,
                    "BIENVENIDO AL CASINO " + session.getNombreUsuario());
            frame.dispose();
            VentanaMenu ventanaMenu = new VentanaMenu(session, ruletaController);
            ventanaMenu.mostrarVentana();
        } else {
            JOptionPane.showMessageDialog(frame, "USUARIO O CONTRASEÑA INCORRECTA");
        }
    }

    /**
     * Abre la ventana de registro para crear un nuevo usuario.
     * Cierra la ventana actual y le pasa los mismos controladores.
     */
    private void abrirRegistro() {
        frame.dispose();
        VentanaRegistro ventanaRegistro = new VentanaRegistro(session, ruletaController);
        ventanaRegistro.mostrarVentana();
    }
}