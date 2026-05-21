import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Random;

public class MainPanel extends JPanel {
    private final Image BG;
    Fork fork1 = new Fork(1,584,188);
    Fork fork2 = new Fork(2,640,335);
    Fork fork3 = new Fork(3,487,450);
    Fork fork4 = new Fork(4,335,335);
    Fork fork5 = new Fork(5,390,188);

    Philosopher philosopher1 = new Philosopher("P1", fork1, fork5);
    Philosopher philosopher2 = new Philosopher("P2", fork1, fork2);
    Philosopher philosopher3 = new Philosopher("P3", fork2, fork3);
    Philosopher philosopher4 = new Philosopher("P4", fork3, fork4);
    Philosopher philosopher5 = new Philosopher("P5", fork5, fork1);

    private JLabel philosopherNameLabel1;
    private JLabel philosopherNameLabel2;
    private JLabel philosopherNameLabel3;
    private JLabel philosopherNameLabel4;
    private JLabel philosopherNameLabel5;
    public MainPanel(){
        this.setLayout(null);
        BG = new ImageIcon(getClass().getResource("/images/DP_BG.png")).getImage();
        philosopherNameLabel1 = new JLabel(labelUpdatedText(philosopher1));
        philosopherNameLabel1.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLabel1.setForeground(Color.black);
        philosopherNameLabel1.setBounds(580, 30, 200, 100);
        add(philosopherNameLabel1);

        philosopherNameLabel2 = new JLabel(labelUpdatedText(philosopher2));
        philosopherNameLabel2.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLabel2.setForeground(Color.black);
        philosopherNameLabel2.setBounds(770, 250, 200, 100);
        add(philosopherNameLabel2);

        philosopherNameLabel3 = new JLabel(labelUpdatedText(philosopher3));
        philosopherNameLabel3.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLabel3.setForeground(Color.black);
        philosopherNameLabel3.setBounds(730, 500, 200, 100);
        add(philosopherNameLabel3);

        philosopherNameLabel4 = new JLabel(labelUpdatedText(philosopher4));
        philosopherNameLabel4.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLabel4.setForeground(Color.black);
        philosopherNameLabel4.setBounds(250, 500, 200, 100);
        add(philosopherNameLabel4);

        philosopherNameLabel5 = new JLabel(labelUpdatedText(philosopher5));
        philosopherNameLabel5.setFont(new Font("Impact", Font.BOLD, 18));
        philosopherNameLabel5.setForeground(Color.black);
        philosopherNameLabel5.setBounds(250, 250, 200, 100);
        add(philosopherNameLabel5);

        Philosopher[] philosophers = {philosopher1, philosopher2, philosopher3, philosopher4, philosopher5};
        JLabel[] nameLabels = {philosopherNameLabel1, philosopherNameLabel2, philosopherNameLabel3, philosopherNameLabel4, philosopherNameLabel5};
        Random random = new Random();

        JButton stopRandomButton = new JButton("Stop Philosopher");
        stopRandomButton.setBounds(900, 50, 150, 30);
        stopRandomButton.addActionListener(e -> {
            int index = random.nextInt(philosophers.length);
            nameLabels[index].setForeground(Color.GRAY);
            philosophers[index].stop();
        });
        add(stopRandomButton);


        new Thread(() -> {
            while (true) {
                System.out.println(philosopher1);
                System.out.println(philosopher2);
                System.out.println(philosopher3);
                System.out.println(philosopher4);
                System.out.println(philosopher5);

                SwingUtilities.invokeLater(() -> {
                    philosopherNameLabel1.setText(labelUpdatedText(philosopher1));
                    philosopherNameLabel1.setForeground(philosopher1.isStopped() ? Color.GRAY : eatingColor(philosopher1));

                    philosopherNameLabel2.setText(labelUpdatedText(philosopher2));
                    philosopherNameLabel2.setForeground(philosopher2.isStopped() ? Color.GRAY : eatingColor(philosopher2));

                    philosopherNameLabel3.setText(labelUpdatedText(philosopher3));
                    philosopherNameLabel3.setForeground(philosopher3.isStopped() ? Color.GRAY : eatingColor(philosopher3));

                    philosopherNameLabel4.setText(labelUpdatedText(philosopher4));
                    philosopherNameLabel4.setForeground(philosopher4.isStopped() ? Color.GRAY : eatingColor(philosopher4));

                    philosopherNameLabel5.setText(labelUpdatedText(philosopher5));
                    philosopherNameLabel5.setForeground(philosopher5.isStopped() ? Color.GRAY : eatingColor(philosopher5));

                    repaint();
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
        if (philosopher.getStatus() == Philosopher.EATING){
            color = Color.red;
        }
        else {
            color = Color.BLACK;
        }
        return color;
    }


}
