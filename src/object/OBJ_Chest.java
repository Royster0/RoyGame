package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

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

    // Setting this chest's loot
    @Override
    public void setLoot(Entity loot) {
        this.loot = loot;
        // Setting here to prevent loot bug
        setDialogue();
    }

    // Setting chest dialogue
    public void setDialogue() {
        dialogues[0][0] = "You found a " + loot.name + "!";
        dialogues[0][1] = "Your inventory is full though.";

        dialogues[1][0] = "You found a " + loot.name + "!";
        dialogues[1][1] = "It has been added to your bag.";

        dialogues[2][0] = "You already opened this chest! You hog!";
    }

    // Interacting with the chest
    @Override
    public void interact() {
        gamePanel.gameState = gamePanel.dialogueState;

        if(!opened) {
            gamePanel.playEffect(3);

            if(!gamePanel.player.canObtainItem(loot)) {
                startDialogue(this, 0);
            }
            else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }
        else {
            startDialogue(this, 2);
        }
    }
}
