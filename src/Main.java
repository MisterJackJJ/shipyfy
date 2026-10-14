/**
 * Einstiegspunkt: erzeugt ein Spiel, platziert Demo-Schiffe und öffnet
 * die grafische Ansicht. Schüsse erfolgen per Klick auf eine Zelle;
 * parallel läuft die Konsole-Ausgabe mit.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 10);

        Ship destroyer = new Ship(2, 3, Direction.HORIZONTAL, 3, 10, 10);
        Ship cruiser = new Ship(5, 7, Direction.VERTICAL, 4, 10, 10);

        System.out.println("Zerstörer platziert: " + game.placeShip(destroyer));
        System.out.println("Kreuzer platziert: " + game.placeShip(cruiser));

        System.out.println("\nKonsole-Ansicht nach Platzierung:");
        game.showField();

        // Grafische Ansicht öffnen: Klick auf eine Zelle = Schuss
        game.showSwingView();
    }
}
