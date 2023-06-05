package entity;

import main.GamePanel;
import main.UtilityTool;
import object.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// Entity class that parents all the entities in the game.
public class Entity {

    GamePanel gamePanel;

    // IMAGES
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
                         attackLeft1, attackLeft2, attackRight1, attackRight2,
                         guardUp, guardDown, guardLeft, guardRight;

    // HITBOX
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefaultX, hitBoxDefaultY;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean collision = false;
    public String[][] dialogues = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public boolean opened = false;
    public boolean rageMode = false;
    public String knockbackDir;
    public Entity loot;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionInterval = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockbackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;

    // CHARACTER STATUS
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int maxMana;
    public int mana;
    public int strength;
    public int attack;
    public int defense;
    public int dexterity;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_dur;
    public int motion2_dur;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;
    public boolean boss;


    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int manaCost;
    public int price;
    public int knockbackPower = 0;
    public boolean stackable = false;
    public int stackAmount = 1;
    public int lightRadius;

    // TYPE OF ENTITY
    public int type;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickup = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    // Entity constructor that initializes a GamePanel instance.
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Returns screen position of player on x-axis
    public int getScreenX() {
        return worldX - gamePanel.player.worldX + gamePanel.player.cameraX;
    }

    // Returns screen position of player on y-axis
    public int getScreenY() {
        return worldY - gamePanel.player.worldY + gamePanel.player.cameraY;
    }

    // Getter methods for position of Entity
    public int getLeftX() { return worldX + hitBox.x; }

    public int getRightX() { return worldX + hitBox.width; }

    public int getTopY() { return worldY + hitBox.y; }

    public int getBottomY() { return worldY + hitBox.height; }

    // Getter methods for column and row of entity.
    public int getCol() { return (worldX + hitBox.x) / gamePanel.tileSize; }

    public int getRow() { return (worldY + hitBox.y) / gamePanel.tileSize; }

    public int getCenterX() {
        return worldX + left1.getWidth() / 2;
    }

    public int getCenterY() {
        return worldY + up1.getHeight() / 2;
    }

    // Getter method for xDist
    public int getXDist(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }

    // Getter method for yDist
    public int getYDist(Entity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }

    // Getter method for tile distance
    public int getTileDist(Entity target) {
        return (getXDist(target) + getYDist(target)) / gamePanel.tileSize;
    }

    // Getter method for the goal column (x).
    public int getGoalCol(Entity target) {
        return (target.worldX + target.hitBox.x) / gamePanel.tileSize;
    }

    // Getter method for the goal row (x).
    public int getGoalRow(Entity target) {
        return (target.worldY + target.hitBox.y) / gamePanel.tileSize;
    }

    public void setLoot(Entity loot) {}

    // Sets actions for other entities to override.
    public void setAction() {}

    // Used to manipulate movement based on player direction.
    public void move(String direction) {}

    // Resetting the counters on death
    public void resetCounters() {
        spriteCounter = 0;
        actionInterval = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockbackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }

    // Damage reaction method for other entities to react to damage.
    public void damageReaction() {}

    // Method for other entities to have dialogue.
    public void speak() {}

    public void startDialogue(Entity entity, int setNum) {
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.npc = entity;
        dialogueSet = setNum;
    }

    // Make entity face player when speaking
    public void facePlayer() {
        switch (gamePanel.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    // Interact with obstacles/objects
    public void interact() {}

    // Method to allow other entities to use an object.
    public boolean use(Entity entity) { return false; }

    // Check drop method to allow other entities to drop items.
    public void checkDrop() {}

    // Drop an item on the map where they die.
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gamePanel.objects[1].length; i++) {
            if(gamePanel.objects[gamePanel.currentMap][i] == null) {
                gamePanel.objects[gamePanel.currentMap][i] = droppedItem;
                gamePanel.objects[gamePanel.currentMap][i].worldX = worldX; // drops item at dead monster's body
                gamePanel.objects[gamePanel.currentMap][i].worldY = worldY;
                break; // break on first occurrence of empty slot
            }
        }
    }

