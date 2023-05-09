package tiles_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends InteractiveTiles{

    GamePanel gamePanel;

    public IT_DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;
        life = 1;

        down1 = setup("tiles_interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructable = true;
    }

    @Override
    public boolean correctItem(Entity entity) {
        return entity.currentWeapon.type == type_axe;
    }

    @Override
    public void playEffect() {
        gamePanel.playEffect(11);
    }

    @Override
    public InteractiveTiles getDestroyedForm() {
        return new IT_Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
    }

    @Override
    public Color getParticleColor() { return new Color(65, 50, 30); }

    @Override
    public int getParticleSize() { return 6; } // 6px

    @Override
    public int getParticleSpeed() { return 1; } // 1 px/frame

    @Override
    public int getParticleMaxLife() { return 20; } // lasts for 20 frames
}
