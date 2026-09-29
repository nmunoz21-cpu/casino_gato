import Ventanas.VentanaLogin;

/**
 * Punto de entrada de la aplicacion.
 * Unicamente inicia la ventana de inicio de sesion; no contiene
 * logica de negocio ni de flujo del juego.
 */
public class Launcher {

    public static void main(String[] args) {
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.mostrarVentana();
    }
}