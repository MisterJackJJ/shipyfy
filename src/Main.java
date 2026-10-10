/**
 * Einstiegspunkt: erzeugt ein Spiel und stößt die Demo an.
 * Die eigentliche Logik liegt in Game.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 10);

        Ship destroyer = new Ship(2, 3, Direction.HORIZONTAL, 3, 10, 10);
        Ship cruiser = new Ship(5, 7, Direction.VERTICAL, 4, 10, 10);

        System.out.println("Zerstörer platziert: " + game.placeShip(destroyer));
        System.out.println("Kreuzer platziert: " + game.placeShip(cruiser));

        System.out.println("\nVersuch eines ungültigen Schiffs (ragt heraus):");
        try {
            new Ship(8, 8, Direction.HORIZONTAL, 5, 10, 10);
            System.out.println("Unerwartet: Schiff wurde erzeugt!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erwartete Exception: " + e.getMessage());
        }

        System.out.println("\nSpielfeld nach Platzierung:");
        game.showField();

        game.shoot(2, 3);
        game.shoot(2, 4);
        game.shoot(2, 5);

        System.out.println("\nSpielfeld nach Beschuss:");
        game.showField();
        System.out.println("Spiel beendet: " + game.isGameOver());
    }
}
