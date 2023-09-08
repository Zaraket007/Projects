package gamepack;

import java.awt.*;

public class Ball {

    private double x,y,dx,dy;
    private final int ballsize=30;

    public Ball(){
        x=200;
        y=200;
        dx=1;
        dy=3;
    }

    public void update(){
        setPosition();
    }


    public void setPosition(){
        x+=dx;
        y+=dy;
        if (x<0 || x>(Main.WIDTH-ballsize)){
            dx=-dx;
        }
        if (y<0 || y>(Main.HEIGHT-ballsize)){
            dy=-dy;
        }
    }
    public void draw(Graphics2D g){
        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(4)); //line thickness
        g.drawOval((int)x,(int)y,ballsize,ballsize);

    }
    public Rectangle getRect(){
        return new Rectangle((int)x,(int)y,ballsize,ballsize);
    }
    public double getDy(){
        return dy;
    }
    public void setDy(double dy){
        this.dy=dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDx() {
        return dx;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getBallsize() {
        return ballsize;
    }
}

