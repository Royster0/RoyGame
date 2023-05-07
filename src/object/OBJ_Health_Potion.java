package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity {

    GamePanel gamePanel;

    public OBJ_Health_Potion(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        value = 3;
        type = type_consumable;
        name = "Health Potion";
        down1 = setup("objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nRestore your health by " + value;
    }

    public void use(Entity entity) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "Your health has been \nrecovered by " + value;
        entity.life += value;
        gamePanel.playEffect(2);
    }
}
