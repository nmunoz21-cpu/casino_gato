package Controlador;

import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.TipoApuesta;

import java.util.List;

/**
 * Coordina las acciones de la Vista con el Modelo de la ruleta.
 * No contiene reglas de negocio: esas viven en Ruleta.
 */
public class RuletaController {

    private final Ruleta ruleta;

    // La ruleta se crea afuera (en el Launcher) y se entrega por constructor
    public RuletaController(Ruleta ruleta) {
        this.ruleta = ruleta;
    }

    // Pide al Modelo procesar la apuesta. Si es inválida, la excepción
    // llega hasta la Vista, que decide cómo avisar al usuario.
    public Resultado realizarApuesta(int monto, TipoApuesta tipo) {
        return ruleta.jugar(monto, tipo);
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public int getSaldo() {
        return ruleta.getSaldo();
    }

    // Lista de solo lectura, para VentanaHistorial
    public List<Resultado> getHistorial() {
        return ruleta.getHistorial();
    }
}