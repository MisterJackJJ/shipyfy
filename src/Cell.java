/**
 * Zellzustände des Spielfelds mit zugehörigem Anzeigesymbol (Unicode).
 */
public enum Cell {
    WATER('≈'),
    SHIP('⛵'),
    HIT('💥'),
    MISS('•'),
    SUNK('☠');

    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
