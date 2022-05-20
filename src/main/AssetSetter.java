package main;

import entity.NPC_Luigi;
import entity.NPC_Mario;
import gameObject.Chest;
import gameObject.Door;
import gameObject.Key;
import interactive_tile.ITDryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // TODO: ej effektivt, funkar ba för en mapp ju

    public void setObject() {
        gp.obj[0] = new Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new Key();
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new Door();
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[4] = new Door();
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new Door();
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new Chest();
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;



    }

    public void setNPC(){
        //Mario
        gp.npcList[0] = new NPC_Mario(gp);
        gp.npcList[0].worldX = 1200;
        gp.npcList[0].worldY = 1355;

        //Luigi
        gp.npcList[1] = new NPC_Luigi(gp);
        gp.npcList[1].worldX = 1290;
        gp.npcList[1].worldY = 755;
    }

    public void setInteractiveTiles(){

        int i = 0;
        gp.interactiveTiles[i] = new ITDryTree(gp, 25, 25);
    }
}
