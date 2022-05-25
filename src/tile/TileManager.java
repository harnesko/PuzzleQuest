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

    /**
     * allt händer här idk gl
     *
     * @Author Kinda
     * @Author Måns
     **/

    GamePanel gp;
    public Tile[] tile;
    //public int[][][] mapTileNum;
    public int[][] collisionBoolean;
    private Image image;

    // MAP
    public String currentMap = "";
    MapManager mapManager;
    //mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
    /**
     * current map will be used to determine the map, how it loads, current map takes inputs
     **/

    // TILE ANIMATION SETTINGS
    int frame = 0;

    public TileManager(GamePanel gp, MapManager mapManager, String starterMap) {
        this.gp = gp;
        currentMap = starterMap;
        System.out.println(currentMap);

        tile = new Tile[1200];
        this.mapManager = mapManager;

        getTileImage();
        setupNewMap(currentMap);
    }

    public void switchMaps(String newMapUrl){ // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // TODO: när spelaren kolliderar med ett teleport objekt eller whatever, kalla på denna metod o skicka
        // TODO: textsträngen på den nya mappen som han ska till. I detta fall är det antingen "/mainMaps/sawmill" eller "/mainMaps/main_town"

        this.currentMap = newMapUrl;
        getTileImage();
        setupNewMap(currentMap);
    }

    public void getTileImagesTEST() {
        try {
            //Kom ihåg att 0 innebär null tile, så börja listan på index + 1 när vi lägger in .tmx filer
            tile[0] = new Tile(); // Background
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/black.png")));
            tile[0].collision = true;

            tile[1] = new Tile(); // Bushtest
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/bush_test.png")));
            tile[1].collision = true;

            tile[2] = new Tile(); // Sand
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
            tile[2].collision = false;

            tile[3] = new Tile(); // Tree
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[3].collision = true;

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

            tile[10] = new Tile(); // MARIO TEST, TA BORT OM DU VILL
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcOne/npc_mario_left.png")));
            tile[10].collision = true;

            tile[11] = new Tile(); // MARIO TEST, TA BORT OM DU VILL
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/transparent.png")));

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2.png")));
            tile[12].image = tile[12].image.getSubimage(0, 0, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getTileImage() {
        /***
         * ============= tar nån bort denna metoden så kmr jag söka upp dig /kinda
         ***/

        if (currentMap.equals("/mainMaps/main_town")) {
            try {

                tile[0] = new Tile();
                tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/transparent.png")));

                tile[656] = new Tile();
                tile[656].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2.png")));
                tile[656].image = tile[656].image.getSubimage(48, 48, gp.tileSize, gp.tileSize);
                // TEST TEST TEST.. svart byttes ut mot gräs
                //tile[656] = new Tile(); // ÄNDRA OM FLER TILES LÄGGS TILL, JUST NU ÄR DET 635 ST TILES
                //tile[656].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/black.png")));

                int index = 1;

                for (int i = 0; i < 10; i++) {
                    int x = 0;
                    int y = 0;

                    switch (i) {
                        case 0: // MARK, GRÄS, SAND...
                            for (int j = 0; j < 6; j++) {
                                x = 0;
                                for (int k = 0; k < 9; k++) {
                                    if (index != 11) {
                                        tile[index] = new Tile();
                                        tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2.png")));
                                        tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                        x += gp.tileSize;
                                    } else {
                                        tile[index] = new Tile();
                                        tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
                                        System.out.println("Here!");
                                    }
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 1: // MARK, GRÄS, SAND DETALJER...
                            for (int j = 0; j < 6; j++) {
                                x = 0;
                                for (int k = 0; k < 6; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2b.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 2: // VÄGGAR, TAK
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 12; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A3.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    tile[index].collision = true;
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 3: // TRÄD, VÄXTER, UTOMHUS DETALJ...
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 16; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_B1.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    if (index != 291 && index != 292) {       //that one tree tile that wasn't playing nice
                                        tile[index].collision = true;
                                    }
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 4: // HUS DETALJER
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 8; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_B2.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    tile[index].collision = true;
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 5: // vet nt, men ha collision på
                            for (int j = 0; j < 16; j++) {
                                x = 0;
                                for (int k = 0; k < 16; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/SF_Outside_B.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    if (index != 545) {       //545 is lightpost top-half tile
                                        tile[index].collision = true;
                                    }
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 6: // ===================== nya tileset:en
                            for (int j = 0; j < 5; j++) {// todo:
                                x = 0;
                                for (int k = 0; k < 4; k++) {// todo:
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/EditedOutside.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            System.out.println(index);
                            break;
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (currentMap.equals("/mainMaps/sawmill")) {
            try {

                tile[0] = new Tile();
                tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/transparent.png")));

                tile[854] = new Tile();
                tile[854].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2.png")));
                tile[854].image = tile[854].image.getSubimage(48, 48, gp.tileSize, gp.tileSize);
                // TEST TEST TEST.. svart byttes ut mot gräs
                //tile[854] = new Tile(); // ÄNDRA OM FLER TILES LÄGGS TILL, JUST NU ÄR DET 635 ST TILES
                //tile[854].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/black.png")));

                int index = 1;

                for (int i = 0; i < 10; i++) {
                    int x = 0;
                    int y = 0;

                    switch (i) {
                        case 0: // MARK, GRÄS, SAND...
                            for (int j = 0; j < 6; j++) {
                                x = 0;
                                for (int k = 0; k < 9; k++) {
                                    if (index != 11) {
                                        tile[index] = new Tile();
                                        tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2.png")));
                                        tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                        x += gp.tileSize;
                                    } else {
                                        tile[index] = new Tile();
                                        tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
                                        System.out.println("Here!");
                                    }
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 1: // MARK, GRÄS, SAND DETALJER...
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 16; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_B1.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 2: // VÄGGAR, TAK
                            for (int j = 0; j < 10; j++) { // todo:
                                x = 0;
                                for (int k = 0; k < 15; k++) { // todo:
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/EditedInside.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    tile[index].collision = true;
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 3: // TRÄD, VÄXTER, UTOMHUS DETALJ...
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 12; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A3.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    if (index != 291 && index != 292) {       //that one tree tile that wasn't playing nice
                                        tile[index].collision = true;
                                    }
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 4: // HUS DETALJER
                            for (int j = 0; j < 8; j++) {
                                x = 0;
                                for (int k = 0; k < 8; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_B2.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    tile[index].collision = true;
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 5: // vet nt, men ha collision på
                            for (int j = 0; j < 16; j++) {
                                x = 0;
                                for (int k = 0; k < 16; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/SF_Outside_B.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    if (index != 545) {       //545 is lightpost top-half tile
                                        tile[index].collision = true;
                                    }
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 6: // ===================== nya tileset:en
                            for (int j = 0; j < 6; j++) {
                                x = 0;
                                for (int k = 0; k < 6; k++) {
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/Outside_A2b.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            break;
                        case 7: // vet nt, men ha collision på
                            for (int j = 0; j < 6; j++) {// todo:
                                x = 0;
                                for (int k = 0; k < 8; k++) {// todo:
                                    tile[index] = new Tile();
                                    tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mainTiles/EditedInside2.png")));
                                    tile[index].image = tile[index].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                                    x += gp.tileSize;
                                    index++;
                                }
                                y += gp.tileSize;
                            }
                            System.out.println(index);
                            break;
                    }
                }
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
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

    public void setupNewMap(String currentMap) {
        ArrayList<Map> mapList = mapManager.getMapList();

        boolean colBoolCreated = false;

        for (int j = 0; j < mapList.size(); j++) {

            if (mapList.get(j).getMapTxtFile().equals(currentMap)) {
                Map map = mapList.get(j);

                for (int k = 0; k < map.getMapLayers().size(); k++) {
                    String url = map.getMapLayers().get(k).getMapTxtFile();
                    int layerH = map.getMapLayers().get(k).getHeight();
                    int layerW = map.getMapLayers().get(k).getWidth();

                    System.out.println("LAYER H " + layerH + "\nLAYER W " + layerW);

                    if (!colBoolCreated) {
                        collisionBoolean = new int[map.getMapLayers().get(0).getWidth()][map.getMapLayers().get(0).getHeight()];

                        System.out.println("HEIGHT " + map.getMapLayers().get(0).getWidth() + "\nWIDTH " + map.getMapLayers().get(0).getHeight());

                        colBoolCreated = true;
                    }

                    /** denna for loop går igenom alla mappar o deras layers för o uppdatera dom
                     * sen skickar den tbx goda, färdiga listor som kmr kallas på i draw **/

                    int[][] newMapTileNum = new int[layerH][layerW];
                    newMapTileNum = loadMap(url, layerH, layerW, k, newMapTileNum, collisionBoolean);

                    if (map.getBufferedMaps() == null) {
                        System.out.println("ERROR 283");
                        System.exit(0);
                    }
                    if (newMapTileNum == null) {
                        System.out.println("ERROR 287");
                        System.exit(0);
                    }

                    map.getBufferedMaps().add(newMapTileNum);
                }
            }
        }
    }

    public int[][] loadMap(String url, int layerH, int layerW, int i, int[][] newMapTileNum, int[][] collisionBooleanMap) {
        try {

            /** inputstream hämtar filen, antar det som skiljer sig från buffered image är att
             * images är ju .png medan inputstream tar up textfiler. anledningen vi ens kör på textfil
             * när vi skapar mappar är för att förenkla processen. istället för att behöva skriva 20 rader
             * med new Tile... så skapar vi en "kod mapp" i en vanlig .txt fil. Gör det med notepad eller nåt
             * spelar ingen roll ba textfilen är .txt.
             *
             * kolla i resource.maps.snabbmapguide.pdf för info på hur denna text fil skapas */


            /** @author Kinda, fråga om saker o ting är förvirrande **/
            newMapTileNum = new int[layerH][layerW];
            //System.out.println("MAP FOUND");

            InputStream is = getClass().getResourceAsStream(url); // text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // bufferedReader läser text filen

            // i = row
            int row = 0;
            // j = col
            int col = 0;

            for (row = 0; row < layerW; row++) {

                String line = br.readLine(); // här läses en line

                for (col = 0; col < layerH; col++) {
                    if (line != null) {
                        String[] numbers = line.split(","); // vi säger åt systemet att separera siffrorna

                        int num = Integer.parseInt(numbers[col]); // vi vill ha int så vi översätter
                        if (this.tile[num] == null) {
                            System.out.println("Found null error in tile.[" + num + "]");
                        }
                        if (tile[num].collision) {
                            collisionBoolean[col][row] = 1;
                        }

                        newMapTileNum[col][row] = num;     // och sedan sparar siffran i vår map array
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMapTileNum;
    }

    public void draw(Graphics2D g2, boolean debugON) {
        Map map = getMap(currentMap);

        Debug debug = new Debug(); // DELETE LATER, not now

        int layerListSize = map.getMapLayers().size();

        boolean setCollisions = false;

        for (int i = 0; i < layerListSize; i++) {

            //loadMap(currentMap, i, collisionBoolean);

            if (i == (layerListSize - 1)) {
                setCollisions = true;
            }

            for (int worldRow = 0; worldRow < map.getMapLayers().get(i).getWidth(); worldRow++) {
                for (int worldCol = 0; worldCol < map.getMapLayers().get(i).getHeight(); worldCol++) {

                    int tileIndex = map.getBufferedMaps().get(i)[worldCol][worldRow];


                    /** här blir worldCol & worldRow mängden av tiles.
                     *
                     * Så en 50x50 tiled mapp får max 50 worldCol och worldRow
                     * Man kan säga att worldX och worldY är höjd och längden på mappen i pixel prefix. så tile nr 25
                     * som då är 25 * tilesize (64 just nu) blir 1600 pixlar.
                     * */

                    int worldX = worldCol * gp.tileSize; // i pixlar
                    int worldY = worldRow * gp.tileSize; // i pixlar
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; // spelarskärmens x axel + dens bredd
                    int screenY = worldY - gp.player.worldY + gp.player.screenY; // spelarskärmens y axel + dens längd
                    // ovan variabler ger oss "världens kamera" som brukar kunna vara utanför gui:n
                    // därför kan screenX/Y bli negativ. Eftersom mappen kan vara större än bara måtten som gui:n visar oss

                    /** Det som egentligen görs här är att måtten på världskameran (i pixlar) jämförs med spelarens
                     * kamera och säkerställer att vi ritar ENDAST pixlarna/tiles:en som vi kan se inuti GUI:t. Resten
                     * ritas endast där spelarkameran rör sig till, alltså när vi rör på gubben.
                     *
                     * Detta ger bättre rendering performance.*/


                    if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                        //tileIndex = playTileAnimations(tileIndex);
                        if (tile[tileIndex] != null && tileIndex != 11) {
                            g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                        }

                    }

                    if (debugON) { // OK att ta bort
                        //debug.showMapTiles(g2, screenX, screenY, gp.tileSize, tileIndex);
                    }
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
