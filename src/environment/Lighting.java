package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    // Time of day states
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    // Lighting constructor
    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    // Create a lighting gradiant that is drawn on a buffered image.
    public void setLightSource() {
        // Creating buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        if(gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0.01f, 0.1f, .95f));
        }
        else {
            // Get center of "light" circle
            int centerX = gp.player.cameraX + (gp.tileSize) / 2;
            int centerY = gp.player.cameraY + (gp.tileSize) / 2;

            // Gradiant effect for light circle
            Color[] color = new Color[12];
            float[] fraction = new float[12];
            color[0] = new Color(0, 0.01f, 0.1f, 0.1f);
            color[1] = new Color(0, 0.01f, 0.1f, 0.35f);
            color[2] = new Color(0, 0.01f, 0.1f, 0.47f);
            color[3] = new Color(0, 0.01f, 0.1f, 0.59f);
            color[4] = new Color(0, 0.01f, 0.1f, 0.68f);
            color[5] = new Color(0, 0.01f, 0.1f, 0.76f);
            color[6] = new Color(0, 0.01f, 0.1f, 0.82f);
            color[7] = new Color(0, 0.01f, 0.1f, 0.85f);
            color[8] = new Color(0, 0.01f, 0.1f, 0.88f);
            color[9] = new Color(0, 0.01f, 0.1f, 0.90f);
            color[10] = new Color(0, 0.01f, 0.1f, 0.92f);
            color[11] = new Color(0, 0.01f, 0.1f, 0.93f);
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
            RadialGradientPaint gradient = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);
            g2.setPaint(gradient);
        }
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();
    }

    // Resets day cycle upon death.
    public void resetCycle() {
        dayState = day;
        filterAlpha = 0f;
    }

    // Updates darkness whenever player equips/unequips lighting item.
    // Manages day and night cycle.
    public void update() {
        if(gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }

        // if day, count until dusk
        if(dayState == day) {
            dayCounter++;
            if(dayCounter >= 5000) { //5400 default
                dayState = dusk;
                dayCounter = 0;
            }
        }
        // if dusk, count until night
        if(dayState == dusk) {
            filterAlpha += 0.001f;
            if(filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        // if night, count until dawn
        if(dayState == night) {
            dayCounter++;
            if(dayCounter > 5000) { // 5400 default
                dayState = dawn;
                dayCounter = 0;
            }
        }
        // if dawn, count until day
        if(dayState == dawn) {
            filterAlpha -= 0.001f;
            if(filterAlpha <= 0f) {
                filterAlpha = 0f;
                dayState = day;
            }
        }
    }

    // Drawing the filter onto the screen.
    public void draw(Graphics2D g2) {

        if(gp.currentArea == gp.outside) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // DEBUGGING
        String s = "";
        switch(dayState) {
            case day -> s = "Day";
            case dusk -> s = "Dusk";
            case night -> s = "Night";
            case dawn -> s = "Dawn";
        }
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(60f));
        g2.drawString(s, 800, 500);
    }
}
