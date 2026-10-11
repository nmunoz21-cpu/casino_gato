
package Vista;

import Controlador.ResultadoController;
import Modelo.Resultado;
import Modelo.TipoApuesta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class VentanaHistorial {

    // Controlador encargado de consultar el historial personal
    private final ResultadoController resultadoController;

    // Acción que permite regresar al menú principal
    private final Runnable alVolver;

    // Ventana principal del historial
    private final JFrame frame =
            new JFrame("HISTORIAL - Casino Black Cat");

    // Recibe el controlador de resultados y la acción de regreso
    public VentanaHistorial(ResultadoController resultadoController,
                            Runnable alVolver) {

        this.resultadoController = resultadoController;
        this.alVolver = alVolver;

        configurarVentana();
        construirInterfaz();
    }

    // Configura el tamaño y el cierre de la ventana
    private void configurarVentana() {

        frame.setSize(600, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (alVolver != null) {
                    alVolver.run();
                }
            }
        });
    }

    // Construye la tabla con las apuestas del usuario activo
    private void construirInterfaz() {

        // Consulta el historial mediante el controlador
        List<Resultado> historial =
                resultadoController.obtenerHistorial();

        String[] columnas = {
                "#", "Numero", "Color", "Apuesta",
                "Monto", "Resultado", "Saldo"
        };

        // Crea una tabla que no permite editar sus datos
        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };

        // Agrega cada resultado del usuario a la tabla
        int n = 1;

        for (Resultado r : historial) {
            modelo.addRow(new Object[]{
                    n++,
                    r.getNumero(),
                    r.getColor(),
                    nombreApuesta(r.getTipoApuesta()),
                    "$" + r.getMonto(),
                    r.isAcierto() ? "GANASTE" : "PERDISTE",
                    "$" + r.getSaldoActual()
            });
        }

        JTable tabla = new JTable(modelo);

        // Muestra un mensaje cuando no existen apuestas
        JLabel lblVacio = new JLabel(
                historial.isEmpty()
                        ? "Aun no has jugado ninguna ronda."
                        : " "
        );

        // Permite regresar al menú principal
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> frame.dispose());

        JPanel sur = new JPanel(new BorderLayout(5, 5));
        sur.add(lblVacio, BorderLayout.CENTER);
        sur.add(btnVolver, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(tabla), BorderLayout.CENTER);
        frame.add(sur, BorderLayout.SOUTH);
    }

    // Convierte el tipo de apuesta en un texto legible
    private String nombreApuesta(TipoApuesta tipo) {

        return switch (tipo) {
            case ROJO -> "Rojo";
            case NEGRO -> "Negro";
            case PAR -> "Par";
            case IMPAR -> "Impar";
        };
    }

    // Muestra la ventana centrada en pantalla
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
