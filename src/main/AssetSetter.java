package main;

import entity.NPC_CatLady;
import entity.NPC_Wock;
import gameObject.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    public void setObjects(){
        int mapNum = 0;
        gp.obj[1][1] = new Cat();
        gp.obj[1][1].worldX = 70 * gp.tileSize + (gp.tileSize/2);
        gp.obj[1][1].worldY = 38 * gp.tileSize;

        gp.obj[1][2] = new Book();
        gp.obj[1][2].worldX = 6 * gp.tileSize + (gp.tileSize/2);
        gp.obj[1][2].worldY = 18 * gp.tileSize;

        gp.obj[1][3] = new Wok();
        gp.obj[1][3].worldX = 59 * gp.tileSize + (gp.tileSize / 2);
        gp.obj[1][3].worldY = 41 * gp.tileSize;

        gp.obj[1][4] = new Stuff();
        gp.obj[1][4].worldX = 17 * gp.tileSize + (gp.tileSize / 2);
        gp.obj[1][4].worldY = 65 * gp.tileSize;

        gp.obj[1][5] = new Door();
        gp.obj[1][5].worldX = 47 * gp.tileSize - (gp.tileSize/2);
        gp.obj[1][5].worldY = 61 * gp.tileSize;

        gp.obj[1][7] = new Teleporter();
        gp.obj[1][7].worldX = 60 * gp.tileSize + (gp.tileSize/2);     //[0] means map 0, Main town
        gp.obj[1][7].worldY = 10 * gp.tileSize;

        gp.obj[0][7] = new Teleporter();
        gp.obj[0][7].worldX = 22 * gp.tileSize;
        gp.obj[0][7].worldY = 40 * gp.tileSize;

        //                mapList.get(i).setPlayerSpawnX(tileSize * 51);
        //                mapList.get(i).setPlayerSpawnY(tileSize * 9);
    }

    public void setNPC(){

        int mainTown = 0;
        //Wock
        gp.npcList[mainTown][0] = new NPC_Wock(gp);
        gp.npcList[mainTown][0].worldX = 48 * gp.tileSize;
        gp.npcList[mainTown][0].worldY = 39 * gp.tileSize;

        //Cracy cat lady
        gp.npcList[mainTown][1] = new NPC_CatLady(gp);
        gp.npcList[mainTown][1].worldX = 50 * gp.tileSize;
        gp.npcList[mainTown][1].worldY = 39 * gp.tileSize;
    }
}
