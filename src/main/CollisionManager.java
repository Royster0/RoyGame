package main;

import entity.Entity;

// Manages all the collision in the game.
public class CollisionManager {

    GamePanel gamePanel;

    // Constructor that passes in GamePanel.
    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Checks the tiles and if it is colliding with entities.
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    // Retrieving index
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < gamePanel.objects[1].length; i++) {

            if(gamePanel.objects[gamePanel.currentMap][i] != null) {
                // Get entity's hitBox position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's hitBox position
                gamePanel.objects[gamePanel.currentMap][i].hitBox.x = gamePanel.objects[gamePanel.currentMap][i].worldX + gamePanel.objects[gamePanel.currentMap][i].hitBox.x;
                gamePanel.objects[gamePanel.currentMap][i].hitBox.y = gamePanel.objects[gamePanel.currentMap][i].worldY + gamePanel.objects[gamePanel.currentMap][i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gamePanel.objects[gamePanel.currentMap][i].hitBox)) {
                    if(gamePanel.objects[gamePanel.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                gamePanel.objects[gamePanel.currentMap][i].hitBox.x = gamePanel.objects[gamePanel.currentMap][i].hitBoxDefaultX;
                gamePanel.objects[gamePanel.currentMap][i].hitBox.y = gamePanel.objects[gamePanel.currentMap][i].hitBoxDefaultY;
            }
        }

        return index;
    }

    // check npc or monster collision with player
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for(int i = 0; i < target[1].length; i++) {
            if(target[gamePanel.currentMap][i] != null) {

                // Get entity's hitBox position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's hitBox position
                target[gamePanel.currentMap][i].hitBox.x = target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].hitBox.x;
                target[gamePanel.currentMap][i].hitBox.y = target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(target[gamePanel.currentMap][i].hitBox)) {
                    if(target[gamePanel.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                target[gamePanel.currentMap][i].hitBox.x = target[gamePanel.currentMap][i].hitBoxDefaultX;
                target[gamePanel.currentMap][i].hitBox.y = target[gamePanel.currentMap][i].hitBoxDefaultY;
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactedPlayer = false;

        // Get entity's hitBox position
        entity.hitBox.x = entity.worldX + entity.hitBox.x;
        entity.hitBox.y = entity.worldY + entity.hitBox.y;

        // Get object's hitBox position
        gamePanel.player.hitBox.x = gamePanel.player.worldX + gamePanel.player.hitBox.x;
        gamePanel.player.hitBox.y = gamePanel.player.worldY + gamePanel.player.hitBox.y;

        switch (entity.direction) {
            case "up" -> entity.hitBox.y -= entity.speed;
            case "down" -> entity.hitBox.y += entity.speed;
            case "left" -> entity.hitBox.x -= entity.speed;
            case "right" -> entity.hitBox.x += entity.speed;
        }
        if (entity.hitBox.intersects(gamePanel.player.hitBox)) {
            entity.collisionOn = true;
            contactedPlayer = true;
        }

        entity.hitBox.x = entity.hitBoxDefaultX;
        entity.hitBox.y = entity.hitBoxDefaultY;
        gamePanel.player.hitBox.x = gamePanel.player.hitBoxDefaultX;
        gamePanel.player.hitBox.y = gamePanel.player.hitBoxDefaultY;

        return contactedPlayer;
    }
}
