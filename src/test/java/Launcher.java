public class Launcher {
    public static void main(String[] args) {
        // Inicia la aplicación mostrando la ventana de login
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.mostrarVentana();
        });
    }
}