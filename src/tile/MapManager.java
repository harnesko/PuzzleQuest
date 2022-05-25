package tile;

import main.GamePanel;

import java.util.ArrayList;

public class MapManager {

    /**
     * tanken med denna klassen är att checka vilka mappar man tar när man laddar upp till ett nytt område
     *
     * @Author Kinda
     **/
    // TODO: behövs en stage controller klass? ska allt funka i gp?
    ArrayList<Map> mapList;

    public MapManager(GamePanel gp) {
        int tileSize = gp.tileSize;

        mapList = new ArrayList<>();
        setupMaps(tileSize);
    }

    public void setupMaps(int tileSize) {
        Map sawmill = new Map("/mainMaps/sawmill", MapType.file); // MAIN MAP test
        Map mainTown = new Map("/mainMaps/main_town", MapType.file);

        mapList.add(sawmill); // TODO: när det finns mer än ett objekt i listan snear den pga gammla mått
        mapList.add(mainTown);

        setPlayerPosition(tileSize); // sätt ner alla positionerna som spelaren ska spawans
    }

    public void setPlayerPosition(int tileSize) { //
        for (int i = 0; i < mapList.size(); i++) {

            if (mapList.get(i).getMapTxtFile().equals("/mainMaps/sawmill")) { // SAWMILL SPAWN COORDS
                mapList.get(i).setPlayerSpawnX(tileSize * 26);
                mapList.get(i).setPlayerSpawnY(tileSize * 19);
                System.out.println("Added positioning for SAWMILL");
            }

            if (mapList.get(i).getMapTxtFile().equals("/mainMaps/main_town")) { // MAIN TOWN SPAWN COORDS
                mapList.get(i).setPlayerSpawnX(tileSize * 60);
                mapList.get(i).setPlayerSpawnY(tileSize * 12);
                System.out.println("Added positioning for MAIN TOWN");

                //Testing spawn coords, currently set to main town square
                /*mapList.get(1).setPlayerSpawnX(tileSize * 50);
                mapList.get(1).setPlayerSpawnY(tileSize * 40);*/
            }
        }
    }

    public ArrayList<Map> getMapList() {
        return mapList;
    }
}
