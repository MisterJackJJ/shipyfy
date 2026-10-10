/**
 * Ein einzelnes Feld eines Schiffs als Knoten einer einfach verketteten Liste.
 * Jedes Feld kennt seine Position auf dem Spielfeld und ob es getroffen wurde.
 */
public class ShipPart {

    private final int x;
    private final int y;
    private boolean hit;
    private ShipPart next;

    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.next = null;
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

    public ShipPart getNext() {
        return next;
    }

    public void setNext(ShipPart next) {
        this.next = next;
    }
}
