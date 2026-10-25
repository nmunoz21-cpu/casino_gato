
package Controlador;

import Datos.RepositorioCuentas;
import Datos.RepositorioUsuarios;
import Modelo.Usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controlador encargado de administrar el registro,
 * inicio y cierre de sesion de los usuarios.
 *
 * Utiliza repositorios para conservar las cuentas
 * y los saldos entre ejecuciones del programa.
 */
public class SessionController {

    // Repositorio encargado de registrar y autenticar cuentas
    private final RepositorioCuentas repositorioCuentas;

    // Repositorio encargado de guardar y recuperar saldos
    private final RepositorioUsuarios repositorioUsuarios;

    // Conserva los usuarios cargados durante la ejecucion
    private final Map<String, Usuario> usuariosEnMemoria;

    // Usuario que actualmente tiene una sesion activa
    private Usuario usuarioActual;

    /**
     * Constructor del controlador de sesiones.
     *
     * Inicializa los repositorios y la memoria
     * temporal de los usuarios.
     */
    public SessionController() {

        this.repositorioCuentas = new RepositorioCuentas();

        this.repositorioUsuarios = new RepositorioUsuarios();

        this.usuariosEnMemoria = new HashMap<>();

        this.usuarioActual = null;
    }

    /**
     * Registra una nueva cuenta de usuario.
     *
     * Delega al repositorio la validacion
     * y el almacenamiento de las credenciales.
     *
     * @param username identificador del usuario
     * @param clave contrasena del usuario
     * @param nombre nombre del usuario
     */
    public void registrarUsuario(String username,
                                 String clave,
                                 String nombre) {

        try {

            // Solicita guardar la nueva cuenta
            repositorioCuentas.registrarCuenta(
                    username,
                    clave,
                    nombre
            );

        } catch (IOException ex) {

            // Informa problemas de almacenamiento
            throw new IllegalStateException(
                    "No se pudo guardar la cuenta del usuario",
                    ex
            );
        }
    }

    /**
     * Inicia sesion utilizando las credenciales
     * almacenadas en el repositorio.
     *
     * Si la autenticacion es correcta, recupera
     * el saldo correspondiente al usuario.
     *
     * @param username identificador del usuario
     * @param clave contrasena ingresada
     * @return true si el inicio de sesion es correcto
     */
    public boolean iniciarSesion(String username,
                                 String clave) {

        // Limpia cualquier sesion anterior
        usuarioActual = null;

        try {

            // Solicita verificar las credenciales
            RepositorioCuentas.CuentaGuardada cuenta =
                    repositorioCuentas.autenticar(
                            username,
                            clave
                    );

            // Si las credenciales son incorrectas
            if (cuenta == null) {
                return false;
            }

            // Recupera los datos de la cuenta autenticada
            String usernameGuardado = cuenta.getUsername();
            String nombreGuardado = cuenta.getNombre();

            // Identificador para la memoria temporal
            String identificador =
                    usernameGuardado.toLowerCase(Locale.ROOT);

            // Comprueba si el usuario ya fue cargado
            Usuario usuario =
                    usuariosEnMemoria.get(identificador);

            // Si no existe en memoria, crea el modelo
            if (usuario == null) {

                // La contrasena se verifica en el repositorio.
                // No necesitamos copiarla al modelo Usuario.
                usuario = new Usuario(
                        usernameGuardado,
                        "",
                        nombreGuardado
                );
            }

            // Recupera el saldo desde el archivo
            int saldoRecuperado =
                    repositorioUsuarios.cargarSaldo(
                            usernameGuardado,
                            usuario.getSaldo()
                    );

            // Restaura el saldo del usuario
            usuario.restaurarSaldo(saldoRecuperado);

            // Conserva el usuario durante esta ejecucion
            usuariosEnMemoria.put(
                    identificador,
                    usuario
            );

            // Activa la sesion autenticada
            usuarioActual = usuario;

            return true;

        } catch (IOException ex) {

            // Evita iniciar sesion cuando existen
            // problemas al recuperar los datos
            throw new IllegalStateException(
                    "No se pudieron recuperar los datos del usuario",
                    ex
            );
        }
    }

    /**
     * Comprueba si existe una sesion activa.
     *
     * @return true si un usuario inicio sesion
     */
    public boolean hayUsuario() {

        return usuarioActual != null;
    }

    /**
     * Devuelve el usuario de la sesion activa.
     *
     * @return usuario autenticado
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
     * Devuelve el nombre del usuario activo.
     *
     * @return nombre o cadena vacia
     */
    public String getNombreUsuario() {

        return hayUsuario()
                ? usuarioActual.getNombre()
                : "";
    }

    /**
     * Devuelve el username del usuario activo.
     *
     * @return identificador del usuario
     */
    public String getUsername() {

        return hayUsuario()
                ? usuarioActual.getUsername()
                : "";
    }

    /**
     * Modifica el nombre del usuario activo.
     *
     * Por ahora el cambio se conserva en memoria.
     */
    public void cambiarNombre(String nuevoNombre) {

        getUsuarioActual().setNombre(nuevoNombre);
    }

    /**
     * Cierra la sesion del usuario actual.
     *
     * Los objetos de usuarios permanecen en memoria
     * hasta que termine la ejecucion del programa.
     */
    public void cerrarSesion() {

        usuarioActual = null;
    }
}
