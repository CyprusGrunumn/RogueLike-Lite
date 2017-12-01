package RogueLikeTut.screens;

import RogueLikeTut.Creature;
import RogueLikeTut.Line;
import RogueLikeTut.Point;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by Cyprus on 11/29/2017.
 */
public class TargetBasedScreen implements Screen {

    protected PlayScreen playScreen;
    protected Creature player;
    protected String caption;
    private int sx;
    private int sy;
    private int x;
    private int y;

    public TargetBasedScreen(PlayScreen playScreen, Creature player, String caption, int sx, int sy){
        this.playScreen = playScreen;
        this.player = player;
        this.caption = caption;
        this.sx = sx;
        this.sy = sy;
    }

    public void displayOutput(SpritePanel terminal){
        int screenSx = sx - playScreen.getScrollX(terminal.getWidthInCharacters());
        int screenSy = sy - playScreen.getScrollY(terminal.getHeightInCharacters());
        for (Point p : new Line(screenSx, screenSy, screenSx + x, screenSy + y)){
            if(p.x < 0 || p.x >= 80 || p.y < 0 || p.y >= 24)
                continue;

            terminal.write('*', p.x, p.y, AsciiPanel.brightMagenta);
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write(caption, 0, 23);
    }

    public Screen respondToUserInput(KeyEvent key) {
        int px = x;
        int py = y;

        switch (key.getKeyCode()){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: x--; break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: x++; break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_J: y--; break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_K: y++; break;
            case KeyEvent.VK_Y: x--; y--; break;
            case KeyEvent.VK_U: x++; y--; break;
            case KeyEvent.VK_B: x--; y++; break;
            case KeyEvent.VK_N: x++; y++; break;
            case KeyEvent.VK_ENTER: selectWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y); return null;
            case KeyEvent.VK_ESCAPE: return null;
        }

        if (!isAcceptable(player.x + x, player.y + y)){
            x = px;
            y = py;
        }

        enterWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y);

        return this;
    }

    public boolean isAcceptable(int x, int y){
        return true;
    }

    public void enterWorldCoordinate(int x, int y, int screenX, int screenY){

    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){

    }
}
