package tiles_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTiles{

    GamePanel gamePanel;

    public IT_Trunk(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("tiles_interactive/trunk", gamePanel.tileSize, gamePanel.tileSize);

        hitBox.x = 0;
        hitBox.y = 0;
        hitBox.width = 0;
        hitBox.height = 0;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
    }
}
