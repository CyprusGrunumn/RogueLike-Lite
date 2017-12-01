package RogueLikeTut.screens;

import java.awt.event.KeyEvent;

import RogueLikeTut.spritePanel.SpritePanel;

public interface Screen {
    public Screen respondToUserInput(KeyEvent key);
    public void displayOutput(SpritePanel terminal);
}
