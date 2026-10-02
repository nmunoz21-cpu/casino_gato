package Launcher;

import Ventanas.VentanaLogin;

/**
 * Punto de entrada de la aplicacion.
 * Unicamente inicia la ventana de inicio de sesion.
 */
public class Launcher {

    public static void main(String[] args) {
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.mostrarVentana();
    }
}