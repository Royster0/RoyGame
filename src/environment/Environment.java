package environment;

import main.GamePanel;

import java.awt.*;

public class Environment {

    GamePanel gp;
    public Lighting lighting;

    // Pass in GamePanel
    public Environment(GamePanel gp) {
        this.gp = gp;
    }

    // Setting up lighting
    public void setup() {
        lighting = new Lighting(gp);
    }

    // Update lighting
    public void update() {
        lighting.update();
    }

    // Draw lighting
    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
