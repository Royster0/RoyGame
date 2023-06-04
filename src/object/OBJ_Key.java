package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Key";

    // Constructor for the key, initializes variables
    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        type = type_consumable;
        down1 = setup("objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "] \nI wonder how I got this...";
        price = 5;
        stackable = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You opened the door, congratulations!";
        dialogues[1][0] = "What are you doing, man";
    }

    // When player uses key, consume and unlock door.
    @Override
    public boolean use(Entity entity) {

        int objIndex = getDetected(entity, gamePanel.objects, "Door");

        if(objIndex != 999) {
            startDialogue(this, 0);
            gamePanel.playEffect(3);
            gamePanel.objects[gamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
