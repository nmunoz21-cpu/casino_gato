
package Vista;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin {

    // Controladores compartidos entre las ventanas
    private final SessionController session;
    private final RuletaController ruletaController;
    private final ResultadoController resultadoController;

    // Componentes de la interfaz gráfica
    private final JFrame frame = new JFrame("Login - Casino Black Cat");

    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();

    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrarse");

    // Inicializa el login con los tres controladores compartidos
    public VentanaLogin(SessionController session,
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
        frame.setSize(350, 200);
    }

    // Construye el formulario y sus botones
    private void construirInterfaz() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        panel.add(lblUsuario);
        panel.add(txtUsuario);

        panel.add(lblClave);
        panel.add(txtClave);

        panel.add(btnIngresar);
        panel.add(btnRegistrar);

        frame.add(panel);

        // Asigna las acciones a los botones
        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    // Valida las credenciales y abre el menú principal
    private void login() {

        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        if (session.iniciarSesion(usuario, clave)) {

            JOptionPane.showMessageDialog(
                    frame,
                    "BIENVENIDO AL CASINO " + session.getNombreUsuario()
            );

            frame.dispose();

            // Entrega los tres controladores al menú
            VentanaMenu ventanaMenu = new VentanaMenu(
                    session,
                    ruletaController,
                    resultadoController
            );

            ventanaMenu.mostrarVentana();

        } else {

            JOptionPane.showMessageDialog(
                    frame,
                    "USUARIO O CONTRASEÑA INCORRECTA"
            );
        }
    }

    // Abre el registro compartiendo los mismos controladores
    private void abrirRegistro() {

        frame.dispose();

        VentanaRegistro ventanaRegistro = new VentanaRegistro(
                session,
                ruletaController,
                resultadoController
        );

        ventanaRegistro.mostrarVentana();
    }

    // Muestra la ventana centrada en pantalla
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
