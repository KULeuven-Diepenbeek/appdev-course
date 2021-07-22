package be.kuleuven.walkerfx.model;

public class Walker {
    private int x;
    private int y;

    private static final int MODIFIER = 5;

    public Walker(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void goLeft() {
        x-= MODIFIER;
    }

    public void goRight() {
        x+= MODIFIER;
    }

    public void goUp() {
        y-= MODIFIER;
    }

    public void goDown() {  y+= MODIFIER; }
}
