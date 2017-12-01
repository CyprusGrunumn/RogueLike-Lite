package RogueLikeTut;

import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

/**
 * Created by Cyprus on 7/20/2017.
 */
public class Renderer {
    private AsciiPanel terminal;
    public AsciiPanel terminal() { return terminal; }

    private SpritePanel canvas;
    public SpritePanel canvas() { return canvas; }

    public Renderer(AsciiPanel terminal, SpritePanel canvas) {
        this.terminal = terminal;
        this.canvas = canvas;
    }

}