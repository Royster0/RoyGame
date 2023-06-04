package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Health Potion";

    public OBJ_Health_Potion(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        value = 2;
        type = type_consumable;
        name = objName;
        down1 = setup("objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nRestore your health by " + value;
        price = 10;
        stackable = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Your health has been \nrecovered by " + value/2;
    }

    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.life += value;
        gamePanel.playEffect(2);
        return true;
    }
}
