package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {

    private ArrayList<Map> mapLayers;
    private String mapTxtFile;
    private int height; // TODO: bättre namn?
    private int width;
    private int playerSpawnX = 0;
    private int playerSpawnY = 0;

    public Map(String mapTxtFile, MapType mapType){
        this.mapTxtFile = mapTxtFile;

        if (mapType.equals(MapType.file)){mapLayers = new ArrayList<>(setUpLayers());
            if (mapLayers.size() < 1){
                System.out.println("LIST IS NULL... ");
                System.exit(0);
            }
        } else if (mapType.equals(MapType.layer)){
            height = measureMap(2);
            width = measureMap(1);
        }
    }


    public ArrayList<Map> setUpLayers(){
        InputStream is = getClass().getResourceAsStream(mapTxtFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        mapLayers = new ArrayList<>();

        try {
            String line;
            while ((line = br.readLine()) != null){
                Map layer = new Map(line, MapType.layer);
                mapLayers.add(layer);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("ERROR IN SETUPLAYERS");
            System.exit(0);
        }

        return mapLayers != null ? mapLayers : null;
    }
    public int measureMap(int x) {
        InputStream is = getClass().getResourceAsStream(mapTxtFile); // text file
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // bufferedReader läser text filen
        int lineCount = 0; //
        int totalCharCount = 0;

        try {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                int charCount = line.split(",").length;
                totalCharCount += charCount;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return x == 1 ? lineCount : (totalCharCount / lineCount);
    }

    public ArrayList<Map> getMapLayers() {
        return mapLayers;
    }

    public void setMapLayers(ArrayList<Map> mapLayers) {
        this.mapLayers = mapLayers;
    }

    public String getMapTxtFile() {
        return mapTxtFile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlayerSpawnX() {
        return playerSpawnX;
    }

    public int getPlayerSpawnY() {
        return playerSpawnY;
    }

    public void setPlayerSpawnX(int playerSpawnX) {
        this.playerSpawnX = playerSpawnX;
    }

    public void setPlayerSpawnY(int playerSpawnY) {
        this.playerSpawnY = playerSpawnY;
    }
}
