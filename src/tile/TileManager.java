package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[50][50];

        getTileImage();
        loadMap("/maps/map02.txt");
    }

    public void getTileImage() { // TODO: för kinda, ersätta, lägga till, byta gfx sen
        try {
            //Kom ihåg att 0 innebär null tile, så börja listan på index + 1 när vi lägger in .tmx filer
            tile[0] = new Tile(); // Background
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/black.png")));

            tile[1] = new Tile(); // Bushtest
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bush_test.png")));
            tile[1].collision = true;

            tile[2] = new Tile(); // Sand
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
            tile[2].collision = true;

            tile[3] = new Tile(); // Tree
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));

            tile[4] = new Tile(); // Wall
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[4].collision = true;

            tile[5] = new Tile(); // Water
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[5].collision = true;

            tile[6] = new Tile(); // Grass
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[7] = new Tile(); // Sand
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMapHeight(String filePath) {
        int lineCount = 0;      //Can be used to get current map height to set in gp.maxWorldCol/Rows
        try {
            lineCount = 0;
            String data = "";
            InputStream is = getClass().getResourceAsStream(filePath); // text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // bufferedReader läser text filen
            while((data = br.readLine()) != null) {
                lineCount++;
                gp.worldWidth = data.length() / 2;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
    }

    public void loadMap(String filePath) { // om du inte fattar nåt här, skit i det. ändra bara inget
        try {

            /** inputstream hämtar filen, antar det som skiljer sig från buffered image är att
             * images är ju .png medan inputstream tar up textfiler. anledningen vi ens kör på textfil
             * när vi skapar mappar är för att förenkla processen. istället för att behöva skriva 20 rader
             * med new Tile... så skapar vi en "kod mapp" i en vanlig .txt fil. Gör det med notepad eller nåt
             * spelar ingen roll ba textfilen är .txt.
             *
             * kolla i resource.maps.snabbmapguide.pdf för info på hur denna text fil skapas */

            InputStream is = getClass().getResourceAsStream(filePath); // text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // bufferedReader läser text filen

            // TODO: for-loop, kinda, kanske, vi får se

            int mapHeight = getMapHeight(filePath);
            int mapWidth =  30;


            int col = 0;
            int row = 0;
            String line = null;
            do{                        //Read atleast one line
                line = br.readLine(); // här läses en line

                if (line != null){                                                          //(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" "); // vi säger åt systemet att separera siffrorna
                    // efter varje space, så att den behandlar
                    // varje siffra enskilt
                    System.out.println(line);
                    int num = Integer.parseInt(numbers[col]); // vi vill ha int så vi översätter

                    mapTileNum[col][row] = num;     // och sedan sparar siffran i vår map array
                    col++;
                }
                if (col == mapHeight) {
                    col = 0;
                    row++;
                }
                System.out.println("Current line is done..");
            } while (line != null); //read until there is no more lines
            System.out.println("");
            br.close();             //close the stream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        /** dessa funktioner ritar mappen genom att ta värden från textfilen vi skapar (se snabbmapguide.pdf)*/

        //g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);

        int tileToDraw = 0;
        int worldCol = 0;
        int worldRow = 0;
        try {
            worldCol = 30;
            worldRow = 30; //lazy rn
        } catch (Exception e) {
            e.printStackTrace();
        }

        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;        //worldx/y och screen x/y är lite fucked atm
        for (int i = 0; i < worldCol; i++){
            for (int j = 0; j < worldRow; j++){
                if(mapTileNum[i] != null){
                    if(tileToDraw != 0){
                        System.out.println("the tile to be drawn is number: " + tileToDraw);
                    }
                    tileToDraw = mapTileNum[i][j];
                    g2.drawImage(tile[tileToDraw].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                }
            }
        }

       /* while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            // TODO: för kinda, förklara dessa sen
            //  snabb tldr: har med hur kameran + mappen + player gubben reagerar me varan

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // denna if-satsen säkerställer att mappen
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // ritas ENDAST där kameran ser för att
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && // förbättra performance och slippa rita
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // 500 pixlar vi inte ser + ger lag

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;*/

          /*  if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;*/


}