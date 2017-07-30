package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.Renderer;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

    public void displayOutput(SpritePanel terminal) {
        terminal.write("You Lose.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", terminal.getHeightInCharacters() - 2);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}