
package Controlador;

import Modelo.ApuestaBase;
import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;

import java.util.List;

/**
 * Controlador encargado de coordinar
 * las apuestas y la sesion del usuario.
 */
public class RuletaController {

    // Modelo de la ruleta
    private final Ruleta ruleta;

    // Controlador de sesion compartido
    private final SessionController session;

    /**
     * Constructor del controlador.
     *
     * @param ruleta modelo que ejecuta las apuestas
     * @param session controlador de sesion
     */
    public RuletaController(Ruleta ruleta,
                            SessionController session) {

        if (ruleta == null || session == null) {
            throw new IllegalArgumentException(
                    "La ruleta y la sesion son obligatorias"
            );
        }

        this.ruleta = ruleta;
        this.session = session;
    }

    /**
     * Ejecuta una apuesta del usuario activo.
     *
     * Recibe ApuestaBase para aprovechar
     * el polimorfismo de las subclases.
     */
    public Resultado realizarApuesta(ApuestaBase apuesta) {

        // Obtiene el usuario que inicio sesion
        Usuario usuario = session.getUsuarioActual();

        // Ejecuta la apuesta con su saldo actual
        Resultado resultado = ruleta.jugar(
                apuesta,
                usuario.getSaldo()
        );

        // Registra el resultado y actualiza el saldo
        usuario.agregarResultado(resultado);

        return resultado;
    }

    /**
     * Permite depositar dinero en la cuenta
     * del usuario activo.
     */
    public void depositar(int monto) {
        session.getUsuarioActual().depositar(monto);
    }

    /**
     * Devuelve el saldo del usuario activo.
     */
    public int getSaldo() {
        return session.getUsuarioActual().getSaldo();
    }

    /**
     * Devuelve el historial individual
     * del usuario que inicio sesion.
     */
    public List<Resultado> getHistorial() {
        return session.getUsuarioActual().getHistorial();
    }
}
