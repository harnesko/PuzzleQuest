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
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt", 0);
        loadMap("/maps/interior01.txt", 1);
    }

    public void getTileImage() { // TODO: för kinda, ersätta, lägga till, byta gfx sen
        try {
            tile[0] = new Tile(); // GRASS
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile(); // WALL
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile(); // WATER
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile(); // EARTH
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile(); // TREE
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile(); // SAND
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

            tile[6] = new Tile(); // FLOOR
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/floor01.png")));

            tile[7] = new Tile(); // HUT
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hut.png")));

            tile[8] = new Tile(); // TABLE
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/table01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) { // om du inte fattar nåt här, skit i det. ändra bara inget
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

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = null;
                line = br.readLine(); // här läses en line

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" "); // vi säger åt systemet att separera siffrorna
                    // efter varje space, så att den behandlar
                    // varje siffra enskilt

                    int num = Integer.parseInt(numbers[col]); // vi vill ha int så vi översätter

                    mapTileNum[map][col][row] = num;     // och sedan sparar siffran i vår map array
                    if(num == 7){
                        System.out.println("Map : " + map + ", Hut Col: " + col + "\nHut Row: " + row);
                    }
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        /** dessa funktioner ritar mappen genom att ta värden från textfilen vi skapar (se snabbmapguide.pdf)*/

        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;

        // TODO: för kinda, ändra detta till en for-loop lmao



        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            // TODO: för kinda, förklara dessa sen
            //  snabb tldr: har med hur kameran + mappen + player gubben reagerar me varan


            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //Stop moving the camera when at the edge of the map
            if (gp.player.screenX > gp.player.worldX){
                screenX = worldX;
            }

            if (gp.player.screenY > gp.player.worldY){
                screenY = worldY;
            }

            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX){
                screenX = gp.screenWidth - gp.worldWidth - worldX;
            }
            int downOffset = gp.screenHeight - gp.player.screenY;
            if (downOffset > gp.worldHeight - gp.player.worldY){
                screenY = gp.screenHeight - gp.worldHeight - worldY;
            }

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // denna if-satsen säkerställer att mappen
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // ritas ENDAST där kameran ser för att
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && // förbättra performance och slippa rita
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // 500 pixlar vi inte ser + ger lag

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            else if (gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    rightOffset > gp.worldWidth - gp.player.worldX ||
                    downOffset > gp.worldHeight - gp.player.worldY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }



            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
