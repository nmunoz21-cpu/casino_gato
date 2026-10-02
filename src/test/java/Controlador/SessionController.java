import Modelo.Usuario;


public class SessionController {

    private Usuario usuarioActual;
    public void registrarUsuario(String usuario, String clave, String nombre) {
        if (usuario == null || usuario.isBlank() || clave == null || clave.isBlank() ||
                nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Datos requeridos");
        }
        usuarioActual = new Usuario(usuario, clave, nombre);
    }


    public boolean iniciarSesion(String usuario, String clave) {
        if (usuarioActual == null) return false;
        return usuarioActual.validarCredenciales(usuario, clave);
    }


    public boolean hayUsuario() {
        return usuarioActual != null;
    }


    public String getNombreUsuario() {
        return hayUsuario()
                ? usuarioActual.getNombre()
                : "";
    }


    public Usuario getUsuarioActual() {
        return usuarioActual;
    }


    public void cerrarSesion() {
        usuarioActual = null;
    }
}