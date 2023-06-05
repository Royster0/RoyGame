package tiles_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Tent;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTiles{

    GamePanel gamePanel;
    public static final String itName = "Destructible Wall";

    public IT_DestructibleWall(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        name = itName;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;
        life = 2;

        down1 = setup("tiles_interactive/destructiblewall", gamePanel.tileSize, gamePanel.tileSize);
        destructable = true;
    }

    @Override
    public boolean correctItem(Entity entity) {
        return entity.currentWeapon.type == type_pickaxe;
    }

    @Override
    public void playEffect() {
        gamePanel.playEffect(20);
    }

    @Override
    public Color getParticleColor() { return new Color(110, 110, 110); }

    @Override
    public int getParticleSize() { return 6; } // 6px

    @Override
    public int getParticleSpeed() { return 1; } // 1 px/frame

    @Override
    public int getParticleMaxLife() { return 20; } // lasts for 20 frames

    // Item drops from green slime.
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        // 70% chance for item drop. 30% for coin, 20% for either mana or heal
        if(i < 15) dropItem(new OBJ_Coin(gamePanel));
        if(i >= 15 && i < 25) dropItem(new OBJ_Heart(gamePanel));
        if(i >= 25 && i <= 30) dropItem(new OBJ_Tent(gamePanel));
    }
}