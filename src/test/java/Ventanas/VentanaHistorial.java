package Ventanas;

import Logica.ResultadoRonda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class VentanaHistorial {

    private final List<ResultadoRonda> historial;
    private final Runnable alVolver;
    private final JFrame frame = new JFrame("HISTORIAL - Casino Black Cat");

    public VentanaHistorial(List<ResultadoRonda> historial, Runnable alVolver) {
        this.historial = historial;
        this.alVolver = alVolver;
        configurarVentana();
        construirInterfaz();
    }

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

    private void construirInterfaz() {
        String[] columnas = {"#", "Numero", "Color", "Apuesta", "Monto", "Resultado", "Saldo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // tabla de solo lectura
            }
        };

        int n = 1;
        for (ResultadoRonda r : historial) {
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

        JLabel lblVacio = new JLabel(historial.isEmpty() ? "Aun no has jugado ninguna ronda." : " ");
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> frame.dispose());

        JPanel sur = new JPanel(new BorderLayout(5, 5));
        sur.add(lblVacio, BorderLayout.CENTER);
        sur.add(btnVolver, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(tabla), BorderLayout.CENTER);
        frame.add(sur, BorderLayout.SOUTH);
    }

    private String nombreApuesta(char tipo) {
        switch (tipo) {
            case 'R': return "Rojo";
            case 'N': return "Negro";
            case 'P': return "Par";
            default:  return "Impar";
        }
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}