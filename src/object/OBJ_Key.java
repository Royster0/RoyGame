package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gamePanel;

    // Constructor for the key, initializes variables
    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Key";
        type = type_consumable;
        down1 = setup("objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "] \nI wonder how I got this...";
        price = 5;
    }

    // When player uses key, consume and unlock door.
    @Override
    public boolean use(Entity entity) {
        gamePanel.gameState = gamePanel.dialogueState;

        int objIndex = getDetected(entity, gamePanel.objects, "Door");

        if(objIndex != 999) {
            gamePanel.ui.currentDialogue = "You opened the door, congratulations!";
            gamePanel.playEffect(3);
            gamePanel.objects[gamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            gamePanel.ui.currentDialogue = "What are you doing, man";
            return false;
        }
    }
}
