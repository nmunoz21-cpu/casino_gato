package Launcher;

import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.Ruleta;
import Vista.VentanaLogin;

/**
 * Punto de entrada de la aplicacion.
 * Unicamente inicia la ventana de inicio de sesion.
 */
public class Launcher {

    public static void main(String[] args) {
        SessionController session = new SessionController();
        RuletaController ruletaController = new RuletaController(new Ruleta());
        new VentanaLogin(session, ruletaController).mostrarVentana();
    }
}