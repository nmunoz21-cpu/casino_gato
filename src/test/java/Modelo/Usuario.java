
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un usuario del sistema.
 *
 * Administra sus datos personales, saldo,
 * historial de resultados y estadisticas.
 */
public class Usuario {

    // Datos de la cuenta
    private final String username;
    private final String clave;
    private String nombre;

    // Saldo individual del usuario
    private int saldo;

    // Historial individual de resultados
    private final List<Resultado> historial;

    // Estadisticas asociadas al usuario
    private final Estadisticas estadisticas;

    /**
     * Constructor que inicializa los datos del usuario.
     */
    public Usuario(String username, String clave, String nombre) {

        this.username = username;
        this.clave = clave;

        setNombre(nombre);

        // Saldo inicial para una cuenta nueva
        this.saldo = 1000;

        // Inicializa el historial individual
        this.historial = new ArrayList<>();

        // Vincula las estadisticas al usuario
        this.estadisticas = new Estadisticas(this);
    }

    /**
     * Devuelve el identificador del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devuelve el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del usuario.
     */
    public void setNombre(String nuevoNombre) {

        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio"
            );
        }

        this.nombre = nuevoNombre.trim();
    }

    /**
     * Comprueba las credenciales de acceso.
     */
    public boolean validarCredenciales(String username,
                                       String clave) {

        return this.username.equalsIgnoreCase(username)
                && this.clave.equals(clave);
    }

    /**
     * Devuelve el saldo actual.
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Agrega un monto positivo al saldo del usuario.
     */
    public void depositar(int monto) {

        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor a cero"
            );
        }

        // Evita que el saldo supere el maximo de int
        if (monto > Integer.MAX_VALUE - saldo) {
            throw new IllegalArgumentException(
                    "El monto supera el saldo permitido"
            );
        }

        saldo += monto;
    }

    /**
     * Registra un resultado y actualiza el saldo.
     */
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

        // Guarda el resultado en el historial
        historial.add(resultado);

        // Actualiza el saldo despues de la ronda
        saldo = resultado.getSaldo();
    }

    /**
     * Restaura el saldo previamente guardado.
     *
     * Este metodo se utiliza al iniciar sesion,
     * una vez que el repositorio recupera el saldo
     * desde el archivo correspondiente.
     *
     * @param saldoGuardado saldo recuperado
     */
    public void restaurarSaldo(int saldoGuardado) {

        if (saldoGuardado < 0) {
            throw new IllegalArgumentException(
                    "El saldo guardado no puede ser negativo"
            );
        }

        // Reemplaza el saldo inicial por el guardado
        this.saldo = saldoGuardado;
    }

    /**
     * Devuelve las estadisticas del usuario.
     */
    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    /**
     * Devuelve el historial sin permitir
     * modificaciones externas.
     */
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
