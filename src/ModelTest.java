/**
 * Einfacher Test ohne JUnit: prüft Platzierung, Treffer und versenkte Schiffe.
 * Ausführen mit: java -ea -cp out ModelTest
 */
public class ModelTest {

    public static void main(String[] args) {
        Model model = new Model(10, 10);

        // Platzierung
        Ship ship = new Ship(0, 0, Direction.HORIZONTAL, 3, 10, 10);
        assert model.placeShip(ship) : "Schiff muss platzierbar sein";
        assert !model.placeShip(new Ship(0, 1, Direction.VERTICAL, 4, 10, 10))
                : "Kollidierendes Schiff darf nicht platziert werden";

        // Herausragendes Schiff wirft bereits im Konstruktor
        try {
            new Ship(9, 9, Direction.VERTICAL, 3, 10, 10);
            assert false : "Herausragendes Schiff muss eine Exception werfen";
        } catch (IllegalArgumentException expected) {
        }

        // Treffer in den Schiffsfeldern
        assert model.shoot(0, 0) : "Treffer erwartet";
        assert !ship.isSunk() : "Schiff darf noch nicht versenkt sein";
        assert !model.shoot(5, 5) : "Fehlschuss erwartet";
        assert model.getCell(0, 0) == Cell.HIT : "Teiltreffer muss HIT sein, nicht SUNK";
        assert model.shoot(0, 1) && model.shoot(0, 2) : "Treffer erwartet";
        assert ship.isSunk() : "Schiff muss versenkt sein";
        assert model.getCell(0, 0) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert model.getCell(0, 1) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert model.getCell(0, 2) == Cell.SUNK : "Versenkte Schiffsteile müssen SUNK sein";
        assert model.getCell(5, 5) == Cell.MISS : "Zelle muss MISS sein";
        assert model.allShipsSunk() : "Einziges Schiff ist versenkt";

        System.out.println("Alle Tests bestanden.");
    }
}
