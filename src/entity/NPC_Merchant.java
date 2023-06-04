package entity;

import main.GamePanel;
import object.*;

import java.awt.*;

// Merchant NPC class.
public class NPC_Merchant extends Entity{
    public NPC_Merchant(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        hitBox = new Rectangle();
        hitBox.x = 8;
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;

        getImage();
        setDialogue();
        setItems();
    }

    // Retrieves images and sets up.
    public void getImage() {
        up1 = setup("npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Sets the dialogue for the merchant.
    public void setDialogue() {
        // Greeting dialogue
        dialogues[0][0] = "I have some stuff you may want.\nI got tons of stuff\nTake a look";
        // Exit dialogue
        dialogues[1][0] = "Farewell, see you again.";
        // Not enough coins dialogue
        dialogues[2][0] = "You need more money, bum";
        // Inventory full
        dialogues[3][0] = "Your inventory is full dude!";
        // Selling equipped item
        dialogues[4][0] = "You have that equipped!";
    }

    // Items that the merchant sells.
    public void setItems() {
        inventory.add(new OBJ_Health_Potion(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Sword_Normal(gamePanel));
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_Shield_Wood(gamePanel));
        inventory.add(new OBJ_Shield_Upgrade(gamePanel));
    }

    // Shows dialogue and speaks to player.
    @Override
    public void speak() {

        gamePanel.gameState = gamePanel.tradeState;
        gamePanel.ui.npc = this; // give this npc to ui
    }
}
