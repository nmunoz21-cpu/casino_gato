
package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Ejecuta una ronda de ruleta sin conocer
 * los tipos concretos de apuesta.
 */
public class Ruleta {

    // Generador de numeros aleatorios
    private final Random random;

    // Historial de resultados de la ruleta
    private final List<Resultado> historial;

    // Numeros rojos de la ruleta europea
    private static final List<Integer> NUMEROS_ROJOS = List.of(
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    );

    /**
     * Constructor de la ruleta.
     * Inicializa el generador y el historial.
     */
    public Ruleta() {
        this.random = new Random();
        this.historial = new ArrayList<>();
    }

    /**
     * Genera un numero aleatorio entre 0 y 36.
     */
    private int girar() {
        return random.nextInt(37);
    }

    /**
     * Determina si el numero pertenece
     * a los numeros rojos de la ruleta.
     */
    private boolean esRojo(int numero) {
        return NUMEROS_ROJOS.contains(numero);
    }

    /**
     * Ejecuta una apuesta utilizando polimorfismo.
     *
     * @param apuesta objeto de cualquier subclase
     *                 de ApuestaBase
     * @param saldoUsuario saldo actual del jugador
     * @return resultado de la ronda
     */
    public Resultado jugar(ApuestaBase apuesta,
                           int saldoUsuario) {

        // Verifica que exista una apuesta
        if (apuesta == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar una apuesta"
            );
        }

        // Obtiene el monto desde la clase base
        int monto = apuesta.getMonto();

        // Verifica que el saldo sea suficiente
        if (saldoUsuario < 0 || monto > saldoUsuario) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente para apostar"
            );
        }

        // Evita superar el limite de saldo
        if ((long) saldoUsuario + monto >
                Integer.MAX_VALUE) {

            throw new IllegalArgumentException(
                    "La posible ganancia supera el saldo permitido"
            );
        }

        // Genera un unico numero para la ronda
        int numero = girar();

        // Determina el color del numero
        boolean rojo = esRojo(numero);

        String color = numero == 0
                ? "Verde"
                : (rojo ? "Rojo" : "Negro");

        // POLIMORFISMO:
        // Cada subclase determina si la apuesta acierta
        boolean gano = apuesta.acierta(numero, color);

        // Calcula el nuevo saldo del usuario
        int nuevoSaldo = gano
                ? saldoUsuario + monto
                : saldoUsuario - monto;

        // Crea el resultado de la jugada
        Resultado resultado = new Resultado(
                numero,
                rojo,
                apuesta,
                gano,
                nuevoSaldo
        );

        // Registra el resultado en el historial
        historial.add(resultado);

        return resultado;
    }

    /**
     * Devuelve el historial sin permitir
     * modificaciones externas.
     */
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
