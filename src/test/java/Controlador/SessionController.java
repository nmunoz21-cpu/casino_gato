package Controlador;

import Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Administra los usuarios registrados y la sesión activa.
 * Las ventanas lo reciben por constructor: todas comparten la misma instancia.
 */
public class SessionController {

    private final List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    public SessionController() {
        // Usuarios de prueba (antes vivían en VentanaLogin)
        usuarios.add(new Usuario("Nure", "1234", "Nureddin"));
        usuarios.add(new Usuario("admin", "1234", "Administrador"));
    }

    // Registra un usuario nuevo. Rechaza datos vacíos y usernames repetidos.
    public void registrarUsuario(String username, String clave, String nombre) {
        if (username == null || username.isBlank() || clave == null || clave.isBlank()
                || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Datos requeridos");
        }
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("El usuario ya existe");
            }
        }
        usuarios.add(new Usuario(username, clave, nombre));
    }

    // Si las credenciales coinciden con un usuario, lo deja como usuario actual
    public boolean iniciarSesion(String username, String clave) {
        for (Usuario u : usuarios) {
            if (u.validarCredenciales(username, clave)) {
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }
    // Cambia el nombre del usuario en sesión. La validación
    public void cambiarNombre(String nuevoNombre) {
        if (!hayUsuario()) {
            throw new IllegalStateException("No hay una sesión activa");
        }
        usuarioActual.setNombre(nuevoNombre);
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
    public String getUsername() {
        return hayUsuario() ? usuarioActual.getUsername() : "";
    }
}