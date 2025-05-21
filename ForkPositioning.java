import java.awt.*;
import java.util.Map;

public class ForkPositioning {
    public static final Map<String, Point> forkOffsets = Map.ofEntries(
            Map.entry("1_P1", new Point(580, 130)),
            Map.entry("1_P5", new Point(470, 175)),
            Map.entry("2_P1", new Point(630, 180)),
            Map.entry("2_P2", new Point(660, 300)),
            Map.entry("3_P2", new Point(570, 390)),
            Map.entry("3_P3", new Point(470, 430)),
            Map.entry("4_P3", new Point(390, 430)),
            Map.entry("4_P4", new Point(320, 350)),
            Map.entry("5_P4", new Point(330, 220)),
            Map.entry("5_P5", new Point(410, 170))
    );
}
