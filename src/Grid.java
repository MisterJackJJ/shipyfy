/**
 * Das Spielfeldraster: kapselt das 2D-Array der Zellzustände
 * einschließlich Rahmenprüfung und Zellzugriff.
 */
public class Grid {

    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    public Grid(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Spielfeldmaße müssen positiv sein.");
        }
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = Cell.WATER;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Prüft, ob die Position innerhalb des Rasters liegt.
     */
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    /**
     * Gibt die Zelle an Position (x, y) zurück.
     *
     * @throws IllegalArgumentException bei Position außerhalb des Rasters
     */
    public Cell getCell(int x, int y) {
        if (!isInBounds(x, y)) {
            throw new IllegalArgumentException(
                    "Position außerhalb des Spielfelds: (" + x + "," + y + ")");
        }
        return cells[x][y];
    }

    /**
     * Setzt die Zelle an Position (x, y).
     *
     * @throws IllegalArgumentException bei Position außerhalb des Rasters
     */
    public void setCell(int x, int y, Cell cell) {
        if (!isInBounds(x, y)) {
            throw new IllegalArgumentException(
                    "Position außerhalb des Spielfelds: (" + x + "," + y + ")");
        }
        cells[x][y] = cell;
    }
}
