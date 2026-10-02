package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 */
public class Ruleta {

    public static final int SALDO_INICIAL = 1000;
    private static final int MULTIPLICADOR_PREMIO = 2;

    private static final Set<Integer> ROJOS = Set.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36);

    private final Random random = new Random();
    private final List<Resultado> historial = new ArrayList<>();
    private int saldo;

    public Ruleta() {
        this(SALDO_INICIAL);
    }

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    /**
     * @param monto cantidad apostada
     * @param tipo  'R' rojo, 'N' negro, 'P' par, 'I' impar
     * @throws IllegalArgumentException si la apuesta no es válida
     */
    public Resultado jugar(int monto, char tipo) {
        if (tipo != 'R' && tipo != 'N' && tipo != 'P' && tipo != 'I') {
            throw new IllegalArgumentException("Tipo de apuesta desconocido: " + tipo);
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

    private boolean evaluar(char tipo, int numero, boolean rojo) {
        if (numero == 0) {
            return false; // el 0 pierde en color y paridad
        }
        switch (tipo) {
            case 'R': return rojo;
            case 'N': return !rojo;
            case 'P': return numero % 2 == 0;
            default:  return numero % 2 != 0; // 'I'
        }
    }

    public int getSaldo() {
        return saldo;
    }

    /** Solo lectura, para que VentanaHistorial la muestre. */
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}