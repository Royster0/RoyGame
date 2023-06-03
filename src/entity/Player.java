package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

// Player class handles the bulk of player functionality.
public class Player extends Entity {
    KeyHandler keyHandler;
    public final int cameraX;
    public final int cameraY;
    int positionCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    // Constructor that initializes the camera, key handling and setting default values.
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        cameraX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        cameraY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        hitBox = new Rectangle(10, 16, 28, 32);
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    // Sets the player's default values (position, stats, items)
    public void setDefaultValues() {
        // PLAYER DEFAULT POSITION
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 22;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER DEFAULT STATS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;

        // PLAYER DEFAULT WEAPONS
        currentWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Shield_Wood(gamePanel);
        projectile = new OBJ_Fireball(gamePanel);
        attack = getAttack();
        defense = getDefense();
    }

    // Sets the player's default positions.
    public void setDefaultPositions() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        direction = "down";
    }

    // Restores player mana
    public void restoreLifeMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    // Sets the player's default items
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_Lantern(gamePanel));
        inventory.add(new OBJ_Tent(gamePanel));
    }

    // Player's attack calculation
    public int getAttack() {
            attackArea = currentWeapon.attackArea;
            return attack = strength * currentWeapon.attackValue;
    }

    // Player's defense calculation
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    // Import and scale player images
    public void getPlayerImage() {
        up1 = setup("player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Import and scale player attacking images
    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }
    }

    // Import and scale player sleeping images.
    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }

    // Check for updates on player (animations)
    @Override
    public void update() {
        if(attacking) {

            attacking();

        } else if(keyHandler.upPressed || keyHandler.downPressed ||
            keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {

            if(keyHandler.upPressed) {
                direction = "up";
            }
            if(keyHandler.downPressed) {
                direction = "down";
            }
            if(keyHandler.leftPressed) {
                direction = "left";
            }
            if(keyHandler.rightPressed) {
                direction = "right";
            }

            // TILE COLLISION
            collisionOn = false;
            gamePanel.collision.checkTile(this); // send this player to CollisionManager

            // OBJECT COLLISION
            int objectIndex = gamePanel.collision.checkObject(this, true);
            pickUpObject(objectIndex);

            // NPC COLLISION
            int npcIndex = gamePanel.collision.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            // MONSTER COLLISION
            int monsterIndex = gamePanel.collision.checkEntity(this, gamePanel.monster);
            interactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gamePanel.collision.checkEntity(this, gamePanel.iTile);

            // CHECK EVENT
            gamePanel.eventManager.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyHandler.enterPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            // EVENT PRESSED & ATTACK BUG FIX
            if(gamePanel.keyHandler.enterPressed && !attackCanceled) {
                gamePanel.playEffect(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gamePanel.keyHandler.enterPressed = false;

            // SPRITE COUNTER ANIMATION
            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            positionCounter++;

            if(positionCounter == 25) {
                spriteNum = 1;
                positionCounter = 0;
            }
        }

        // PROJECTILE HANDLING
        if(gamePanel.keyHandler.shootPressed && !projectile.alive &&
                shotAvailableCounter == 30 && projectile.hasMana(this)) {
            // Projectile shoots from player's current position, pass as parameter
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractMana(this);
            // Add to projectile list
            for(int i = 0; i < gamePanel.projectile[1].length; i++) {
                Entity temp = gamePanel.projectile[gamePanel.currentMap][i];
                if(temp == null) {
                    gamePanel.projectile[gamePanel.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gamePanel.playEffect(10);
        }

        // DAMAGE-INVULNERABILITY AFTER DAMAGE TAKEN
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Bug Fix for multiple fireballs shooting in close range
        if(shotAvailableCounter < 30) shotAvailableCounter++;

        // Prevent over-healing.
        if(life > maxLife) life = maxLife;
        if(mana > maxMana) mana = maxMana;

        if(life <= 0) {
            gamePanel.gameState = gamePanel.gameOverState;
            gamePanel.ui.commandNum = -1;
            gamePanel.stopMusic();
            gamePanel.playEffect(12);
        }
    }

    // Player attacking animation and extending hitbox to sword
    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // During this animation, check of weapon is colliding with attack-able monster
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int hitboxAreaWidth = hitBox.width;
            int hitboxAreaHeight = hitBox.height;

            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }
            // Attack area becomes solid
            hitBox.width = attackArea.width;
            hitBox.height = attackArea.height;

            // Check monster collision with new worldX and worldY
            int monsterIndex = gamePanel.collision.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockbackPower);

            // Check iTile collision
            int iTileIndex = gamePanel.collision.checkEntity(this, gamePanel.iTile);
            damageITile(iTileIndex);

            // Check projectile collision
            int projectileIndex = gamePanel.collision.checkEntity(this, gamePanel.projectile);
            damageProjectile(projectileIndex);

            // After checking collision, restore the original data.
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitBox.width = hitboxAreaWidth;
            hitBox.height = hitboxAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    // Pick up an object, check inventory size
    public void pickUpObject(int objIndex) {
        if(objIndex != 999) {
            // PICKUP ONLY ITEMS
            if(gamePanel.objects[gamePanel.currentMap][objIndex].type == type_pickup) {
                gamePanel.objects[gamePanel.currentMap][objIndex].use(this);
                gamePanel.objects[gamePanel.currentMap][objIndex] = null;
            }
            // OBSTACLES
            else if(gamePanel.objects[gamePanel.currentMap][objIndex].type == type_obstacle) {
                if(keyHandler.enterPressed) {
                    attackCanceled = true;
                    gamePanel.objects[gamePanel.currentMap][objIndex].interact();
                }
            }
            // INVENTORY ITEMS
            else {
                String text;
                if(canObtainItem(gamePanel.objects[gamePanel.currentMap][objIndex])) {
                    gamePanel.playEffect(1);
                    text = "Picked up a " + gamePanel.objects[gamePanel.currentMap][objIndex].name;
                } else {
                    text = "Your inventory is full!";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.objects[gamePanel.currentMap][objIndex] = null;
            }
        }
    }

    // Interaction with NPCs
    public void interactNPC(int npcIndex) {
        if(gamePanel.keyHandler.enterPressed) {
            if(npcIndex != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[gamePanel.currentMap][npcIndex].speak();
            }
        }
    }

    // Enemy damaging player
    public void interactMonster(int monsterIndex) {
        if(monsterIndex != 999 && !gamePanel.monster[gamePanel.currentMap][monsterIndex].dying) {
            if(!invincible) {
                gamePanel.playEffect(6);

                // damage calc
                int damage = gamePanel.monster[gamePanel.currentMap][monsterIndex].attack - defense;
                life -= damage;
                invincible = true;
            }
        }
    }

    // Player damaging enemy
    public void damageMonster(int monIndex, int attack, int knockbackPower) {
        if(monIndex != 999) {
            if(!gamePanel.monster[gamePanel.currentMap][monIndex].invincible) {
                gamePanel.playEffect(5);

                if(knockbackPower > 0) {
                    knockBack(gamePanel.monster[gamePanel.currentMap][monIndex], knockbackPower);
                }

                // damage calc
                int damage = attack - gamePanel.monster[gamePanel.currentMap][monIndex].defense;
                if(damage < 0) {
                    damage = 0;
                }
                gamePanel.monster[gamePanel.currentMap][monIndex].life -= damage;
                gamePanel.ui.addMessage(damage + " dmg");
                gamePanel.monster[gamePanel.currentMap][monIndex].invincible = true;
                gamePanel.monster[gamePanel.currentMap][monIndex].damageReaction();

                if(gamePanel.monster[gamePanel.currentMap][monIndex].life <= 0) {
                    gamePanel.monster[gamePanel.currentMap][monIndex].dying = true;
                    gamePanel.ui.addMessage(gamePanel.monster[gamePanel.currentMap][monIndex].name + " slain");
                    gamePanel.ui.addMessage("+ " + gamePanel.monster[gamePanel.currentMap][monIndex].exp + " exp");
                    exp += gamePanel.monster[gamePanel.currentMap][monIndex].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void knockBack(Entity entity, int knockbackPower) {
        entity.direction = direction;
        entity.speed += knockbackPower;
        entity.knockback = true;
    }

    // Player damaging an interactive tile.
    public void damageITile(int iTileIndex) {
        if(iTileIndex != 999 && gamePanel.iTile[gamePanel.currentMap][iTileIndex].destructable &&
                gamePanel.iTile[gamePanel.currentMap][iTileIndex].correctItem(this) && !gamePanel.iTile[gamePanel.currentMap][iTileIndex].invincible) {
            gamePanel.iTile[gamePanel.currentMap][iTileIndex].life--;
            gamePanel.iTile[gamePanel.currentMap][iTileIndex].playEffect();
            gamePanel.iTile[gamePanel.currentMap][iTileIndex].invincible = true;

            generateParticle(gamePanel.iTile[gamePanel.currentMap][iTileIndex], gamePanel.iTile[gamePanel.currentMap][iTileIndex]);

            if(gamePanel.iTile[gamePanel.currentMap][iTileIndex].life < 0) {
                gamePanel.iTile[gamePanel.currentMap][iTileIndex] = gamePanel.iTile[gamePanel.currentMap][iTileIndex].getDestroyedForm();
            }
        }
    }

    // Damage monster's projectile
    public void damageProjectile(int i) {
        if(i != 999) {
            Entity projectile = gamePanel.projectile[gamePanel.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    // Check if player has leveled up, increase stats
    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 3;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            life = maxLife;

            gamePanel.playEffect(8);
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue = "You are now level " + level +"\nYour resolve strengthens" ;
        }
    }

    // Selecting and updating new item in inventory.
    public void selectItem() {
        int itemIndex = gamePanel.ui.getItemIndex(gamePanel.ui.playerSlotCol, gamePanel.ui.playerSlotRow);
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable) {
                if(selectedItem.use(this)) {
                    if(selectedItem.stackAmount > 1) selectedItem.stackAmount--;
                    else inventory.remove(itemIndex);
                }
            }
            if(selectedItem.type == type_light) {
                if(currentLight == selectedItem) {
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
        }
    }

    // Searches the player's inventory and returns index where it is.
    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
            }
        }
        return itemIndex;
    }

    // Check if item is obtainable (max stack amount, max inventory, etc).
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        // Check if stackable
        if(item.stackable) {
            int index = searchItemInInventory(item.name);
            if(index != 999) {
                inventory.get(index).stackAmount++;
                canObtain = true;
            }
            else {
                if(inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        // not stackable
        else {
            if(inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    // Draws player on screen
    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int tempCameraX = cameraX;
        int tempCameraY = cameraY;

        switch(direction) {
            case "up" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if(attacking) {
                    tempCameraY = cameraY - gamePanel.tileSize; // bug fix for attacking position
                    if (spriteNum == 1) image = attackUp1;
                    if (spriteNum == 2) image = attackUp2;
                }
            }
            case "down" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if(attacking) {
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                }
            }
            case "left" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                if(attacking) {
                    tempCameraX = cameraX - gamePanel.tileSize; // bug fix for attacking position
                    if (spriteNum == 1) image = attackLeft1;
                    if (spriteNum == 2) image = attackLeft2;
                }
            }
            case "right" -> {
                if(!attacking) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
                if(attacking) {
                    if (spriteNum == 1) image = attackRight1;
                    if (spriteNum == 2) image = attackRight2;
                }
            }
        }

        // DAMAGE-TAKEN EFFECT & DRAW CHARACTER
        if(invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        graphics2D.drawImage(image, tempCameraX, tempCameraY, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        /* DEBUG
        // Draw Player HitBox
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(cameraX + hitBox.x, cameraY + hitBox.y, hitBox.width, hitBox.height);
        */
    }
}
