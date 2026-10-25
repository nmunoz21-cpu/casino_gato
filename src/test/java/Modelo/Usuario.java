
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un usuario del sistema.
 *
 * Administra los datos personales, el saldo,
 * los resultados y las estadisticas individuales.
 */
public class Usuario {

    // Datos de la cuenta
    private final String username;
    private final String clave;
    private String nombre;

    // Saldo individual
    private int saldo;

    // Historial individual de resultados
    private final List<Resultado> historial;

    // Estadisticas calculadas desde el historial
    private final Estadisticas estadisticas;

    /**
     * Inicializa los datos del usuario.
     */
    public Usuario(String username, String clave, String nombre) {

        this.username = username;
        this.clave = clave;

        setNombre(nombre);

        // Saldo predeterminado para una cuenta nueva
        this.saldo = 1000;

        // Inicializa el historial
        this.historial = new ArrayList<>();

        // Asocia las estadisticas con este usuario
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
     * Verifica las credenciales almacenadas
     * en el objeto.
     *
     * La autenticacion persistente se realiza
     * mediante RepositorioCuentas.
     */
    public boolean validarCredenciales(
            String username, String clave) {

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
     * Agrega un deposito al saldo.
     */
    public void depositar(int monto) {

        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser positivo"
            );
        }

        if (monto > Integer.MAX_VALUE - saldo) {
            throw new IllegalArgumentException(
                    "El saldo supera el valor permitido"
            );
        }

        saldo += monto;
    }

    /**
     * Registra el resultado de una ronda
     * y actualiza el saldo del usuario.
     */
    public void agregarResultado(Resultado resultado) {

        if (resultado == null) {
            throw new IllegalArgumentException(
                    "El resultado es obligatorio"
            );
        }

        if (resultado.getSaldo() < 0) {
            throw new IllegalArgumentException(
                    "El saldo no puede ser negativo"
            );
        }

        // Registra la nueva ronda
        historial.add(resultado);

        // Actualiza el saldo
        saldo = resultado.getSaldo();
    }

    /**
     * Restaura el saldo desde un archivo.
     */
    public void restaurarSaldo(int saldoGuardado) {

        if (saldoGuardado < 0) {
            throw new IllegalArgumentException(
                    "El saldo guardado no puede ser negativo"
            );
        }

        saldo = saldoGuardado;
    }

    /**
     * NUEVO METODO
     *
     * Restaura el historial de resultados
     * previamente recuperado del archivo.
     *
     * Reemplaza el historial existente para
     * evitar duplicaciones al iniciar sesion.
     *
     * No modifica el saldo, porque este se
     * restaura por separado.
     */
    public void restaurarHistorial(
            List<Resultado> historialGuardado) {

        if (historialGuardado == null) {
            throw new IllegalArgumentException(
                    "El historial no puede ser nulo"
            );
        }

        // Crea una copia independiente de los datos
        List<Resultado> copia =
                new ArrayList<>(historialGuardado);

        if (copia.contains(null)) {
            throw new IllegalArgumentException(
                    "El historial contiene resultados nulos"
            );
        }

        // Elimina los resultados actuales
        historial.clear();

        // Restaura los resultados recuperados
        historial.addAll(copia);
    }

    /**
     * Devuelve el historial sin permitir
     * modificaciones desde otras clases.
     */
    public List<Resultado> getHistorial() {

        return Collections.unmodifiableList(historial);
    }

    /**
     * Devuelve las estadisticas del usuario.
     *
     * Estas se calculan automaticamente
     * a partir del historial restaurado.
     */
    public Estadisticas getEstadisticas() {
        return estadisticas;
    }
}
