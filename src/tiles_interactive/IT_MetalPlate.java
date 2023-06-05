package tiles_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTiles{

    GamePanel gamePanel;
    public static final String itName = "Metal Plate";

    public IT_MetalPlate(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        name = itName;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("tiles_interactive/metalplate", gamePanel.tileSize, gamePanel.tileSize);
    }
}
