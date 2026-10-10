/**
 * Ansicht (View) für ein Spielfeld: gibt das Model-Array zeilenweise aus.
 */
public class ModelView {

    /**
     * Gibt das Spielfeld zeilenweise aus (eigene Sicht: Schiffe sichtbar).
     */
    public void print(Model model) {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                System.out.print(model.getCell(i, j).getSymbol());
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
