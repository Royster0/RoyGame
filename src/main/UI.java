package main;

import entity.Entity;
import object.OBJ_Coin;
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
    public Font litebulb_arcade, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int substate = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    // CONSTRUCTOR
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // FONT CREATION
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/litebulb-arcade.ttf");
            assert is != null;
            litebulb_arcade = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getClassLoader().getResourceAsStream("font/Purisa Bold.ttf");
            assert is != null;
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
        Entity bronzeCoin = new OBJ_Coin(gamePanel);
        coin = bronzeCoin.down1;
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
            drawInventory(gamePanel.player, true);
        }
        // OPTIONS MENU
        if(gamePanel.gameState == gamePanel.optionState) {
            drawOptionsMenu();
        }
        // GAME OVER STATE
        if(gamePanel.gameState == gamePanel.gameOverState) {
            drawGameOver();
        }
        // TRANSITION
        if(gamePanel.gameState == gamePanel.transitionState) {
            drawTransition();
        }
        // TRADING STATE
        if(gamePanel.gameState == gamePanel.tradeState) {
            drawTradeScreen();
        }
        // SLEEP STATE
        if(gamePanel.gameState == gamePanel.sleepState) {
            drawSleepScreen();
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
        int x = gamePanel.tileSize * 3;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 6);
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 51));

        // If text not null, display
        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            // currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if(charIndex < characters.length) {

                gamePanel.playEffect(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText += s;

                currentDialogue = combinedText;

                charIndex++;
            }

            // If enter pressed, then dialogue index increases.
            if(gamePanel.keyHandler.enterPressed) {

                charIndex = 0;
                combinedText = "";

                if(gamePanel.gameState == gamePanel.dialogueState) {
                    npc.dialogueIndex++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            }
        }
        // If it is null, conversation is over.
        else {
            npc.dialogueIndex = 0;

            if(gamePanel.gameState == gamePanel.dialogueState) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        assert currentDialogue != null;
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

        value = gamePanel.player.life + "/" + gamePanel.player.maxLife;
        textX = getXAlignRightText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.player.mana + "/" + gamePanel.player.maxMana;
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
    public void drawInventory(Entity entity, boolean cursor) {

        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if(entity == gamePanel.player) {
            frameX = gamePanel.tileSize * 9;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        // SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;
        // DRAW ITEMS
        for(int i = 0; i < entity.inventory.size(); i++) {
            // EQUIP CURSOR
            Entity current = entity.inventory.get(i);
            if(current == entity.currentWeapon || current == entity.currentShield || current == entity.currentLight) {
                g2d.setColor(new Color(217, 174, 92));
                g2d.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }
            g2d.drawImage(current.down1, slotX, slotY, null);

            // Display amount
            if(current.stackAmount > 1 && entity == gamePanel.player) {
                g2d.setFont(g2d.getFont().deriveFont(40f));
                String s = String.valueOf(entity.inventory.get(i).stackAmount);
                int amountX = getXAlignRightText(s, slotX + 44);
                int amountY = slotY + gamePanel.tileSize;

                // Number shadow
                g2d.setColor(new Color(60, 70, 60));
                g2d.drawString(s, amountX, amountY);
                // Number
                g2d.setColor(Color.white);
                g2d.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            // if i reaches edge case, reset x, increment y's height to add tilesize.
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }
        //CURSOR
        if(cursor) {
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
            int itemIndex = getItemIndex(slotCol, slotRow);
            g2d.setFont(g2d.getFont().deriveFont(35F));
            if(itemIndex < entity.inventory.size()) {
                drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
                for(String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2d.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    // Draws the game over screen upon death.
    public void drawGameOver() {
        // Darken screen
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Shadow
        String text = "Wasted";
        int x = getXCenteredText(text) - gamePanel.tileSize * 3;
        int y = gamePanel.tileSize * 4;
        g2d.setFont(g2d.getFont().deriveFont(180f));
        g2d.setColor(Color.black);
        g2d.drawString(text, x, y);
        // Main text
        g2d.setColor(Color.white);
        g2d.drawString(text, x - 4, y - 4);

        // Load Last Save
        g2d.setFont(g2d.getFont().deriveFont(90f));
        text = "Retry";
        x += gamePanel.tileSize * 2;
        y += gamePanel.tileSize * 4;
        g2d.drawString(text, x, y);
        if(commandNum == 0) {
            g2d.drawString(">", x - 45, y);
        }

        // Back to Title Screen
        text = "Quit";
        x += 10;
        y += 72;
        g2d.drawString(text, x, y);
        if(commandNum == 1) {
            g2d.drawString(">", x - 45, y);
        }
    }

    // Draw options menu
    public void drawOptionsMenu() {
        g2d.setColor(Color.white);
        g2d.setFont(g2d.getFont().deriveFont(65F));

        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (substate) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_controls(frameX, frameY);
            case 2 -> options_endGameConfirm(frameX, frameY);
        }

        gamePanel.keyHandler.enterPressed = false;
    }

    // Draw top of options menu
    public void options_top(int frameX, int frameY) {
        // TITLE
        String text = "Options";
        int textX = getXCenteredText(text);
        int textY = frameY + gamePanel.tileSize;
        g2d.drawString(text, textX, textY);

        // MUSIC
        g2d.setFont(g2d.getFont().deriveFont(50F));
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        g2d.drawString("Music", textX, textY);
        if(commandNum == 0) g2d.drawString(">", textX - 25, textY);
        // SOUND EFFECTS
        textY += gamePanel.tileSize;
        g2d.drawString("Effects", textX, textY);
        if(commandNum == 1) g2d.drawString(">", textX - 25, textY);
        // CONTROLS
        textY += gamePanel.tileSize;
        g2d.drawString("Controls", textX, textY);
        if(commandNum == 2) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                substate = 1;
                commandNum = 0;
            }
        }
        // END GAME
        textY += gamePanel.tileSize;
        g2d.drawString("End Game", textX, textY);
        if(commandNum == 3) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                substate = 2;
                commandNum = 0;
            }
        }
        // BACK
        textY += gamePanel.tileSize * 2;
        g2d.drawString("Back", textX, textY);
        if(commandNum == 4) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.playState;
                commandNum = 0;
            }
        }

        // MUSIC SLIDER
        textX = frameX + gamePanel.tileSize * 5;
        textY = frameY + (gamePanel.tileSize * 2) + 30;
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(textX, textY, 120, gamePanel.tileSize/2);
        int volumeWidth = 24 * gamePanel.music.volumeScale;
        g2d.fillRect(textX, textY, volumeWidth, gamePanel.tileSize/2);

        // SOUND EFFECT SLIDER
        textY += gamePanel.tileSize;
        g2d.drawRect(textX, textY, 120, gamePanel.tileSize/2);
        volumeWidth = 24 * gamePanel.soundEffect.volumeScale;
        g2d.fillRect(textX, textY, volumeWidth, gamePanel.tileSize/2);

        // LOAD CONFIG
        gamePanel.config.saveConfig();
    }

    // Draw controls in options menu
    public void options_controls(int frameX, int frameY) {
        String text = "Controls";
        int textX = getXCenteredText(text);
        int textY = frameY + gamePanel.tileSize;
        g2d.setFont(g2d.getFont().deriveFont(50F));
        g2d.drawString(text, textX, textY);

        // LEFT SIDE
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        g2d.drawString("Move", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("Confirm/Attack", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("Shoot/Cast", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("Inventory", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("Pause", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("Options", textX, textY);

        // RIGHT SIDE
        textX = frameX + gamePanel.tileSize * 6;
        textY = frameY + gamePanel.tileSize * 2;
        g2d.drawString("WASD", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("ENTER", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("F", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("C", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("P", textX, textY); textY += gamePanel.tileSize;
        g2d.drawString("ESC", textX, textY);

        // BACK BUTTON
        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        g2d.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                substate = 0;
                commandNum = 2;
            }
        }
    }

    // Drawing end game screen with options to continue or start new.
    public void options_endGameConfirm(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;
        g2d.setFont(g2d.getFont().deriveFont(50F));
        currentDialogue = "Quit the game and \nreturn to title screen?";

        for(String line : currentDialogue.split("\n")) {
            g2d.drawString(line, textX, textY);
            textY += 40;
        }

        //YES
        String text = "Yes";
        textX = getXCenteredText(text);
        textY += gamePanel.tileSize * 3;
        g2d.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                substate = 0;
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }
        // NO
        text = "No";
        textX = getXCenteredText(text);
        textY += gamePanel.tileSize;
        g2d.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2d.drawString(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed) {
                substate = 0;
                commandNum = 3;
                gamePanel.resetGame(true);
            }
        }
    }

    // Draw a transition that fades to black.
    public void drawTransition() {
        counter++;
        g2d.setColor(new Color(0, 0, 0, counter * 5));
        g2d.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter == 40) {
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventManager.tempMap;
            gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventManager.tempCol;
            gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventManager.tempRow;
            gamePanel.eventManager.previousEventX = gamePanel.player.worldX;
            gamePanel.eventManager.previousEventY = gamePanel.player.worldY;
        }
    }

    // Draw trading screen.
    public void drawTradeScreen() {
        switch(substate) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    // Draw merchant select screen.
    public void trade_select() {

        npc.dialogueSet = 0;
        drawDialogueScreen();

        // DRAW WINDOW
        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 6;
        int width = gamePanel.tileSize * 3;
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        // DRAW OPTIONS
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2d.drawString("Buy", x, y);
        if(commandNum == 0) {
            g2d.drawString(">", x - 25, y);
            if(gamePanel.keyHandler.enterPressed) substate = 1;
        }
        y += gamePanel.tileSize;
        g2d.drawString("Sell", x, y);
        if(commandNum == 1) {
            g2d.drawString(">", x - 25, y);
            if(gamePanel.keyHandler.enterPressed) substate = 2;
        }
        y += gamePanel.tileSize;
        g2d.drawString("Leave", x, y);
        if(commandNum == 2) {
            g2d.drawString(">", x - 25, y);
            commandNum = 0;
            npc.startDialogue(npc, 1);
        }
    }

    // Draw merchant's buy screen.
    public void trade_buy() {
        // DRAW PLAYER INVENTORY IN BUY MENU
        drawInventory(gamePanel.player, false);
        //DRAW NPC INVENTORY IN BUY MENU
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.setFont(g2d.getFont().deriveFont(54F));
        g2d.drawString("[ESC] Back", x + 23, y + 58);

        // DRAW PLAYER'S COINS
        x = gamePanel.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2d.drawString("Coins: " + gamePanel.player.coin, x + 23, y + 58);

        // DRAW PRICE
        int itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()) {
            x = gamePanel.tileSize * 5;
            y = gamePanel.tileSize * 5;
            width = gamePanel.tileSize * 3;
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2d.drawImage(coin, x + 10, y + 7, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = String.valueOf(price);
            x = getXAlignRightText(text, gamePanel.tileSize * 8);
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 38F));
            g2d.drawString(text, x - 8, y + 31);

            // Buy an item
            if(gamePanel.keyHandler.enterPressed) {
                if(npc.inventory.get(itemIndex).price > gamePanel.player.coin) {
                    substate = 0;
                    npc.startDialogue(npc, 2);
                }
                else {
                    if(gamePanel.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gamePanel.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        substate = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }

    // Draw the merchant's selling screen.
    public void trade_sell() {
        // DRAW PLAYER INVENTORY
        drawInventory(gamePanel.player, true);

        // DRAW HINT
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.setFont(g2d.getFont().deriveFont(54F));
        g2d.drawString("[ESC] Back", x + 23, y + 58);

        // DRAW PLAYER'S COINS
        x = gamePanel.tileSize * 9;
        drawSubWindow(x, y, width, height);
        g2d.drawString("Coins: " + gamePanel.player.coin, x + 23, y + 58);

        // DRAW PRICE
        int itemIndex = getItemIndex(playerSlotCol, playerSlotRow);
        if(itemIndex < gamePanel.player.inventory.size()) {
            x = gamePanel.tileSize * 12;
            y = gamePanel.tileSize * 5;
            width = gamePanel.tileSize * 3;
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2d.drawImage(coin, x + 7, y + 8, 32, 32, null);

            int price = gamePanel.player.inventory.get(itemIndex).price;
            price -= (price / 3); // sell price is 2/3 the value of buy price
            String text = String.valueOf(price);
            x = getXAlignRightText(text, gamePanel.tileSize * 14 + 20);
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 38F));
            g2d.drawString(text, x - 18, y + 31);

            // KeyHandler Sell Item
            if(gamePanel.keyHandler.enterPressed) {
                if(gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield ||
                        gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon) {
                    commandNum = 0;
                    substate = 0;
                    npc.startDialogue(npc, 4);
                } else {
                    if(gamePanel.player.inventory.get(itemIndex).stackAmount > 1) {
                        gamePanel.player.inventory.get(itemIndex).stackAmount--;
                    }
                    else {
                        gamePanel.player.inventory.remove(itemIndex);
                    }
                    gamePanel.player.coin += price;
                }
            }
        }
    }

    // Draw sleeping transition screen, gradually gets brighter until daytime.
    public void drawSleepScreen() {
        counter++;

        if(counter < 120) {
            gamePanel.environment.lighting.filterAlpha += 0.01f;
            if(gamePanel.environment.lighting.filterAlpha > 1f) {
                gamePanel.environment.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120) {
            gamePanel.environment.lighting.filterAlpha -= 0.01f;
            if(gamePanel.environment.lighting.filterAlpha <= 0f) {
                gamePanel.environment.lighting.filterAlpha = 0f;
                counter = 0;
                gamePanel.environment.lighting.dayState = gamePanel.environment.lighting.day;
                gamePanel.environment.lighting.dayCounter = 0;
                gamePanel.gameState = gamePanel.playState;
                gamePanel.player.getImage();
            }
        }
    }

    // Returns item index given its position.
    public int getItemIndex(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5); // returns item index in ArrayList
    }

    // General use-case drawing sub window.
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 180); // black RGB
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255); // white RGB
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 20, 20);
    }

    // Receive coordinate for centered text.
    public int getXCenteredText(String text) {
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return (gamePanel.screenWidth / 2) - (length / 2);
    }

    // Receive coordinate for aligning text to the right.
    public int getXAlignRightText(String text, int tailX) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return tailX - length;
    }
}