    // Checking collision with other NPCs, monsters, tiles and player.
    public void checkCollision() {
        collisionOn = false;
        gamePanel.collision.checkTile(this);
        gamePanel.collision.checkObject(this, false);
        gamePanel.collision.checkEntity(this, gamePanel.npc);
        gamePanel.collision.checkEntity(this, gamePanel.monster);
        gamePanel.collision.checkEntity(this, gamePanel.iTile);
        boolean contactedPlayer = gamePanel.collision.checkPlayer(this);

        // If monster, damage player
        if(this.type == type_monster && contactedPlayer) {
            damagePlayer(attack);
        }
    }

    // Entity update method that checks collision and redirects if colliding.
    public void update() {

        if(knockback) {
            checkCollision();

            if(collisionOn) {
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
            else {
                switch(knockbackDir) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            knockbackCounter++;
            if(knockbackCounter == 5) {
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }

        }
        else if(attacking) {
            attacking();
        }
        else {
            setAction();
            checkCollision();

            // If no collision, move
            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Animation
            spriteCounter++;
            if(spriteCounter > 20) {
                if(spriteNum == 1) spriteNum = 2;
                else if(spriteNum == 2) spriteNum = 1;

                spriteCounter = 0;
            }
        }

        // Damage taken invincibility
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 25) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30) shotAvailableCounter++;

        if(offBalance) {
            offBalanceCounter++;
            if(offBalanceCounter > 60) {
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }

    // Checking if attack is within range
    public void checkAttacking(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDist = getXDist(gamePanel.player);
        int yDist = getYDist(gamePanel.player);

        // Check if player is < (or >) than entity's world position.
        // Check if distance from entity/player is greater than given distance.
        switch (direction) {
            case "up" -> {
                if (gamePanel.player.getCenterY() < getCenterY() && yDist < straight && xDist < horizontal) {
                    targetInRange = true;
                }
            }
            case "down" -> {
                if (gamePanel.player.getCenterY() > getCenterY() && yDist < straight && xDist < horizontal) {
                    targetInRange = true;
                }
            }
            case "left" -> {
                if (gamePanel.player.getCenterX() < getCenterX() && xDist < straight && yDist < horizontal) {
                    targetInRange = true;
                }
            }
            case "right" -> {
                if (gamePanel.player.getCenterX() > getCenterX() && xDist < straight && yDist < horizontal) {
                    targetInRange = true;
                }
            }
        }

        // If in range, change behavior.
        if(targetInRange) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter++;
                shotAvailableCounter = 0;
            }
        }
    }

    // Check if projectile is shot and in path
    public void checkProjectileShot(int rate, int shotInterval) {
        // Check if projectile is shot
        int i = new Random().nextInt(rate);
        if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);

            // check for vacancy
            for (int j = 0; j < gamePanel.projectile[1].length; j++) {
                if (gamePanel.projectile[gamePanel.currentMap][j] == null) {
                    gamePanel.projectile[gamePanel.currentMap][j] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
        }
    }

