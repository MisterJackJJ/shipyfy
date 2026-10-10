/**
 * Ein Schiff auf dem Spielfeld.
 * Das Schiff kennt seine Position über seine Felder, die in einem
 * Array verwaltet werden (die Größe ist von Beginn an fest).
 * Treffer werden am entsprechenden Feld markiert.
 */
public class Ship {

    private final ShipPart[] parts;

    /**
     * Erstellt ein Schiff ab Position (x, y) in Richtung direction
     * mit size Feldern. Das Schiff muss vollständig innerhalb des
     * Spielfelds liegen, sonst wird eine Exception geworfen.
     *
     * @param x         Startzeile
     * @param y         Startspalte
     * @param direction Direction.HORIZONTAL oder Direction.VERTICAL
     * @param size      Anzahl der Felder
     * @param rows      Höhe des Spielfelds
     * @param cols      Breite des Spielfelds
     */
    public Ship(int x, int y, Direction direction, int size, int rows, int cols) {
        if (size <= 0) {
            throw new IllegalArgumentException("Schiff muss mindestens ein Feld haben.");
        }
        int endX = direction == Direction.VERTICAL ? x + size - 1 : x;
        int endY = direction == Direction.HORIZONTAL ? y + size - 1 : y;
        if (x < 0 || y < 0 || endX >= rows || endY >= cols) {
            throw new IllegalArgumentException(
                    "Schiff ragt über das Spielfeld hinaus: (" + x + "," + y + ") "
                    + direction + ", Größe " + size);
        }
        this.parts = new ShipPart[size];
        for (int i = 0; i < size; i++) {
            int partX = direction == Direction.VERTICAL ? x + i : x;
            int partY = direction == Direction.HORIZONTAL ? y + i : y;
            parts[i] = new ShipPart(partX, partY);
        }
    }

    public int getSize() {
        return parts.length;
    }

    /**
     * Gibt das Feld des Schiffs am Index index zurück.
     */
    public ShipPart getPart(int index) {
        return parts[index];
    }

    /**
     * Prüft, ob das Schiff ein Feld an Position (x, y) belegt.
     */
    public boolean occupies(int x, int y) {
        for (ShipPart part : parts) {
            if (part.getX() == x && part.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Markiert das Feld an Position (x, y) als getroffen, sofern es
     * zum Schiff gehört.
     *
     * @return true, wenn das Schiff an dieser Position getroffen wurde
     */
    public boolean registerHit(int x, int y) {
        for (ShipPart part : parts) {
            if (part.getX() == x && part.getY() == y) {
                part.markHit();
                return true;
            }
        }
        return false;
    }

    /**
     * Prüft, ob alle Felder des Schiffs getroffen sind (Schiff versenkt).
     */
    public boolean isSunk() {
        for (ShipPart part : parts) {
            if (!part.isHit()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prüft, ob das Schiff mit einem anderen Schiff kollidiert
     * (gleiche Felder belegt) oder direkt daran anschließt
     * (Schiffe dürfen sich nicht berühren).
     */
    public boolean collidesWith(Ship other) {
        for (ShipPart part : parts) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (other.occupies(part.getX() + dx, part.getY() + dy)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
