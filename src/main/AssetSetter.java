package main;

import entity.NPC_Luigi;
import entity.NPC_Mario;
import gameObject.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // TODO: ej effektivt, funkar ba för en mapp ju

    public void setObject() {
        for (int i = 0; i < gp.mapManager.getMapList().size(); i++) {
            if (gp.mapManager.getMapList().get(i).getMapTxtFile().equals("/maps/maintown")){ //TODO: Fixa if Sats;

                gp.obj[1] = new Cat();
                gp.obj[1].worldX = 52 * gp.tileSize + (gp.tileSize/2);
                gp.obj[1].worldY = 27 * gp.tileSize;

                gp.obj[2] = new Book();
                gp.obj[2].worldX = 10 * gp.tileSize + (gp.tileSize/2);
                gp.obj[2].worldY = 14 * gp.tileSize;

                gp.obj[3] = new Wok();
                gp.obj[3].worldX = 45 * gp.tileSize + (gp.tileSize/2);
                gp.obj[3].worldY = 30 * gp.tileSize;

                gp.obj[4] = new Stuff();
                gp.obj[4].worldX = 13 * gp.tileSize + (gp.tileSize/2);
                gp.obj[4].worldY = 43 * gp.tileSize;

                gp.obj[5] = new Door();
                gp.obj[5].worldX = 20 * gp.tileSize;
                gp.obj[5].worldY = 37 * gp.tileSize;
            }
        }


        /*gp.obj[0] = new Key();
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
        gp.obj[6].worldY = 7 * gp.tileSize;*/



    }

    public void setNPC(){
        //Mario
        gp.npcList[0] = new NPC_Mario(gp);
        gp.npcList[0].worldX = 1460;
        gp.npcList[0].worldY = 1355;

        //Luigi
        gp.npcList[1] = new NPC_Luigi(gp);
        gp.npcList[1].worldX = 1390;
        gp.npcList[1].worldY = 1355;
    }
}
