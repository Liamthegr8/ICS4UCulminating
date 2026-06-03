/**
 * Game.java
 * Main game.
 * Created by Tanush, Liam, Erik
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class Game extends JFrame {
    // int windowX = 900;
    // int windowY = 500;
    
    GamePanel gamePanel;
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Run GameWindow");
                new Game();
                
			}
		});
    }

    Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Game Window");
        //More custom stuff
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //IGNORED DUE TO JPANELPACK
        this.setResizable(true);
        //this.setUndecorated(true);  //hides the title bar of JFrame

        // gamePanel = new game.panel;
        // this.add(gamePanel);
        // // this.setSize(windowX,windowY); //IGNORED DUE TO JPANELPACK

        // //debugging elements
        // windowXLabel = new JLabel();
        // windowYLabel = new JLabel();
        // playerXLabel = new JLabel();
        // playerYLabel = new JLabel();
        // playervxLabel = new JLabel();
        // playervyLabel = new JLabel();
        // isPlayerGroundedLabel = new JLabel();
        // isPlayerWalledRLabel = new JLabel();
        // isPlayerWalledLLabel = new JLabel();
        // playerLocLabel = new JLabel();
        // // panel.add(windowXLabel);
        // // panel.add(windowYLabel);
        // panel.add(playerXLabel);
        // panel.add(playerYLabel);
        // panel.add(playervxLabel);
        // panel.add(playervyLabel);
        // panel.add(isPlayerWalledRLabel);
        // panel.add(isPlayerWalledLLabel);
        // panel.add(isPlayerGroundedLabel);
        // panel.add(playerLocLabel);

        setupGamePanel(); //this adds game panel

        this.pack(); //important to understand size of window is size of panel

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // end of setup
    }
 
    void setupGamePanel() {
        gamePanel = new GamePanel();
        this.add(gamePanel);    

        this.pack(); //important to understand size of window is size of panel

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}



// public class Game {
//     public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
// 			public void run() {
// 				System.out.println("Run GameWindow");
//                 new GameWindow();
                
// 			}
// 		});
//     } 
// }
