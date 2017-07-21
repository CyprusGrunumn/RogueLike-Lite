package RogueLikeTut.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements AsciiScreen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Lose.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public AsciiScreen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}