package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

// Old man that speaks to the player at the start of the game.
public class NPC_WiseMan extends Entity{
    public NPC_WiseMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        hitBox = new Rectangle();
        hitBox.x = 8;
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 30;
        hitBox.height = 30;

        getNPCImage();
        setDialogue();
    }

    // Retrieves images.
    public void getNPCImage() {
        up1 = setup("npc/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("npc/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("npc/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("npc/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("npc/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("npc/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("npc/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("npc/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Sets the dialogue the man says
    public void setDialogue() {
        dialogues[0] = "Greetings, lad";
        dialogues[1] = "There are things here no man should see \nfor his own eyes...";
        dialogues[2] = "I used to be a great wizard...";
        dialogues[3] = "Good luck, youngster!";
    }

    // Sets action of old man after being spoken to.
    @Override
    public void setAction() {
        if(onPath) {
            int goalCol = 12;
            int goalRow = 9;

            searchPath(goalCol, goalRow);
        } else {
            actionInterval++;

            if(actionInterval == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // random from 1-100

                if(i <= 25) direction = "up";
                if(i > 25 && i <= 50) direction = "down";
                if(i > 50 && i <= 75) direction = "left";
                if(i > 75) direction = "right";

                actionInterval = 0;
            }
        }
    }

    // Speaks and then sets the man on a path
    @Override
    public void speak() {
        super.speak();
        onPath = true;
    }
}
