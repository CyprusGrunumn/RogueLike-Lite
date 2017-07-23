package RogueLikeTut;

import javax.swing.*;

import RogueLikeTut.screens.Screen;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import RogueLikeTut.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {


    private static final long serialVersionUID = -4978082929122180476L;

    private Screen screen;
    private Renderer renderer;

    public ApplicationMain(){
        super();
        AsciiPanel terminal = new AsciiPanel();
        int width = terminal.getCharWidth() * terminal.getWidthInCharacters();
        int height = terminal.getCharHeight() * terminal.getHeightInCharacters();
        SpritePanel canvas = new SpritePanel(width, height);
        renderer = new Renderer(terminal, canvas);
        OverlayLayout layout = new OverlayLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        setupCanvas(canvas);
        add(terminal);
        add(canvas);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void setupCanvas(JPanel canvas){
        canvas.setPreferredSize(renderer.terminal().getPreferredSize());
        canvas.setBackground(Color.blue);
    }

    @Override
    public void repaint(){
        renderer.terminal().clear();
        screen.displayOutput(renderer);
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
