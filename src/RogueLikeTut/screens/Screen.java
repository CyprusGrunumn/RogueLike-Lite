package RogueLikeTut.screens;

import RogueLikeTut.Renderer;

import java.awt.event.KeyEvent;

/**
 * Created by Cyprus on 7/20/2017.
 */
public interface Screen {
    public Screen respondToUserInput(KeyEvent key);
    public void displayOutput(Renderer renderer);
}
