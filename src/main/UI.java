package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2d;
    Font litebulb_arcade, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int slotCol = 0;
    public int slotRow = 0;

    // CONSTRUCTOR
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // FONT CREATION
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/litebulb-arcade.ttf");
            litebulb_arcade = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getClassLoader().getResourceAsStream("font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_Mana_Crystal(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }

    // Add a new message the message list, will be printed on screen
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    // Draw method, executes in run()
    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        g2d.setFont(litebulb_arcade);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.white);

        // TITLE STATE
        if(gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if(gamePanel.gameState == gamePanel.playState) {
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        }
        // PAUSE STATE
        if(gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
            drawPlayerMana();
        }
        // DIALOGUE STATE
        if(gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if(gamePanel.gameState == gamePanel.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
    }

    // Draws player's healthbar
    public void drawPlayerLife() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        // DRAW BLANK HEARTS
        while(i < gamePanel.player.maxLife / 2) {
            g2d.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        i = 0;
        // DRAW FULL HEARTS
        while(i < gamePanel.player.life) {
            g2d.drawImage(heart_half, x, y, null);
            i++;
            if(i < gamePanel.player.life) {
                g2d.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }

    }

    public void drawPlayerMana() {
        int x = (gamePanel.tileSize / 2) - 5;
        int y = (int)(gamePanel.tileSize * 1.5);
        int i = 0;
        while(i < gamePanel.player.maxMana) {
            g2d.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        // DRAW CURRENT MANA
        x = (gamePanel.tileSize / 2) - 5;
        i = 0;
        while(i < gamePanel.player.mana) {
            g2d.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    // Draw message that is in the list
    public void drawMessage() {
        int messageX = (gamePanel.tileSize / 2) - 6;
        int messageY = gamePanel.tileSize * 4;
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 40F));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2d.setColor(Color.black);
                g2d.drawString(message.get(i), messageX + 2, messageY + 2);
                g2d.setColor(Color.white);
                g2d.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 30;

                if(messageCounter.get(i) > 160) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    // Draw the title screen
    public void drawTitleScreen() {
        // Title Screen State, draw Title Screen
        if(titleScreenState == 0) {
            // BACKGROUND COLOR
            g2d.setColor(new Color(56, 134, 61));
            g2d.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
            // TITLE TEXT
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 140F));
            String text = "Some Guy's Adventure";
            int x = getXCenteredText(text);
            int y = gamePanel.tileSize * 3;
            //SHADOW
            g2d.setColor(Color.black);
            g2d.drawString(text, x + 5 ,y + 5);
            // MAIN TEXT COLOR
            g2d.setColor(Color.white);
            g2d.drawString(text, x, y);
            // BLUE BOY IMAGE
            x = (gamePanel.screenWidth / 2) - ((gamePanel.tileSize * 2) / 2);
            y += gamePanel.tileSize * 2;
            g2d.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            // MENU
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 75F));
            text = "New Game";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 3.5;
            g2d.drawString(text, x, y);
            if(commandNum == 0) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Load Game";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2d.drawString(text, x, y);
            if(commandNum == 1) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Quit";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2d.drawString(text, x, y);
            if(commandNum == 2) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }
        } else if(titleScreenState == 1) {
            g2d.setColor(Color.white);
            g2d.setFont(g2d.getFont().deriveFont(75F));

            String text = "Select your class";
            int x = getXCenteredText(text);
            int y = gamePanel.tileSize * 3;
            g2d.drawString(text, x, y);

            g2d.setFont(g2d.getFont().deriveFont(60F));

            text = "Brute";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2d.drawString(text, x, y);
            if(commandNum == 0) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Ninja";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2d.drawString(text, x, y);
            if(commandNum == 1) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Mage";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2d.drawString(text, x, y);
            if(commandNum == 2) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "Cancel";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2d.drawString(text, x, y);
            if(commandNum == 3) {
                g2d.drawString(">", x - gamePanel.tileSize, y);
            }
        }
    }

    // Draw the pause screen
    public void drawPauseScreen() {
        String text = "Paused";
        int x = getXCenteredText(text) - (gamePanel.tileSize * 3);
        int y = gamePanel.screenHeight / 2 + (gamePanel.tileSize / 3);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 200F));
        g2d.drawString(text, x, y);
    }

    // Draw the dialogue screen
    public void drawDialogueScreen() {
        // DIALOGUE WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 5;
        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 51));

        for(String line : currentDialogue.split("\n")) {
            g2d.drawString(line, x, y);
            y += gamePanel.tileSize;
        }
    }

    // Draw character stats screen
    public void drawCharacterScreen() {
        // FRAME
        final int frameX = gamePanel.tileSize;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2d.setColor(Color.white);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 51f));
        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 33;

        // NAMES OF STATS
        g2d.drawString("Level", textX, textY); textY += lineHeight;
        g2d.drawString("Life", textX, textY); textY += lineHeight;
        g2d.drawString("Mana", textX, textY); textY += lineHeight;
        g2d.drawString("Strength", textX, textY); textY += lineHeight;
        g2d.drawString("Dexterity", textX, textY); textY += lineHeight;
        g2d.drawString("Attack", textX, textY); textY += lineHeight;
        g2d.drawString("Defense", textX, textY); textY += lineHeight;
        g2d.drawString("EXP", textX, textY); textY += lineHeight;
        g2d.drawString("Next Level", textX, textY); textY += lineHeight;
        g2d.drawString("Coins", textX, textY); textY += lineHeight + 18;

        g2d.drawString("Weapon", textX, textY); textY += lineHeight + 18;
        g2d.drawString("Shield", textX, textY);

        // VALUES AND IMAGES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize; // reset
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        g2d.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 13, null);
        textY += gamePanel.tileSize;
        g2d.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 14, null);
    }

    // Draw inventory
    public void drawInventory() {
        // FRAME
        int frameX = gamePanel.tileSize * 9;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        // SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;
        // DRAW ITEMS
        for(int i = 0; i < gamePanel.player.inventory.size(); i++) {
            // EQUIP CURSOR
            if(gamePanel.player.inventory.get(i) == gamePanel.player.currentWeapon ||
                    gamePanel.player.inventory.get(i) == gamePanel.player.currentShield) {
                g2d.setColor(new Color(217, 174, 92));
                g2d.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }
            g2d.drawImage(gamePanel.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            // if i reaches edge case, reset x, increment y's height to add tilesize.
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }
        //CURSOR
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWt = gamePanel.tileSize;
        int cursorHt = gamePanel.tileSize;
        // DRAW CURSOR
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(cursorX, cursorY, cursorWt, cursorHt, 10, 10);
        // ITEM DESCRIPTION FRAME
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gamePanel.tileSize * 3;
        // DRAW ITEM DESCRIPTION
        int textX = frameX + 20;
        int textY = dFrameY + 30;
        int itemIndex = getItemIndex();
        g2d.setFont(g2d.getFont().deriveFont(35F));
        if(itemIndex < gamePanel.player.inventory.size()) {
            drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
            for(String line : gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
                g2d.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndex() {
        return slotCol + (slotRow * 5); // returns item index in ArrayList
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 180); // black RGB
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255); // white RGB
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 20, 20);
    }

    public int getXCenteredText(String text) {
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return (gamePanel.screenWidth / 2) - (length / 2);
    }

    public int getXAlignRightText(String text, int tailX) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = tailX - length;
        return x;
    }
}
