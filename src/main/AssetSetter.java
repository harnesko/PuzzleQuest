package main;

import entity.NPC_CatLady;
import entity.NPC_Clerk;
import entity.NPC_Wock;
import gameObject.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    public void setObjects(){
        gp.obj[1][1] = new Cat();
        gp.obj[1][1].worldX = 70 * gp.tileSize + (gp.tileSize/2);
        gp.obj[1][1].worldY = 38 * gp.tileSize;

        gp.obj[1][5] = new Door();
        gp.obj[1][5].worldX = 47 * gp.tileSize - (gp.tileSize/2);
        gp.obj[1][5].worldY = 61 * gp.tileSize;

        gp.obj[1][6] = new End();
        gp.obj[1][6].worldX = 47 * gp.tileSize - (gp.tileSize/2);
        gp.obj[1][6].worldY = 64 * gp.tileSize;

        gp.obj[1][7] = new Teleporter();
        gp.obj[1][7].worldX = 60 * gp.tileSize + (gp.tileSize/2);
        gp.obj[1][7].worldY = 10 * gp.tileSize;

        gp.obj[0][7] = new Teleporter();
        gp.obj[0][7].worldX = 22 * gp.tileSize;
        gp.obj[0][7].worldY = 40 * gp.tileSize;

        //                mapList.get(i).setPlayerSpawnX(tileSize * 51);
        //                mapList.get(i).setPlayerSpawnY(tileSize * 9);
    }

    /**
     * Helper method to initialize the NPC list
     * @author Måns
     */
    public void setNPC(){

        int mainTown = 1;
        //Wock
        gp.npcList[mainTown][0] = new NPC_Wock(gp);
        gp.npcList[mainTown][0].worldX = 59 * gp.tileSize + (gp.tileSize / 2);
        gp.npcList[mainTown][0].worldY = 41 * gp.tileSize;

        //Cracy cat lady
        gp.npcList[mainTown][1] = new NPC_CatLady(gp);
        gp.npcList[mainTown][1].worldX = 62 * gp.tileSize;
        gp.npcList[mainTown][1].worldY = 21 * gp.tileSize + (gp.tileSize / 2);

        gp.npcList[mainTown][2] = new NPC_Clerk(gp);
        gp.npcList[mainTown][2].worldX = 17 * gp.tileSize + (gp.tileSize / 2);
        gp.npcList[mainTown][2].worldY = 65 * gp.tileSize;

    }
}
