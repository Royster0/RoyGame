package environment;

import main.GamePanel;

import java.awt.*;

public class Environment {

    GamePanel gp;
    Lighting lighting;

    public Environment(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lighting = new Lighting(gp, 350);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
