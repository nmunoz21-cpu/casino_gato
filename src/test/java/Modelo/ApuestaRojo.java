package Modelo;

/**
 * Representa una apuesta al color rojo.
 *
 * Hereda los atributos y métodos comunes de ApuestaBase
 * e implementa su propia lógica de acierto.
 */
public class ApuestaRojo extends ApuestaBase {

    /**
     * Constructor que inicializa la apuesta.
     *
     * @param monto cantidad apostada
     */
    public ApuestaRojo(int monto) {
        super(monto, "ROJO");
    }

    /**
     * Comprueba si el resultado corresponde al color rojo.
     *
     * @param numero número generado por la ruleta
     * @param color color del número obtenido
     * @return true si el número es rojo y distinto de cero
     */
    @Override
    public boolean acierta(int numero, String color) {

        return numero != 0 && "ROJO".equalsIgnoreCase(color);
    }
}
