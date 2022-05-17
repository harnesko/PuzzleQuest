package tile;

import java.util.ArrayList;

public class MapManager {

    /** tanken med denna klassen är att checka vilka mappar man tar när man laddar upp till ett nytt område **/
    // TODO: behövs en stage controller klass? ska allt funka i gp?
    ArrayList<Map> mapList;

    public MapManager(){
        setupMaps();
    }

    public void setupMaps(){
        Map starterMap = new Map("/maps/testMap.txt"); // TODO: add an actual starter map

        mapList.add(starterMap);
    }

}
