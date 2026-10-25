
package Modelo;

/**
 * Conserva los datos de una ronda de la ruleta.
 */
public class Resultado {

    private final int numero;
    private final boolean rojo;
    private final ApuestaBase apuesta;
    private final boolean gano;
    private final int saldo;

    /**
     * Constructor que registra el resultado de una jugada.
     */
    public Resultado(int numero, boolean rojo,
                     ApuestaBase apuesta,
                     boolean gano, int saldo) {

        if (apuesta == null) {
            throw new IllegalArgumentException(
                    "La apuesta es obligatoria"
            );
        }

        this.numero = numero;
        this.rojo = rojo;
        this.apuesta = apuesta;
        this.gano = gano;
        this.saldo = saldo;
    }

    // Devuelve el numero obtenido en la ruleta
    public int getNumero() {
        return numero;
    }

    // Indica si el numero obtenido es rojo
    public boolean isRojo() {
        return rojo;
    }

    // Devuelve el color del numero obtenido
    public String getColor() {
        if (numero == 0) {
            return "Verde";
        }

        return rojo ? "Rojo" : "Negro";
    }

    // Devuelve el objeto de apuesta realizado
    public ApuestaBase getApuesta() {
        return apuesta;
    }

    // Devuelve el monto apostado
    public int getMonto() {
        return apuesta.getMonto();
    }

    // Indica si el jugador gano
    public boolean isGano() {
        return gano;
    }

    // Devuelve si la apuesta fue acertada
    public boolean isAcierto() {
        return gano;
    }

    // Devuelve el saldo despues de la ronda
    public int getSaldo() {
        return saldo;
    }

    // Devuelve el saldo actualizado del jugador
    public int getSaldoActual() {
        return saldo;
    }
}
