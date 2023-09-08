package gamepack;

import java.awt.*;
import java.util.Random;

public class Map {
    private int[][] theMap;
    private final int brickWidth,brickHeight;



    public final int HOR_PAD=80,VER_PAD=50;


    public Map(int rows,int cols){
        initMap(rows,cols);
        brickWidth=(Main.WIDTH-2*HOR_PAD)/cols;
        brickHeight=(Main.HEIGHT/2-VER_PAD*2)/rows;

    }
    public void initMap(int rows,int cols){
        theMap=new int[rows][cols];
        Random random = new Random();

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                int randomNumber = random.nextInt(5) + 1;
                if(randomNumber==5 || randomNumber==4 ) randomNumber=random.nextInt(5) + 1;
                theMap[i][j]=randomNumber;
            }
        }


    }

    public void draw(Graphics2D g){

        for (int row=0;row<theMap.length;row++){
            for (int col=0;col<theMap[0].length;col++){

                if(theMap[row][col]>0){

                    switch (theMap[row][col]){
                        case 1: g.setColor(Color.gray);
                                break;
                        case 2: g.setColor(Color.darkGray);
                                break;
                        case 3: g.setColor(new Color(173, 216, 230));
                                break;
                        case PowerUp.WIDEPADDLE: g.setColor(PowerUp.WIDECOLOR);
                            break;
                        case PowerUp.FASTBALL: g.setColor(PowerUp.FASTCOLOR);
                            break;
                        default: g.setColor(Color.gray);
                                 break;
                    }

                    g.fillRect(col*brickWidth+HOR_PAD ,row*brickHeight+VER_PAD,brickWidth,brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.white);
                    g.drawRect(col*brickWidth+HOR_PAD ,row*brickHeight+VER_PAD,brickWidth,brickHeight);
                }


            }
        }


    }
    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public int[][] getTheMap() {
        return theMap;
    }
    public void setBrick(int r, int c, int value){
        theMap[r][c]=value;
    }

    public void hitBrick(int i, int j) {
        theMap[i][j]=theMap[i][j]-1;

    }
}
