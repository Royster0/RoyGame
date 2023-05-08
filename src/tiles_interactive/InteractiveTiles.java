package tiles_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTiles extends Entity {

    GamePanel gamePanel;
    public boolean destructable = false;

    // Constructor
    public InteractiveTiles(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    // Returns true of player's current weapon is an axe (cuttable)
    public boolean correctItem(Entity entity) {
        return false;
    }

    public void playEffect() {}

    public InteractiveTiles getDestroyedForm() {
        return null;
    }

    @Override
    public void update() {
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
}
