package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        type = type_obstacle;
        down1 = setup("objects/door", gamePanel.tileSize, gamePanel.tileSize);
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
        dialogues[0][0] = "You need a key...";
    }

    // Interaction with player
    @Override
    public void interact() {
        startDialogue(this, 0);
    }
}
