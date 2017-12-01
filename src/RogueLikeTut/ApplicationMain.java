package RogueLikeTut;

import javax.swing.*;

import RogueLikeTut.screens.Screen;
import RogueLikeTut.spritePanel.SpriteLibrary;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.*;

import RogueLikeTut.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {
    private static final long serialVersionUID = -4978082929122180476L;

    private static int charWidth = 80;
    private static int charHeight = 24;

    private static int scale = 1;

    private SpritePanel terminal;

    private Screen screen;
    private SpriteLibrary library;

    public ApplicationMain(){
        super();
        library = new SpriteLibrary();
        SpriteFactory factory = new SpriteFactory(library);
        terminal = new SpritePanel(charWidth, charHeight, scale, library);
        //terminal.enableSprites(false);
        this.setBackground(Color.black);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    private void resize() {
        // We need an integer ceil here, not floor
        charWidth = this.getContentPane().getWidth() / (terminal.getCharWidth() * terminal.getScale());
        charHeight = this.getContentPane().getHeight() / (terminal.getCharHeight() * terminal.getScale());

        if (terminal.getWidthInCharacters() != charWidth || terminal.getHeightInCharacters() != charHeight) {
            remove(terminal);
            terminal = new SpritePanel(charWidth, charHeight, terminal.getScale(), library);
            add(terminal);

            validate();
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        resize();
        super.paint(g);
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
