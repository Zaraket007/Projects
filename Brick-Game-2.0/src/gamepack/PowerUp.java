package gamepack;

import java.awt.*;
import java.util.Random;

public class PowerUp {
    // Fields
    private int x, y, dy, type, width, height;
    private boolean isOnScreen;
    private Color color;
    public final static int WIDEPADDLE = 4;
    public final static int FASTBALL = 5;
    public final static Color WIDECOLOR=new Color(50,200,50);
    public final static Color FASTCOLOR = new Color(250,60,60);
    Random random;
    boolean wasUsed;
    public PowerUp(int xStart, int yStart, int theType, int theWidth, int theHeight) {
        random=new Random();
        x=xStart;
        y=yStart;
        type=theType;
        width=theWidth;
        height=theHeight;

        if(type<4) type=4;
        if(type>5) type=5;
        if(type== WIDEPADDLE){ color=WIDECOLOR;}
        if(type== FASTBALL){ color=FASTCOLOR;}


        dy=random.nextInt(6)+1;
        wasUsed=false;

    }
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
    public void update() {
        y+=dy;

        if (y>Main.HEIGHT){
            isOnScreen=false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getType() {
        return type;
    }

    public boolean isOnScreen() {
        return isOnScreen;
    }

    public void setOnScreen(boolean onScreen) {
        isOnScreen = onScreen;
    }

    public Rectangle getRect() {
        return new Rectangle(x,y,width,height);

    }

    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }
}