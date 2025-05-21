import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        MainPanel mainPanel = new MainPanel();

        JFrame window = new JFrame("Dining Philosophers Problem");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1080, 720));
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.add(mainPanel);
        window.setVisible(true);
    }
}