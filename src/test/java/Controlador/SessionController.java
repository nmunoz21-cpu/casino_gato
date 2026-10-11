
package Controlador;

import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class SessionController {

    // Almacena los usuarios registrados
    private final List<Usuario> usuarios = new ArrayList<>();

    // Guarda el usuario que inició sesión
    private Usuario usuarioActual;

    // Inicializa el sistema con usuarios de prueba
    public SessionController() {
        usuarios.add(new Usuario("Nure", "1234", "Nureddin"));
        usuarios.add(new Usuario("admin", "1234", "Administrador"));
    }

    // Registra un nuevo usuario y verifica sus datos
    public void registrarUsuario(String username, String clave, String nombre) {

        if (username == null || username.isBlank()
                || clave == null || clave.isBlank()
                || nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException("Datos requeridos");
        }

        // Comprueba que el usuario no esté registrado
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("El usuario ya existe");
            }
        }

        usuarios.add(new Usuario(username, clave, nombre));
    }

    // Valida las credenciales e inicia sesión
    public boolean iniciarSesion(String username, String clave) {
        for (Usuario u : usuarios) {
            if (u.validarCredenciales(username, clave)) {
                usuarioActual = u;
                return true;
            }
        }

        return false;
    }

    // Comprueba si existe una sesión activa
    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    // Devuelve el usuario que inició sesión
    public Usuario getUsuarioActual() {
        if (!hayUsuario()) {
            throw new IllegalStateException("No hay una sesión activa");
        }

        return usuarioActual;
    }

    // Devuelve el nombre del usuario activo
    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }

    // Devuelve el identificador del usuario activo
    public String getUsername() {
        return hayUsuario() ? usuarioActual.getUsername() : "";
    }

    // Modifica el nombre del usuario activo
    public void cambiarNombre(String nuevoNombre) {
        getUsuarioActual().setNombre(nuevoNombre);
    }

    // Finaliza la sesión actual
    public void cerrarSesion() {
        usuarioActual = null;
    }
}
