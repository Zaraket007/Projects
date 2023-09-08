package gamepack;

import java.awt.*;

public class WinLossCheck {
    boolean win;
    boolean loss;
    int[][] theMap;
    Ball ball;
    public WinLossCheck(int[][] theMap, Ball ball){
        win=false;
        loss=false;
        this.theMap=theMap;
        this.ball=ball;
    }

    public void drawWin(Graphics2D g){
        Font font = new Font("Courier New", Font.PLAIN, 50);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        String text = "Winner!!!";
        int stringWidth = fontMetrics.stringWidth(text);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Winner!!",(int)(Main.WIDTH-stringWidth)/2,(int)Main.HEIGHT/2);
    }
    public void drawLoss(Graphics2D g){
        Font font = new Font("Courier New", Font.PLAIN, 50);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        String text = "Loser!!!";
        int stringWidth = fontMetrics.stringWidth(text);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Loser!!",(int)(Main.WIDTH-stringWidth)/2,(int)Main.HEIGHT/2);
    }
    public boolean checkWin(){
        int nbBricks=0;
        for (int[] ints : theMap) {
            for (int col = 0; col < theMap[0].length; col++) {
                nbBricks+=ints[col];
            }
        }
        return nbBricks == 0;
    }
    public boolean checkLoss(){
        return ball.getY()>Main.HEIGHT-ball.getBallsize();
    }

    public void setTheMap(int[][] theMap) {
        this.theMap = theMap;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
}
