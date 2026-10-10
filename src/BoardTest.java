/**
 * Einfacher Test ohne JUnit: prüft Platzierung, Treffer und versenkte Schiffe.
 * Ausführen mit: java -ea -cp out BoardTest
 */
public class BoardTest {

    public static void main(String[] args) {
        Board board = new Board(10, 10);

        // Platzierung
        Ship ship = new Ship(0, 0, Direction.HORIZONTAL, 3);
        assert board.placeShip(ship) : "Schiff muss platzierbar sein";
        assert !board.placeShip(new Ship(0, 1, Direction.VERTICAL, 4))
                : "Kollidierendes Schiff darf nicht platziert werden";
        assert !board.placeShip(new Ship(9, 9, Direction.VERTICAL, 3))
                : "Schiff außerhalb des Felds darf nicht platziert werden";

        // Treffer über die verkettete Liste
        assert board.shoot(0, 0) : "Treffer erwartet";
        assert !ship.isSunk() : "Schiff darf noch nicht versenkt sein";
        assert !board.shoot(5, 5) : "Fehlschuss erwartet";
        assert board.getCell(0, 0) == Cell.HIT : "Teiltreffer muss HIT sein, nicht SUNK";
        assert board.shoot(0, 1) && board.shoot(0, 2) : "Treffer erwartet";
        assert ship.isSunk() : "Schiff muss versenkt sein";
        assert board.getCell(0, 0) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert board.getCell(0, 1) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert board.getCell(0, 2) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert board.getCell(5, 5) == Cell.MISS : "Zelle muss MISS sein";
        assert board.allShipsSunk() : "Einziges Schiff ist versenkt";

        System.out.println("Alle Tests bestanden.");
    }
}
