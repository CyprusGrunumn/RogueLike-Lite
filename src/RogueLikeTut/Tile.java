package RogueLikeTut;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR((char)250, AsciiPanel.yellow),
    WALL((char)177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    STAIRS_DOWN('>', AsciiPanel.white),
    STAIRS_UP('<', AsciiPanel.white),
    UNKNOWN(' ', AsciiPanel.white);

    private String details;
    public String details(){ return details; }

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }

    public boolean isDiggable(){
        return this == Tile.WALL;
    }
    public boolean isGround(){
        return this != WALL && this != BOUNDS;
    }
}
