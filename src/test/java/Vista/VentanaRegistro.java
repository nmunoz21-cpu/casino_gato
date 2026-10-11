
package Vista;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

    // Controladores compartidos entre las ventanas
    private final SessionController session;
    private final RuletaController ruletaController;
    private final ResultadoController resultadoController;

    // Componentes de la interfaz gráfica
    private final JFrame frame =
            new JFrame("Registro - Casino Black Cat");

    private final JLabel lblNombre =
            new JLabel("Nombre Completo:");
    private final JTextField txtNombre = new JTextField();

    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Contraseña:");
    private final JPasswordField txtClave = new JPasswordField();

    private final JButton btnRegistrar = new JButton("Registrar");
    private final JButton btnVolver = new JButton("Volver");

    // Inicializa la ventana con los controladores compartidos
    public VentanaRegistro(SessionController session,
                           RuletaController ruletaController,
                           ResultadoController resultadoController) {

        this.session = session;
        this.ruletaController = ruletaController;
        this.resultadoController = resultadoController;

        configurarVentana();
        construirInterfaz();
    }

    // Configura el tamaño y cierre de la ventana
    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(380, 250);
    }

    // Construye el formulario de registro
    private void construirInterfaz() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblUsuario);
        panel.add(txtUsuario);

        panel.add(lblClave);
        panel.add(txtClave);

        panel.add(btnRegistrar);
        panel.add(btnVolver);

        frame.add(panel);

        // Asigna las acciones a los botones
        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> volverLogin());
    }

    // Registra un nuevo usuario mediante SessionController
    private void registrar() {

        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        try {

            session.registrarUsuario(usuario, clave, nombre);

            JOptionPane.showMessageDialog(
                    frame,
                    "¡REGISTRO EXITOSO!",
                    "ÉXITO",
                    JOptionPane.INFORMATION_MESSAGE
            );

            volverLogin();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    ex.getMessage(),
                    "REGISTRO INVÁLIDO",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // Regresa al login conservando los tres controladores
    private void volverLogin() {

        frame.dispose();

        SwingUtilities.invokeLater(() -> {

            VentanaLogin ventanaLogin = new VentanaLogin(
                    session,
                    ruletaController,
                    resultadoController
            );

            ventanaLogin.mostrarVentana();
        });
    }

    // Muestra la ventana centrada en pantalla
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
