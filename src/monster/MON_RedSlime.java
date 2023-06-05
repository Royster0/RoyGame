package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Rock;

import java.util.Random;

// Green slime monster
public class MON_RedSlime extends Entity {

    GamePanel gamePanel;

    // Initialize all values
    public MON_RedSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Red Slime";
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 5;
        life = maxLife;
        attack = 5;
        defense = 1;
        exp = 10;
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
        up1 = setup("monster/redslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("monster/redslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("monster/redslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("monster/redslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("monster/redslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("monster/redslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("monster/redslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("monster/redslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Sets green slime action
    @Override
    public void setAction() {

        if(onPath) {

            // if player leaves range, de-aggro
            checkDeAggro(gamePanel.player, 13, 40);

            // Search direction to go
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

            // Check if it shoots a projectile
            checkProjectileShot(50, 30);
        }
        // If not on path.
        else {
            // Check de-aggro
            checkAggro(gamePanel.player, 6, 100);
            // Get random direction.
            getRandomDire(100);
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
