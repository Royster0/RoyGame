package main;

import entity.Entity;

public class EventManager {
    GamePanel gamePanel;
    EventRect[][][] eventRect;
    Entity eventMaster;

    // margin between events being able to trigger
    int previousEventX, previousEventY;
    int tempMap, tempCol, tempRow;
    boolean canTriggerEvent = true;

    public EventManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventMaster = new Entity(gamePanel);

        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 22;
            eventRect[map][col][row].y = 22;
            eventRect[map][col][row].width = 3;
            eventRect[map][col][row].height = 3;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;

            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;

                if (row == gamePanel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You've fell into a pit.";

        eventMaster.dialogues[1][0] = "Your life and mana has been recovered.";
        eventMaster.dialogues[1][1] = "Your progress has been saved.";
    }

    // Checking if player is allowed to trigger an event
    public void checkEvent() {
        // Check if player is > 1 tile away from last event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int totDistance = Math.max(xDistance, yDistance);
        if (totDistance > gamePanel.tileSize) {
            canTriggerEvent = true;
        }

        if (canTriggerEvent) {
            if (hit(0, 27, 16, "right")) damagePit(gamePanel.dialogueState);

            // Healing pool positions
            else if (hit(0, 23, 12, "up")) healingPool(gamePanel.dialogueState);
            else if (hit(0, 24, 12, "up")) healingPool(gamePanel.dialogueState);
            else if (hit(0, 22, 12, "up")) healingPool(gamePanel.dialogueState);
            else if (hit(0, 21, 12, "up")) healingPool(gamePanel.dialogueState);
            else if (hit(0, 25, 12, "up")) healingPool(gamePanel.dialogueState);

            // Shop teleports
            else if(hit(0, 10, 39, "any")) teleport(1, 12, 13, gamePanel.indoor);
            else if(hit(1, 12, 13, "any")) teleport(0, 10, 39, gamePanel.outside);
            else if(hit(1, 12, 13, "any")) teleport(0, 10, 39, gamePanel.outside);

            // Dungeon teleports
            else if(hit(0, 12, 9, "any")) teleport(2, 9,41, gamePanel.dungeon); // to dungeon
            else if(hit(2, 9, 41, "any")) teleport(0, 12,9, gamePanel.outside); // to world
            else if(hit(2, 8, 7, "any")) teleport(3, 26 ,41, gamePanel.dungeon); // to dungeon 2
            else if(hit(3, 26, 41, "any")) teleport(2, 8 ,7, gamePanel.dungeon); // to dungeon 1

            else if(hit(1, 12, 9, "up")) speak(gamePanel.npc[1][0]);
        }
    }

    // Checks if event has been triggered ir "hit".
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gamePanel.currentMap) {
            gamePanel.player.hitBox.x = gamePanel.player.worldX + gamePanel.player.hitBox.x;
            gamePanel.player.hitBox.y = gamePanel.player.worldY + gamePanel.player.hitBox.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            // Can check if player is facing certain direction, or if there is no direction requirement.
            if (gamePanel.player.hitBox.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }
            }

            gamePanel.player.hitBox.x = gamePanel.player.hitBoxDefaultX;
            gamePanel.player.hitBox.y = gamePanel.player.hitBoxDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    // Fall damage pit, experimental
    public void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playEffect(6);
        eventMaster.startDialogue(eventMaster, 0);
        gamePanel.player.life -= 1;
        canTriggerEvent = false;
    }

    // Healing pool that saves player game and restores health and mana.
    public void healingPool(int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playEffect(2);
            eventMaster.startDialogue(eventMaster, 1);
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assManager.setMonster();

            // Healing pool can also save
            gamePanel.saveLoad.save();
        }
    }

    // Experimental
    public void teleport(int map, int col, int row, int area) {
        gamePanel.gameState = gamePanel.transitionState;
        gamePanel.nextAreaBuffer = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTriggerEvent = false;
        gamePanel.playEffect(13);
    }

    public void speak(Entity entity) {
        if(gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }
}
