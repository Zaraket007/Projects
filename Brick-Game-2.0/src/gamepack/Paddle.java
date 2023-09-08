package gamepack;

import java.awt.*;

public class Paddle {
    private double x;
    private int width;
    private final int height;
    private int startWidth;
    public final int YPos=Main.HEIGHT-100;
    private long widthTimer;
    private boolean altWidth;

    public long getWidthTimer() {
        return widthTimer;
    }

    public void setWidthTimer() {
        widthTimer=System.nanoTime();
    }

    public Paddle(){
        width=100;
        height=10;
        startWidth=width;
        widthTimer=0;
        altWidth=false;
        x=(Main.WIDTH-width)/2.0; //center the paddle
        altWidth=false;
    }
    //update
    public void update(){
        if((System.nanoTime()-widthTimer)/1000 >9000000){
            width=startWidth;
            altWidth=false;

        }
    }

    //draw
    public void draw(Graphics2D g){


        if(altWidth){
            g.setColor(Color.green);
        }
        else         g.setColor(Color.darkGray);

        g.fillRect((int)x,YPos,width,height);
    }
    public void mouseMove(int mouseXPos){
        x=mouseXPos-width/2.0;
        if(x<0) x=0;
        if (x>Main.WIDTH-width) x=Main.WIDTH-width;
    }
    public Rectangle getRect(){
        return new Rectangle((int)x,YPos,width,height);
    }

    public void setWidth(int width){
        this.width=width;
        altWidth=true;
        setWidthTimer();
    }

    public int getWidth() {
        return width;
    }
}
