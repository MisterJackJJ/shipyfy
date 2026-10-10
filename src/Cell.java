/**
 * Zellzustände des Spielfelds mit zugehörigem Anzeigesymbol.
 */
public enum Cell {
    WATER('w'),
    SHIP('s'),
    HIT('x'),
    MISS('o'),
    SUNK('#');

    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
