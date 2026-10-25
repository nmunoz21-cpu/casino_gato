
package Vista;

import Controlador.ResultadoController;
import Modelo.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Ventana que muestra el historial
 * de apuestas del usuario activo.
 */
public class VentanaHistorial {

    // Controlador del historial
    private final ResultadoController resultadoController;

    // Accion para regresar al menu
    private final Runnable alVolver;

    // Ventana principal
    private final JFrame frame =
            new JFrame("HISTORIAL - Casino Black Cat");

    /**
     * Constructor de la ventana.
     */
    public VentanaHistorial(
            ResultadoController resultadoController,
            Runnable alVolver) {

        this.resultadoController = resultadoController;
        this.alVolver = alVolver;

        configurarVentana();
        construirInterfaz();
    }

    /**
     * Configura las propiedades de la ventana.
     */
    private void configurarVentana() {

        frame.setSize(600, 350);

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Regresa al menu cuando se cierra
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                if (alVolver != null) {
                    alVolver.run();
                }
            }
        });
    }

    /**
     * Construye la tabla del historial.
     */
    private void construirInterfaz() {

        // Obtiene los resultados del usuario
        List<Resultado> historial =
                resultadoController.obtenerHistorial();

        // Define las columnas
        String[] columnas = {
                "#",
                "Numero",
                "Color",
                "Apuesta",
                "Monto",
                "Resultado",
                "Saldo"
        };

        // Modelo de tabla no editable
        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(
                            int fila, int columna) {

                        return false;
                    }
                };

        int n = 1;

        // Recorre los resultados del historial
        for (Resultado resultado : historial) {

            modelo.addRow(new Object[]{
                    n++,
                    resultado.getNumero(),
                    resultado.getColor(),
                    resultado.getApuesta().getEtiqueta(),
                    "$" + resultado.getMonto(),
                    resultado.isAcierto()
                            ? "GANASTE"
                            : "PERDISTE",
                    "$" + resultado.getSaldoActual()
            });
        }

        // Construye la tabla
        JTable tabla = new JTable(modelo);

        // Mensaje cuando el historial esta vacio
        JLabel lblVacio = new JLabel(
                historial.isEmpty()
                        ? "Aún no has jugado ninguna ronda."
                        : " "
        );

        // Boton para volver al menu
        JButton btnVolver = new JButton("Volver");

        btnVolver.addActionListener(
                e -> frame.dispose()
        );

        JPanel sur = new JPanel(
                new BorderLayout(5, 5)
        );

        sur.add(lblVacio, BorderLayout.CENTER);
        sur.add(btnVolver, BorderLayout.EAST);

        // Distribuye los componentes
        frame.setLayout(new BorderLayout());

        frame.add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        frame.add(sur, BorderLayout.SOUTH);
    }

    /**
     * Muestra la ventana centrada.
     */
    public void mostrarVentana() {

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

