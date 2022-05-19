package tile;

import main.Debug;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    // MAP
    public String currentMap = "";
    MapManager mapManager;
    /**
     * current map will be used to determine the map, how it loads, current map takes inputs
     **/

    // TILE ANIMATION SETTINGS
    int frame = 0;

    public TileManager(GamePanel gp, MapManager mapManager, String starterMap) {
        this.gp = gp;
        currentMap = starterMap;

        tile = new Tile[1200];
        this.mapManager = mapManager;

        getTileImages();
        loadMap(currentMap);
    }

    public void getTileImagesTEST() {
        // TODO: för kinda, ersätta, lägga till, byta gfx sen
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

    public void getTileImage() { // TODO: för kinda, ersätta, lägga till, byta gfx sen
        try {
            tile[0] = new Tile(); // GRASS
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
            tile[0].collision = false;

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
            tile[5].collision = false;

            tile[6] = new Tile(); //something else


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTileImages() { // debug, testa här först sen kopiera över uppåt
        try {
            tile[0] = new Tile(); // TRANSPARENT
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/black.png")));
            tile[0].collision = true;

            tile[1] = new Tile(); // TRANSPARENT
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/grass.png")));

            tile[2] = new Tile(); // TRANSPARENT
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/watermid_1.png")));
            tile[2].collision = true;

            tile[3] = new Tile(); // TRANSPARENT
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/big_tree1.png")));
            tile[3].collision = true;

            tile[4] = new Tile(); // TRANSPARENT
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/big_tree2.png")));
            tile[4].collision = true;

            tile[5] = new Tile(); // TRANSPARENT
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/big_tree3.png")));
            tile[5].collision = true;

            tile[6] = new Tile(); // TRANSPARENT
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/big_tree4.png")));
            tile[6].collision = true;

            tile[7] = new Tile(); // TRANSPARENT
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/water1_1.png")));
            tile[7].collision = true;

            tile[8] = new Tile(); // TRANSPARENT
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/water2_1.png")));
            tile[8].collision = true;

            tile[9] = new Tile(); // TRANSPARENT
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/water3_1.png")));
            tile[9].collision = true;

            tile[10] = new Tile(); // TRANSPARENT
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/altTiles/water4_1.png")));
            tile[10].collision = true;

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

            Map map = getMap(filePath);

            /** @author Kinda, fråga om saker o ting är förvirrande **/
            if (map != null) {
                mapTileNum = new int[map.getHeight()][map.getWidth()];
                System.out.println("MAP FOUND");

                InputStream is = getClass().getResourceAsStream(filePath); // text file
                BufferedReader br = new BufferedReader(new InputStreamReader(is)); // bufferedReader läser text filen

                // i = row
                int row = 0;
                // j = col
                int col = 0;

                for (row = 0; (row < map.getHeight()); row++) {

                    String line = br.readLine(); // här läses en line

                    for (col = 0; col < map.getWidth(); col++) {

                        if (line != null) {
                            String[] numbers = line.split(","); // vi säger åt systemet att separera siffrorna

                            int num = Integer.parseInt(numbers[col]); // vi vill ha int så vi översätter

                            mapTileNum[row][col] = num;     // och sedan sparar siffran i vår map array
                        }
                    }
                }

                br.close();
            } else {
                System.out.println("MAP NOT FOUND");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, boolean debugON) {
        Map map = getMap(currentMap);
        Debug debug = new Debug(); // DELETE LATER, not now
        System.out.println("CONFIRMED");

        /** dessa funktioner ritar mappen genom att ta värden från textfilen vi skapar (se snabbmapguide.pdf)*/

        for (int worldRow = 0; worldRow < map.getHeight(); worldRow++) {
            for (int worldCol = 0; worldCol < map.getWidth(); worldCol++) {

                int tileIndex = mapTileNum[worldRow][worldCol];

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
                    if (tile[tileIndex] != null) {
                        g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }

                }

                if (debugON) { // OK att ta bort
                    debug.showMapTiles(g2, screenX, screenY, gp.tileSize);
                }
            }
        }
    }

    public Map getMap(String file) {
        ArrayList<Map> maps = mapManager.getMapList();
        for (int i = 0; i < maps.size(); i++) {
            if (file.equals(maps.get(i).getMapTxtFile())) {
                return maps.get(i);
            }
        }
        return null;
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
