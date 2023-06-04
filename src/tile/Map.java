// This class introduces a minimap accessible to the player

package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{

    GamePanel gp;
    BufferedImage[] worldMap;
    public boolean minimapOn = false;

    // Constructor instantiates GamePanel.
    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    // Creating the world map that can be used to draw full and mini map.
    public void createWorldMap() {
        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        // initialize world map
        for(int i = 0; i < gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                int tileNum = mapTileNum[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                g2.drawImage(tile[tileNum].image, x, y, null);

                col++;
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }

    // Drawing full map of the world.
    public void drawFullMap(Graphics2D g2) {

        // Background
        g2.setColor(new Color(74, 118, 169));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw Map
        int width = 500;
        int height = 500;
        int x = (gp.screenWidth / 2) - (width / 2);
        int y = (gp.screenHeight / 2) - (height / 2);
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

        // Draw player on the map
        double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
        int playerX = (int)(x + gp.player.worldX / scale);
        int playerY = (int)(y + gp.player.worldY / scale);
        int playerSize = (int)(gp.tileSize / scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        // Hint on map
        g2.setFont(gp.ui.litebulb_arcade.deriveFont(48f));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 750, 500);
    }

    public void drawMinimap(Graphics2D g2) {
        if(minimapOn) {
            // Drawing map
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width;
            int y = 0;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

            // Draw player on the map
            double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
            int playerX = (int)(x + gp.player.worldX / scale) - 6;
            int playerY = (int)(y + gp.player.worldY / scale) - 6;
            int playerSize = (int)(gp.tileSize / 3);
            g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
