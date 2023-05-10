package main;

import java.awt.*;

public class EventManager {
    GamePanel gamePanel;
    EventRect[][] eventRect;

    // margin between events being able to trigger
    int previousEventX, previousEventY;
    boolean canTriggerEvent = true;

    public EventManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 22;
            eventRect[col][row].y = 22;
            eventRect[col][row].width = 3;
            eventRect[col][row].height = 3;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        // Check if player is > 1 tile away from last event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int totDistance = Math.max(xDistance, yDistance);
        if(totDistance > gamePanel.tileSize) {
            canTriggerEvent = true;
        }

        if(canTriggerEvent) {
            if(hit(27, 16, "right")) damagePit(27, 16, gamePanel.dialogueState);
            if(hit(23, 12, "up")) healingPool(23, 12, gamePanel.dialogueState);
            if(hit(24, 12, "up")) healingPool(24, 12, gamePanel.dialogueState);
            if(hit(22, 12, "up")) healingPool(22, 12, gamePanel.dialogueState);
            if(hit(21, 12, "up")) healingPool(21, 12, gamePanel.dialogueState);
            if(hit(25, 12, "up")) healingPool(25, 12, gamePanel.dialogueState);
        }

    }

    // Checks if event has been triggered ir "hit".
    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gamePanel.player.hitBox.x = gamePanel.player.worldX + gamePanel.player.hitBox.x;
        gamePanel.player.hitBox.y = gamePanel.player.worldY + gamePanel.player.hitBox.y;
        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        // Can check if player is facing certain direction, or if there is no direction requirement.
        if(gamePanel.player.hitBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.hitBox.x = gamePanel.player.hitBoxDefaultX;
        gamePanel.player.hitBox.y = gamePanel.player.hitBoxDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playEffect(6);
        gamePanel.ui.currentDialogue = "You've fell into a pit.";
        gamePanel.player.life -= 1;
        canTriggerEvent = false;
    }

    public void healingPool(int col, int row, int gameState) {
        if(gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playEffect(2);
            gamePanel.ui.currentDialogue = "You've drank some water. \nYour life and mana has been recovered.";
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assManager.setMonster();
        }
    }

    public void teleport(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You've been transported.";
        gamePanel.player.worldX = gamePanel.tileSize * 37;
        gamePanel.player.worldY = gamePanel.tileSize * 10;
    }
}
