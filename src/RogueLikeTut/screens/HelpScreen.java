package RogueLikeTut.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

/**
 * Created by Cyprus on 11/29/2017.
 */
public class HelpScreen implements Screen{

    public void displayOutput(AsciiPanel terminal){
        terminal.clear();
        terminal.writeCenter("RogueLike Help", 1);
        terminal.write("Descend the Caves of Slight Danger, find the lost Teddy bear, and return to", 1, 3);
        terminal.write("the surface to win. Use what you find to avoid dying.", 1, 4);

        int y = 6;
        terminal.write("[g] or [,] to pick up", 2, y++);
        terminal.write("[d] to drop", 2, y++);
        terminal.write("[e] to eat", 2, y++);
        terminal.write("[w] to wear or wield", 2, y++);
        terminal.write("[?] for help", 2, y++);
        terminal.write("[x] to examine your items", 2, y++);
        terminal.write("[;] to look around", 2, y++);
        terminal.write("[t] to throw an item", 2, y++);
        terminal.write("[F] to Fire an item", 2, y++);

        terminal.writeCenter("-- press any key to continue --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}

