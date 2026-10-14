/**
 * Zellzustände des Spielfelds mit zugehörigem Anzeigesymbol (Unicode)
 * und Bild-Dateinamen für die grafische Ansicht.
 *
 * Bild-Konvention:
 * - Statisches Icon:  resources/&lt;name&gt;.png   (z. B. resources/water.png)
 * - Animations-Frames: resources/&lt;name&gt;_0.png, _1.png, ... (z. B. water_0.png)
 *   Ein Frame reicht für eine Zelle: frameCount &gt;= 2 bedeutet Animation.
 */
public enum Cell {
    WATER("water", 6),
    SHIP("ship", 1),
    HIT("hit", 5),
    MISS("miss", 1),
    SUNK("sunk", 1);

    private final char symbol;
    private final String imageName;
    private final int frameCount;

    Cell(String imageName, int frameCount) {
        this.symbol = defaultSymbol();
        this.imageName = imageName;
        this.frameCount = frameCount;
    }

    private char defaultSymbol() {
        switch (this) {
            case WATER: return '≈';
            case SHIP:  return '⛵';
            case HIT:   return '💥';
            case MISS:  return '•';
            case SUNK:  return '☠';
            default:    return '?';
        }
    }

    public char getSymbol() {
        return symbol;
    }

    /**
     * Basisname der Bilddatei (ohne Pfad und Endung), z. B. "water".
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Anzahl der Animations-Frames. 1 = statisches Bild.
     */
    public int getFrameCount() {
        return frameCount;
    }

    /**
     * Dateiname des Frames (Frame 0 bei statischen Zellen),
     * z. B. "water_0.png" oder "ship.png" wenn frameCount == 1.
     */
    public String getFrameFile(int frame) {
        if (frameCount == 1) {
            return imageName + ".png";
        }
        return imageName + "_" + frame + ".png";
    }

    /**
     * Ob die Zelle animiert dargestellt werden soll.
     */
    public boolean isAnimated() {
        return frameCount > 1;
    }
}
