package RogueLikeTut;

import RogueLikeTut.spritePanel.Sprite;
import RogueLikeTut.spritePanel.SpriteLibrary;

import java.awt.*;

public class SpriteFactory {
    private final SpriteLibrary library;

    public SpriteFactory(SpriteLibrary library) {
        this.library = library;

        addTiles();
        addCreatures();
        addItems();
    }

    public SpriteFactory addSprite(char character, String resource, int x, int y) {
        return addSprite(character, null, null, resource, x, y);
    }

    public SpriteFactory addSprite(char character, Color foreground, String resource, int x, int y) {
        return addSprite(character, foreground, null, resource, x, y);
    }

    public SpriteFactory addSprite(Tile tile, String resource, int x, int y) {
        return addSprite(tile.glyph(), tile.color(), null, resource, x, y);
    }

    public SpriteFactory addSprite(StuffFactory.Glyph glyph, String resource, int x, int y) {
        return addSprite(glyph.glyph(), glyph.color(), null, resource, x, y);
    }

    public SpriteFactory addSprite(char character, Color foreground, Color background, String resource, int x, int y) {
        library.addSprite(character, foreground, background, "RogueLikeTut/resources/DawnLike/" + resource, x, y);

        return this;
    }

    private void addTiles() {
        addSprite(Tile.FLOOR,
                "Objects/Floor.png",
                1, 19);

        addSprite(Tile.FLOOR.glyph(), Color.darkGray, null,
                "Objects/Floor.png",
                1, 25);

        addSprite(Tile.WALL,
                "Objects/Wall.png",
                10, 15);

        addSprite(Tile.WALL.glyph(), Color.darkGray, null,
                "Objects/Wall.png",
                10, 21);
    }

    private void addCreatures(){
        addSprite(StuffFactory.Glyph.FUNGUS,
                "Characters/Plant0.png",
                0, 7);
        addSprite(StuffFactory.Glyph.BAT,
                "Characters/Avian0.png",
                0, 11);
        addSprite(StuffFactory.Glyph.PLAYER,
                "Characters/Player0.png",
                0, 0);
    }

    private void addItems() {
        addSprite(StuffFactory.Glyph.ROCK,
                "Items/Rock.png",
                0, 1);
    }
}
