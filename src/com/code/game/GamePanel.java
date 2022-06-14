package com.code.game;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author 7eo
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 550;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 175;
    //arrays -> snake body
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyparts = 4;
    //apple
    int applesEaten;
    int appleX;
    int appleY;

    char direction = 'R';
    boolean runningGame = false;
    Timer timer;
    Random random;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(40, 55, 71));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    /**
     *
     */
    private void startGame() {
        newApple();
        runningGame = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     *
     * @param g
     */
    private void draw(Graphics g) {
        for (int i = 0; i < (SCREEN_WIDTH / UNIT_SIZE); i++) {
            g.setColor(new Color(86, 101, 115));
            g.drawLine((i * UNIT_SIZE), 0, (i * UNIT_SIZE), SCREEN_HEIGHT);
            g.drawLine(0, (i * UNIT_SIZE), SCREEN_WIDTH, (i * UNIT_SIZE));
        }

        g.setColor(new Color(231, 76, 60));
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i < bodyparts; i++) {
            if (i == 0) {
                g.setColor(new Color(17, 122, 101));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(35, 155, 86));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    /**
     *
     * Creates a new apple in the runningGame
     */
    private void newApple() {
        appleX = random.nextInt(((int) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt(((int) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     *
     * Snake moves
     */
    private void move() {
        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        /**
         * R = right L = left U = up D = down
         */
        switch (direction) {
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * 
     */
    private void checkApple() {
       if ((x[0] == appleX) && (y[0] == appleY)) {
           bodyparts++;
           applesEaten++;
           newApple();
       } 
    }

    /**
     * 
     */
    private void checkCollision() {
        //checks if the head collides with body
        for (int i = bodyparts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                runningGame = false;
            }
        }
        //check if the head touches the left border
        if (x[0] < 0) {
            runningGame = false;
        }
        //check if the head touches the rigth border
        if (x[0] > SCREEN_WIDTH) {
            runningGame = false;
        }
        //check if the head touches the top border
        if (y[0] < 0) {
            runningGame = false;
        }
        //check if the head touches the botton border
        if (y[0] > SCREEN_HEIGHT) {
            runningGame = false;
        }
        
        //if the game ends, the timer stop
        if (!runningGame) {
            timer.stop();
        }
    }

    /**
     *
     */
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    /**
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (runningGame) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

}
