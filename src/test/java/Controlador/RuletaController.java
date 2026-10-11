
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

    // Recibe la ruleta y la sesión compartida
    public RuletaController(Ruleta ruleta, SessionController session) {

        if (ruleta == null || session == null) {
            throw new IllegalArgumentException(
                    "La ruleta y la sesión son obligatorias"
            );
        }

        this.ruleta = ruleta;
        this.session = session;
    }

    // Realiza una apuesta utilizando el saldo del usuario activo
    public Resultado realizarApuesta(int monto, TipoApuesta tipo) {

        // Obtiene el usuario que inició sesión
        Usuario usuario = session.getUsuarioActual();

        // Ejecuta la apuesta con el saldo individual
        Resultado resultado = ruleta.jugar(
                monto,
                tipo,
                usuario.getSaldo()
        );

        // Guarda la jugada y actualiza el saldo del usuario
        usuario.agregarResultado(resultado);

        return resultado;
    }

    // Recarga el saldo del usuario activo
    public void depositar(int monto) {

        Usuario usuario = session.getUsuarioActual();

        usuario.depositar(monto);
    }

    // Devuelve el saldo del usuario activo
    public int getSaldo() {

        Usuario usuario = session.getUsuarioActual();

        return usuario.getSaldo();
    }

    // Devuelve el historial individual del usuario activo
    public List<Resultado> getHistorial() {

        Usuario usuario = session.getUsuarioActual();

        return usuario.getHistorial();
    }
}
