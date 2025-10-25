package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    final int originalTileSize = 16; //16x16 tiles
    final int scale = 3;//making the tiles bigger for modern pixel counts

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; // 4:3 window ratio
    final int screenWidth = tileSize * maxScreenCol; //768 px
    final int screenHeight = tileSize * maxScreenRow; //576 px

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //can be started and stopped, useful for rerunning a set of actions.

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);// can improve games rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);// game panel can be focused to receive key input
    }

    public void startGameThread() {
        gameThread = new Thread(this); //passing this gamePanel to the Thread constructor
        gameThread.start(); // automatically calls run();
    }

    @Override
    public void run() { // automatically run when Thread is called

        //Game Loop
        while (gameThread != null) {
            //System.out.println("Game Loop Running");

            // 1. UPDATE: update information such as character position
            update();
            // 2. DRAW: draw the screen with updated information
            repaint();
        }
    }

    public void update() {

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; //extends the Graphics class to provide more sophisticated control
                                        // over geometry, coordinate transformations, color management and text layout

        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);
        g2.dispose();// dispose of graphics content and release system resources it is using.
    }

}
