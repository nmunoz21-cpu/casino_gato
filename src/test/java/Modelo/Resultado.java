package Modelo;

/** Resultado inmutable de una ronda. */
public class Resultado {

    private final int numero;
    private final boolean rojo;
    private final TipoApuesta tipoApuesta;
    private final int monto;
    private final boolean acierto;
    private final int saldoActual;

    public Resultado(int numero, boolean rojo, TipoApuesta tipoApuesta,
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
    public TipoApuesta getTipoApuesta() { return tipoApuesta; }
    public int getMonto() { return monto; }
    public boolean isAcierto() { return acierto; }
    public int getSaldoActual() { return saldoActual; }

    /** El 0 es verde; el resto rojo o negro. */
    public String getColor() {
        if (numero == 0) {
            return "Verde";
        }
        return rojo ? "Rojo" : "Negro";
    }

    @Override
    public String toString() {
        return String.format("Numero %d (%s) | Apuesta=%s | Monto=$%d | %s | Saldo=$%d",
                numero, getColor(), tipoApuesta, monto,
                acierto ? "GANASTE" : "PERDISTE", saldoActual);
    }
}