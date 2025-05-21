import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;

public class Fork {
    private int number;
    private Philosopher heldBy;
    private int x;
    private int y;
    private Image image;




    public int getNumber() {
        return number;
    }

    public Fork (int number, int x, int y) {
        this.number = number;
        this.heldBy = null;
        this.image = loadImage(""+this.number);
        this.x = x;
        this.y = y;
    }

    public Philosopher getHeldBy() {
        return heldBy;
    }

    public void setHeldBy(Philosopher heldBy) {
        this.heldBy = heldBy;
    }

    public String toString () {
        if (this.heldBy == null) {
            return "Fork " + this.number
                     + " is not currently held by anyone";

        } else {
            return "Fork " + this.number +
                    " is currently held by " +
                    this.heldBy.getName();

        }
    }
    private Image loadImage(String filename) {
        String path = "/images/Fork " + this.number + ".png";
        URL resource = getClass().getResource(path);
        if (resource == null) {
            System.err.println("Error: picture isnt in path " + path);
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        return new ImageIcon(resource).getImage();
    }
    public void draw(Graphics g) {
        int drawX = this.x;
        int drawY = this.y;

        if (this.heldBy != null) {
            String key = this.number + "_" + heldBy.getName();
            Point p = ForkPositioning.forkOffsets.getOrDefault(key, new Point(this.x, this.y));
            drawX = p.x;
            drawY = p.y;
        }

        g.drawImage(image, drawX, drawY, null);
    }

}
