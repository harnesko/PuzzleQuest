package tile;

import main.GamePanel;

import java.util.ArrayList;

public class MapManager {

    /** tanken med denna klassen är att checka vilka mappar man tar när man laddar upp till ett nytt område
     *
     * @Author Kinda
     * **/
    // TODO: behövs en stage controller klass? ska allt funka i gp?
    ArrayList<Map> mapList;

    public MapManager(GamePanel gp){
        int tileSize = gp.tileSize;

        mapList = new ArrayList<>();
        setupMaps(tileSize);
    }

    public void setupMaps(int tileSize){
        Map sawmill = new Map("/mainMaps/sawmill", MapType.file); // MAIN MAP test

        Map mainTown = new Map("/mainMaps/main_town", MapType.file);
        /*
        Map firstMap = new Map("/maps/sawmill"); // STARTER AREA FOR PLAYER
        Map playersHome = new Map("/maps/playerHouse"); // PLAYER HOME
        Map townMap = new Map("/maps/maintown");*/

        //mapList.add(testingMap); // test
        mapList.add(sawmill);
        //mapList.add(mainTown);
        /*
        mapList.add(firstMap);
        mapList.add(playersHome);
        mapList.add(townMap);
*/
        setPlayerPosition(tileSize); // sätt ner alla positionerna som spelaren ska spawans
    }

    public void setPlayerPosition(int tileSize){ //  TODO: men what if det finns mer än ett spawn point?
        for (int i = 0; i < mapList.size(); i++) {

            if (mapList.get(i).getMapTxtFile().equals("/mainMaps/sawmill")){
                mapList.get(i).setPlayerSpawnX(tileSize * 26);
                mapList.get(i).setPlayerSpawnY(tileSize * 19);
                System.out.println("Added positioning for SAWMILL");
            }

            if (mapList.get(i).getMapTxtFile().equals("/mainMaps/main_town")){
                mapList.get(i).setPlayerSpawnX(tileSize * 60);
                mapList.get(i).setPlayerSpawnY(tileSize * 12);
                System.out.println("Added positioning for MAIN TOWN");
            }

            if (mapList.get(i).getMapTxtFile().equals("/maps/TiledTesting.txt")){
                mapList.get(i).setPlayerSpawnX(tileSize * 18);
                mapList.get(i).setPlayerSpawnY(tileSize * 14);
                System.out.println("Added positioning");
            }
            if (mapList.get(i).getMapTxtFile().equals("/maps/playerHouse")){
                mapList.get(i).setPlayerSpawnX(tileSize * 16);
                mapList.get(i).setPlayerSpawnY((tileSize * 27) + (tileSize / 2));
            }
            if (mapList.get(i).getMapTxtFile().equals("/maps/map01.txt")){
                mapList.get(i).setPlayerSpawnX(tileSize * 1);
                mapList.get(i).setPlayerSpawnY(tileSize * 2);
            }
            if (mapList.get(i).getMapTxtFile().equals("/maps/sawmill")){
                mapList.get(i).setPlayerSpawnX(tileSize * 21);
                mapList.get(i).setPlayerSpawnY((tileSize * 14) + (tileSize /2 ));
            }
            if (mapList.get(i).getMapTxtFile().equals("/maps/maintown")){
                mapList.get(i).setPlayerSpawnX(tileSize * 18);
                mapList.get(i).setPlayerSpawnY((tileSize * 23) + (tileSize /2 ));
            }
        }
    }

    public ArrayList<Map> getMapList() {
        return mapList;
    }
}
