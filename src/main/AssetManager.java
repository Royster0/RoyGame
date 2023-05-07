package main;

import entity.NPC_WiseMan;
import monster.MON_GreenSlime;
import object.*;

public class AssetManager {

    GamePanel gamePanel;
    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        // Coin 1
        gamePanel.objects[0] = new OBJ_Coin(gamePanel);
        gamePanel.objects[0].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[0].worldY = gamePanel.tileSize * 39;

        // Key 2
        gamePanel.objects[1] = new OBJ_Key(gamePanel);
        gamePanel.objects[1].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[1].worldY = gamePanel.tileSize * 41;

        // Axe
        gamePanel.objects[2] = new OBJ_Axe(gamePanel);
        gamePanel.objects[2].worldX = gamePanel.tileSize * 33;
        gamePanel.objects[2].worldY = gamePanel.tileSize * 21;

        // Blue Shield
        gamePanel.objects[3] = new OBJ_Shield_Upgrade(gamePanel);
        gamePanel.objects[3].worldX = gamePanel.tileSize * 35;
        gamePanel.objects[3].worldY = gamePanel.tileSize * 21;

        // Health Potion
        gamePanel.objects[4] = new OBJ_Health_Potion(gamePanel);
        gamePanel.objects[4].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[4].worldY = gamePanel.tileSize * 27;
    }

    public void setNPC() {
        // Wise Man
        gamePanel.npc[0] = new NPC_WiseMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;
        // Green slime 1
        gamePanel.monster[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 21;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 38;
        i++;
        // Green slime 2
        gamePanel.monster[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;
        // Green slime 3
        gamePanel.monster[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 37;
        i++;
        // Green slime 4
        gamePanel.monster[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;
        // Green slime 5
        gamePanel.monster[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
    }
}
