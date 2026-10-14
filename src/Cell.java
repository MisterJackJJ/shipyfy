/**
 * Zellzustände des Spielfelds mit zugehörigem Anzeigesymbol (Unicode)
 * und Bild-Dateinamen für die grafische Ansicht.
 *
 * Bild-Konvention (Dateien im Verzeichnis resources/):
 * - Statisches Icon:  NAME_00.png (z. B. SHIP_00.png)
 * - Animations-Frames: NAME_00.png, NAME_01.png, ... (z. B. WATER_00..05.png)
 *   frameCount >= 2 bedeutet Animation.
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
     * z. B. "SHIP_00.png" bzw. "WATER_03.png" für Frame 3.
     */
    public String getFrameFile(int frame) {
        return imageName.toUpperCase() + "_"
                + String.format("%02d", frame) + ".png";
    }

    /**
     * Ob die Zelle animiert dargestellt werden soll.
     */
    public boolean isAnimated() {
        return frameCount > 1;
    }
}
