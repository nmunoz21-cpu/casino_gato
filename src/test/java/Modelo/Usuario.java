
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    // Datos de la cuenta del usuario
    private final String username;
    private final String clave;
    private String nombre;

    // Guarda los resultados de las apuestas del usuario
    private final List<Resultado> historial;

    // Crea un usuario con sus datos y un historial vacío
    public Usuario(String username, String clave, String nombre) {
        this.username = username;
        this.clave = clave;
        setNombre(nombre);
        this.historial = new ArrayList<>();
    }

    // Devuelve el nombre de usuario
    public String getUsername() {
        return username;
    }

    // Devuelve el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre y verifica que no esté vacío
    public void setNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío"
            );
        }

        this.nombre = nuevoNombre.trim();
    }

    // Comprueba si el usuario y la contraseña coinciden
    public boolean validarCredenciales(String username, String clave) {
        return this.username.equalsIgnoreCase(username)
                && this.clave.equals(clave);
    }

    // Agrega una nueva jugada al historial del usuario
    public void agregarResultado(Resultado resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException(
                    "El resultado no puede ser nulo"
            );
        }

        historial.add(resultado);
    }

    // Devuelve el historial sin permitir modificaciones externas
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
