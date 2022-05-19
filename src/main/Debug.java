package main;

import java.awt.*;

public class Debug {


    public void showMapTiles(Graphics2D g2, int screenX, int screenY, int tileSize, int tileIndex){
        g2.setColor(Color.blue);
        String s = String.valueOf(tileIndex);
        g2.drawRect(screenX,screenY,tileSize,tileSize);
        g2.drawString(s, screenX, screenY);
    }

    public void showPlayerCollisionBox(Graphics2D g2, int screenX, int screenY, int solidAreaX, int solidAreaY, int solidAreaWidth, int solidAreaHeight){
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidAreaX,screenY + solidAreaY, solidAreaWidth, solidAreaHeight);
    }
}
