package MainMenu;

import java.awt.*;

public class GamePanel {
    myFrame frame;
    int width = 1000;
    int height = 800;
    boolean fullscreen = false;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Clean Up is needed
     */
    public GamePanel(){
        frame = new myFrame(this,width,height);
        if(!frame.panel.fullscreen){
            frame.dispose();
            frame.repaint();
            this.width = 1000;
            this.height = 800;
        }else {
            this.width = (int) size.getWidth();
            this.height = (int) size.getHeight();
            frame.dispose();
            frame.repaint();
        }
        frame.dispose();
        frame = new myFrame(this,width,height);
        frame.repaint();

    }

}
