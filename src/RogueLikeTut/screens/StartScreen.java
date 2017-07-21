package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.Renderer;
import asciiPanel.AsciiPanel;

public class StartScreen implements AsciiScreen {

    public void displayOutput(Renderer renderer) {
        AsciiPanel terminal = renderer.terminal();
        terminal.write("rl Tutorial", 1, 1);
        terminal.writeCenter("--Press [Enter] to start--", 22);
    }

    public AsciiScreen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
