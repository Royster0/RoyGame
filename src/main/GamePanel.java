package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; // 16x16px tiles
    final int scale = 3; // scale to size (will look 48x48px)

    public final int tileSize = originalTileSize * scale; // 48x48px
    public final int maxScreenCol = 16; // max tile column size is 16
    public final int maxScreenRow = 12; // max tile row size is 12
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    int drawCount;

    // System
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    public CollisionManager collision = new CollisionManager(this); // collision manager
    public AssetManager assManager = new AssetManager(this);
    public UI ui = new UI(this);
    public EventManager eventManager = new EventManager(this);
    Thread gameThread;

    // Entity & Object
    public Player player = new Player(this, keyHandler); // player model
    public Entity[] objects = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>(); // entity render order

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assManager.setObject();
        assManager.setNPC();
        assManager.setMonster();
        // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }


    public void update() {
        if(gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (Entity entity : npc) {
                if (entity != null) entity.update();
            }

            // MONSTER
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    if(monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if(!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }

            // PROJECTILE
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i).alive) projectileList.get(i).update();
                if(!projectileList.get(i).alive) projectileList.remove(i);
            }
        }
        if(gameState == pauseState) {
            // nothing
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        // Debug
        long drawStart = 0;
        if(keyHandler.checkDrawTime) drawStart = System.nanoTime();

        // TITLE SCREEN
        if(gameState == titleState) ui.draw(graphics2D);
        else {
            // DRAW TILES
            tileManager.draw(graphics2D);

            // ADD PLAYER
            entityList.add(player);
            // ADD NPC
            for(Entity npcEntity : npc) {
                if(npcEntity != null) entityList.add(npcEntity);
            }
            // ADD OBJECTS
            for(Entity objEntity : objects) {
                if(objEntity != null) entityList.add(objEntity);
            }
            // ADD MONSTERS
            for(Entity monEntity : monster) {
                if(monEntity != null) entityList.add(monEntity);
            }
            // ADD PROJECTILES
            for(Entity projectile : projectileList) {
                if(projectile != null) entityList.add(projectile);
            }

            // SORT ENTITIES BASED ON THEIR WORLD Y
            entityList.sort(Comparator.comparingInt(ent -> ent.worldY));

            // DRAW ENTITIES
            for(Entity entity : entityList) {
                entity.draw(graphics2D);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(graphics2D);
        }

        // DEBUG
        if(keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long drawPassed = drawEnd - drawStart;
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Draw Time: " + drawPassed, 10, 400);
            System.out.println("Draw Time: " + drawPassed);
        }

        graphics2D.dispose();
    }

    // play game's background music
    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    // Stop music
    public void stopMusic() {
        music.stop();
    }

    // play sound effect
    public void playEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
