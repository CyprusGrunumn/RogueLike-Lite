package RogueLikeTut.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class WinScreen implements Screen{
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You Won!", 1, 1);
        terminal.writeCenter("--press [Enter] to restart--", 22);
    }

    public Screen respondToUserInput(KeyEvent key){
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(): this;
    }
}
