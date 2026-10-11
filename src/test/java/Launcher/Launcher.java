
package Launcher;

import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.Ruleta;
import Vista.VentanaLogin;

import javax.swing.SwingUtilities;

public class Launcher {

    // Inicia la aplicación Casino BlackCat
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Crea el controlador de sesiones compartido
            SessionController session = new SessionController();

            // Crea el modelo encargado de la ruleta
            Ruleta ruleta = new Ruleta();

            // Conecta la ruleta con la sesión del usuario
            RuletaController ruletaController =
                    new RuletaController(ruleta, session);

            // Abre la ventana inicial del sistema
            VentanaLogin ventanaLogin =
                    new VentanaLogin(session, ruletaController);

            ventanaLogin.mostrarVentana();
        });
    }
}
