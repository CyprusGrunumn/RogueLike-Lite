package RogueLikeTut;

import javax.swing.*;

import RogueLikeTut.screens.Screen;
import RogueLikeTut.spritePanel.SpriteLibrary;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import RogueLikeTut.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {
    private static final long serialVersionUID = -4978082929122180476L;
    private final SpritePanel terminal;

    private Screen screen;

    public ApplicationMain(){
        super();
        SpriteLibrary library = new SpriteLibrary();
        SpriteFactory factory = new SpriteFactory(library);
        terminal = new SpritePanel(45, 24, library);
        //terminal.enableSprites(false);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint(){
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
