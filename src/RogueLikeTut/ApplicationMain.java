package RogueLikeTut;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import RogueLikeTut.screens.AsciiScreen;
import RogueLikeTut.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {

    private static final long serialVersionUID = -4978082929122180476L;

    private AsciiPanel terminal;
    private AsciiScreen asciiScreen;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        asciiScreen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint(){
        terminal.clear();
        asciiScreen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        asciiScreen = asciiScreen.respondToUserInput(e);
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
