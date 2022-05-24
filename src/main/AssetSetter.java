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
        gp.obj[0][1] = new Cat();
        gp.obj[0][1].worldX = 70 * gp.tileSize + (gp.tileSize/2);
        gp.obj[0][1].worldY = 38 * gp.tileSize;


            gp.obj[0][2] = new Book();
            gp.obj[0][2].worldX = 6 * gp.tileSize + (gp.tileSize/2);
            gp.obj[0][2].worldY = 18 * gp.tileSize;



            gp.obj[0][4] = new Stuff();
            gp.obj[0][4].worldX = 17 * gp.tileSize + (gp.tileSize / 2);
            gp.obj[0][4].worldY = 65 * gp.tileSize;



            gp.obj[0][3] = new Wok();
            gp.obj[0][3].worldX = 59 * gp.tileSize + (gp.tileSize / 2);
            gp.obj[0][3].worldY = 41 * gp.tileSize;


        gp.obj[0][5] = new Door();
        gp.obj[0][5].worldX = 47 * gp.tileSize - (gp.tileSize/2);
        gp.obj[0][5].worldY = 61 * gp.tileSize;

        gp.obj[0][7] = new Teleporter();
        gp.obj[0][7].worldX = 50 * gp.tileSize;     //[0] means map 0, Main town
        gp.obj[0][7].worldY = 12 * gp.tileSize;

        gp.obj[1][7] = new Teleporter();
        gp.obj[1][7].worldX = 11 * gp.tileSize;
        gp.obj[1][7].worldY = 13 * gp.tileSize;

        //                mapList.get(i).setPlayerSpawnX(tileSize * 51);
        //                mapList.get(i).setPlayerSpawnY(tileSize * 9);
    }

    public void setNPC(){

        int mainTown = 0;
        //Mario
        gp.npcList[mainTown][0] = new NPC_Mario(gp);
        gp.npcList[mainTown][0].worldX = 40 * gp.tileSize;
        gp.npcList[mainTown][0].worldY = 32 * gp.tileSize;

        //Luigi
        gp.npcList[mainTown][1] = new NPC_Luigi(gp);
        gp.npcList[mainTown][1].worldX = 40 * gp.tileSize;

        gp.npcList[mainTown][1].worldY = 35 * gp.tileSize;
    }
}
