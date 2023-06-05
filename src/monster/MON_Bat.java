package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Rock;

import java.util.Random;

// Green slime monster
public class MON_Bat extends Entity {

    GamePanel gamePanel;

    // Initialize all values
    public MON_Bat(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Bat";
        type = type_monster;
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 5;
        life = maxLife;
        attack = 3;
        defense = 1;
        exp = 10;

        hitBox.x = 3;
        hitBox.y = 15;
        hitBox.width = 42;
        hitBox.height = 21;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImage();
    }

    // Setting up images
    public void getImage() {
        up1 = setup("monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    // Sets green slime action
    @Override
    public void setAction() {
        getRandomDire(10);
    }

    // Reaction to damage
    @Override
    public void damageReaction() {
        actionInterval = 0;
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
