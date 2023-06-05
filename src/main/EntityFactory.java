// Receive an entity and return it. Creates unique objects instead of the same.

package main;

import entity.Entity;
import object.*;

public class EntityFactory {

    GamePanel gp;

    public EntityFactory(GamePanel gp) {
        this.gp = gp;
    }

    // Return an object given its name
    public Entity getObject(String name) {

        Entity obj = null;

        switch (name) {
            case OBJ_Axe.objName -> obj = new OBJ_Axe(gp);
            case OBJ_Boots.objName -> obj = new OBJ_Boots(gp);
            case OBJ_Chest.objName -> obj = new OBJ_Chest(gp);
            case OBJ_Coin.objName -> obj = new OBJ_Coin(gp);
            case OBJ_Door.objName -> obj = new OBJ_Door(gp);
            case OBJ_Door_Iron.objName -> obj = new OBJ_Door_Iron(gp);
            case OBJ_Fireball.objName -> obj = new OBJ_Fireball(gp);
            case OBJ_Health_Potion.objName -> obj = new OBJ_Health_Potion(gp);
            case OBJ_Heart.objName -> obj = new OBJ_Heart(gp);
            case OBJ_Key.objName -> obj = new OBJ_Key(gp);
            case OBJ_Lantern.objName -> obj = new OBJ_Lantern(gp);
            case OBJ_Mana_Crystal.objName -> obj = new OBJ_Mana_Crystal(gp);
            case OBJ_Pickaxe.objName -> obj = new OBJ_Pickaxe(gp);
            case OBJ_Rock.objName -> obj = new OBJ_Rock(gp);
            case OBJ_Shield_Upgrade.objName -> obj = new OBJ_Shield_Upgrade(gp);
            case OBJ_Shield_Wood.objName -> obj = new OBJ_Shield_Wood(gp);
            case OBJ_Sword_Normal.objName -> obj = new OBJ_Sword_Normal(gp);
            case OBJ_Tent.objName -> obj = new OBJ_Tent(gp);
        }

        return obj;
    }
}
