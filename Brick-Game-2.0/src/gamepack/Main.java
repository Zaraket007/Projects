package gamepack;

import javax.swing.*;
import java.awt.*;
//modified

public class Main {
    public static final int WIDTH=640,HEIGHT=480;
    public static void main(String[] args) {


        Toolkit toolkit=Toolkit.getDefaultToolkit();
        //get screen size
        Dimension screenSize = toolkit.getScreenSize();

        JFrame frame=new JFrame();
        GamePanel gamepanel=new GamePanel();


        frame.setTitle("Brick Breaker V2.0");
        //frame.setLocationRelativeTo(null);
        frame.setLocation((screenSize.width-WIDTH)/2,(screenSize.height-HEIGHT)/2);

        frame.setResizable(false);
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(gamepanel);
        gamepanel.playGame();

    }
}