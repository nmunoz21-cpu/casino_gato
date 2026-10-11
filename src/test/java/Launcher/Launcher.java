
package Launcher;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.Ruleta;
import Vista.VentanaLogin;

import javax.swing.SwingUtilities;

public class Launcher {

    // Inicia la aplicación Casino BlackCat
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Crea la sesión compartida
            SessionController session = new SessionController();

            // Crea el modelo de la ruleta
            Ruleta ruleta = new Ruleta();

            // Conecta la ruleta con la sesión
            RuletaController ruletaController =
                    new RuletaController(ruleta, session);

            // Permite consultar el historial del usuario activo
            ResultadoController resultadoController =
                    new ResultadoController(session);

            // Abre el login con los tres controladores
            VentanaLogin ventanaLogin = new VentanaLogin(
                    session,
                    ruletaController,
                    resultadoController
            );

            ventanaLogin.mostrarVentana();
        });
    }
}
