/**
 * Game.java
 * Main game.
 * Created by Tanush, Liam, Erik
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;


public class Game extends JFrame implements ActionListener  {
    JPanel activePanel;
    MainMenuPanel menuPanel;
    GamePanel gamePanel;
    LeaderboardPanel leaderboardPanel;
    String[] leaderboardScores;
    Timer tick;
    int tickDelay = 10;

    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Run GameWindow");
                new Game();
                
			}
		});
    }

    Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Game");
        this.setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        

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

        //switchTo(menuPanel);
        //setupGamePanel(); //this adds game panel

        tick = new Timer(tickDelay, this);
        tick.start();

        //pack(); //important to understand size of window is size of panel

        setLocationRelativeTo(null);
        setVisible(true);

        leaderboardScores = new String[10];
        leaderboardScores[0] = "Player 1: 67";
        leaderboardScores[1] = "Player 2: 67";
        leaderboardScores[3] = "Player 4: 67";
        leaderboardScores[4] = "Player 5: 67";
        leaderboardScores[5] = "Player 6: 67";

        gamePanel = new GamePanel(getWidth(), getHeight(), leaderboardScores);
        menuPanel = new MainMenuPanel(getWidth(), getHeight());
        leaderboardPanel = new LeaderboardPanel(getWidth(), getHeight(), leaderboardScores); 

        switchTo(menuPanel);
        
        // end of setup
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (activePanel == menuPanel) {
            if (menuPanel.menuOption.equals("play")) {
                menuPanel.tick.stop();
                gamePanel.resetGame();
                switchTo(gamePanel);
                
            } else if (menuPanel.menuOption.equals("leaderboard")) {
                menuPanel.tick.stop();
                leaderboardPanel.reset();
                switchTo(leaderboardPanel);
            }
        } else if (activePanel == gamePanel) {
            if (!gamePanel.panelActive) {
                gamePanel.tick.stop();
                menuPanel.reset();
                switchTo(menuPanel);
            }
        } else if (activePanel == leaderboardPanel) {
            if (!leaderboardPanel.panelActive) {
                leaderboardPanel.tick.stop();
                menuPanel.reset();
                switchTo(menuPanel);
            }
        }
    }

    void switchTo(JPanel next) {
        if (activePanel != null) {
            remove(activePanel);
        }

        activePanel = next;
        add(activePanel);

        revalidate();
        repaint();

        activePanel.setFocusable(true);
        activePanel.requestFocusInWindow();
        // SwingUtilities.invokeLater(() -> activePanel.requestFocusInWindow());
    }

}
