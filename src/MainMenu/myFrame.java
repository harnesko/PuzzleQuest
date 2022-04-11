package MainMenu;

import javax.swing.*;
import java.awt.*;

public class myFrame extends JFrame {
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public boolean gamestate = true;
    KeyHandler keyH = new KeyHandler(this);
    MainMenu panel;
    int width;
    int height;

    myFrame(GamePanel gamePanel, int width, int height){
        this.width = width;
        this.height = height;
        panel = new MainMenu(gamePanel, this.width, this.height);
        /*
            Todo Fixa Fullscreen
         */
        if(!panel.fullscreen){
            this.width = 1000;
            this.height = 800;
            this.setSize(this.width, this.height);
            panel.revalidate();
            panel.repaint();
        }
        else{
            this.width = (int) size.getWidth();
            this.height = (int) size.getHeight();
            this.setSize(this.width, this.height);
            panel.revalidate();
            panel.repaint();

        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }
    public MainMenu getPanel() {
        return panel;
    }


}
