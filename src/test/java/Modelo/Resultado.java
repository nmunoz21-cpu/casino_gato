package Modelo;

/** Resultado inmutable de una ronda. */
public class Resultado {
    private final int numero;
    private final TipoApuesta tipoApuesta;
    private final int monto;
    private final boolean gano;

    // Constructor: crea el resultado de un giro con todos sus datos.
    public Resultado(int numero, TipoApuesta tipoApuesta, int monto, boolean gano) {
        this.numero = numero;
        this.tipoApuesta = tipoApuesta;
        this.monto = monto;
        this.gano = gano;
    }

    // Devuelve el número que salió en la ruleta.
    public int getNumero() { return numero; }

    // Devuelve el tipo de apuesta que originó este resultado.
    public TipoApuesta getTipoApuesta() { return tipoApuesta; }

    // Devuelve el monto apostado en la jugada.
    public int getMonto() { return monto; }

    // Indica si la apuesta fue ganadora.
    public boolean isGano() { return gano; }
}