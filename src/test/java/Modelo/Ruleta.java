package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *  administra el saldo, aplica las reglas de apuesta
 * y guarda el historial de resultados.
 */
public class Ruleta {

    public static final int SALDO_INICIAL = 0;
    private static final int MULTIPLICADOR_PREMIO = 2;

    private static final Set<Integer> ROJOS = Set.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36);

    private final Random random = new Random();
    private final List<Resultado> historial = new ArrayList<>();
    private int saldo;

    // Crea la ruleta con saldo cero
    public Ruleta() {
        this(SALDO_INICIAL);
    }

    // Crea la ruleta con un saldo inicial determinado
    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    /**
     * @param monto cantidad apostada
     * @param tipo  tipo de apuesta (ROJO, NEGRO, PAR o IMPAR)
     * @throws IllegalArgumentException si la apuesta no es válida
     */
    public Resultado jugar(int monto, TipoApuesta tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Debes elegir un tipo de apuesta");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente: tienes $" + saldo + " y quieres apostar $" + monto);
        }

        saldo -= monto;

        int numero = random.nextInt(37); // 0 a 36
        boolean rojo = ROJOS.contains(numero);
        boolean acierto = evaluar(tipo, numero, rojo);

        if (acierto) {
            saldo += monto * MULTIPLICADOR_PREMIO;
        }

        Resultado resultado = new Resultado(numero, rojo, tipo, monto, acierto, saldo);
        historial.add(resultado);
        return resultado;
    }

    private boolean evaluar(TipoApuesta tipo, int numero, boolean rojo) {
        if (numero == 0) {
            return false; // el 0 pierde en color y paridad
        }
        return switch (tipo) {
            case ROJO  -> rojo;
            case NEGRO -> !rojo;
            case PAR   -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }

    // Recarga el saldo de la ruleta. Solo acepta montos mayores a 0,
    // para que el saldo nunca disminuya ni quede inconsistente por un depósito.
    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0");
        }
        saldo += monto;
    }

    public int getSaldo() {
        return saldo;
    }

    /** Solo lectura, para que VentanaHistorial la muestre. */
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}