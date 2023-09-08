package gamepack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GamePanel extends JPanel{

    private boolean running;
    private BufferedImage image;
    private Graphics2D g;
    Ball ball;
    Paddle paddle;
    Map theMap;
    HUD hud;
    double mouseX;
    boolean win,loss;

    WinLossCheck winLossCheck;
    private MyMouseMotionListener mouseMotionListener;
    private ArrayList<PowerUp> powerUps;
    public GamePanel(){
        init();
    }
    public void init(){
        running=true;
        image=new BufferedImage(Main.WIDTH,Main.HEIGHT,BufferedImage.TYPE_INT_RGB);
        g= (Graphics2D) image.getGraphics();
        ball=new Ball();
        paddle=new Paddle();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        mouseMotionListener=new MyMouseMotionListener();
        addMouseMotionListener(mouseMotionListener);
        theMap=new Map(6,10);

        hud=new HUD();
        mouseX=0;
        winLossCheck=new WinLossCheck(theMap.getTheMap(),ball);
        win=loss=false;
        powerUps=new ArrayList<>();
    }

    public void playGame() {

        //wait 2 sec before game start.
        try {
            Thread.sleep(0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        while(running){
            //update
            update();


            //render
            draw();


            //display
            repaint();


            try {
                Thread.sleep(10);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public void update(){
        ball.update();
        paddle.update();
        checkCollision();
        if(winLossCheck.checkWin()){
            win=true;
            running=false;
        }
        if((winLossCheck.checkLoss())){
            loss=true;
            running=false;
        }
        for (PowerUp pu: powerUps){
            pu.update();
        }

    }
    public void draw(){
        //background color:
        g.setColor(Color.white);
        g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
        theMap.draw(g);
        ball.draw(g);
        paddle.draw(g);
        hud.draw(g);
        if(win) winLossCheck.drawWin(g);
        if(loss) winLossCheck.drawLoss(g);
        drawPowerUps(g);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image,0,0, Main.WIDTH,Main.HEIGHT,null);
        g2.dispose();//for memorey purposes


    }
    private class MyMouseMotionListener implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            paddle.mouseMove(e.getX());
            mouseX=e.getX();
        }
    }
    public void checkCollision(){
        //coll with paddle
        Rectangle ballRect = ball.getRect();
        Rectangle paddleRect = paddle.getRect();
        for (int i=0;i<powerUps.size();i++){
            Rectangle puRect= powerUps.get(i).getRect();
            if(paddleRect.intersects(puRect) && !powerUps.get(i).wasUsed){
                powerUps.get(i).setWasUsed(true);
                paddle.setWidthTimer();
                if(powerUps.get(i).getType()==PowerUp.WIDEPADDLE){
                    paddle.setWidth(paddle.getWidth()*2);
                }
            }

        }

        if(ballRect.intersects(paddleRect)){

            ball.setDy(-ball.getDy());
            ball.setDx(-ball.getDx());

            if(ball.getX()>mouseX){
                if(ball.getDx()<0) ball.setDx(0);
                ball.setDx(ball.getDx()+1);

            }
            else if (ball.getX()<mouseX) {
                if(ball.getDx()>0) ball.setDx(0);
                ball.setDx(ball.getDx()-1);

            }
            else ball.setDx(0);

        }
            //col with bricks


        A: for (int i=0;i<theMap.getTheMap().length;i++){
            for (int j=0;j<theMap.getTheMap()[0].length;j++) {
                if (theMap.getTheMap()[i][j] > 0) {

                    int brickWidth = theMap.getBrickWidth();
                    int brickHeight = theMap.getBrickHeight();
                    int brickX = j * brickWidth + theMap.HOR_PAD;
                    int brickY = i * brickHeight + theMap.VER_PAD;

                    Rectangle brickRect=new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    if (ballRect.intersects(brickRect)) {

                        if(theMap.getTheMap()[i][j]>3){
                            powerUps.add( new PowerUp(brickX,brickY,theMap.getTheMap()[i][j],brickWidth,brickHeight) );
                            theMap.setBrick(i,j,3);
                        }else{
                            theMap.hitBrick(i,j);
                        }


                        ball.setDy(-ball.getDy());
                        theMap.getTheMap()[i][j]--;
                        hud.addScore();
                        break A;
                    }
                }
            }
        }


    }
    public void drawPowerUps(Graphics2D g){
        for (PowerUp pu: powerUps){
            pu.draw(g);
        }
    }
}
