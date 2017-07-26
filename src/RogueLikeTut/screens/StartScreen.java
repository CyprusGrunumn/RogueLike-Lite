package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.Renderer;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

    public void displayOutput(SpritePanel terminal) {
        terminal.write("rl Tutorial", 1, 1);
        terminal.writeCenter("--Press [Enter] to start--", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
