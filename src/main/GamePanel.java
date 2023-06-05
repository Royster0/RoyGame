package main;

// Import local packages
import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.Environment;
import tile.Map;
import tile.TileManager;
import tiles_interactive.InteractiveTiles;

// Import java utilities
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

// GamePanel class. Central class for handling the bulk of the game operations.
public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; // 16x16px tiles
    final int scale = 3; // scale to size (will look 48x48px)

    public final int tileSize = originalTileSize * scale; // 48x48px
    public final int maxScreenCol = 20; // max tile column size is 16
    public final int maxScreenRow = 12; // max tile row size is 12
    public final int screenWidth = tileSize * maxScreenCol; // 960px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FPS
    int FPS = 60;
    int drawCount;

    // System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    public CollisionManager collision = new CollisionManager(this); // collision manager
    public AssetManager assManager = new AssetManager(this);
    public UI ui = new UI(this);
    public EventManager eventManager = new EventManager(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Environment environment = new Environment(this);
    Map map = new Map(this);
    public EntityFactory eFactory = new EntityFactory(this);
    SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread;

    // Entity & Object
    public Player player = new Player(this, keyHandler); // player model
    public Entity[][] objects = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTiles[][] iTile = new InteractiveTiles[maxMap][50];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    public Entity[][] projectile = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>(); // entity render order

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;

    // Area player is in
    public int currentArea;
    public int nextAreaBuffer;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

    // GamePanel constructor. Initializes screen and key listener.
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    // Initialize the assets and spawns entities.
    public void setupGame() {
        assManager.setObject();
        assManager.setNPC();
        assManager.setMonster();
        assManager.setInteractiveTile();
        environment.setup();
        currentArea = outside;

        gameState = titleState;
    }

    // Retry or restart after the player dies.
    public void resetGame(boolean restart) {
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounters();
        assManager.setNPC();
        assManager.setMonster();

        if(restart) {
            player.setDefaultValues();
            assManager.setObject();
            assManager.setInteractiveTile();
            environment.lighting.resetCycle();
        }
    }

    // Starting the game thread.
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game loop. Overrides from runnable.
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


    // Update method that updates player position and entity spawning per frame.
    public void update() {
        if(gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // MONSTER
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            // PROJECTILE
            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive) projectile[currentMap][i].update();
                    if(!projectile[currentMap][i].alive) projectile[currentMap][i] = null;
                }
            }

            // PARTICLES
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            // INTERACTIVE TILES
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            // LIGHTING
            environment.update();
        }
        if(gameState == pauseState) {
            // nothing
        }
    }

    // Paints components onto the screen using Graphics2D. Override from JComponent.
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        // Debug
        long drawStart = 0;
        if(keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(graphics2D);
        }
        // MAP SCREEN
        else if(gameState == mapState) {
            map.drawFullMap(graphics2D);
        }
        else {
            // DRAW TILES
            tileManager.draw(graphics2D);

            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) iTile[currentMap][i].draw(graphics2D);
            }

            // ADD PLAYER
            entityList.add(player);
            // ADD NPC
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) entityList.add(npc[currentMap][i]);
            }
            // ADD OBJECTS
            for(int i = 0; i < objects[1].length; i++) {
                if(objects[currentMap][i] != null) entityList.add(objects[currentMap][i]);
            }
            // ADD MONSTERS
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) entityList.add(monster[currentMap][i]);
            }
            // ADD PROJECTILES
            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            // ADD PARTICLES
            for (Entity currPart : particleList) {
                if (currPart != null) entityList.add(currPart);
            }

            // SORT ENTITIES BASED ON THEIR WORLD Y
            entityList.sort(Comparator.comparingInt(ent -> ent.worldY));

            // DRAW ENTITIES
            for(Entity entity : entityList) {
                entity.draw(graphics2D);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // ENVIRONMENT
            environment.draw(graphics2D);

            // MINI MAP
            map.drawMinimap(graphics2D);

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

    // Changes player area. Changes music and respawns monsters.
    public void changeArea() {

        if(nextAreaBuffer != currentArea) {
            stopMusic();

            if(nextAreaBuffer == outside) playMusic(0);
            if(nextAreaBuffer == indoor) playMusic(18);
            if(nextAreaBuffer == dungeon) playMusic(19);
        }

        currentArea = nextAreaBuffer;
        assManager.setMonster();
    }
}
