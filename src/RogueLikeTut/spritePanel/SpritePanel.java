package RogueLikeTut.spritePanel;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpritePanel extends AsciiPanel {
    private static final long serialVersionUID = 3034994395811568366L;
    private static final Color TRANSPARENT = new Color(0, true);

    private final BufferedImage offscreenBuffer;
    private final Graphics offscreenGraphics;

    private final BufferedImage overlayBuffer;
    private final Graphics overlayGraphics;

    private final Sprite[][] oldSprites;
    private final Sprite[][] sprites;

    private final SpriteLibrary library;
    private boolean spritesEnabled;

    public boolean spritesEnabled() { return spritesEnabled; }
    public SpritePanel enableSprites(boolean spritesEnabled) {
        this.spritesEnabled = spritesEnabled;
        return this;
    }

    public SpriteLibrary getSpriteLibrary() { return library; }

    public Graphics getOverlayGraphics() { return overlayGraphics; }

    public SpritePanel(int width, int height, SpriteLibrary library) {
        super(width, height);
        setAsciiFont(AsciiFont.CP437_16x16);

        spritesEnabled = true;

        offscreenBuffer = new BufferedImage(width * getCharWidth(), height * getCharHeight(), BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreenBuffer.getGraphics();
        ((Graphics2D)offscreenGraphics).setBackground(TRANSPARENT);

        overlayBuffer = new BufferedImage(width * getCharWidth(), height * getCharHeight(), BufferedImage.TYPE_INT_ARGB);
        overlayGraphics = overlayBuffer.getGraphics();
        ((Graphics2D)overlayGraphics).setBackground(TRANSPARENT);

        oldSprites = new Sprite[getWidthInCharacters()][getHeightInCharacters()];
        sprites = new Sprite[getWidthInCharacters()][getHeightInCharacters()];

        this.library = library;
    }

    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        super.paint(g);

        if (!spritesEnabled)
            return;

        for (int x = 0; x < getWidthInCharacters(); x++) {
            for (int y = 0; y < getHeightInCharacters(); y++) {
                if (oldSprites[x][y] == sprites[x][y])
                    continue;

                Sprite sprite = sprites[x][y];

                if (sprite != null) {
                    Image img = sprite.getSheet();
                    offscreenGraphics.drawImage(img,
                            x * getCharWidth(), y * getCharHeight(),
                            x * getCharWidth() + getCharWidth(), y * getCharHeight() + getCharHeight(),
                            sprite.getX() * getCharWidth(), sprite.getY() * getCharHeight(),
                            sprite.getX() * getCharWidth() + getCharWidth(), sprite.getY() * getCharHeight() + getCharHeight(),
                            null);
                } else {
                    offscreenGraphics.clearRect(
                            x * getCharWidth(), y * getCharHeight(),
                            getCharWidth(), getCharHeight()
                    );
                }

                oldSprites[x][y] = sprites[x][y];
            }
        }

        g.drawImage(offscreenBuffer, 0, 0, this);
        g.drawImage(overlayBuffer, 0, 0, this);
    }

    public boolean isSprite(int x, int y) {
        return sprites[x][y] != null;
    }

    /**
     * Write a character to the cursor's position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, boolean isSprite) {
        return write(character, getCursorX(), getCursorY(), null, null, isSprite);
    }

    /**
     * Write a character to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, Color foreground, boolean isSprite) {
        return write(character, getCursorX(), getCursorY(), foreground, null, isSprite);
    }

    /**
     * Write a character to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, Color foreground, Color background, boolean isSprite) {
        return write(character, getCursorX(), getCursorY(), foreground, background, isSprite);
    }

    /**
     * Write a character to the specified position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, int x, int y, boolean isSprite) {
        return write(character, x, y, null, null, isSprite);
    }

    /**
     * Write a character to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, int x, int y, Color foreground, boolean isSprite) {
        return write(character, x, y, foreground, null, isSprite);
    }

    /**
     * Write a character to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @param isSprite   is the character a sprite?
     * @return this for convenient chaining of method calls
     */
    public SpritePanel write(char character, int x, int y, Color foreground, Color background, boolean isSprite) {
        if (getWidthInCharacters() <= x || getHeightInCharacters() <= y) {
            return this;
        }

        Color fg = foreground;
        Color bg = background;

        super.write(character, x, y, foreground, background);

        if (isSprite)
            sprites[x][y] = library.getSprite(character, fg, bg);
        else
            sprites[x][y] = null;

        overlayGraphics.clearRect(
                x * getCharWidth(), y * getCharHeight(),
                getCharWidth(), getCharHeight()
        );

        return this;
    }

    /**
     * Clear the entire screen to whatever the default background color is.
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel clear() {
        return clear(' ', 0, 0, getWidthInCharacters(), getHeightInCharacters(), getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Clear the entire screen with the specified character and whatever the default foreground and background colors are.
     * @param character  the character to write
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel clear(char character) {
        return clear(character, 0, 0, getWidthInCharacters(), getHeightInCharacters(), getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Clear the entire screen with the specified character and whatever the specified foreground and background colors are.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel clear(char character, Color foreground, Color background) {
        return clear(character, 0, 0, getWidthInCharacters(), getHeightInCharacters(), foreground, background);
    }

    /**
     * Clear the section of the screen with the specified character and whatever the default foreground and background colors are.
     * The cursor position will not be modified.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the height of the section to clear
     * @param height     the width of the section to clear
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel clear(char character, int x, int y, int width, int height) {
        return clear(character, x, y, width, height, getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Clear the section of the screen with the specified character and whatever the specified foreground and background colors are.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the height of the section to clear
     * @param height     the width of the section to clear
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel clear(char character, int x, int y, int width, int height, Color foreground, Color background) {
        super.clear(character, x, y, width, height, foreground, background);

        for (int xo = x; xo < x + width; xo++) {
            for (int yo = y; yo < y + height; yo++) {
                sprites[xo][yo] = null;

                overlayGraphics.clearRect(
                        xo * getCharWidth(), yo * getCharHeight(),
                        getCharWidth(), getCharHeight()
                );
            }
        }

        return this;
    }

    /**
     * Write a character to the cursor's position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character) {
        return write(character, getCursorX(), getCursorY(), getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Write a character to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character, Color foreground) {
        return write(character, getCursorX(), getCursorY(), foreground, getDefaultBackgroundColor());
    }

    /**
     * Write a character to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character, Color foreground, Color background) {
        return write(character, getCursorX(), getCursorY(), foreground, background);
    }

    /**
     * Write a character to the specified position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character, int x, int y) {
        return write(character, x, y, getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Write a character to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character, int x, int y, Color foreground) {
        return write(character, x, y, foreground, getDefaultBackgroundColor());
    }

    /**
     * Write a character to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(char character, int x, int y, Color foreground, Color background) {
        super.write(character, x, y, foreground, background);

        sprites[x][y] = null;

        overlayGraphics.clearRect(
                x * getCharWidth(), y * getCharHeight(),
                getCharWidth(), getCharHeight()
        );

        return this;
    }

    /**
     * Write a string to the cursor's position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string) {
        return write(string, getCursorX(), getCursorY(), getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Write a string to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string, Color foreground) {
        return write(string, getCursorX(), getCursorY(), foreground, getDefaultBackgroundColor());
    }

    /**
     * Write a string to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string, Color foreground, Color background) {
        return write(string, getCursorX(), getCursorY(), foreground, background);
    }

    /**
     * Write a string to the specified position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string, int x, int y) {
        return write(string, x, y, getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Write a string to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string, int x, int y, Color foreground) {
        return write(string, x, y, foreground, getDefaultBackgroundColor());
    }

    /**
     * Write a string to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel write(String string, int x, int y, Color foreground, Color background) {
        if (getWidthInCharacters() <= x || getHeightInCharacters() <= y) {
            return this;
        }

        if (string.length() + x > getWidthInCharacters()) {
            string = string.substring(0, getWidthInCharacters() - x - 1);
        }

        super.write(string, x, y, foreground, background);

        for (int i = 0; i < string.length(); i++) {
            sprites[x + i][y] = null;

            overlayGraphics.clearRect(
                    (x + i) * getCharWidth(), y * getCharHeight(),
                    getCharWidth(), getCharHeight()
            );
        }

        return this;
    }

    /**
     * Write a string to the center of the panel at the specified y position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel writeCenter(String string, int y) {
        return writeCenter(string, y, getDefaultForegroundColor(), getDefaultBackgroundColor());
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel writeCenter(String string, int y, Color foreground) {
        return writeCenter(string, y, foreground, getDefaultBackgroundColor());
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    @Override
    public SpritePanel writeCenter(String string, int y, Color foreground, Color background) {
        if (getHeightInCharacters() <= y) {
            return this;
        }

        if (string.length() > getWidthInCharacters()) {
            int excess = (string.length() - getWidthInCharacters()) / 2;
            string = string.substring(excess, getWidthInCharacters() + excess - 1);
        }

        super.writeCenter(string, y, foreground, background);

        int x = (getWidthInCharacters() - string.length()) / 2;

        for (int i = 0; i < string.length(); i++) {
            sprites[x + i][y] = null;

            overlayGraphics.clearRect(
                    (x + i) * getCharWidth(), y * getCharHeight(),
                    getCharWidth(), getCharHeight()
            );
        }

        return this;
    }
}
