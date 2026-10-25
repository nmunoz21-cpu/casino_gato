
package Modelo;

/**
 * Clase abstracta que representa una apuesta general
 * dentro del juego de la ruleta.
 *
 * Define los atributos y comportamientos comunes
 * que heredarán los diferentes tipos de apuestas.
 */
public abstract class ApuestaBase {

    // Cantidad de dinero apostada por el usuario
    private final int monto;

    // Nombre que identifica el tipo de apuesta
    private final String etiqueta;

    /**
     * Constructor de la clase base.
     * Inicializa los datos comunes de una apuesta.
     *
     * @param monto cantidad de dinero apostada
     * @param etiqueta nombre del tipo de apuesta
     */
    protected ApuestaBase(int monto, String etiqueta) {

        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor que cero"
            );
        }

        if (etiqueta == null || etiqueta.isBlank()) {
            throw new IllegalArgumentException(
                    "La etiqueta no puede estar vacia"
            );
        }

        this.monto = monto;
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve el monto de la apuesta.
     *
     * @return monto apostado
     */
    public int getMonto() {
        return monto;
    }

    /**
     * Devuelve la etiqueta de la apuesta.
     *
     * @return nombre del tipo de apuesta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Determina si la apuesta fue ganadora.
     *
     * Cada subclase implementará su propia regla
     * para verificar el resultado.
     *
     * @param numero numero generado por la ruleta
     * @param color color correspondiente al numero
     * @return true si acierta, false en caso contrario
     */
    public abstract boolean acierta(int numero, String color);
}
