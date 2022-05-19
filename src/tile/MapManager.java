package tile;

import main.GamePanel;

import java.util.ArrayList;

public class MapManager {

    /** tanken med denna klassen är att checka vilka mappar man tar när man laddar upp till ett nytt område **/
    // TODO: behövs en stage controller klass? ska allt funka i gp?
    ArrayList<Map> mapList;

    public MapManager(GamePanel gp){
        int tileSize = gp.tileSize;

        mapList = new ArrayList<>();
        setupMaps(tileSize);
    }

    public void setupMaps(int tileSize){
        Map starterMap = new Map("/maps/TiledTesting.txt"); // TODO: add an actual starter map
        Map testingMap = new Map("/maps/testy");

        Map playersHome = new Map("/maps/playerHouse"); // PLAYER HOME

        mapList.add(starterMap);
        mapList.add(testingMap);

        mapList.add(playersHome);

        setPlayerPosition(tileSize); // sätt ner alla positionerna som spelaren ska spawans
    }

    public void setPlayerPosition(int tileSize){ //  TODO: men what if det finns mer än ett spawn point?
        for (int i = 0; i < mapList.size(); i++) {

            if (mapList.get(i).getMapTxtFile().equals("/maps/TiledTesting.txt")){
                mapList.get(i).setPlayerSpawnX(tileSize * 18);
                mapList.get(i).setPlayerSpawnY(tileSize * 14);
                System.out.println("Added positioning");
            }
            if (mapList.get(i).getMapTxtFile().equals("/maps/playerHouse")){
                mapList.get(i).setPlayerSpawnX(tileSize * 16);
                mapList.get(i).setPlayerSpawnY((tileSize * 27) + (tileSize / 2));
            }
        }
    }

    public ArrayList<Map> getMapList() {
        return mapList;
    }
}
