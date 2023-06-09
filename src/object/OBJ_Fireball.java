package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {

    GamePanel gamePanel;
    public static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        manaCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("projectile/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("projectile/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("projectile/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("projectile/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("projectile/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("projectile/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("projectile/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("projectile/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public boolean hasMana(Entity user) {
        return user.mana >= manaCost;
    }

    @Override
    public void subtractMana(Entity user) {
        user.mana -= manaCost;
    }

    @Override
    public Color getParticleColor() { return new Color(213, 41, 18); }

    @Override
    public int getParticleSize() { return 7; } // 6px

    @Override
    public int getParticleSpeed() { return 1; } // 1 px/frame

    @Override
    public int getParticleMaxLife() { return 20; } // lasts for 20 frames
}
