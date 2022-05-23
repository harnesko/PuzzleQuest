package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
/*
        solidArea = new Rectangle();        //Hitbox
        solidArea.x = 8;                    //Defines center of hitbox
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png for guide
 */
    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;        //position?
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 32;    //size
            eventRect[map][col][row].height = 32;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;

                if (row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }

        }
    }

    public void checkEvent(){

        //Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }
        if (canTouchEvent){
            //It could be this line below that's messing it up IDK
            if (hit(0, 23, 23, "any")){     //Set teleport entry point
                teleport(1,12,13);                    //Set target map and teleport exit point
            }else if (hit(1, 12, 13, "any")){     //Same as comment above but for another map
                teleport(0,21,41);
            }
        }
    }

    /**
     *
     * @param map - What map is currently played on
     * @param col - A set x position on where the teleporter is
     * @param row - A set y position on where the teleporter is
     * @param reqDirection - Sets if player needs to move a certain direction to allow teleport
     * @return - Boolean, if true -> allow teleport
     * @author - Amer, Måns
     */
    public boolean hit(int map, int col, int row, String reqDirection){
        //Todo fix hit, its never true.. figure out why. Setting hit = true -> teleport works
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row + gp.tileSize + eventRect[map][col][row].y;

            //first if clause is what breaks it, it's never true for w/e reason

            System.out.println("Eventrect col: : " + col + " Row: " + row);
            System.out.println("Player pos X: " + gp.player.worldX / gp.tileSize + " Player pos y " + gp.player.worldY / gp.tileSize);
            System.out.println("Player solid area x: " + gp.player.solidArea.x / gp.tileSize+ "\nPlayer solid area y : " + gp.player.solidArea.y / gp.tileSize);
            /**
             * (Notes)
             * Player pos (row, col) seems correct
             * eventRect pos (col)(row) is determined where?
             * Check why they never intersects, first if clause is what's breaking it (its never true).
             */
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;
                    System.out.println("Teleporting..");
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        if (hit) {
            System.out.println("IT FUCKING WORKS?!");
        }
        return hit;
    }

    /**
     * @param map - Set target map to teleport into
     * @param col - Set x position on teleport
     * @param row - Set y position on teleport
     * @author - Amer
     */
    public void teleport(int map, int col, int row){ //The parameters are used to update the players position
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
    }
}
