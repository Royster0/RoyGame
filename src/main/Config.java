package main;

import java.io.*;

public class Config {

    GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config"));
            // Music Volume
            bw.write(String.valueOf(gamePanel.music.volumeScale));
            bw.newLine();
            // Sound Effect Volume
            bw.write(String.valueOf(gamePanel.soundEffect.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config"));

            String s = br.readLine();
            gamePanel.music.volumeScale = Integer.parseInt(s);

            s = br.readLine();
            gamePanel.soundEffect.volumeScale = Integer.parseInt(s);

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
