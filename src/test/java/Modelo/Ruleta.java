
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ruleta {

    // Generador de números aleatorios
    private final Random random;

    // Almacena los resultados generados por la ruleta
    private final List<Resultado> historial;

    // Números rojos de la ruleta europea
    private static final List<Integer> NUMEROS_ROJOS = List.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    );

    // Inicializa la ruleta con un historial vacío
    public Ruleta() {
        this.random = new Random();
        this.historial = new ArrayList<>();
    }

    // Genera un número aleatorio entre 0 y 36
    private int girar() {
        return random.nextInt(37);
    }

    // Comprueba si el número obtenido es rojo
    private boolean esRojo(int numero) {
        return NUMEROS_ROJOS.contains(numero);
    }

    // Verifica si la apuesta coincide con el número obtenido
    private boolean verificarApuesta(int numero, TipoApuesta tipo) {

        // El cero no gana apuestas de color ni paridad
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

    // Realiza una apuesta utilizando el saldo del usuario
    public Resultado jugar(int monto, TipoApuesta tipo,
                           int saldoUsuario) {

        // Comprueba que se haya seleccionado una apuesta
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un tipo de apuesta"
            );
        }

        // Valida que el monto sea positivo
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "La apuesta debe ser mayor a cero"
            );
        }

        // Comprueba que exista saldo suficiente
        if (saldoUsuario < 0 || monto > saldoUsuario) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente para apostar"
            );
        }

        // Evita superar el límite de saldo permitido
        if ((long) saldoUsuario + monto > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "La posible ganancia supera el saldo permitido"
            );
        }

        // Genera el número de la ronda
        int numero = girar();

        // Determina el color y comprueba la apuesta
        boolean rojo = esRojo(numero);
        boolean gano = verificarApuesta(numero, tipo);

        // Calcula el saldo resultante del usuario
        int nuevoSaldo;

        if (gano) {
            nuevoSaldo = saldoUsuario + monto;
        } else {
            nuevoSaldo = saldoUsuario - monto;
        }

        // Crea un resultado con los datos de la ronda
        Resultado resultado = new Resultado(
                numero,
                rojo,
                tipo,
                monto,
                gano,
                nuevoSaldo
        );

        // Almacena el resultado generado
        historial.add(resultado);

        return resultado;
    }

    // Devuelve el historial sin permitir modificaciones externas
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
