// Orc monster

package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import java.util.Random;

public class MON_Orc extends Entity {

    GamePanel gp;

    // Constructor, sets up stats and pictures
    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 7;
        defense = 2;
        exp = 10;

        hitBox.x = 4;
        hitBox.y = 4;
        hitBox.width = 40;
        hitBox.height = 44;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        attackArea.width = 48;
        attackArea.height = 48;

        getImage();
        getAttackImage();
    }

    // Sets up default images for each direction.
    public void getImage() {
        up1 = setup("monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/orc_right_2", gp.tileSize, gp.tileSize);
    }

    // Sets up attack images for each direction
    public void getAttackImage() {
        up1 = setup("monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        up2 = setup("monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        down1 = setup("monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        down2 = setup("monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        left1 = setup("monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        left2 = setup("monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        right1 = setup("monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        right2 = setup("monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    // Sets orc actions
    @Override
    public void setAction() {
        if(onPath) {
            checkDeAggro(gp.player, 15, 100);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {
            checkAggro(gp.player, 5, 100);
            getRandomDire();
        }
    }

    @Override
    public void damageReaction() {
        actionInterval = 0;
        onPath = true;
    }

    // Item drops from green slime.
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        // 70% chance for item drop. 30% for coin, 20% for either mana or heal
        if(i < 30) dropItem(new OBJ_Coin(gp));
        if(i >= 30 && i < 50) dropItem(new OBJ_Heart(gp));
        if(i >= 50 && i <= 75) dropItem(new OBJ_Mana_Crystal(gp));
    }
}
