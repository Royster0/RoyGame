package entity;

import main.GamePanel;
import main.UtilityTool;
import object.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

// Entity class that parents all the entities in the game.
public class Entity {

    GamePanel gamePanel;

    // IMAGES
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
                         attackLeft1, attackLeft2, attackRight1, attackRight2;

    // HITBOX
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefaultX, hitBoxDefaultY;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean collision = false;

    String[] dialogues = new String[20];

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockback = false;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionInterval = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockbackCounter = 0;

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
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

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

    // TYPE OF ENTITY
    public int type;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickup = 7;

    // Entity constructor that initializes a GamePanel instance.
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Sets actions for other entities to override.
    public void setAction() {}

    // Damage reaction method for other entities to react to damage.
    public void damageReaction() {}

    // Method for other entities to have dialogue.
    public void speak() {
        // reset dialogue if there is no dialogue left
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // Make entity face player when speaking
        switch (gamePanel.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    // Method to allow other entities to use an object.
    public void use(Entity entity) {}

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
            else if(!collisionOn) {
                switch(gamePanel.player.direction) {
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

        } else {
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
        }

        // Animation
        spriteCounter++;
        if(spriteCounter > 20) {
            if(spriteNum == 1) spriteNum = 2;
            else if(spriteNum == 2) spriteNum = 1;

            spriteCounter = 0;
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
    }

    // Damage to player calculation
    public void damagePlayer(int attack) {
        if(!gamePanel.player.invincible) {
            gamePanel.playEffect(6);
            int damage = attack - gamePanel.player.defense;
            if(damage < 0) damage = 0;
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;
        }
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

    // Drawing the entity relative to player and world.
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        int cameraX = worldX - gamePanel.player.worldX + gamePanel.player.cameraX;
        int cameraY = worldY - gamePanel.player.worldY + gamePanel.player.cameraY;

        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.cameraX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.cameraX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.cameraY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.cameraY) {

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                case "down" -> {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                case "left" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                case "right" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }

            // Monster Health Bar
            if(type == 2 && hpBarOn) {
                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                if(hpBarValue <= 0) hpBarValue = 0;
                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(cameraX - 1, cameraY - 6, gamePanel.tileSize + 2, 12);

                graphics2D.setColor(new Color(215, 9, 16));
                graphics2D.fillRect(cameraX, cameraY - 5, (int)hpBarValue, 10);

                // Turn off HP bar after 600 frames
                hpBarCounter++;
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
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
            graphics2D.drawImage(image, cameraX, cameraY,null);
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
}
