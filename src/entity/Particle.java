package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd; // delta x
    int yd; // delta y

    public Particle(GamePanel gamePanel, Entity generator, Color color,
                    int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gamePanel.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    @Override
    public void update() {
        life--;
        if(life < maxLife / 3) yd++; // add 1 to delta y per loop, animates
        worldX += xd * speed;
        worldY += yd * speed;
        if(life == 0) alive = false; // despawn
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.cameraX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.cameraY;

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
