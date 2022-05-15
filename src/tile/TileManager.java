package tile;

import main.Debug;
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

    // TILE ANIMATION SETTINGS
    int frame = 0;
    int tileNum = 1;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[1200];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImages();
        loadMap("/maps/world001.txt");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTileImages() { // debug, testa här först sen kopiera över uppåt
        try {
            int i = 0;

            tile[i] = new Tile(); // TRANSPARENT
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/empty.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // BOT RIGHT WATER CORNER
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water1_4.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // MID WATER
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water1_mid.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // TOP RIGHT WATER CORNER
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water1_2.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // GRASS
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/grass2.png")));
            i++;

            ////// ANIMATION SECTION ////// // TODO: detta måste fixas, jätte reduntant o fuckt / kinda

            // WATER 2
            i = 101;
            tile[i] = new Tile(); // BOT RIGHT WATER CORNER
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water2_4.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // MID WATER 101
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water2_mid.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // TOP RIGHT WATER CORNER 102
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water2_2.png")));
            tile[i].collision = true;
            i++;

            // WATER 3
            i = 1001;
            tile[i] = new Tile(); // BOT RIGHT WATER CORNER 103
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water3_4.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // MID WATER 104
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water3_mid.png")));
            tile[i].collision = true;
            i++;

            tile[i] = new Tile(); // TOP RIGHT WATER CORNER 105
            tile[i].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/ani_water3_2.png")));
            tile[i].collision = true;
            i++;

        } catch (IOException e) {
            e.printStackTrace();
        }
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

            // i = row
            int row = 0;
            // j = col
            int col = 0;

            for (row = 0; (row < gp.maxWorldRow) && (col < gp.maxWorldCol) ; row++) {

                String line = br.readLine(); // här läses en line

                for (col = 0; col < gp.maxWorldCol; col++) {

                    String[] numbers = line.split(" "); // vi säger åt systemet att separera siffrorna
                    // efter varje space, så att den behandlar
                    // varje siffra enskilt

                    int num = Integer.parseInt(numbers[col]); // vi vill ha int så vi översätter

                    mapTileNum[col][row] = num;     // och sedan sparar siffran i vår map array
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, boolean debugON) {

        Debug debug = new Debug(); // DELETE LATER, not now
        System.out.println("CONFIRMED");

        /** dessa funktioner ritar mappen genom att ta värden från textfilen vi skapar (se snabbmapguide.pdf)*/

        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {

                int tileIndex = mapTileNum[worldCol][worldRow];

                /** här blir worldCol & worldRow mängden av tiles.
                 *
                 * Så en 50x50 tiled mapp får max 50 worldCol och worldRow
                 * Man kan säga att worldX och worldY är höjd och längden på mappen i pixel prefix. så tile nr 25
                 * som då är 25 * tilesize (64 just nu) blir 1600 pixlar.
                 * */

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX; // spelarskärmens x axel + dens bredd
                int screenY = worldY - gp.player.worldY + gp.player.screenY; // spelarskärmens y axel + dens längd
                // ovan variabler ger oss "världens kamera" som brukar kunna vara utanför gui:n
                // därför kan screenX/Y bli negativ. Eftersom mappen kan vara större än bara måtten som gui:n visar oss

                /** Det som egentligen görs här är att måtten på världskameran (i pixlar) jämförs med spelarens
                 * kamera och säkerställer att vi ritar ENDAST pixlarna/tiles:en som vi kan se inuti GUI:t. Resten
                 * ritas endast där spelarkameran rör sig till, alltså när vi rör på gubben.
                 *
                 * detta ger bättre rendering performance.*/


                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    tileIndex = playTileAnimations(tileIndex);

                    g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }

                if (debugON) { // OK att ta bort
                    debug.showMapTiles(g2, screenX, screenY, gp.tileSize);
                }
            }
        }
    }

    public int playTileAnimations(int i) { // TODO: fixa lol
        frame++;

        if (frame > 30) {
            i = i == 1 ? 101 : i == 2 ? 102 : i == 3 ? 103 :
                    i == 101 ? 1001 : i == 102 ? 1002 : i == 103 ? 1003 :
                            i == 1001 ? 1 : i == 1002 ? 2 : i == 1003 ? 3 : i;

            frame = 0;
        }
        return i;
    }

}
