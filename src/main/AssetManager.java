package main;

import entity.NPC_WiseMan;
import monster.MON_GreenSlime;
import object.*;
import tiles_interactive.IT_DryTree;

public class AssetManager {

    GamePanel gamePanel;
    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int mapNum = 0;

        // Coin 1
        gamePanel.objects[mapNum][0] = new OBJ_Coin(gamePanel);
        gamePanel.objects[mapNum][0].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[mapNum][0].worldY = gamePanel.tileSize * 39;

        // Key 2
        gamePanel.objects[mapNum][1] = new OBJ_Key(gamePanel);
        gamePanel.objects[mapNum][1].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[mapNum][1].worldY = gamePanel.tileSize * 41;

        // Axe
        gamePanel.objects[mapNum][2] = new OBJ_Axe(gamePanel);
        gamePanel.objects[mapNum][2].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[mapNum][2].worldY = gamePanel.tileSize * 8;

        // Blue Shield
        gamePanel.objects[mapNum][3] = new OBJ_Shield_Upgrade(gamePanel);
        gamePanel.objects[mapNum][3].worldX = gamePanel.tileSize * 35;
        gamePanel.objects[mapNum][3].worldY = gamePanel.tileSize * 39;

        // Health Potion
        gamePanel.objects[mapNum][4] = new OBJ_Health_Potion(gamePanel);
        gamePanel.objects[mapNum][4].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[mapNum][4].worldY = gamePanel.tileSize * 27;
    }

    public void setNPC() {
        int mapNum = 0;
        // Wise Man
        gamePanel.npc[mapNum][0] = new NPC_WiseMan(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        // Green slime 1
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 38;
        i++;
        // Green slime 2
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 42;
        i++;
        // Green slime 3
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 37;
        i++;
        // Green slime 4
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 42;
        i++;
        // Green slime 5
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 42;
        i++;
        // Slime 6
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 11;
        i++;
        // Slime 7
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 37;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 10;
        i++;
        //Slime 8
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 35;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 20;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        // Top right dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 31, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 12); i++;

        // Left dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 12, 23); i++;

        // Right dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 20); i++;
        // gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 21); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 22); i++;

        // Shop dry trees
        /*
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 17, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 16, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 15, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 14, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 38); */
    }
}