    // Checking aggro against player
    public void checkAggro(Entity target, int dist, int rate) {
        if(getTileDist(target) < dist) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = true;
            }
        }
    }

    // Checking deaggro against player
    public void checkDeAggro(Entity target, int dist, int rate) {
        if(getTileDist(target) > dist) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = false;
            }
        }
    }

    // Sets random direction using action interval.
    public void getRandomDire(int interval) {
        // Get a random direction
        actionInterval++;
        if (actionInterval > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) direction = "up";
            if (i > 25 && i <= 50) direction = "down";
            if (i > 50 && i <= 75) direction = "left";
            if (i > 75) direction = "right";
            actionInterval = 0;
        }
    }

    // Move towards player
    public void moveToPlayer(int interval) {

        actionInterval++;

        // see which distance (x or y) is greater, move other way
        if(actionInterval > interval) {

            if(getXDist(gamePanel.player) > getYDist(gamePanel.player)) {
                if(gamePanel.player.getCenterX() < getCenterX()) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            }

            else if(getXDist(gamePanel.player) < getYDist(gamePanel.player)) {
                if(gamePanel.player.getCenterY() < getCenterY()) {
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionInterval = 0;
        }
    }

    // Returns opposite direction of direction.
    public String getOppDir(String direction) {
        String opposite = "";
        switch(direction) {
            case "up" -> opposite = "down";
            case "down" -> opposite = "up";
            case "left" -> opposite = "right";
            case "right" -> opposite = "left";
        }
        return opposite;
    }

    // Player attacking animation and extending hitbox to sword
    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= motion1_dur) {
            spriteNum = 1;
        }
        if(spriteCounter > motion1_dur && spriteCounter <= motion2_dur) {
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

            // Monster attacking player
            if(type == type_monster) {
                if(gamePanel.collision.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            }
            // Player attacking others
            else {
                // Check monster collision with new worldX and worldY
                int monsterIndex = gamePanel.collision.checkEntity(this, gamePanel.monster);
                gamePanel.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockbackPower);

                // Check iTile collision
                int iTileIndex = gamePanel.collision.checkEntity(this, gamePanel.iTile);
                gamePanel.player.damageITile(iTileIndex);

                // Check projectile collision
                int projectileIndex = gamePanel.collision.checkEntity(this, gamePanel.projectile);
                gamePanel.player.damageProjectile(projectileIndex);
            }

            // After checking collision, restore the original data.
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitBox.width = hitboxAreaWidth;
            hitBox.height = hitboxAreaHeight;
        }
        if(spriteCounter > motion2_dur) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    // Damage to player calculation
    public void damagePlayer(int attack) {
        if(!gamePanel.player.invincible) {
            // Check damage
            int damage = attack - gamePanel.player.defense;
            String canGuardDir = getOppDir(direction);

            // If player is guarding in the right direction, negate damage.
            if(gamePanel.player.guarding && gamePanel.player.direction.equals(canGuardDir)) {

                // 8 frame window to Parry and knock monster off balance
                if(gamePanel.player.guardCounter < 10) {
                    damage = 0;
                    gamePanel.playEffect(16);
                    knockBack(this, gamePanel.player, knockbackPower);
                    offBalance = true;
                    // show "off-balance" sprite
                    spriteCounter -= 80;
                }
                // normal block
                else {
                    damage /= 3;
                    gamePanel.playEffect(15);
                }
            }
            // if not guarding
            else {
                gamePanel.playEffect(6);
                if(damage < 1) damage = 1;
            }

            if(damage != 0) {
                gamePanel.player.transparent = true;
                knockBack(gamePanel.player, this, knockbackPower);
            }
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;
        }
    }

    // Applies knockback to entities
    public void knockBack(Entity target, Entity attacker, int knockbackPower) {
        this.attacker = attacker;
        target.knockbackDir = attacker.direction;
        target.speed += knockbackPower;
        target.knockback = true;
    }

    // Retrieves a particle color. Mainly used in interactive Tiles.
    public Color getParticleColor() { return null; }

    // Returns size of particle in pixels
    public int getParticleSize() { return 0; }

    // Speed of animation of particle
    public int getParticleSpeed() { return 0; }

    // Returns the max life-span of a particle.
    public int getParticleMaxLife() { return 0; }

    // Creates a new particle to display on the screen.
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gamePanel, target, color, size, speed, maxLife, -1, -1);
        Particle p2 = new Particle(gamePanel, target, color, size, speed, maxLife, 1, -1);
        Particle p3 = new Particle(gamePanel, target, color, size, speed, maxLife, -1, 1);
        Particle p4 = new Particle(gamePanel, target, color, size, speed, maxLife, 1, 1);
        gamePanel.particleList.add(p1);
        gamePanel.particleList.add(p2);
        gamePanel.particleList.add(p3);
        gamePanel.particleList.add(p4);
    }

    public boolean inCamera() {
        return worldX + gamePanel.tileSize * 5 > gamePanel.player.worldX - gamePanel.player.cameraX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.cameraX &&
                worldY + gamePanel.tileSize * 5 > gamePanel.player.worldY - gamePanel.player.cameraY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.cameraY;
    }

    // Drawing the entity relative to player and world.
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        if(inCamera()) {

            int tempCameraX = getScreenX();
            int tempCameraY = getScreenY();

            // Handle drawing images of sprites
            switch(direction) {
                case "up" -> {
                    if(!attacking) {
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                    }
                    if(attacking) {
                        tempCameraY = getScreenY() - up1.getHeight();
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
                        tempCameraX = getScreenX() - left1.getWidth();
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

            if(invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(graphics2D, 0.5f);
            }
            if(dying) {
                dyingAnimation(graphics2D);
            }
            graphics2D.drawImage(image, tempCameraX, tempCameraY,null);
            changeAlpha(graphics2D, 1f);
        }
    }

    // Dying animation for the entity.
    public void dyingAnimation(Graphics2D graphics2D) {
        dyingCounter++;
        int i = 5; // interval

        if(dyingCounter <= i) changeAlpha(graphics2D, 0f);
        if(dyingCounter > i && dyingCounter <= i * 2) changeAlpha(graphics2D, 1f);
        if(dyingCounter > i * 2 && dyingCounter <= i * 3) changeAlpha(graphics2D, 0f);
        if(dyingCounter > i * 3 && dyingCounter <= i * 4) changeAlpha(graphics2D, 1f);
        if(dyingCounter > i * 4 && dyingCounter <= i * 5) changeAlpha(graphics2D, 0f);
        if(dyingCounter > i * 5 && dyingCounter <= i * 6) changeAlpha(graphics2D, 1f);
        if(dyingCounter > i * 6) {
            alive = false;
        }
    }

    // Changes the transparency of an entity.
    public void changeAlpha(Graphics2D graphics2D, float alpha) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    // Allows for automation of importing and scaling images.
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Path finding for an entity using the A* algorithm.
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + hitBox.x) / gamePanel.tileSize;
        int startRow = (worldY + hitBox.y) / gamePanel.tileSize;

        gamePanel.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gamePanel.pFinder.search()) {
            // Next world X and world Y
            int nextX = gamePanel.pFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pFinder.pathList.get(0).row * gamePanel.tileSize;

            // Entity's hitbox position
            int entLeftX = worldX + hitBox.x;
            int entRightX = worldX + hitBox.x + hitBox.width;
            int entTopY = worldY + hitBox.y;
            int entBottomY = worldY + hitBox.y + hitBox.height;

            if(entTopY > nextY && entLeftX >= nextX && entRightX < nextX + gamePanel.tileSize) {
                direction = "up";

            } else if(entTopY < nextY && entLeftX >= nextX && entRightX < nextX + gamePanel.tileSize) {
                direction = "down";

            } else if(entTopY >= nextY && entBottomY < nextY + gamePanel.tileSize) {
                if(entLeftX > nextX) direction = "left";
                if(entLeftX < nextX) direction = "right";

            } else if(entTopY > nextY && entLeftX > nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "left";

            } else if(entTopY > nextY && entLeftX < nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "right";

            } else if(entTopY < nextY && entLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "left";

            } else if(entTopY < nextY && entLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "right";
            }

            int nextCol = gamePanel.pFinder.pathList.get(0).col;
            int nextRow = gamePanel.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) onPath = false;
        }
    }

    // Receives the index of the target name if it is adjacent to the player.
    public int getDetected(Entity user, Entity[][] target, String targetName) {
        int index = 999;

        // Check surrounding objects.
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        // If object is adjacent to user it is detected.
        switch(user.direction) {
            case "up" -> nextWorldY = user.getTopY() - gamePanel.player.speed;
            case "down" -> nextWorldY = user.getBottomY() + gamePanel.player.speed;
            case "left" -> nextWorldX = user.getLeftX() - gamePanel.player.speed;
            case "right" -> nextWorldX = user.getRightX() + gamePanel.player.speed;
        }

        int col = nextWorldX / gamePanel.tileSize;
        int row = nextWorldY / gamePanel.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            Entity temp = target[gamePanel.currentMap][i];
            if(temp != null) {
                if(temp.getCol() == col && temp.getRow() == row && temp.name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}
