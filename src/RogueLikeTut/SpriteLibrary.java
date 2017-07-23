package RogueLikeTut;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {
    private Map<String, BufferedImage> spriteSheets;
    private Map<Character, Sprite> sprites;

    public SpriteLibrary() {
        spriteSheets = new HashMap<>();
        sprites = new HashMap<>();
    }

    public void loadSpriteSheet(String name, String resource) {
        try {
            BufferedImage sheet = ImageIO.read(
                    SpriteLibrary.class.getClassLoader().getResource("RogueLikeTut/resources/" + resource));
            spriteSheets.put(name, sheet);
        } catch (IOException e) {
            System.err.println("loadSpriteSheet(): " + e.getMessage());
        }
    }

    public void addSprite(char glyph, String sheet, int x, int y, int w, int h) {
        sprites.put(glyph, new Sprite(sheet, x, y, w, h));
    }

    public Sprite getSprite(char glyph) {
        return sprites.get(glyph);
    }

    public class Sprite {
        private String sheet;
        private int x;
        private int y;
        private int w;
        private int h;

        public BufferedImage sheet() { return spriteSheets.get(sheet); }
        public int x() { return x; }
        public int y() { return y; }
        public int w() { return w; }
        public int h() { return h; }

        public Sprite(String sheet, int x, int y, int w, int h) {
            this.sheet = sheet;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
}
