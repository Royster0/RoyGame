package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    // Return an object given its name
    public Entity getObject(String name) {
        return switch (name) {
            case "Woodcutter's Axe" -> new OBJ_Axe(gp);
            case "Boots" -> new OBJ_Boots(gp);
            case "Health Potion" -> new OBJ_Health_Potion(gp);
            case "Key" -> new OBJ_Key(gp);
            case "Lantern" -> new OBJ_Lantern(gp);
            case "Blue Shield" -> new OBJ_Shield_Upgrade(gp);
            case "Wood Shield" -> new OBJ_Shield_Wood(gp);
            case "Basic Sword" -> new OBJ_Sword_Normal(gp);
            case "Tent" -> new OBJ_Tent(gp);
            case "Door" -> new OBJ_Door(gp);
            case "Chest" -> new OBJ_Chest(gp);
            default -> null;
        };
    }

    // Saves the game
    public void save() {
        try {
            // Create output to file
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("save.dat"));
            DataStorage data = new DataStorage();

            // Write player stats
            data.level = gp.player.level;
            data.maxLife = gp.player.maxLife;
            data.life = gp.player.life;
            data.maxMana = gp.player.maxMana;
            data.mana = gp.player.mana;
            data.strength = gp.player.strength;
            data.dexterity = gp.player.dexterity;
            data.exp = gp.player.exp;
            data.nextLevelExp = gp.player.nextLevelExp;
            data.coin = gp.player.coin;

            // Write player inventory
            for(int i = 0; i < gp.player.inventory.size(); i++) {
                data.itemNames.add(gp.player.inventory.get(i).name);
                data.itemAmounts.add(gp.player.inventory.get(i).stackAmount);
            }

            // Player equipment
            data.currentWeaponSlot = gp.player.getCurrWeaponSlot();
            data.currentShieldSlot = gp.player.getCurrShieldSlot();

            // Objects on map
            data.mapObjectNames = new String[gp.maxMap][gp.objects[1].length];
            data.mapObjectWorldX = new int[gp.maxMap][gp.objects[1].length];
            data.mapObjectWorldY = new int[gp.maxMap][gp.objects[1].length];
            data.mapObjectLootNames = new String[gp.maxMap][gp.objects[1].length];
            data.objectOpened = new boolean[gp.maxMap][gp.objects[1].length];

            for(int i = 0; i < gp.maxMap; i++) {

                for(int j = 0; j < gp.objects[1].length; j++) {

                    if(gp.objects[i][j] == null) {
                        data.mapObjectNames[i][j] = "no";
                    }
                    else {
                        data.mapObjectNames[i][j] = gp.objects[i][j].name;
                        data.mapObjectWorldX[i][j] = gp.objects[i][j].worldX;
                        data.mapObjectWorldY[i][j] = gp.objects[i][j].worldY;
                        if(gp.objects[i][j].loot != null) {
                            data.mapObjectLootNames[i][j] = gp.objects[i][j].loot.name;
                        }
                        data.objectOpened[i][j] = gp.objects[i][j].opened;
                    }
                }
            }

            // Write to DataStorage
            os.writeObject(data);

        } catch (IOException e) {
            System.out.println("Save failed!");
        }
    }

    public void load() {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream("save.dat"));

            // Read player stats
            DataStorage data = (DataStorage)os.readObject();
            gp.player.level = data.level;
            gp.player.maxLife = data.maxLife;
            gp.player.life = data.life;
            gp.player.maxMana = data.maxMana;
            gp.player.mana = data.mana;
            gp.player.strength = data.strength;
            gp.player.dexterity = data.dexterity;
            gp.player.exp = data.exp;
            gp.player.nextLevelExp = data.nextLevelExp;
            gp.player.coin = data.coin;

            // Read player inventory
            gp.player.inventory.clear();
            for(int i = 0; i < data.itemNames.size(); i++) {
                gp.player.inventory.add(getObject(data.itemNames.get(i)));
                gp.player.inventory.get(i).stackAmount = data.itemAmounts.get(i);
            }

            // Read player equipment
            gp.player.currentWeapon = gp.player.inventory.get(data.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(data.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();

            // Objects on map
            for(int i = 0; i < gp.maxMap; i++) {

                for(int j = 0; j < gp.objects[1].length; j++) {

                    if(data.mapObjectNames[i][j].equals("no")) {
                        gp.objects[i][j] = null;
                    }
                    else {
                        gp.objects[i][j] = getObject(data.mapObjectNames[i][j]);
                        gp.objects[i][j].worldX = data.mapObjectWorldX[i][j];
                        gp.objects[i][j].worldY = data.mapObjectWorldY[i][j];
                        if(data.mapObjectLootNames[i][j] != null) {
                            gp.objects[i][j].loot = getObject(data.mapObjectLootNames[i][j]);
                        }
                        gp.objects[i][j].opened = data.objectOpened[i][j];
                        if(gp.objects[i][j].opened) {
                            gp.objects[i][j].down1 = gp.objects[i][j].image2;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Load failed!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
