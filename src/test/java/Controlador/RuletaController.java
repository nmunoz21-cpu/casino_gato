
package Controlador;

import Datos.RepositorioHistorial;
import Datos.RepositorioUsuarios;

import Modelo.ApuestaBase;
import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Controlador encargado de coordinar
 * las operaciones de la ruleta.
 *
 * Solicita guardar el historial y el saldo
 * actualizado del usuario.
 */
public class RuletaController {

    // Modelo encargado de ejecutar las rondas
    private final Ruleta ruleta;

    // Controlador de la sesion activa
    private final SessionController session;

    // Repositorio de saldos
    private final RepositorioUsuarios repositorioUsuarios;

    // Repositorio del historial de resultados
    private final RepositorioHistorial repositorioHistorial;

    /**
     * Constructor del controlador.
     */
    public RuletaController(
            Ruleta ruleta,
            SessionController session) {

        if (ruleta == null || session == null) {
            throw new IllegalArgumentException(
                    "La ruleta y la sesion son obligatorias"
            );
        }

        this.ruleta = ruleta;
        this.session = session;

        // Inicializa los repositorios
        this.repositorioUsuarios =
                new RepositorioUsuarios();

        this.repositorioHistorial =
                new RepositorioHistorial();
    }

    /**
     * Ejecuta una ronda utilizando polimorfismo.
     *
     * Actualiza el modelo y guarda los resultados
     * del usuario en sus archivos correspondientes.
     */
    public Resultado realizarApuesta(ApuestaBase apuesta) {

        // Obtiene el usuario de la sesion
        Usuario usuario = session.getUsuarioActual();

        // Ejecuta la ronda mediante ApuestaBase
        Resultado resultado = ruleta.jugar(
                apuesta,
                usuario.getSaldo()
        );

        // Actualiza el historial y saldo en memoria
        usuario.agregarResultado(resultado);

        try {

            // Guarda el historial completo
            repositorioHistorial.guardarHistorial(
                    usuario.getUsername(),
                    usuario.getHistorial()
            );

            // Guarda el saldo actualizado
            repositorioUsuarios.guardarSaldo(
                    usuario.getUsername(),
                    usuario.getSaldo()
            );

        } catch (IOException ex) {

            // Informa que no se pudo completar
            // correctamente la persistencia.
            throw new UncheckedIOException(
                    "La ronda se registro en memoria, "
                            + "pero no se pudieron guardar todos los datos",
                    ex
            );
        }

        return resultado;
    }

    /**
     * Deposita saldo en la cuenta del usuario.
     *
     * Actualiza el saldo en memoria y solicita
     * guardarlo en el archivo correspondiente.
     */
    public void depositar(int monto) {

        Usuario usuario = session.getUsuarioActual();

        // Realiza el deposito
        usuario.depositar(monto);

        try {

            // Guarda el saldo actualizado
            repositorioUsuarios.guardarSaldo(
                    usuario.getUsername(),
                    usuario.getSaldo()
            );

        } catch (IOException ex) {

            throw new UncheckedIOException(
                    "El deposito cambio el saldo en memoria, "
                            + "pero no pudo guardarse",
                    ex
            );
        }
    }

    /**
     * Devuelve el saldo del usuario activo.
     */
    public int getSaldo() {

        return session.getUsuarioActual().getSaldo();
    }

    /**
     * Devuelve el historial del usuario activo.
     */
    public List<Resultado> getHistorial() {

        return session.getUsuarioActual().getHistorial();
    }
}
