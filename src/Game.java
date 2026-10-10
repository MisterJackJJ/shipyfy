/**
 * Der Spielablauf: hält das Modell (Board mit Schiffen) und nimmt
 * die Befehle entgegen, die bisher direkt in Main standen –
 * Platzieren, Schießen, Ausgeben, Spielende-Prüfung.
 */
public class Game {

    private final Board board;
    private final BoardView view;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.view = new BoardView();
    }

    /**
     * Platziert ein Schiff auf dem Spielfeld.
     *
     * @return true, wenn die Platzierung gültig war
     */
    public boolean placeShip(Ship ship) {
        return board.placeShip(ship);
    }

    /**
     * Beschreibt ein Feld und gibt das Ergebnis aus.
     *
     * @return true bei Treffer
     */
    public boolean shoot(int x, int y) {
        boolean hit = board.shoot(x, y);
        System.out.println("Schuss auf (" + x + "," + y + "): "
                + (hit ? "Treffer" : "Fehlschuss"));
        return hit;
    }

    /**
     * Zeigt das aktuelle Spielfeld an.
     */
    public void showBoard() {
        view.print(board);
    }

    /**
     * Prüft, ob alle Schiffe versenkt sind.
     */
    public boolean isGameOver() {
        return board.allShipsSunk();
    }
}
