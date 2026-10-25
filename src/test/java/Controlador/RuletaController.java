
package Controlador;

import Datos.RepositorioUsuarios;
import Modelo.ApuestaBase;
import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Controlador encargado de coordinar las operaciones
 * de la ruleta con el usuario que inicio sesion.
 *
 * Tambien solicita al repositorio guardar el saldo
 * cuando este cambia.
 */
public class RuletaController {

    // Modelo encargado de ejecutar las rondas
    private final Ruleta ruleta;

    // Controlador de la sesion compartida
    private final SessionController session;

    // Repositorio encargado de guardar el saldo
    private final RepositorioUsuarios repositorioUsuarios;

    /**
     * Constructor del controlador.
     *
     * @param ruleta modelo de la ruleta
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

        // Inicializa el repositorio de usuarios
        this.repositorioUsuarios =
                new RepositorioUsuarios();
    }

    /**
     * Ejecuta una ronda mediante polimorfismo.
     *
     * Registra el resultado en el usuario
     * y guarda el saldo actualizado.
     *
     * @param apuesta objeto que hereda de ApuestaBase
     * @return resultado de la ronda
     */
    public Resultado realizarApuesta(ApuestaBase apuesta) {

        // Obtiene al usuario autenticado
        Usuario usuario = session.getUsuarioActual();

        // Ejecuta la ronda con el saldo actual
        Resultado resultado = ruleta.jugar(
                apuesta,
                usuario.getSaldo()
        );

        // Actualiza el historial y saldo del usuario
        usuario.agregarResultado(resultado);

        // Guarda el nuevo saldo en su archivo
        guardarSaldoUsuario(usuario);

        return resultado;
    }

    /**
     * Registra un deposito en el saldo del usuario.
     *
     * Despues del deposito, guarda el nuevo saldo.
     *
     * @param monto cantidad que se agrega al saldo
     */
    public void depositar(int monto) {

        // Obtiene al usuario activo
        Usuario usuario = session.getUsuarioActual();

        // Actualiza el saldo en memoria
        usuario.depositar(monto);

        // Guarda el saldo actualizado
        guardarSaldoUsuario(usuario);
    }

    /**
     * Recupera el saldo del usuario activo.
     *
     * @return saldo actual
     */
    public int getSaldo() {

        return session.getUsuarioActual().getSaldo();
    }

    /**
     * Recupera el historial del usuario activo.
     *
     * @return lista de resultados
     */
    public List<Resultado> getHistorial() {

        return session.getUsuarioActual().getHistorial();
    }

    /**
     * Guarda el saldo del usuario en un archivo.
     *
     * Cada usuario tiene su propio archivo,
     * identificado mediante su username.
     *
     * @param usuario usuario cuyo saldo se guardara
     */
    private void guardarSaldoUsuario(Usuario usuario) {

        try {

            // Solicita al repositorio persistir el saldo
            repositorioUsuarios.guardarSaldo(
                    usuario.getUsername(),
                    usuario.getSaldo()
            );

        } catch (IOException ex) {

            // Informa que el saldo cambio en memoria,
            // pero no pudo guardarse en el archivo.
            throw new UncheckedIOException(
                    "El saldo cambio, pero no pudo guardarse",
                    ex
            );
        }
    }
}
