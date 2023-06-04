package object;

import main.GamePanel;

public class OBJ_Rock extends Projectile{

    public static final String objName = "Rock";

    public OBJ_Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        speed = 4;
        maxLife = 50;
        life = maxLife;
        attack = 2;
        manaCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
    }
}
