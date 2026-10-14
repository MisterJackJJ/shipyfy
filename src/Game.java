/**
 * Der Spielablauf: hält das Modell (Model mit Schiffen) und die Views
 * (Konsole und optional JFrame) und nimmt die Befehle entgegen –
 * Platzieren, Schießen, Ausgeben, Spielende-Prüfung.
 */
public class Game {

    private final Model model;
    private final ModelView view;
    private SwingView swingView;

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
     * Beschreibt ein Feld, aktualisiert beide Views und gibt das
     * Ergebnis auf der Konsole aus.
     *
     * @return true bei Treffer
     */
    public boolean shoot(int x, int y) {
        boolean hit = model.shoot(x, y);
        System.out.println("Schuss auf (" + x + "," + y + "): "
                + (hit ? "Treffer" : "Fehlschuss"));
        if (swingView != null) {
            swingView.refresh();
            if (hit) {
                swingView.playOnce(hitCellAt(x, y));
            }
        }
        return hit;
    }

    /**
     * Ermittelt die Zellart an der geschossenen Position nach dem Schuss
     * (HIT für Teiltreffer, SUNK für versenkt) – entscheidet über die
     * abzuspielende Animation.
     */
    private Cell hitCellAt(int x, int y) {
        return model.getCell(x, y);
    }

    /**
     * Zeigt das aktuelle Spielfeld auf der Konsole an.
     */
    public void showField() {
        view.print(model);
    }

    /**
     * Öffnet zusätzlich die grafische Ansicht. Klicks auf Zellen
     * werden als Schuss interpretiert.
     */
    public void showSwingView() {
        this.swingView = new SwingView(model, this::shoot);
        this.swingView.show();
    }

    /**
     * Prüft, ob alle Schiffe versenkt sind.
     */
    public boolean isGameOver() {
        return model.allShipsSunk();
    }
}
