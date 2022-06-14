package com.code.game;

import javax.swing.*;

/**
 *
 * @author 7eo
 */
public class GameFrame extends JFrame {

    public GameFrame() {
        // Add panel 
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
