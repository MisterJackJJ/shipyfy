/**
 * Einstiegspunkt: erzeugt ein Spiel und stößt die Demo an.
 * Die eigentliche Logik liegt in Game.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 10);

        Ship destroyer = new Ship(2, 3, Direction.HORIZONTAL, 3);
        Ship cruiser = new Ship(5, 7, Direction.VERTICAL, 4);
        Ship invalid = new Ship(8, 8, Direction.HORIZONTAL, 5); // ragt heraus

        System.out.println("Zerstörer platziert: " + game.placeShip(destroyer));
        System.out.println("Kreuzer platziert: " + game.placeShip(cruiser));
        System.out.println("Ungültiges Schiff platziert: " + game.placeShip(invalid));

        System.out.println("\nSpielfeld nach Platzierung:");
        game.showBoard();

        game.shoot(2, 3);
        game.shoot(2, 4);
        game.shoot(2, 5);

        System.out.println("\nSpielfeld nach Beschuss:");
        game.showBoard();
        System.out.println("Spiel beendet: " + game.isGameOver());
    }
}
