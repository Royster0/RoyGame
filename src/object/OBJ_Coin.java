package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {

    GamePanel gamePanel;
    int coinValue;

    public OBJ_Coin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickup;
        name = "Coin";
        value = 1;
        down1 = setup("objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void use(Entity entity) {
        gamePanel.playEffect(1);
        gamePanel.ui.addMessage("Coin + " + value);
        gamePanel.player.coin += value;
    }
}
