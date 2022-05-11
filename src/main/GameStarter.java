package main;

import javax.swing.*;
import java.awt.*;

public class GameStarter {
    public static JFrame window;
    /**
     * Bla bla bla
     *  @author Kinda
     *  Added an if statment so if fullscreen is activated setUndecorated is activated.
     *  This removes the borders so that the screen is just the game.
     *  @author Kristoffer
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Puzzle Quest Demo ?!!");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.ui.fullscreen){
            window.setUndecorated(true);
        }
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}

// öppna inte player foldern, städar det sen - k