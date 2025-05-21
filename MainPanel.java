import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainPanel extends JPanel {
    private final Image BG;
    Fork fork1 = new Fork(1,584,188);
    Fork fork2 = new Fork(2,640,335);
    Fork fork3 = new Fork(3,487,450);
    Fork fork4 = new Fork(4,335,335);
    Fork fork5 = new Fork(5,390,188);

    Philosopher philosopher1 = new Philosopher("P1", fork1, fork2);
    Philosopher philosopher2 = new Philosopher("P2", fork2, fork3);
    Philosopher philosopher3 = new Philosopher("P3", fork3, fork4);
    Philosopher philosopher4 = new Philosopher("P4", fork4, fork5);
    Philosopher philosopher5 = new Philosopher("P5", fork5, fork1);

    private JLabel philosopherNameLable1;
    private JLabel philosopherNameLable2;
    private JLabel philosopherNameLable3;
    private JLabel philosopherNameLable4;
    private JLabel philosopherNameLable5;
    public MainPanel(){
        this.setLayout(null);
        BG = new ImageIcon(getClass().getResource("/images/DP_BG.png")).getImage();
        philosopherNameLable1 = new JLabel(labelUpdatedText(philosopher1));
        philosopherNameLable1.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLable1.setForeground(Color.black);
        philosopherNameLable1.setBounds(580, 30, 200, 100);
        add(philosopherNameLable1);

        philosopherNameLable2 = new JLabel(labelUpdatedText(philosopher2));
        philosopherNameLable2.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLable2.setForeground(Color.black);
        philosopherNameLable2.setBounds(770, 250, 200, 100);
        add(philosopherNameLable2);

        philosopherNameLable3 = new JLabel(labelUpdatedText(philosopher3));
        philosopherNameLable3.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLable3.setForeground(Color.black);
        philosopherNameLable3.setBounds(700, 400, 200, 100);
        add(philosopherNameLable3);

        philosopherNameLable4 = new JLabel(labelUpdatedText(philosopher4));
        philosopherNameLable4.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLable4.setForeground(Color.black);
        philosopherNameLable4.setBounds(340, 400, 200, 100);
        add(philosopherNameLable4);

        philosopherNameLable5 = new JLabel(labelUpdatedText(philosopher4));
        philosopherNameLable5.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLable5.setForeground(Color.black);
        philosopherNameLable5.setBounds(290, 250, 200, 100);
        add(philosopherNameLable5);

        new Thread(() -> {
            while (true) {
                System.out.println(philosopher1);
                System.out.println(philosopher2);
                System.out.println(philosopher3);
                System.out.println(philosopher4);
                System.out.println(philosopher5);

                SwingUtilities.invokeLater(() -> {
                    philosopherNameLable1.setText(labelUpdatedText(philosopher1));
                    philosopherNameLable1.setForeground(eatingColor(philosopher1));
                    philosopherNameLable2.setText(labelUpdatedText(philosopher2));
                    philosopherNameLable2.setForeground(eatingColor(philosopher2));
                    philosopherNameLable3.setText(labelUpdatedText(philosopher3));
                    philosopherNameLable3.setForeground(eatingColor(philosopher3));
                    philosopherNameLable4.setText(labelUpdatedText(philosopher4));
                    philosopherNameLable4.setForeground(eatingColor(philosopher4));
                    philosopherNameLable5.setText(labelUpdatedText(philosopher5));
                    philosopherNameLable5.setForeground(eatingColor(philosopher5));
                });

                Utils.sleep(1000);
            }
        }).start();

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), this);
        fork1.draw(g);
        fork2.draw(g);
        fork3.draw(g);
        fork4.draw(g);
        fork5.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1080, 720);
    }
    public String labelUpdatedText(Philosopher philosopher){
        return "<html>" + philosopher.getName() + "<br>" +
                philosopher.getStatusText() + "<br>" +
                "Ate: " + philosopher.getEatingCount() + " times</html>";
    }
    public Color eatingColor (Philosopher philosopher){
        Color color;
        if (philosopher.getStatus()== 4){
            color = Color.red;
        }
        else {
            color = Color.BLACK;
        }
        return color;
    }


}
