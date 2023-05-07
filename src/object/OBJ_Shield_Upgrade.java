package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Upgrade extends Entity{

    public OBJ_Shield_Upgrade(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Blue Shield";
        down1 = setup("objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny new shield";
    }
}
