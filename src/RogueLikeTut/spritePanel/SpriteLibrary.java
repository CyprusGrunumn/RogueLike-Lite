package RogueLikeTut.spritePanel;

import RogueLikeTut.StuffFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpriteLibrary {
    private Map<String, BufferedImage> spriteSheets;
    private Map<Integer, Sprite> sprites;

    public SpriteLibrary() {
        spriteSheets = new HashMap<>();
        sprites = new HashMap<>();
    }

    private void loadSpriteSheet(String resource) {
        try {
            BufferedImage sheet = ImageIO.read(
                    SpriteLibrary.class.getClassLoader().getResource(resource));
            spriteSheets.put(resource, sheet);
        } catch (IOException e) {
            System.err.println("loadSpriteSheet(): " + e.getMessage());
        }
    }

    private int getCompoundHash(char glyph, Color foreground, Color background) {
        final int prime = 31;
        int result = 1;
        result = prime * result + glyph;
        result = prime * result + Objects.hashCode(foreground);
        result = prime * result + Objects.hashCode(background);
        return result;
    }

    public void addSprite(char glyph, Color foreground, Color background, String sheet, int x, int y) {
        if (!spriteSheets.containsKey(sheet)) {
            loadSpriteSheet(sheet);
        }
        sprites.put(getCompoundHash(glyph, foreground, background), new Sprite(spriteSheets.get(sheet), x, y));
    }

    public Sprite getSprite(char glyph, Color foreground, Color background) {
        return sprites.get(getCompoundHash(glyph, foreground, background));
    }
}
