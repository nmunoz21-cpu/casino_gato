package Modelo;

public class Usuario {
    private String username;
    private String password;
    private String nombre;

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }
    // Verifica si las credenciales ingresadas pertenecen al usuario
    public boolean validarCredenciales(String u, String p) {

        return this.username.equals(u) && this.password.equals(p);
    }

    public String getNombre() {
        return nombre;
    }
    //Devuelve el nombre de usuario con el que inicia sesión.
    public String getUsername() {
        return username;
    }

    // Actualiza el nombre del usuario. No acepta valores nulos ni vacíos
    // para que el objeto nunca quede con un nombre inválido.
    public void setNombre(String nombre){
        if (nombre != null && !nombre.isBlank()){
            this.nombre= nombre;
        }else{
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }
}
