import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

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
     */
    public VentanaRegistro() {
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
     * Cierra la ventana actual y vuelve a la ventana de Login.
     */
    private void volverLogin() {
        frame.dispose();
        SwingUtilities.invokeLater(() -> new VentanaLogin().mostrarVentana());
    }

    /**
     * Valida los campos y agrega un nuevo usuario a la lista.
     */
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String user = txtUsuario.getText().trim();
        String pass = new String(txtClave.getPassword()).trim();

        // Validar que no haya campos vacíos
        if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "POR FAVOR, COMPLETE TODO LOS CAMPOS.",
                    "CAMPO VACIO",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si el nombre de usuario ya existe en la lista
        for (Usuario u : VentanaLogin.USUARIOS) {
            if (u.getUsername().equalsIgnoreCase(user)) {
                JOptionPane.showMessageDialog(frame,
                        "EL NOMBRE DEL USUARIO YA EXISTE .",
                        "USUARIO EXISTENTE",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Agregar el nuevo usuario a la lista compartida
        VentanaLogin.USUARIOS.add(new Usuario(nombre, user, pass));

        JOptionPane.showMessageDialog(frame,
                "¡REGISTRO EXITOSO!",
                "ÉXITO",
                JOptionPane.INFORMATION_MESSAGE);

        volverLogin();
    }
}