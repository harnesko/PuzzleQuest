package main;

import entity.EntityType;
import entity.NPC_Luigi;
import entity.NPC_Mario;
import gameObject.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // TODO: ej effektivt, funkar ba för en mapp ju

    public void setObjects(){
    int mapNum = 0;
    gp.obj[mapNum][0] = new Key();
    gp.obj[mapNum][0].worldX = 23 * gp.tileSize;
    gp.obj[mapNum][0].worldY = 7 * gp.tileSize;

    gp.obj[mapNum][1] = new Key();
    gp.obj[mapNum][1].worldX = 23 * gp.tileSize;
    gp.obj[mapNum][1].worldY = 40 * gp.tileSize;

    gp.obj[mapNum][2] = new Key();
    gp.obj[mapNum][2].worldX = 38 * gp.tileSize;
    gp.obj[mapNum][2].worldY = 8 * gp.tileSize;

    gp.obj[mapNum][3] = new Door();
    gp.obj[mapNum][3].worldX = 10 * gp.tileSize;
    gp.obj[mapNum][3].worldY = 11 * gp.tileSize;

    gp.obj[mapNum][4] = new Door();
    gp.obj[mapNum][4].worldX = 8 * gp.tileSize;
    gp.obj[mapNum][4].worldY = 28 * gp.tileSize;

    gp.obj[mapNum][5] = new Door();
    gp.obj[mapNum][5].worldX = 12 * gp.tileSize;
    gp.obj[mapNum][5].worldY = 22 * gp.tileSize;

    gp.obj[mapNum][6] = new Chest();
    gp.obj[mapNum][6].worldX = 10 * gp.tileSize;
    gp.obj[mapNum][6].worldY = 7 * gp.tileSize;

    gp.obj[0][7] = new Teleporter();
    gp.obj[0][7].worldX = 50 * gp.tileSize;     //[0] means map 0, Main town
    gp.obj[0][7].worldY = 12 * gp.tileSize;

    gp.obj[1][7] = new Teleporter();
    gp.obj[1][7].worldX = 11 * gp.tileSize;
    gp.obj[1][7].worldY = 13 * gp.tileSize;
    }

    public void setNPC(){

        int mapNum = 0;
        //Mario
        gp.npcList[mapNum][0] = new NPC_Mario(gp);
        gp.npcList[mapNum][0].worldX = 1460;
        gp.npcList[mapNum][0].worldY = 1355;

        //Luigi
        gp.npcList[mapNum][1] = new NPC_Luigi(gp);
        gp.npcList[mapNum][1].worldX = 1390;
        gp.npcList[mapNum][1].worldY = 1355;
    }
}
