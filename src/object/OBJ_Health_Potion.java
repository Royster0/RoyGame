package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity {

    GamePanel gamePanel;

    public OBJ_Health_Potion(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        value = 2;
        type = type_consumable;
        name = "Health Potion";
        down1 = setup("objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nRestore your health by " + value;
        price = 10;
    }

    public boolean use(Entity entity) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "Your health has been \nrecovered by " + value/2;
        entity.life += value;
        gamePanel.playEffect(2);
        return true;
    }
}
