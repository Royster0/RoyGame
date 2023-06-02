package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gamePanel, Entity loot) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
        image = setup("objects/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("objects/chest_opened", gamePanel.tileSize, gamePanel.tileSize);
        down1 = image;
        collision = true;

        hitBox.x = 4;
        hitBox.y = 16;
        hitBox.width = 40;
        hitBox.height = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
    }

    @Override
    public void interact() {
        gamePanel.gameState = gamePanel.dialogueState;

        if(!opened) {
            gamePanel.playEffect(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You found a ").append(loot.name).append("! ");

            if(!gamePanel.player.canObtainItem(loot)) {
                sb.append("Your inventory is full though.");
            }
            else {
                sb.append(" It has been added to your bag.");
                down1 = image2;
                opened = true;
            }
            gamePanel.ui.currentDialogue = sb.toString();
        }
        else {
            gamePanel.ui.currentDialogue = "You already hit this chest, you hog!";
        }
    }
}
