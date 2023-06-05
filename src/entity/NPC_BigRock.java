package entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tiles_interactive.IT_MetalPlate;
import tiles_interactive.InteractiveTiles;

import java.awt.*;
import java.util.ArrayList;

public class NPC_BigRock extends Entity{

    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        direction = "down";
        speed = 4;

        hitBox = new Rectangle();
        hitBox.x = 2;
        hitBox.y = 6;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 44;
        hitBox.height = 40;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "It's just... a really big rock!";
    }

    @Override
    public void update() {}

    @Override
    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if(dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }
    }

    // Moving the rock with the player's push
    @Override
    public void move(String dir) {

        this.direction = dir;
        checkCollision();

        if(!collision) {
            switch(direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        detectPlate();
    }

    // See if rock is on top of a plate
    public void detectPlate() {
        ArrayList<InteractiveTiles> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // Add all the plates in iTiles to plate list
        for(int i = 0; i < gamePanel.iTile[1].length; i++) {

            if(gamePanel.iTile[gamePanel.currentMap][i] != null &&
                    gamePanel.iTile[gamePanel.currentMap][i].name != null &&
                    gamePanel.iTile[gamePanel.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                plateList.add(gamePanel.iTile[gamePanel.currentMap][i]);
            }
        }

        // Adds all the rocks in entity list to rock list
        for(int i = 0; i < gamePanel.npc[1].length; i++) {

            if(gamePanel.npc[gamePanel.currentMap][i] != null &&
                    gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_BigRock.npcName)) {
                rockList.add(gamePanel.npc[gamePanel.currentMap][i]);
            }
        }

        int count = 0;

        // Scan plate list
        for (InteractiveTiles interactiveTiles : plateList) {
            int xDist = Math.abs(worldX - interactiveTiles.worldX);
            int yDist = Math.abs(worldY - interactiveTiles.worldY);
            int dist = Math.max(xDist, yDist);

            if (dist < 10) {
                if (linkedEntity == null) {
                    linkedEntity = interactiveTiles;
                    gamePanel.playEffect(3);
                }
            }
            // Only if plate is linked to rock, set the plate to null.
            else {
                if (linkedEntity == interactiveTiles) linkedEntity = null;
            }
        }

        // Scan rock list
        for (Entity entity : rockList) {
            if (entity.linkedEntity != null) {
                count++;
            }
        }

        // If all rocks are on a plate
        if(count == rockList.size()) {
            for(int i = 0; i < gamePanel.objects[1].length; i++) {

                if(gamePanel.objects[gamePanel.currentMap][i] != null &&
                        gamePanel.objects[gamePanel.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                    gamePanel.objects[gamePanel.currentMap][i] = null;
                    gamePanel.playEffect(21);
                }
            }
        }
    }
}
