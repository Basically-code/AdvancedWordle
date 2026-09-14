import javax.swing.*;
import java.awt.*;

public class GUIgame{

    public static void main(String[] args){
        JFrame frame = new JFrame("GridLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Create a grid with 3 rows, 2 columns, 5px horizontal gap, 5px vertical gap
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));
        panel.add(new JButton("Button 3"));
        panel.add(new JButton("Button 4"));
        panel.add(new JButton("Button 5"));
        panel.add(new JButton("Button 6"));
        frame.add(panel);
        frame.setVisible(true);
    }
}