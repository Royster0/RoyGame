package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyHandler;
    public final int cameraX;
    public final int cameraY;
    int positionCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    // Constructor
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        cameraX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        cameraY = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);

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
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER DEFAULT STATS
        level = 1;
        maxLife = 8;
        life = 6;
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

    // Sets the player's default items
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel));
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
            gamePanel.projectileList.add(projectile);
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
            damageMonster(monsterIndex, attack);

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
            if(gamePanel.objects[objIndex].type == type_pickup) {
                gamePanel.objects[objIndex].use(this);
                gamePanel.objects[objIndex] = null;
            } else {
                // INVENTORY ITEMS
                String text = "";
                if(inventory.size() < maxInventorySize) {
                    inventory.add(gamePanel.objects[objIndex]);
                    gamePanel.playEffect(1);
                    text = "Picked up a " + gamePanel.objects[objIndex].name;
                } else {
                    text = "Your inventory is full!";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.objects[objIndex] = null;
            }
        }
    }

    // Interaction with NPCs
    public void interactNPC(int npcIndex) {
        if(gamePanel.keyHandler.enterPressed) {
            if(npcIndex != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[npcIndex].speak();
            }
        }
    }

    // Enemy damaging player
    public void interactMonster(int monsterIndex) {
        if(monsterIndex != 999 && !gamePanel.monster[monsterIndex].dying) {
            if(!invincible) {
                gamePanel.playEffect(6);

                // damage calc
                int damage = gamePanel.monster[monsterIndex].attack - defense;
                life -= damage;
                invincible = true;
            }
        }
    }

    // Player damaging enemy
    public void damageMonster(int monIndex, int attack) {
        if(monIndex != 999) {
            if(!gamePanel.monster[monIndex].invincible) {
                gamePanel.playEffect(5);

                // damage calc
                int damage = attack - gamePanel.monster[monIndex].defense;
                if(damage < 0) {
                    damage = 0;
                }
                gamePanel.monster[monIndex].life -= damage;
                gamePanel.ui.addMessage(damage + " dmg");
                gamePanel.monster[monIndex].invincible = true;
                gamePanel.monster[monIndex].damageReaction();

                if(gamePanel.monster[monIndex].life <= 0) {
                    gamePanel.monster[monIndex].dying = true;
                    gamePanel.ui.addMessage(gamePanel.monster[monIndex].name + " slain");
                    gamePanel.ui.addMessage("+ " + gamePanel.monster[monIndex].exp + " exp");
                    exp += gamePanel.monster[monIndex].exp;
                    checkLevelUp();
                }
            }
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

            gamePanel.playEffect(8);
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue = "You are now level " + level +"\nYour resolve strengthens" ;
        }
    }

    // Selecting and updating new item in inventory.
    public void selectItem() {
        int itemIndex = gamePanel.ui.getItemIndex();
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
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

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
