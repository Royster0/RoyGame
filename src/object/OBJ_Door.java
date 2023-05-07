package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "Door";
        down1 = setup("objects/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        hitBox.x = 0;
        hitBox.y = 16;
        hitBox.width = 48;
        hitBox.height = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
    }
}
