package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Lógica de la ruleta (SRP: no sabe nada de ventanas Swing).
 *
 * Reglas de saldo:
 *  - Al apostar se RESTA el monto del saldo.
 *  - Si gana, se SUMA el doble del monto.
 *  - Si pierde, no se suma nada.
 *  - No se puede apostar más de lo que se tiene ni montos <= 0.
 */
public class Ruleta {

    public static final int SALDO_INICIAL_POR_DEFECTO = 1000;
    private static final int MULTIPLICADOR_PREMIO = 2;

    // Números rojos de la ruleta europea. El 0 es verde; el resto es negro.
    private static final Set<Integer> ROJOS = Set.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36);

    private final Random random = new Random();
    private final List<ResultadoRonda> historial = new ArrayList<>();
    private int saldo;

    public Ruleta() {
        this(SALDO_INICIAL_POR_DEFECTO);
    }

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    /**
     * Juega una ronda.
     *
     * @param tipoApuesta "Color", "Paridad" o "Numero"
     * @param seleccion   "Rojo"/"Negro", "Par"/"Impar", o un número "0".."36"
     * @param monto       cantidad a apostar
     * @throws IllegalArgumentException si la apuesta no es válida
     */
    public ResultadoRonda jugar(String tipoApuesta, String seleccion, int monto) {
        validarApuesta(tipoApuesta, seleccion, monto);

        saldo -= monto; // se descuenta al apostar

        int numero = random.nextInt(37); // 0 a 36
        String color = colorDe(numero);
        boolean gano = evaluar(tipoApuesta, seleccion, numero, color);

        if (gano) {
            saldo += monto * MULTIPLICADOR_PREMIO; // devuelve el doble
        }

        ResultadoRonda resultado = new ResultadoRonda(
                numero, color, tipoApuesta, seleccion, monto, gano, saldo);
        historial.add(resultado);
        return resultado;
    }

    private void validarApuesta(String tipoApuesta, String seleccion, int monto) {
        if (tipoApuesta == null || seleccion == null) {
            throw new IllegalArgumentException("Debes elegir el tipo de apuesta y tu selección");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente: tienes $" + saldo + " y quieres apostar $" + monto);
        }
        if (tipoApuesta.equals("Numero")) {
            try {
                int n = Integer.parseInt(seleccion);
                if (n < 0 || n > 36) {
                    throw new IllegalArgumentException("El número debe estar entre 0 y 36");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El número apostado no es válido");
            }
        }
    }

    private boolean evaluar(String tipoApuesta, String seleccion, int numero, String color) {
        switch (tipoApuesta) {
            case "Color":
                return color.equals(seleccion);
            case "Paridad":
                if (numero == 0) {
                    return false; // el 0 no es par ni impar para la apuesta
                }
                return (numero % 2 == 0) ? seleccion.equals("Par") : seleccion.equals("Impar");
            case "Numero":
                return Integer.parseInt(seleccion) == numero;
            default:
                throw new IllegalArgumentException("Tipo de apuesta desconocido: " + tipoApuesta);
        }
    }

    private String colorDe(int numero) {
        if (numero == 0) {
            return "Verde";
        }
        return ROJOS.contains(numero) ? "Rojo" : "Negro";
    }

    public int getSaldo() {
        return saldo;
    }

    /** Vista de solo lectura para que VentanaHistorial la muestre sin poder modificarla. */
    public List<ResultadoRonda> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}