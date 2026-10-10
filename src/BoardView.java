/**
 * Ansicht (View) für ein Spielfeld: gibt das Board-Array zeilenweise aus.
 */
public class BoardView {

    /**
     * Gibt das Spielfeld zeilenweise aus (eigene Sicht: Schiffe sichtbar).
     */
    public void print(Board board) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                System.out.print(board.getCell(i, j).getSymbol());
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
