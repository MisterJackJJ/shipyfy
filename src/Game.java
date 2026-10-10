/**
 * Der Spielablauf: hält das Modell (Model mit Schiffen) und nimmt
 * die Befehle entgegen, die bisher direkt in Main standen –
 * Platzieren, Schießen, Ausgeben, Spielende-Prüfung.
 */
public class Game {

    private final Model model;
    private final ModelView view;

    public Game(int rows, int cols) {
        this.model = new Model(rows, cols);
        this.view = new ModelView();
    }

    /**
     * Platziert ein Schiff auf dem Spielfeld.
     *
     * @return true, wenn die Platzierung gültig war
     */
    public boolean placeShip(Ship ship) {
        return model.placeShip(ship);
    }

    /**
     * Beschreibt ein Feld und gibt das Ergebnis aus.
     *
     * @return true bei Treffer
     */
    public boolean shoot(int x, int y) {
        boolean hit = model.shoot(x, y);
        System.out.println("Schuss auf (" + x + "," + y + "): "
                + (hit ? "Treffer" : "Fehlschuss"));
        return hit;
    }

    /**
     * Zeigt das aktuelle Spielfeld an.
     */
    public void showField() {
        view.print(model);
    }

    /**
     * Prüft, ob alle Schiffe versenkt sind.
     */
    public boolean isGameOver() {
        return model.allShipsSunk();
    }
}
