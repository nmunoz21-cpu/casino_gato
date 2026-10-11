
package Modelo;

// Representa el resultado de una ronda de ruleta
public class Resultado {

    // Datos obtenidos después de una apuesta
    private final int numero;
    private final boolean rojo;
    private final TipoApuesta tipoApuesta;
    private final int monto;
    private final boolean gano;
    private final int saldo;

    // Crea un resultado con los datos de la ronda
    public Resultado(int numero, boolean rojo,
                     TipoApuesta tipoApuesta, int monto,
                     boolean gano, int saldo) {
        this.numero = numero;
        this.rojo = rojo;
        this.tipoApuesta = tipoApuesta;
        this.monto = monto;
        this.gano = gano;
        this.saldo = saldo;
    }

    // Devuelve el número obtenido en la ruleta
    public int getNumero() {
        return numero;
    }

    // Indica si el número obtenido es rojo
    public boolean isRojo() {
        return rojo;
    }

    // Devuelve el color del número obtenido
    public String getColor() {
        if (numero == 0) {
            return "Verde";
        }
        return rojo ? "Rojo" : "Negro";
    }

    // Devuelve el tipo de apuesta realizada
    public TipoApuesta getTipoApuesta() {
        return tipoApuesta;
    }

    // Devuelve el monto apostado
    public int getMonto() {
        return monto;
    }

    // Indica si el jugador ganó la apuesta
    public boolean isGano() {
        return gano;
    }

    // Devuelve si la apuesta fue acertada
    public boolean isAcierto() {
        return gano;
    }

    // Devuelve el saldo después de la ronda
    public int getSaldo() {
        return saldo;
    }

    // Devuelve el saldo actualizado del jugador
    public int getSaldoActual() {
        return saldo;
    }
}
