package RogueLikeTut.spritePanel;

import java.awt.*;

public class Sprite {
    private Image sheet;
    private int x;
    private int y;

    public Image getSheet() { return sheet; }
    public int getX() { return x; }
    public int getY() { return y; }

    public Sprite(Image sheet, int x, int y) {
        this.sheet = sheet;
        this.x = x;
        this.y = y;
    }
}
