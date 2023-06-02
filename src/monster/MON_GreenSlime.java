package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Rock;

import java.util.Random;

// Green slime monster
public class MON_GreenSlime extends Entity {

    GamePanel gamePanel;

    // Initialize all values
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Green Slime";
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 3;
        life = maxLife;
        attack = 3;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gamePanel);

        hitBox.x = 3;
        hitBox.y = 18;
        hitBox.width = 42;
        hitBox.height = 30;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImage();
    }

    // Setting up images
    public void getImage() {
        up1 = setup("monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Slime update aggressiveness
    public void update() {
        super.update();

        int xDist = Math.abs(worldX - gamePanel.player.worldX);
        int yDist = Math.abs(worldY - gamePanel.player.worldY);
        int tileDist = (xDist + yDist) / gamePanel.tileSize;

        // if player comes into distance, becomes aggro
        if(!onPath && tileDist < 5) {
            int i = new Random().nextInt(100) + 1;
            if(i > 50) {
                onPath = true;
            }
        }

        // if player leaves range, de-aggro
        if(onPath && tileDist > 10) {
            onPath = false;
        }
    }

    // Sets green slime action
    @Override
    public void setAction() {
        if(onPath) {
            int goalCol = (gamePanel.player.worldX + gamePanel.player.hitBox.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.hitBox.y) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(200) + 1;

            if(i > 197 && !projectile.alive && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);

                // check for vacancy
                for(int j = 0; j < gamePanel.projectile[1].length; j++) {
                    if(gamePanel.projectile[gamePanel.currentMap][j] == null) {
                        gamePanel.projectile[gamePanel.currentMap][j] = projectile;
                        break;
                    }
                }

                shotAvailableCounter = 0;
            }
        } else {
            actionInterval++;

            if(actionInterval == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if(i <= 25) {
                    direction = "up";
                }
                if(i > 25 && i <= 50) {
                    direction = "down";
                }
                if(i > 50 && i <= 75) {
                    direction = "left";
                }
                if(i > 75) {
                    direction = "right";
                }
                actionInterval = 0;
            }
        }
    }

    // Reaction to damage
    @Override
    public void damageReaction() {
        actionInterval = 0;
        onPath = true; // become aggro to player on damage.
    }

    // Item drops from green slime.
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        // 70% chance for item drop. 30% for coin, 20% for either mana or heal
        if(i < 30) dropItem(new OBJ_Coin(gamePanel));
        if(i >= 30 && i < 50) dropItem(new OBJ_Heart(gamePanel));
        if(i >= 50 && i <= 75) dropItem(new OBJ_Mana_Crystal(gamePanel));
    }
}
