/**
 * Ein Spieler: besitzt ein eigenes Spielfeld (Model) mit seinen Schiffen
 * und hat einen Namen. Auf dieses Feld schießen die anderen Spieler.
 *
 * Der Spieler selbst enthält keine Spiellogik – Regeln leben im Model,
 * der Spielablauf (wer schießt auf wen, Zugwechsel) in Game.
 */
public class Player {

    private final String name;
    private final Model board;

    public Player(String name, int rows, int cols) {
        this.name = name;
        this.board = new Model(rows, cols);
    }

    public String getName() {
        return name;
    }

    /**
     * Das eigene Spielfeld des Spielers (mit seinen Schiffen).
     */
    public Model getBoard() {
        return board;
    }
}
