package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    GamePanel gp;
    public static final String objName = "Tent";

    // Tent object, brings sleep function.
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("objects/tent", gp.tileSize, gp.tileSize);
        description = "[Tent]\nSleep through the scary night.";
        price = 300;
        stackable =  true;
    }

    // When using tent, go into sleep state and use item.
    // Restores player health and mana.
    @Override
    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;
        gp.playEffect(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
