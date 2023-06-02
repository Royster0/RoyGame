package main;

import entity.NPC_Merchant;
import entity.NPC_WiseMan;
import monster.MON_GreenSlime;
import object.*;
import tiles_interactive.IT_DryTree;

// Asset manager class that handles objects/entities on the map.
public class AssetManager {

    GamePanel gamePanel;

    // Constructor that handles game panel.
    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Add objects (such as coins) on the map into an 2D array.
    public void setObject() {
        int mapNum = 0;
        int i = 0;
        // Coin 1
        gamePanel.objects[mapNum][i] = new OBJ_Coin(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 39;

        // Key 2
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Key(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 41;

        // Axe
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Axe(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 8;

        // Blue Shield
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Shield_Upgrade(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 35;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 39;

        // Health Potion
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Health_Potion(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 22;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 27;

        // Door 1
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 14;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 28;

        // Door 2
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 10;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 12;

        // Chest 1
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel, new OBJ_Key(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 30;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 29;
    }

    // Adds the NPCs into their respective 2D array.
    public void setNPC() {
        int mapNum = 0;
        // Wise Man
        gamePanel.npc[mapNum][0] = new NPC_WiseMan(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 21;

        mapNum = 1;
        gamePanel.npc[mapNum][0] = new NPC_Merchant(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 7;
    }

    // Adds the monsters to the monster 2D array.
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

    // Adds the interactive tiles to their 2D array.
    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        // Top right dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 31, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 12); i++;

        // Right dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 20); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 21); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 22); i++;

        // Shop dry trees
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 17, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 16, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 15, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 14, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 39); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 38); i++;

        // Chest 1 trees
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 28); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 27); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 27); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 27); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 27); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 26, 27); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 25, 27);

    }
}
