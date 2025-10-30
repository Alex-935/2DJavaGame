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

    // FPS = 60
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //can be started and stopped, useful for rerunning a set of actions.

    // Set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

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

    // Sleep method for 60 FPS  -  some claim threadSleep isnt entirely accurate and can be a few milliseconds out
    /*
    @Override
    public void run() { // automatically run when Thread is called

        /*
        First method for 60fps - sleep method
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        Inside game loop:
        try {
                double remainingTime = nextDrawTime - System.nanoTime();// time in milliseconds
                remainingTime = remainingTime/1000000; //covert to nanoseconds

                if (remainingTime < 0) {// if the time taken to update is longer than the time interval
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e) {
                //auto generated
                e.printStackTrace();
            }
         */ /*
        double drawInterval = 1000000000/FPS; // 1 second / 60 fps = time interval for each frame
        double nextDrawTime = System.nanoTime() + drawInterval;

        //Game Loop
        while (gameThread != null) {
            //System.out.println("Game Loop Running");

            long currentTime = System.nanoTime();
            //long currentTime = System.milliTime(); is also an option but nano is more precise

            // 1. UPDATE: update information such as character position
            update();
            // 2. DRAW: draw the screen with updated information
            repaint();

            // first method for 60 fps - sleep method
            try {
                double remainingTime = nextDrawTime - System.nanoTime();// time in milliseconds
                remainingTime = remainingTime/1000000; //covert to nanoseconds

                if (remainingTime < 0) {// if the time taken to update is longer than the time interval
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e) {
                //auto generated
                e.printStackTrace();
            }
        }
    }
    */
    // Delta / Accumulator method for 60 FPS
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; //extends the Graphics class to provide more sophisticated control
                                        // over geometry, coordinate transformations, color management and text layout

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();// dispose of graphics content and release system resources it is using.
    }

}
