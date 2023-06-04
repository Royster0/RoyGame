package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Crystal extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Mana Crystal";

    public OBJ_Mana_Crystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickup;
        value = 1;
        name = objName;
        down1 = setup("objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playEffect(2);
        gamePanel.ui.addMessage("Mana + " + value);
        entity.mana += value;
        return true;
    }
}
