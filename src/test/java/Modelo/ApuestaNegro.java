package Modelo;

/**
 * Representa una apuesta al color negro.
 *
 * Hereda los atributos y métodos comunes de ApuestaBase
 * e implementa su propia lógica de acierto.
 */
public class ApuestaNegro extends ApuestaBase {

    /**
     * Constructor que inicializa la apuesta.
     *
     * @param monto cantidad apostada
     */
    public ApuestaNegro(int monto) {
        super(monto, "NEGRO");
    }

    /**
     * Comprueba si el resultado corresponde al color negro.
     *
     * @param numero número generado por la ruleta
     * @param color color del número obtenido
     * @return true si el número es negro y distinto de cero
     */
    @Override
    public boolean acierta(int numero, String color) {

        return numero != 0 && "NEGRO".equalsIgnoreCase(color);
    }
}
