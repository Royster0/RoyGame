package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int[][][] mapTileNum;
    public boolean drawPath = true;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[50];
        mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("maps/worldmap.txt", 0);
        loadMap("maps/indoor01.txt", 1);
        loadMap("maps/dungeon01.txt", 2);
        loadMap("maps/dungeon02.txt", 3);
    }

    public void getTileImage() {
        setup(0, "000", false);
        setup(1, "001", false);
        setup(2, "002", false);
        setup(3, "003", false);
        setup(4, "004", false);
        setup(5, "005", false);
        setup(6, "006", false);
        setup(7, "007", false);
        setup(8, "008", false);
        setup(9, "009", false);
        setup(10, "010", false);
        setup(11, "011", false);
        setup(12, "012", false);
        setup(13, "013", false);
        setup(14, "014", false);
        setup(15, "015", false);
        setup(16, "016", true);
        setup(17, "017", false);
        setup(18, "018", true);
        setup(19, "019", true);
        setup(20, "020", true);
        setup(21, "021", true);
        setup(22, "022", true);
        setup(23, "023", true);
        setup(24, "024", true);
        setup(25, "025", true);
        setup(26, "026", true);
        setup(27, "027", true);
        setup(28, "028", true);
        setup(29, "029", true);
        setup(30, "030", true);
        setup(31, "031", true);
        setup(32, "032", true);
        setup(33, "033", false);
        setup(34, "034", false);
        setup(35, "035", true);
        setup(36, "036", false);
        setup(37, "037", false);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath, int map) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                while(col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int cameraX = worldX - gamePanel.player.worldX + gamePanel.player.cameraX;
            int cameraY = worldY - gamePanel.player.worldY + gamePanel.player.cameraY;

            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.cameraX &&
               worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.cameraX &&
               worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.cameraY &&
               worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.cameraY) {

                graphics2D.drawImage(tile[tileNum].image, cameraX, cameraY, null);
            }

            worldCol++;

            if(worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

        if(drawPath) {
            graphics2D.setColor(new Color(255, 0, 0, 60));

            for(int i = 0; i < gamePanel.pFinder.pathList.size(); i++) {
                int worldX = gamePanel.pFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.cameraX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.cameraY;

                graphics2D.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }
    }
}
