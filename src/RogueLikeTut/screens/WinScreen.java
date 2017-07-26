package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.Renderer;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {
    public void displayOutput(SpritePanel terminal) {
        terminal.write("You Won!", 1, 1);
        terminal.writeCenter("--press [Enter] to restart--", 22);
    }

    public Screen respondToUserInput(KeyEvent key){
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(): this;
    }
}
