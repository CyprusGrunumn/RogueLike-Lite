package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.Renderer;
import asciiPanel.AsciiPanel;

public class WinScreen implements AsciiScreen {
    public void displayOutput(Renderer renderer) {
        AsciiPanel terminal = renderer.terminal();
        terminal.write("You Won!", 1, 1);
        terminal.writeCenter("--press [Enter] to restart--", 22);
    }

    public AsciiScreen respondToUserInput(KeyEvent key){
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(): this;
    }
}
