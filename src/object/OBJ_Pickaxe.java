package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gamePanel) {
        super(gamePanel);

        type = type_pickaxe;
        name = objName;
        description = "[" + name + "\nMaybe I can cut stones]";
        down1 = setup("objects/pickaxe", gamePanel.tileSize, gamePanel.tileSize);
        attackArea.width = 30;
        attackArea.height = 30;
        attackValue = 2;
        price = 30;
        knockbackPower = 4;
        motion1_dur = 9;
        motion2_dur = 22;
    }
}
