import java.util.ArrayList;
import java.util.List;

/**
 * Der Spielablauf: verwaltet die Spieler, den Zugwechsel und die Views.
 *
 * Verantwortungs-Trennung:
 * - Player besitzt sein Spielfeld (Model) mit seinen Schiffen.
 * - Model kennt die Regeln für EIN Feld (shoot, placeShip, allShipsSunk).
 * - Game vermittelt: Wer ist dran? Auf wen wird geschossen? Wann ist
 *   der Zug vorbei (Fehlschuss) und wann das Spiel beendet?
 */
public class Game {

    private final List<Player> players = new ArrayList<>();
    private int rows;
    private int cols;
    private int currentPlayerIndex = 0;
    private final ModelView view;
    private SwingView swingView;

    /**
     * Spielfeldgröße (für später hinzugefügte Spieler).
     */
    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.view = new ModelView();
    }

    /**
     * Nimmt einen Spieler ins Spiel auf.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Der Spieler, der gerade am Zug ist.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Wechselt zum nächsten Spieler (bei Fehlschuss).
     */
    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Der Gegner des aktuell Spielenden (bei zwei Spielern der andere,
     * bei mehreren der nächste in der Runde).
     */
    public Player getTarget() {
        int targetIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(targetIndex);
    }

    /**
     * Platziert ein Schiff auf dem Spielfeld des angegebenen Spielers.
     *
     * @return true, wenn die Platzierung gültig war
     */
    public boolean placeShip(Player owner, Ship ship) {
        return owner.getBoard().placeShip(ship);
    }

    /**
     * Der aktuelle Spieler schießt auf das Feld des Gegners.
     * Treffer = weiter am Zug (klassische Regel), Fehlschuss = Zugwechsel.
     *
     * @return true bei Treffer
     */
    public boolean shoot(int x, int y) {
        Player shooter = getCurrentPlayer();
        Player target = getTarget();
        boolean hit = target.getBoard().shoot(x, y);
        System.out.println(shooter.getName() + " schießt auf (" + x + "," + y + "): "
                + (hit ? "Treffer" : "Fehlschuss"));
        if (swingView != null) {
            swingView.refresh();
            if (hit) {
                swingView.playOnce(hitCellAt(target, x, y));
            }
        }
        if (!hit) {
            nextTurn();
        }
        return hit;
    }

    /**
     * Ermittelt die Zellart an der geschossenen Position nach dem Schuss
     * (HIT für Teiltreffer, SUNK für versenkt) – entscheidet über die
     * abzuspielende Animation.
     */
    private Cell hitCellAt(Player target, int x, int y) {
        return target.getBoard().getCell(x, y);
    }

    /**
     * Zeigt das Spielfeld eines Spielers auf der Konsole an.
     */
    public void showField(Player player) {
        view.print(player.getBoard());
    }

    /**
     * Zeigt das Spielfeld des Spielers, auf den gerade geschossen wird.
     */
    public void showField() {
        view.print(getTarget().getBoard());
    }

    /**
     * Öffnet die grafische Ansicht des Bretts, auf das der aktuelle
     * Spieler schießt (Gegner-Sicht: Schiffe bleiben verborgen).
     * Klick auf eine Zelle = Schuss.
     */
    public void showSwingView() {
        this.swingView = new SwingView(getTarget().getBoard(), this::shoot,
                SwingView.Sight.OPPONENT);
        this.swingView.show();
    }

    /**
     * Prüft, ob alle Schiffe irgendeines Spielers versenkt sind
     * (dann hat der andere gewonnen).
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getBoard().allShipsSunk()) {
                return true;
            }
        }
        return false;
    }
}
