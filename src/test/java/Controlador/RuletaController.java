
package Controlador;

import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.TipoApuesta;
import Modelo.Usuario;

import java.util.List;

public class RuletaController {

    // Modelo encargado de ejecutar las apuestas
    private final Ruleta ruleta;

    // Controlador encargado de la sesión activa
    private final SessionController session;

    // Constructor principal: recibe la ruleta y la sesión compartida
    public RuletaController(Ruleta ruleta, SessionController session) {
        if (ruleta == null || session == null) {
            throw new IllegalArgumentException(
                    "La ruleta y la sesión son obligatorias"
            );
        }

        this.ruleta = ruleta;
        this.session = session;
    }

    // Constructor temporal para mantener compatibilidad con Launcher
    public RuletaController(Ruleta ruleta) {
        this.ruleta = ruleta;
        this.session = null;
    }

    // Ejecuta una apuesta y la guarda en el historial del usuario activo
    public Resultado realizarApuesta(int monto, TipoApuesta tipo) {

        // Comprueba que la sesión esté conectada
        if (session == null || !session.hayUsuario()) {
            throw new IllegalStateException(
                    "No hay una sesión activa configurada"
            );
        }

        // Obtiene el usuario que está jugando
        Usuario usuario = session.getUsuarioActual();

        // Realiza la apuesta mediante el modelo Ruleta
        Resultado resultado = ruleta.jugar(monto, tipo);

        // Registra el resultado en el historial personal
        usuario.agregarResultado(resultado);

        return resultado;
    }

    // Agrega dinero al saldo de la ruleta
    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    // Devuelve el saldo disponible
    public int getSaldo() {
        return ruleta.getSaldo();
    }

    // Devuelve el historial general de la ruleta
    public List<Resultado> getHistorial() {
        return ruleta.getHistorial();
    }
}
