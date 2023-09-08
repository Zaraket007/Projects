package gamepack;

import java.awt.*;

public class HUD {
    private int score;

    public HUD() {
        init();
    }

    private void init() {
        score=0;
    }
    public void draw(Graphics2D g){
        g.setColor(Color.black);
        Font font = new Font("Courier New", Font.PLAIN, 10);
        g.setFont(font);
        g.drawString("Score: "+score,20,20);
    }

    public int getScore() {
        return score;
    }
    public void addScore(){
        score++;
    }
}
