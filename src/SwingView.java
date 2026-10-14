import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * Grafische Ansicht des Spielfelds als JFrame mit einem JButton pro Zelle.
 *
 * Anzeige-Modi:
 * 1. Bild-Modus:    wenn unter resources/ PNGs liegen (resources/water.png,
 *                   resources/water_0.png, ...), werden diese geladen.
 * 2. Fallback-Modus: sonst werden die Unicode-Symbole der Zellen angezeigt.
 *
 * Animationen:
 * - Dauerschleifen (z. B. Wasser mit frameCount > 1) laufen über einen Timer.
 * - Einmal-Effekte (z. B. Explosions-Frames von HIT) laufen einmal und
 *   enden im statischen Endzustand.
 */
public class SwingView {

    private static final String RESOURCE_DIR = "resources/";
    private static final int FRAME_DELAY_MS = 200;
    private static final int ANIM_DELAY_MS = 120;

    private final Model model;
    private final CellClickListener listener;
    private final JFrame frame;
    private final JButton[][] buttons;
    private final Map<String, ImageIcon> iconCache = new HashMap<>();
    private final boolean iconsAvailable;

    private Timer loopTimer;      // Dauerschleifen (Wasser-Wellen)
    private int loopFrame = 0;

    /** Callback für Zell-Klicks: liefert die geschossene Position. */
    public interface CellClickListener {
        void onCellClick(int x, int y);
    }

    /** Sichtweise auf ein Brett: Besitzer sieht Schiffe, Gegner nicht. */
    public enum Sight {
        OWNER,
        OPPONENT
    }

    private final Sight sight;

    public SwingView(Model model, CellClickListener listener) {
        this(model, listener, Sight.OWNER);
    }

    public SwingView(Model model, CellClickListener listener, Sight sight) {
        this.model = model;
        this.listener = listener;
        this.sight = sight;
        this.buttons = new JButton[model.getRows()][model.getCols()];
        this.iconsAvailable = iconsExist();

        this.frame = new JFrame("Schiffe versenken");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(model.getRows(), model.getCols()));

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                if (!iconsAvailable) {
                    button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                }
                final int x = i;
                final int y = j;
                button.addActionListener(e -> {
                    if (listener != null) {
                        listener.onCellClick(x, y);
                    }
                    refresh();
                });
                buttons[i][j] = button;
                frame.add(button);
            }
        }
        refresh();
        frame.pack();
        frame.setLocationRelativeTo(null); // zentrieren

        if (iconsAvailable) {
            startLoopTimer();
        }
    }

    /**
     * Zeigt das Fenster (Thread-sicher).
     */
    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    /**
     * Liest alle Zellen neu aus dem Model und aktualisiert die Buttons.
     */
    public void refresh() {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                Cell cell = visibleCell(model.getCell(i, j));
                if (iconsAvailable) {
                    buttons[i][j].setIcon(iconFor(cell, loopFrame));
                } else {
                    buttons[i][j].setText(String.valueOf(cell.getSymbol()));
                }
            }
        }
    }

    /**
     * Übersetzt eine Modell-Zelle in die angezeigte Zelle:
     * In der OPPONENT-Sicht werden intakte Schiffe (SHIP) als Wasser
     * (WATER) dargestellt, damit der Gegner die Flotte nicht sieht.
     * Treffer, Fehlschüsse und versenkte Schiffe bleiben sichtbar.
     */
    private Cell visibleCell(Cell cell) {
        if (sight == Sight.OPPONENT && cell == Cell.SHIP) {
            return Cell.WATER;
        }
        return cell;
    }

    /**
     * Spielt die Frames einer Zelle einmal ab (z. B. Explosion bei HIT)
     * und endet im letzten Frame. Läuft nur im Bild-Modus.
     */
    public void playOnce(Cell cell) {
        if (!iconsAvailable || !cell.isAnimated()) {
            return;
        }
        for (int f = 0; f < cell.getFrameCount(); f++) {
            int frameIndex = f;
            Timer t = new Timer(ANIM_DELAY_MS * (f + 1), e -> {
                for (int i = 0; i < model.getRows(); i++) {
                    for (int j = 0; j < model.getCols(); j++) {
                        if (model.getCell(i, j) == cell) {
                            buttons[i][j].setIcon(iconFor(cell, frameIndex));
                        }
                    }
                }
            });
            t.setRepeats(false);
            t.start();
        }
    }

    /**
     * Lädt das Icon für eine Zelle (mit Caching) oder null, wenn nicht vorhanden.
     */
    private ImageIcon iconFor(Cell cell, int frame) {
        String file = cell.getFrameFile(frame % cell.getFrameCount());
        return iconCache.computeIfAbsent(file, f -> {
            java.net.URL url = getClass().getClassLoader().getResource(RESOURCE_DIR + f);
            if (url == null) {
                return null;
            }
            return new ImageIcon(url);
        });
    }

    /**
     * Startet den Timer für Dauerschleifen-Animationen (z. B. Wasser).
     */
    private void startLoopTimer() {
        loopTimer = new Timer(FRAME_DELAY_MS, e -> {
            loopFrame++;
            for (int i = 0; i < model.getRows(); i++) {
                for (int j = 0; j < model.getCols(); j++) {
                    Cell cell = model.getCell(i, j);
                    if (cell.isAnimated()) {
                        buttons[i][j].setIcon(iconFor(cell, loopFrame));
                    }
                }
            }
        });
        loopTimer.start();
    }

    /**
     * Prüft, ob mindestens ein Icon unter resources/ liegt
     * (der erste Frame jeder Zelle genügt als Probe).
     */
    private boolean iconsExist() {
        for (Cell cell : Cell.values()) {
            java.net.URL url = getClass().getClassLoader()
                    .getResource(RESOURCE_DIR + cell.getFrameFile(0));
            if (url == null) {
                return false;
            }
        }
        return true;
    }
}
