package RogueLikeTut;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpritePanel extends JPanel {
    private static final long serialVersionUID = 3034994395811568366L;

    private final BufferedImage offscreenBuffer;
    private final Graphics offscreenGraphics;

    public Graphics graphics() { return offscreenGraphics; }

    public SpritePanel(int width, int height) {
        offscreenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.getGraphics();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        g.drawImage(offscreenBuffer,0,0,this);
    }
}
