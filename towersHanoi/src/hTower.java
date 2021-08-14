import java.util.Stack;

public class hTower {
    private Stack<Integer> rings = new Stack<Integer>();

    public void addRing(int r) {

        if (!rings.isEmpty() && rings.peek() <= r) {
            System.out.println("Can't place ring " + r);
        } else { rings.push(r); }
    }

    public void moveTopRing(hTower nextTower) {
        int topRing = rings.pop();
        nextTower.addRing(topRing);
    }

    public void moveRings(int n, hTower end, hTower holder) {
        if (n <= 0) return;

        moveRings(n -1, holder, end);
        moveTopRing(end);
        holder.moveRings(n-1, end, this);

    }

    hTower() {
        this.rings = new Stack<Integer>();
    }

}

