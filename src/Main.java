/**
 * Einstiegspunkt: erzeugt ein Spiel mit zwei Spielern, jeder platziert
 * seine Schiffe, dann öffnet sich die grafische Ansicht. Klick auf eine
 * Zelle = Schuss des aktuellen Spielers auf das gegnerische Feld.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 10);

        Player anna = new Player("Anna", 10, 10);
        Player ben = new Player("Ben", 10, 10);
        game.addPlayer(anna);
        game.addPlayer(ben);

        // Platzierungsphase: jeder platziert seine Flotte
        game.placeShip(anna, new Ship(2, 3, Direction.HORIZONTAL, 3, 10, 10));
        game.placeShip(anna, new Ship(5, 7, Direction.VERTICAL, 4, 10, 10));
        game.placeShip(ben, new Ship(1, 1, Direction.VERTICAL, 3, 10, 10));
        game.placeShip(ben, new Ship(6, 4, Direction.HORIZONTAL, 4, 10, 10));

        System.out.println("Beide Spieler haben platziert. Anna beginnt.");
        System.out.println("\nAnnas Sicht auf Bens Brett (Gegner-Sicht):");

        // Grafische Ansicht: zeigt das Brett, auf das Anna schießt
        game.showSwingView();
    }
}
