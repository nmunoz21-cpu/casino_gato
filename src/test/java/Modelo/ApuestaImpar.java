
package Modelo;

/**
 * Representa una apuesta a un número impar.
 *
 * Hereda los atributos y métodos comunes de ApuestaBase
 * e implementa su propia lógica para verificar el acierto.
 */
public class ApuestaImpar extends ApuestaBase {

    /**
     * Constructor que inicializa la apuesta.
     *
     * @param monto cantidad de dinero apostada
     */
    public ApuestaImpar(int monto) {
        super(monto, "IMPAR");
    }

    /**
     * Comprueba si el número obtenido es impar.
     *
     * @param numero número generado por la ruleta
     * @param color color correspondiente al número
     * @return true si el número es impar y distinto de cero
     */
    @Override
    public boolean acierta(int numero, String color) {

        return numero != 0 && numero % 2 != 0;
    }
}
