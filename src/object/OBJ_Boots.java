package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        down1 = setup("objects/boots", gamePanel.tileSize, gamePanel.tileSize);
        price = 20;
    }
}
