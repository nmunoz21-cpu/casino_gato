import javax.swing.*;
import java.awt.*;


public class VentanaSaludo {
    private final JFrame frame = new JFrame("BIENVENIDO AL CASINO BLACK CAT");
    private final JLabel lblSaludo = new JLabel();
    private final JButton btnJugar= new JButton("JUGAR");

    public VentanaSaludo(String nombreUsuario){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,150);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));



    }



}
