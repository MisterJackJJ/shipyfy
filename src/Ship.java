/**
 * Ein Schiff auf dem Spielfeld.
 * Das Schiff kennt seine Position über seine Felder, die als
 * verkettete Liste verwaltet werden. Treffer werden am
 * entsprechenden Feld (Knoten) markiert.
 */
public class Ship {

    private final ShipPart head;
    private final int size;

    /**
     * Erstellt ein Schiff ab Position (x, y) in Richtung direction
     * mit size Feldern.
     *
     * @param x         Startzeile
     * @param y         Startspalte
     * @param direction Direction.HORIZONTAL oder Direction.VERTICAL
     * @param size      Anzahl der Felder
     */
    public Ship(int x, int y, Direction direction, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Schiff muss mindestens ein Feld haben.");
        }
        this.size = size;
        ShipPart current = null;
        ShipPart first = null;
        for (int i = 0; i < size; i++) {
            int partX = direction == Direction.VERTICAL ? x + i : x;
            int partY = direction == Direction.HORIZONTAL ? y + i : y;
            ShipPart part = new ShipPart(partX, partY);
            if (first == null) {
                first = part;
            } else {
                current.setNext(part);
            }
            current = part;
        }
        this.head = first;
    }

    public int getSize() {
        return size;
    }

    public ShipPart getHead() {
        return head;
    }

    /**
     * Trifft für jedes Feld des Schiffs die übergebene Aktion.
     * Ermöglicht das Durchlaufen aller Teile, ohne die Liste
     * nach außen zu geben.
     */
    public void forEachPart(java.util.function.Consumer<ShipPart> action) {
        ShipPart current = head;
        while (current != null) {
            action.accept(current);
            current = current.getNext();
        }
    }

    /**
     * Prüft, ob das Schiff ein Feld an Position (x, y) belegt.
     */
    public boolean occupies(int x, int y) {
        ShipPart current = head;
        while (current != null) {
            if (current.getX() == x && current.getY() == y) {
                return true;
            }
            current = current.getNext();
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
        ShipPart current = head;
        while (current != null) {
            if (current.getX() == x && current.getY() == y) {
                current.markHit();
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Prüft, ob alle Felder des Schiffs getroffen sind (Schiff versenkt).
     */
    public boolean isSunk() {
        ShipPart current = head;
        while (current != null) {
            if (!current.isHit()) {
                return false;
            }
            current = current.getNext();
        }
        return true;
    }

    /**
     * Prüft, ob das Schiff mit einem anderen Schiff kollidiert
     * (gleiche Felder belegt) oder direkt daran anschließt
     * (Schiffe dürfen sich nicht berühren).
     */
    public boolean collidesWith(Ship other) {
        ShipPart current = head;
        while (current != null) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (other.occupies(current.getX() + dx, current.getY() + dy)) {
                        return true;
                    }
                }
            }
            current = current.getNext();
        }
        return false;
    }
}
