package object;

import entity.Entity;
import main.GamePanel;

public class Projectile extends Entity {

    Entity user;
    GamePanel gamePanel;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    // Setting up a projectile to be shot
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    @Override
    public void update() {
        // CHECK IF PLAYER IS SHOOTING, DAMAGE MONSTER
        if(user == gamePanel.player) {
            int monIndex = gamePanel.collision.checkEntity(this, gamePanel.monster);
            if(monIndex != 999) {
                gamePanel.player.damageMonster(monIndex, attack);
                generateParticle(user.projectile, gamePanel.monster[gamePanel.currentMap][monIndex]);
                alive = false;
            }
        }
        // IF MONSTER SHOOTING, DAMAGE PLAYER
        else {
            boolean contactPlayer = gamePanel.collision.checkPlayer(this);
            if(!gamePanel.player.invincible && contactPlayer) {
                damagePlayer(attack);
                generateParticle(user.projectile, gamePanel.player);
                alive = false;
            }
        }

        switch(direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        life--;
        if(life <= 0) alive = false;

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            } else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean hasMana(Entity user) { return false; }

    public void subtractMana(Entity user) {}
}
