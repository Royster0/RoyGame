package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);

        type = type_sword;
        name = "Basic Sword";
        down1 = setup("objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "] \nAn old rusty sword";
    }
}
