package main;

import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_WiseMan;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_RedSlime;
import object.*;
import tiles_interactive.IT_DestructibleWall;
import tiles_interactive.IT_DryTree;
import tiles_interactive.IT_MetalPlate;

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

        // Door 1
        gamePanel.objects[mapNum][i] = new OBJ_Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 14;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 28;

        // Door 2
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 12;

        // Chest
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.objects[mapNum][i].setLoot(new OBJ_Key(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 30;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 29;

        // Axe
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Axe(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 37;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 9;

        // Lantern
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Lantern(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 37;

        // Boots
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Boots(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 35;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 37;

        // Key
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Key(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 31;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 21;

        mapNum = 2;
        i = 0;

        // Chest 1
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.objects[mapNum][i].setLoot(new OBJ_Pickaxe(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 40;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 41;

        // Chest 2
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.objects[mapNum][i].setLoot(new OBJ_Health_Potion(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 13;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 16;

        // Chest 3
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.objects[mapNum][i].setLoot(new OBJ_Health_Potion(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 26;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 34;

        // Chest 4
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.objects[mapNum][i].setLoot(new OBJ_Health_Potion(gamePanel));
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 27;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 15;

        // Iron Door
        i++;
        gamePanel.objects[mapNum][i] = new OBJ_Door_Iron(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 18;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 23;
    }

    // Adds the NPCs into their respective 2D array.
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // Wise Man
        gamePanel.npc[mapNum][i] = new NPC_WiseMan(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 21;

        mapNum = 1;

        // Merchant
        gamePanel.npc[mapNum][i] = new NPC_Merchant(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 7;

        mapNum = 2;

        // Big Rock 1
        gamePanel.npc[mapNum][i] = new NPC_BigRock(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 20;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 25;

        // Big Rock 2
        i++;
        gamePanel.npc[mapNum][i] = new NPC_BigRock(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 11;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 18;

        // Big Rock 3
        i++;
        gamePanel.npc[mapNum][i] = new NPC_BigRock(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 23;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 14;
    }

    // Adds the monsters to the monster 2D array.
    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        // Green slime 1
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 38;

        // Green slime 2
        i++;
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 20;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 36;

        // Green slime 3
        i++;
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 38;

        // Green slime 4
        i++;
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 39;

        // Green slime 5
        i++;
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 37;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 26;

        // Green slime 6
        i++;
        gamePanel.monster[mapNum][i] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 23;

        // Red slime 1
        i++;
        gamePanel.monster[mapNum][i] = new MON_RedSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 26;

        // Red slime 2
        i++;
        gamePanel.monster[mapNum][i] = new MON_RedSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 10;

        // Red slime 3
        i++;
        gamePanel.monster[mapNum][i] = new MON_RedSlime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = gamePanel.tileSize * 35;
        gamePanel.monster[mapNum][i].worldY = gamePanel.tileSize * 11;
    }

    // Adds the interactive tiles to their 2D array.
    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

        // Top right dry trees
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 31, 12); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 12); i++;

        // Middle right dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 21); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 21); i++;

        // Chest dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 28); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 29); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 30); i++;

        // Shop dry tree
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 14, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 40); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 13, 41); i++;
        gamePanel.iTile[mapNum][i] = new IT_DryTree(gamePanel, 12, 41);

        mapNum = 2;
        i = 0;

        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 18, 30); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 17, 31); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 17, 32); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 17, 34); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 18, 34); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 18, 33); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 10, 22); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 10, 24); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 38, 18); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 38, 19); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 38, 20); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 38, 21); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 18, 13); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 18, 14); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 22, 28); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 30, 28); i++;
        gamePanel.iTile[mapNum][i] = new IT_DestructibleWall(gamePanel, 32, 28); i++;

        gamePanel.iTile[mapNum][i] = new IT_MetalPlate(gamePanel, 20, 22); i++;
        gamePanel.iTile[mapNum][i] = new IT_MetalPlate(gamePanel, 8, 17); i++;
        gamePanel.iTile[mapNum][i] = new IT_MetalPlate(gamePanel, 39, 31);
    }
}
