package Logica;

/**
 * Representa el resultado de una ronda jugada en la Ruleta.
 */
public class ResultadoRonda {

    private final int numero;
    private final boolean rojo;
    private final char tipoApuesta;
    private final int monto;
    private final boolean acierto;
    private final int saldoActual;

    public ResultadoRonda(int numero, boolean rojo, char tipoApuesta,
                          int monto, boolean acierto, int saldoActual) {
        this.numero = numero;
        this.rojo = rojo;
        this.tipoApuesta = tipoApuesta;
        this.monto = monto;
        this.acierto = acierto;
        this.saldoActual = saldoActual;
    }

    public int getNumero() { return numero; }
    public boolean isRojo() { return rojo; }
    public char getTipoApuesta() { return tipoApuesta; }
    public int getMonto() { return monto; }
    public boolean isAcierto() { return acierto; }
    public int getSaldoActual() { return saldoActual; }
}