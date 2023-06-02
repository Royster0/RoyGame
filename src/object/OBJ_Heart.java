package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Heart";
        type = type_pickup;
        value = 2;
        down1 = setup("objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playEffect(2);
        gamePanel.ui.addMessage("Health + " + value);
        entity.life += value;
        return false;
    }
}
