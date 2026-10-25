
package Controlador;

import Datos.RepositorioCuentas;
import Datos.RepositorioHistorial;
import Datos.RepositorioUsuarios;

import Modelo.Resultado;
import Modelo.Usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Controlador encargado del registro,
 * inicio y cierre de sesion.
 *
 * Recupera la informacion persistente
 * del usuario al iniciar sesion.
 */
public class SessionController {

    // Repositorio para registrar y autenticar cuentas
    private final RepositorioCuentas repositorioCuentas;

    // Repositorio para recuperar el saldo
    private final RepositorioUsuarios repositorioUsuarios;

    // Repositorio para recuperar el historial
    private final RepositorioHistorial repositorioHistorial;

    // Usuarios cargados durante la ejecucion actual
    private final Map<String, Usuario> usuariosEnMemoria;

    // Usuario que tiene la sesion activa
    private Usuario usuarioActual;

    /**
     * Constructor del controlador.
     */
    public SessionController() {

        this.repositorioCuentas =
                new RepositorioCuentas();

        this.repositorioUsuarios =
                new RepositorioUsuarios();

        this.repositorioHistorial =
                new RepositorioHistorial();

        this.usuariosEnMemoria = new HashMap<>();

        this.usuarioActual = null;
    }

    /**
     * Registra una nueva cuenta.
     *
     * Las credenciales son gestionadas
     * por RepositorioCuentas.
     */
    public void registrarUsuario(
            String username,
            String clave,
            String nombre) {

        try {

            // Guarda la cuenta en el repositorio
            repositorioCuentas.registrarCuenta(
                    username,
                    clave,
                    nombre
            );

        } catch (IOException ex) {

            throw new IllegalStateException(
                    "No se pudo registrar la cuenta",
                    ex
            );
        }
    }

    /**
     * Inicia sesion verificando las credenciales.
     *
     * Recupera el saldo y el historial almacenados
     * antes de activar la sesion del usuario.
     *
     * @return true si las credenciales son correctas
     */
    public boolean iniciarSesion(
            String username,
            String clave) {

        // Desactiva cualquier sesion anterior
        usuarioActual = null;

        try {

            // Verifica las credenciales guardadas
            RepositorioCuentas.CuentaGuardada cuenta =
                    repositorioCuentas.autenticar(
                            username,
                            clave
                    );

            // Credenciales incorrectas
            if (cuenta == null) {
                return false;
            }

            // Recupera los datos de la cuenta
            String usernameGuardado =
                    cuenta.getUsername();

            String nombreGuardado =
                    cuenta.getNombre();

            String identificador =
                    usernameGuardado.toLowerCase(Locale.ROOT);

            // Busca el usuario cargado en memoria
            Usuario usuario =
                    usuariosEnMemoria.get(identificador);

            // Si no existe, crea un nuevo modelo
            if (usuario == null) {

                // La clave ya fue verificada por el
                // repositorio y no se guarda en Usuario.
                usuario = new Usuario(
                        usernameGuardado,
                        "",
                        nombreGuardado
                );
            }

            // Recupera el saldo guardado del usuario
            int saldoGuardado =
                    repositorioUsuarios.cargarSaldo(
                            usernameGuardado,
                            usuario.getSaldo()
                    );

            // NUEVO:
            // Recupera todas las rondas anteriores
            List<Resultado> historialGuardado =
                    repositorioHistorial.cargarHistorial(
                            usernameGuardado
                    );

            // Restaura el historial sin duplicarlo
            usuario.restaurarHistorial(
                    historialGuardado
            );

            // Restaura el saldo individual
            usuario.restaurarSaldo(
                    saldoGuardado
            );

            // Conserva el usuario en memoria
            usuariosEnMemoria.put(
                    identificador,
                    usuario
            );

            // Activa la sesion autenticada
            usuarioActual = usuario;

            return true;

        } catch (IOException ex) {

            // Evita iniciar sesion con informacion
            // que no pudo recuperarse correctamente
            throw new IllegalStateException(
                    "No se pudieron recuperar los datos del usuario",
                    ex
            );
        }
    }

    /**
     * Comprueba si existe un usuario autenticado.
     */
    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    /**
     * Devuelve el usuario autenticado.
     */
    public Usuario getUsuarioActual() {

        if (!hayUsuario()) {
            throw new IllegalStateException(
                    "No hay una sesion activa"
            );
        }

        return usuarioActual;
    }

    /**
     * Devuelve el nombre del usuario actual.
     */
    public String getNombreUsuario() {

        return hayUsuario()
                ? usuarioActual.getNombre()
                : "";
    }

    /**
     * Devuelve el identificador del usuario actual.
     */
    public String getUsername() {

        return hayUsuario()
                ? usuarioActual.getUsername()
                : "";
    }

    /**
     * Modifica el nombre del usuario activo.
     *
     * Este cambio todavia permanece
     * solamente en memoria.
     */
    public void cambiarNombre(String nuevoNombre) {

        getUsuarioActual().setNombre(nuevoNombre);
    }

    /**
     * Cierra la sesion sin eliminar
     * los archivos persistentes.
     */
    public void cerrarSesion() {

        usuarioActual = null;
    }
}

