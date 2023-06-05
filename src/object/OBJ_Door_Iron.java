package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Iron Door";

    public OBJ_Door_Iron(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        type = type_obstacle;
        down1 = setup("objects/door_iron", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        hitBox.x = 0;
        hitBox.y = 16;
        hitBox.width = 48;
        hitBox.height = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "It won't open. There must be something here...";
    }

    // Interaction with player
    @Override
    public void interact() {
        startDialogue(this, 0);
    }
}
