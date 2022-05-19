package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {

    private String mapTxtFile;
    private int height; // TODO: bättre namn?
    private int width;
    private int playerSpawnX = 0;
    private int playerSpawnY = 0;

    public Map(String mapTxtFile){
        this.mapTxtFile = mapTxtFile;

        height = measureMap(2);
        width = measureMap(1);
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
        System.out.println("line count är " + lineCount);
        System.out.println("total char är " + totalCharCount);
        return x == 1 ? lineCount : (totalCharCount / lineCount);
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
