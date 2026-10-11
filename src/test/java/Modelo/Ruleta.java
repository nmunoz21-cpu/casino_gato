
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ruleta {

    // Saldo disponible para realizar apuestas
    private int saldo;

    // Generador de números aleatorios
    private final Random random;

    // Almacena los resultados generados por la ruleta
    private final List<Resultado> historial;

    // Números rojos de la ruleta europea
    private static final List<Integer> NUMEROS_ROJOS = List.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    );

    // Inicializa la ruleta con un saldo de ejemplo
    public Ruleta() {
        this.saldo = 1000;
        this.random = new Random();
        this.historial = new ArrayList<>();
    }

    // Devuelve el saldo disponible
    public int getSaldo() {
        return saldo;
    }

    // Agrega dinero al saldo, validando el monto
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

    // Genera un número aleatorio entre 0 y 36
    private int girar() {
        return random.nextInt(37);
    }

    // Comprueba si un número pertenece a los rojos
    private boolean esRojo(int numero) {
        return NUMEROS_ROJOS.contains(numero);
    }

    // Comprueba si la apuesta coincide con el resultado
    private boolean verificarApuesta(int numero, TipoApuesta tipo) {

        // El cero no gana apuestas de color o paridad
        if (numero == 0) {
            return false;
        }

        return switch (tipo) {
            case ROJO -> esRojo(numero);
            case NEGRO -> !esRojo(numero);
            case PAR -> numero % 2 == 0;
            case IMPAR -> numero % 2 != 0;
        };
    }

    // Ejecuta una apuesta y registra su resultado
    public Resultado jugar(int monto, TipoApuesta tipo) {

        // Valida el monto y el tipo de apuesta
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "La apuesta debe ser mayor a cero"
            );
        }

        if (monto > saldo) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente para apostar"
            );
        }

        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un tipo de apuesta"
            );
        }

        // Gira la ruleta y comprueba la apuesta
        int numero = girar();
        boolean rojo = esRojo(numero);
        boolean gano = verificarApuesta(numero, tipo);

        // Actualiza el saldo según el resultado
        if (gano) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        // Crea el resultado de la ronda
        Resultado resultado = new Resultado(
                numero, rojo, tipo, monto, gano, saldo
        );

        // Guarda el resultado en el historial
        historial.add(resultado);

        return resultado;
    }

    // Devuelve el historial sin permitir cambios externos
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
