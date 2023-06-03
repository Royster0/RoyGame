package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {

        // Creating buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        // Create screen sized rectangle
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        // Get center of "light" circle
        int centerX = gp.player.cameraX + (gp.tileSize) / 2;
        int centerY = gp.player.cameraY + (gp.tileSize) / 2;
        // Get top left x and y of circle
        double x = centerX - ((double) circleSize / 2);
        double y = centerY - ((double) circleSize / 2);

        // Creating "light" circle shape
        Shape lightCircle = new Ellipse2D.Double(x, y, circleSize, circleSize);
        Area lightArea = new Area(lightCircle);

        // Subtract circle area from screen area
        screenArea.subtract(lightArea);

        // Gradiant effect for light circle
        Color[] color = new Color[12];
        float[] fraction = new float[12];
        color[0] = new Color(0, 0, 0, 0.1f);
        color[1] = new Color(0, 0, 0, 0.35f);
        color[2] = new Color(0, 0, 0, 0.47f);
        color[3] = new Color(0, 0, 0, 0.59f);
        color[4] = new Color(0, 0, 0, 0.68f);
        color[5] = new Color(0, 0, 0, 0.76f);
        color[6] = new Color(0, 0, 0, 0.82f);
        color[7] = new Color(0, 0, 0, 0.85f);
        color[8] = new Color(0, 0, 0, 0.88f);
        color[9] = new Color(0, 0, 0, 0.90f);
        color[10] = new Color(0, 0, 0, 0.92f);
        color[11] = new Color(0, 0, 0, 0.93f);
        fraction[0] = 0.1f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        // Creating the gradient
        RadialGradientPaint gradient = new RadialGradientPaint(centerX, centerY, ((float) circleSize / 2), fraction, color);
        g2.setPaint(gradient);
        g2.fill(lightArea);

        // Draw on buffered image
        g2.fill(screenArea);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
