package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shootPressed;

    // debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // On key pressed, execute code
    @Override
    public void keyPressed(KeyEvent e) {
        // Retrieve current keypress
        int code = e.getKeyCode();

        // TITLE STATE
        if(gamePanel.gameState == gamePanel.titleState) titleState(code);
        // PLAY STATE
        else if(gamePanel.gameState == gamePanel.playState) playState(code);
        // PAUSED STATE
        else if(gamePanel.gameState == gamePanel.pauseState) pausedState(code);
        // DIALOGUE STATE
        else if(gamePanel.gameState == gamePanel.dialogueState) dialogueState(code);
        // CHARACTER STATE
        else if(gamePanel.gameState == gamePanel.characterState) characterState(code);
        // OPTIONS MENU STATE
        else if(gamePanel.gameState == gamePanel.optionState) optionState(code);
        // GAME OVER STATE
        else if(gamePanel.gameState == gamePanel.gameOverState) gameOverState(code);
        // TRADE STATE
        else if(gamePanel.gameState == gamePanel.tradeState) tradeState(code);
    }

    // When keypress happens in title screen
    public void titleState(int code) {
        if(gamePanel.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = 2;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum > 2) gamePanel.ui.commandNum = 0;
            }
            // MAIN MENU
            if(code == KeyEvent.VK_ENTER) {
                // NEW GAME
                if(gamePanel.ui.commandNum == 0) {
                    gamePanel.ui.titleScreenState = 1;
                    gamePanel.playMusic(0);
                }
                // LOAD GAME
                if(gamePanel.ui.commandNum == 1) {
                    // load game code goes here
                }
                // QUIT GAME
                if(gamePanel.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        // CLASS SELECTION (nothing happens yet)
        else if(gamePanel.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = 3;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum > 3) gamePanel.ui.commandNum = 0;
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gamePanel.ui.commandNum == 0) gamePanel.gameState = gamePanel.playState;
                if(gamePanel.ui.commandNum == 1) gamePanel.gameState = gamePanel.playState;
                if(gamePanel.ui.commandNum == 2) gamePanel.gameState = gamePanel.playState;
                if(gamePanel.ui.commandNum == 3) gamePanel.ui.titleScreenState = 0;
            }
        }
    }

    // When keypress happens in play state
    public void playState(int code) {
        // Check for key pressed, set their boolean to true
        if(code == KeyEvent.VK_W) upPressed = true;
        if(code == KeyEvent.VK_A) leftPressed = true;
        if(code == KeyEvent.VK_S) downPressed = true;
        if(code == KeyEvent.VK_D) rightPressed = true;
        if(code == KeyEvent.VK_P) gamePanel.gameState = gamePanel.pauseState;
        if(code == KeyEvent.VK_C) gamePanel.gameState = gamePanel.characterState;
        if(code == KeyEvent.VK_ENTER) enterPressed = true;
        if(code == KeyEvent.VK_F) shootPressed = true;
        if(code == KeyEvent.VK_ESCAPE) gamePanel.gameState = gamePanel.optionState;

        // Debug Key
        if(code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if(code == KeyEvent.VK_R) {
            switch (gamePanel.currentMap) {
                case 0 -> gamePanel.tileManager.loadMap("maps/worldV2.txt", 0);
                case 1 -> gamePanel.tileManager.loadMap("maps/interior01.txt", 1);
            }
        }
    }

    // When keypress happens in paused state
    public void pausedState(int code) {
        if(code == KeyEvent.VK_P) gamePanel.gameState = gamePanel.playState;
    }

    // When keypress happens in dialogue state
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) gamePanel.gameState = gamePanel.playState;
    }

    // When keypress happens in character state
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gamePanel.gameState = gamePanel.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gamePanel.player.selectItem();
        }
        playerInventory(code);
    }

    public void optionState(int code) {
        if(code == KeyEvent.VK_ESCAPE) gamePanel.gameState = gamePanel.playState;
        if(code == KeyEvent.VK_ENTER) enterPressed = true;

        int maxCommandNum = 0;
        switch(gamePanel.ui.substate) {
            case 0 -> maxCommandNum = 4;
            case 2 -> maxCommandNum = 1;
        }
        if(code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            gamePanel.playEffect(9);
            if(gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = maxCommandNum;
        }
        if(code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            gamePanel.playEffect(9);
            if(gamePanel.ui.commandNum > maxCommandNum) gamePanel.ui.commandNum = 0;
        }
        if(code == KeyEvent.VK_A) {
            if(gamePanel.ui.substate == 0) {
                if(gamePanel.ui.commandNum == 0 && gamePanel.music.volumeScale > 0) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playEffect(9);
                }
                if(gamePanel.ui.commandNum == 1 && gamePanel.soundEffect.volumeScale > 0) {
                    gamePanel.soundEffect.volumeScale--;
                    gamePanel.playEffect(9);
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gamePanel.ui.substate == 0) {
                if(gamePanel.ui.commandNum == 0 && gamePanel.music.volumeScale < 5) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playEffect(9);
                }
                if(gamePanel.ui.commandNum == 1 && gamePanel.soundEffect.volumeScale < 5) {
                    gamePanel.soundEffect.volumeScale++;
                    gamePanel.playEffect(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if(gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = 1;
            gamePanel.playEffect(9);
        }
        if(code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if(gamePanel.ui.commandNum > 1) gamePanel.ui.commandNum = 0;
            gamePanel.playEffect(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gamePanel.ui.commandNum == 0) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
                gamePanel.playMusic(0);
            }
            else if(gamePanel.ui.commandNum == 1) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.restart();
            }
        }
    }

    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER) enterPressed = true;
        if(gamePanel.ui.substate == 0) {
            if(code == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = 2;
                gamePanel.playEffect(9);
            }
            if(code == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum > 2) gamePanel.ui.commandNum = 0;
                gamePanel.playEffect(9);
            }
        }
        if(gamePanel.ui.substate == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gamePanel.ui.substate = 0;
            }
        }
        if(gamePanel.ui.substate == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gamePanel.ui.substate = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if(code == KeyEvent.VK_W) {
            if(gamePanel.ui.playerSlotRow != 0) {
                gamePanel.ui.playerSlotRow--;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gamePanel.ui.playerSlotCol != 0) {
                gamePanel.ui.playerSlotCol--;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gamePanel.ui.playerSlotRow != 3) {
                gamePanel.ui.playerSlotRow++;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gamePanel.ui.playerSlotCol != 4) {
                gamePanel.ui.playerSlotCol++;
                gamePanel.playEffect(9);
            }
        }
    }

    public void npcInventory(int code) {
        if(code == KeyEvent.VK_W) {
            if(gamePanel.ui.npcSlotRow != 0) {
                gamePanel.ui.npcSlotRow--;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gamePanel.ui.npcSlotCol != 0) {
                gamePanel.ui.npcSlotCol--;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gamePanel.ui.npcSlotRow != 3) {
                gamePanel.ui.npcSlotRow++;
                gamePanel.playEffect(9);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gamePanel.ui.npcSlotCol != 4) {
                gamePanel.ui.npcSlotCol++;
                gamePanel.playEffect(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Retrieve key code
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) upPressed = false;
        if(code == KeyEvent.VK_A) leftPressed = false;
        if(code == KeyEvent.VK_S) downPressed = false;
        if(code == KeyEvent.VK_D) rightPressed = false;
        if(code == KeyEvent.VK_F) shootPressed = false;
    }
}
