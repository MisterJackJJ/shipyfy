/**
 * Ein einzelnes Feld eines Schiffs. Jedes Feld kennt seine
 * Position auf dem Spielfeld und ob es getroffen wurde.
 */
public class ShipPart {

    private final int x;
    private final int y;
    private boolean hit;

    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void markHit() {
        this.hit = true;
    }
}
