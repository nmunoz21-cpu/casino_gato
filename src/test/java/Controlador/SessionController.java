
package Controlador;

import Datos.RepositorioUsuarios;
import Modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de administrar
 * el registro, inicio y cierre de sesion.
 *
 * Tambien recupera el saldo persistente
 * del usuario al iniciar sesion.
 */
public class SessionController {

    // Lista de usuarios registrados en memoria
    private final List<Usuario> usuarios =
            new ArrayList<>();

    // Usuario que tiene la sesion activa
    private Usuario usuarioActual;

    // Repositorio que permite recuperar saldos
    private final RepositorioUsuarios repositorioUsuarios =
            new RepositorioUsuarios();

    /**
     * Constructor de la sesion.
     *
     * Inicializa los usuarios de prueba.
     */
    public SessionController() {

        usuarios.add(
                new Usuario("Nure", "1234", "Nureddin")
        );

        usuarios.add(
                new Usuario("admin", "1234", "Administrador")
        );
    }

    /**
     * Registra un usuario nuevo en el sistema.
     *
     * Valida que sus datos no esten vacios y
     * que el identificador no este repetido.
     */
    public void registrarUsuario(String username,
                                 String clave,
                                 String nombre) {

        // Valida los datos ingresados
        if (username == null || username.isBlank()
                || clave == null || clave.isBlank()
                || nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException(
                    "Datos requeridos"
            );
        }

        // Comprueba que el usuario no exista
        for (Usuario usuario : usuarios) {

            if (usuario.getUsername()
                    .equalsIgnoreCase(username)) {

                throw new IllegalArgumentException(
                        "El usuario ya existe"
                );
            }
        }

        // Crea un nuevo usuario
        usuarios.add(
                new Usuario(username, clave, nombre)
        );
    }

    /**
     * Inicia sesion verificando las credenciales.
     *
     * Si son correctas, solicita al repositorio
     * recuperar el saldo previamente guardado.
     *
     * @return true si las credenciales son validas
     */
    public boolean iniciarSesion(String username,
                                 String clave) {

        // Recorre los usuarios registrados
        for (Usuario usuario : usuarios) {

            if (usuario.validarCredenciales(
                    username, clave)) {

                try {

                    // Recupera el saldo desde el archivo.
                    // Si no existe, usa el saldo inicial.
                    int saldoRecuperado =
                            repositorioUsuarios.cargarSaldo(
                                    usuario.getUsername(),
                                    usuario.getSaldo()
                            );

                    // Actualiza el saldo en el modelo
                    usuario.restaurarSaldo(
                            saldoRecuperado
                    );

                } catch (IOException ex) {

                    // Evita abrir la sesion con informacion
                    // persistente que no pudo recuperarse.
                    throw new IllegalStateException(
                            "No se pudo recuperar el saldo guardado",
                            ex
                    );
                }

                // Activa la sesion una vez recuperado
                // correctamente el saldo
                usuarioActual = usuario;

                return true;
            }
        }

        // Credenciales incorrectas
        return false;
    }

    /**
     * Indica si existe una sesion activa.
     */
    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    /**
     * Devuelve el usuario de la sesion activa.
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
     */
    public String getNombreUsuario() {

        return hayUsuario()
                ? usuarioActual.getNombre()
                : "";
    }

    /**
     * Devuelve el identificador del usuario activo.
     */
    public String getUsername() {

        return hayUsuario()
                ? usuarioActual.getUsername()
                : "";
    }

    /**
     * Cambia el nombre del usuario activo.
     */
    public void cambiarNombre(String nuevoNombre) {

        getUsuarioActual().setNombre(
                nuevoNombre
        );
    }

    /**
     * Cierra la sesion actual.
     */
    public void cerrarSesion() {

        usuarioActual = null;
    }
}
