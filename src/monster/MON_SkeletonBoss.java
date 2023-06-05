package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import java.util.Random;

public class MON_SkeletonBoss extends Entity {

    GamePanel gp;
    public static final String monName = "Skeleton King";

    // Constructor, sets up stats and pictures
    public MON_SkeletonBoss(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 60;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 80;
        knockbackPower = 5;

        int size = gp.tileSize * 5;
        hitBox.x = gp.tileSize;
        hitBox.y = gp.tileSize;
        hitBox.width = size - (gp.tileSize * 2);
        hitBox.height = size - gp.tileSize;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_dur = 30;
        motion2_dur = 50;

        getImage();
        getAttackImage();
    }

    // Sets up default images for each direction.
    public void getImage() {

        int i = 5;

        if(!rageMode) {
            up1 = setup("monster/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("monster/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("monster/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("monster/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("monster/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("monster/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("monster/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("monster/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        }
        else {
            up1 = setup("monster/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("monster/skeletonlord_phase2_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("monster/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("monster/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("monster/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("monster/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("monster/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("monster/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }
    }

    // Sets up attack images for each direction
    public void getAttackImage() {

        int i = 5;

        if(!rageMode) {
            attackUp1 = setup("monster/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("monster/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("monster/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("monster/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("monster/skeletonlord_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonlord_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("monster/skeletonlord_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonlord_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        }
        else {
            attackUp1 = setup("monster/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("monster/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("monster/skeletonlord_phase2_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("monster/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("monster/skeletonlord_phase2_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonlord_phase2_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("monster/skeletonlord_phase2_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonlord_phase2_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        }
    }

    // Move skeleton king towards player if within range.
    // If health goes below half, enter rage mode.
    @Override
    public void setAction() {

        if(!rageMode && life < maxLife / 2) {
            rageMode = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack = 15;
        }

        if(getTileDist(gp.player) < 10) {
            moveToPlayer(60);
        }
        else {
            getRandomDire(100);
        }

        // Check if it attacks
        if(!attacking) {
            checkAttacking(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }

    @Override
    public void damageReaction() {
        actionInterval = 0;
    }
}
