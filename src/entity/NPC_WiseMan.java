package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_WiseMan extends Entity{
    public NPC_WiseMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        getNPCImage();
        setDialogue();
    }

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

    public void setDialogue() {
        dialogues[0] = "Greetings, lad";
        dialogues[1] = "There are things here no man should see \nfor his own eyes...";
        dialogues[2] = "I used to be a great wizard...";
        dialogues[3] = "But then I took an arrow to the knee!";
        dialogues[4] = "Good luck, youngster!";
    }

    @Override
    public void setAction() {
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

    @Override
    public void speak() {
        super.speak();
    }
}
