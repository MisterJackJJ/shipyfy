/**
 * Das Spielfeld als 2D-Array. Es verwaltet die platzierten Schiffe
 * und leitet Beschüsse an das jeweilige Schiff weiter.
 */
public class Board {

    private final int rows;
    private final int cols;
    private final Cell[][] grid;
    private final java.util.List<Ship> ships;

    public Board(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Spielfeldmaße müssen positiv sein.");
        }
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Cell.WATER;
            }
        }
        this.ships = new java.util.ArrayList<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    /**
     * Prüft, ob ein Schiff an der gegebenen Position und Richtung
     * vollständig auf dem Spielfeld liegt.
     */
    public boolean isValidPlacement(int x, int y, Direction direction, int size) {
        int endX = direction == Direction.VERTICAL ? x + size - 1 : x;
        int endY = direction == Direction.HORIZONTAL ? y + size - 1 : y;
        return x >= 0 && y >= 0 && endX < rows && endY < cols;
    }

    /**
     * Platziert ein Schiff, sofern die Position gültig ist und keine
     * Kollision mit einem bestehenden Schiff vorliegt.
     *
     * @return true, wenn das Schiff platziert wurde
     */
    public boolean placeShip(Ship ship) {
        if (!isValidPlacement(ship)) {
            return false;
        }
        for (Ship other : ships) {
            if (ship.collidesWith(other)) {
                return false;
            }
        }
        ships.add(ship);
        markShipOnGrid(ship);
        return true;
    }

    private boolean isValidPlacement(Ship ship) {
        for (int i = 0; i < ship.getSize(); i++) {
            ShipPart part = ship.getPart(i);
            if (part.getX() < 0 || part.getX() >= rows
                    || part.getY() < 0 || part.getY() >= cols) {
                return false;
            }
        }
        return true;
    }

    private void markShipOnGrid(Ship ship) {
        for (int i = 0; i < ship.getSize(); i++) {
            ShipPart part = ship.getPart(i);
            grid[part.getX()][part.getY()] = Cell.SHIP;
        }
    }

    /**
     * Beschreibt das Feld an Position (x, y).
     *
     * @return true bei Treffer, false bei Fehlschuss
     */
    public boolean shoot(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return false;
        }
        for (Ship ship : ships) {
            if (ship.registerHit(x, y)) {
                if (ship.isSunk()) {
                    markSunk(ship);
                } else {
                    grid[x][y] = Cell.HIT;
                }
                return true;
            }
        }
        grid[x][y] = Cell.MISS;
        return false;
    }

    /**
     * Markiert alle Felder eines versenkten Schiffs als SUNK.
     */
    private void markSunk(Ship ship) {
        for (int i = 0; i < ship.getSize(); i++) {
            ShipPart part = ship.getPart(i);
            grid[part.getX()][part.getY()] = Cell.SUNK;
        }
    }

    /**
     * Prüft, ob an Position (x, y) ein (nicht versenktes) Schiff liegt.
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
