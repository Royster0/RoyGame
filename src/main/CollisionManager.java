package main;

import entity.Entity;

public class CollisionManager {

    GamePanel gamePanel;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < gamePanel.objects.length; i++) {
            if(gamePanel.objects[i] != null) {
                // Get entity's hitBox position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's hitBox position
                gamePanel.objects[i].hitBox.x = gamePanel.objects[i].worldX + gamePanel.objects[i].hitBox.x;
                gamePanel.objects[i].hitBox.y = gamePanel.objects[i].worldY + gamePanel.objects[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gamePanel.objects[i].hitBox)) {
                    if(gamePanel.objects[i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                gamePanel.objects[i].hitBox.x = gamePanel.objects[i].hitBoxDefaultX;
                gamePanel.objects[i].hitBox.y = gamePanel.objects[i].hitBoxDefaultY;
            }
        }

        return index;
    }

    // check npc or monster collision with player
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {

                // Get entity's hitBox position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's hitBox position
                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(target[i].hitBox)) {
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                target[i].hitBox.x = target[i].hitBoxDefaultX;
                target[i].hitBox.y = target[i].hitBoxDefaultY;
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
