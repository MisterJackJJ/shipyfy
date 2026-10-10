/**
 * Das Modell des Spiels: verwaltet die platzierten Schiffe und das
 * Spielfeldraster (Grid) und leitet Beschüsse an das jeweilige
 * Schiff weiter.
 */
public class Model {

    private final Grid grid;
    private final java.util.List<Ship> ships;

    public Model(int rows, int cols) {
        this.grid = new Grid(rows, cols);
        this.ships = new java.util.ArrayList<>();
    }

    public int getRows() {
        return grid.getRows();
    }

    public int getCols() {
        return grid.getCols();
    }

    public Cell getCell(int x, int y) {
        return grid.getCell(x, y);
    }

    /**
     * Prüft, ob ein Schiff an der gegebenen Position und Richtung
     * vollständig auf dem Spielfeld liegt.
     */
    public boolean isValidPlacement(int x, int y, Direction direction, int size) {
        int endX = direction == Direction.VERTICAL ? x + size - 1 : x;
        int endY = direction == Direction.HORIZONTAL ? y + size - 1 : y;
        return x >= 0 && y >= 0 && endX < getRows() && endY < getCols();
    }

    /**
     * Platziert ein Schiff, sofern keine Kollision mit einem
     * bestehenden Schiff vorliegt.
     *
     * @return true, wenn das Schiff platziert wurde
     */
    public boolean placeShip(Ship ship) {
        for (Ship other : ships) {
            if (ship.collidesWith(other)) {
                return false;
            }
        }
        ships.add(ship);
        markShipOnGrid(ship);
        return true;
    }

    private void markShipOnGrid(Ship ship) {
        for (int i = 0; i < ship.getSize(); i++) {
            ShipPart part = ship.getPart(i);
            grid.setCell(part.getX(), part.getY(), Cell.SHIP);
        }
    }

    /**
     * Beschreibt das Feld an Position (x, y).
     *
     * @return true bei Treffer, false bei Fehlschuss
     */
    public boolean shoot(int x, int y) {
        if (!grid.isInBounds(x, y)) {
            return false;
        }
        for (Ship ship : ships) {
            if (ship.registerHit(x, y)) {
                if (ship.isSunk()) {
                    markSunk(ship);
                } else {
                    grid.setCell(x, y, Cell.HIT);
                }
                return true;
            }
        }
        grid.setCell(x, y, Cell.MISS);
        return false;
    }

    /**
     * Markiert alle Felder eines versenkten Schiffs als SUNK.
     */
    private void markSunk(Ship ship) {
        for (int i = 0; i < ship.getSize(); i++) {
            ShipPart part = ship.getPart(i);
            grid.setCell(part.getX(), part.getY(), Cell.SUNK);
        }
    }

    /**
     * Prüft, ob an Position (x, y) ein Schiff liegt.
     */
    public boolean hasShipAt(int x, int y) {
        for (Ship ship : ships) {
            if (ship.occupies(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt das Schiff an Position (x, y) zurück oder null.
     */
    public Ship getShipAt(int x, int y) {
        for (Ship ship : ships) {
            if (ship.occupies(x, y)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Prüft, ob alle Schiffe versenkt sind (Spielende).
     */
    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}
