package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gamePanel;

    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Green Slime";
        type = type_monster;
        speed = 1;
        maxLife = 3;
        life = maxLife;
        attack = 5;
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

    @Override
    public void setAction() {
        actionInterval++;

        if(actionInterval == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // random from 1-100

            if(i <= 25) direction = "up";
            if(i > 25 && i <= 50) direction = "down";
            if(i > 50 && i <= 75) direction = "left";
            if(i > 75) direction = "right";

            actionInterval = 0;
        }

        int i = new Random().nextInt(100) + 1;
        if(i > 99 && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionInterval = 0;
        direction = gamePanel.player.direction;
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        // 70% chance for item drop. 30% for coin, 20% for either mana or heal
        if(i < 30) dropItem(new OBJ_Coin(gamePanel));
        if(i >= 30 && i < 50) dropItem(new OBJ_Heart(gamePanel));
        if(i >= 50 && i <= 70) dropItem(new OBJ_Mana_Crystal(gamePanel));
    }
}
