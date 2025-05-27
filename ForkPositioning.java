import java.awt.*;
import java.util.Map;

public class ForkPositioning {
    public static final Map<String, Point> forkOffsets = Map.ofEntries(
            Map.entry("1_P1", new Point(520, 150)),
            Map.entry("1_P2", new Point(620, 188)),

            Map.entry("2_P2", new Point(640, 285)),
            Map.entry("2_P3", new Point(640, 370)),

            Map.entry("3_P3", new Point(530, 450)),
            Map.entry("3_P4", new Point(440, 450)),

            Map.entry("4_P4", new Point(335,380)),
            Map.entry("4_P5", new Point(335,270)),

            Map.entry("5_P5", new Point(360,188)),
            Map.entry("5_P1", new Point(430,140))
    );
}
