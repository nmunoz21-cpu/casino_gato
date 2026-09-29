package Logica;

import java.util.Random;

/**
 * Motor del juego de Ruleta.
 * Concentra las reglas, el calculo del saldo y el historial de rondas.
 * No conoce nada de Swing.
 */
public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static final int CANTIDAD_NUMEROS = 37;
    public static final int SALDO_INICIAL = 1000;

    private static final int[] NUMEROS_ROJOS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    private final Random rng = new Random();
    private int saldo;

    private final int[] historialNumeros = new int[MAX_HISTORIAL];
    private final int[] historialApuestas = new int[MAX_HISTORIAL];
    private final boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    private int historialSize = 0;

    public Ruleta() {
        this.saldo = SALDO_INICIAL;
    }

    public ResultadoRonda jugar(int monto, char tipo) {
        int numero = girar();
        boolean acierto = evaluarResultado(numero, tipo);
        actualizarSaldo(monto, acierto);
        registrarResultado(numero, monto, acierto);
        return new ResultadoRonda(numero, esRojo(numero), tipo, monto, acierto, saldo);
    }

    private int girar() {
        return rng.nextInt(CANTIDAD_NUMEROS);
    }

    private boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false;
        switch (tipo) {
            case 'R': return esRojo(numero);
            case 'N': return !esRojo(numero);
            case 'P': return numero % 2 == 0;
            case 'I': return numero % 2 != 0;
            default: return false;
        }
    }

    private boolean esRojo(int numero) {
        for (int rojo : NUMEROS_ROJOS) {
            if (numero == rojo) return true;
        }
        return false;
    }

    private void actualizarSaldo(int monto, boolean acierto) {
        saldo += acierto ? monto : -monto;
    }

    private void registrarResultado(int numero, int monto, boolean acierto) {
        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = monto;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }

    public int getSaldo() { return saldo; }
    public int getHistorialSize() { return historialSize; }
    public int[] getHistorialNumeros() { return historialNumeros; }
    public int[] getHistorialApuestas() { return historialApuestas; }
    public boolean[] getHistorialAciertos() { return historialAciertos; }
}