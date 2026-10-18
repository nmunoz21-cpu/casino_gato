
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    // Datos de la cuenta
    private final String username;
    private final String clave;
    private String nombre;

    // Saldo individual del usuario
    private int saldo;

    // Historial individual de apuestas
    private final List<Resultado> historial;
    private final Estadisticas estadisticas;

    // Inicializa el usuario con saldo e historial propios
    public Usuario(String username, String clave, String nombre) {
        this.username = username;
        this.clave = clave;
        setNombre(nombre);

        this.saldo = 1000;
        this.historial = new ArrayList<>();
        this.estadisticas = new Estadisticas(this);
    }

    // Devuelve el nombre de usuario
    public String getUsername() {
        return username;
    }

    // Devuelve el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Modifica el nombre validando que no esté vacío
    public void setNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío"
            );
        }

        this.nombre = nuevoNombre.trim();
    }

    // Valida las credenciales del usuario
    public boolean validarCredenciales(String username, String clave) {
        return this.username.equalsIgnoreCase(username)
                && this.clave.equals(clave);
    }

    // Devuelve el saldo individual
    public int getSaldo() {
        return saldo;
    }

    // Permite recargar el saldo del usuario
    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor a cero"
            );
        }

        if (monto > Integer.MAX_VALUE - saldo) {
            throw new IllegalArgumentException(
                    "El monto supera el saldo permitido"
            );
        }

        saldo += monto;
    }

    // Guarda una apuesta y actualiza el saldo del usuario
    public void agregarResultado(Resultado resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException(
                    "El resultado no puede ser nulo"
            );
        }

        if (resultado.getSaldo() < 0) {
            throw new IllegalArgumentException(
                    "El saldo no puede ser negativo"
            );
        }

        historial.add(resultado);
        saldo = resultado.getSaldo();
    }

    // Devuelve el historial sin permitir cambios externos
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    // Devuelve las estadísticas propias del usuario
    public Estadisticas getEstadisticas() {
        return estadisticas;
    }
}
