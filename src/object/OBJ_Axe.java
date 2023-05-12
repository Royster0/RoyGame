package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        type = type_axe;
        name = "Woodcutter's Axe";
        description = "[" + name + "\nMaybe I can cut trees";
        down1 = setup("objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackArea.width = 30;
        attackArea.height = 30;
        price = 10;
    }
}
