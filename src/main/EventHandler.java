package main;

import entity.EntityType;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = false;
/*
        solidArea = new Rectangle();        //Hitbox
        solidArea.x = 8;                    //Defines center of hitbox
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png for guide
 */
    public EventHandler(GamePanel gp){
        this.gp = gp;

        int maxWorldCol = gp.mapManager.getMapList().get(0).measureMap(1);
        int maxWorldRow = gp.mapManager.getMapList().get(0).measureMap(2);
        eventRect = new EventRect[gp.maxMap][maxWorldCol][maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < maxWorldCol && row < maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;        //position?
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 32;    //size
            eventRect[map][col][row].height = 32;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == maxWorldCol){
                col = 0;
                row++;

                if (row == maxWorldRow){
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
            }
            if (hit(1, 12, 9, "any")){     //Same as comment above but for another map
                teleport(0,50,13);
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
           // eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
           // eventRect[map][col][row].y = row + gp.tileSize + eventRect[map][col][row].y;

            //first if clause is what breaks it, it's never true for w/e reason

            System.out.println("Eventrect col: : " + col + " Row: " + row);
            System.out.println("Player pos X: " + gp.player.worldX / gp.tileSize + " Player pos y " + gp.player.worldY / gp.tileSize);
            System.out.println("Player solid area x: " + gp.player.solidArea.x / gp.tileSize+ "\nPlayer solid area y : " + gp.player.solidArea.y / gp.tileSize);
            /*
              (Notes)
              Player pos (row, col) seems correct
              eventRect pos (col)(row) is determined where?
              Check why they never intersects, first if clause is what's breaking it (its never true).
             */

            int teleportIndex = gp.collisionChecker.checkObject(gp.player, EntityType.PLAYER);
            int tpIndex = gp.collisionChecker.checkObject(gp.player, EntityType.PLAYER);
            System.out.println("Test index : " + teleportIndex);
            if (teleportIndex == 7 && gp.currentMap == 0){     //Teleporter 1 is in obj[7]
               /* System.out.println("Current map no: " + gp.currentMap);
                teleport2(1,  12,13);    //Target map and position
                System.out.println("Current map no: " + gp.currentMap);*/
                System.out.println("TRUE");
                return true;
            }
             else if(tpIndex == 7 && gp.currentMap == 1){
                System.out.println("Current map no: " + gp.currentMap);
                //teleport2(0,  23,23);     //Target map and position
                System.out.println("Current map no: " + gp.currentMap);
                return true;
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            //Everything eventRect[][][] is commented our rn, maybe it can be removed completely?
//            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
//            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        if (hit) {
            System.out.println("IT FUCKING WORKS?!");
        }
        return hit;
    }
    public void teleport2(int targetMap, int col, int row){ //The parameters are used to update the players position
        //Set map, followed by x/y co-ordinates for player pos(tilesize, not pixels)
        gp.currentMap = targetMap;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        //Set camera?
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
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
