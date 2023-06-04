// Stores user data

package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // Player stats
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // Player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;
    String[][] mapObjectNames;
    String[][] mapObjectLootNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    boolean[][] objectOpened;
}
