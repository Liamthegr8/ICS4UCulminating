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
    PausePanel pausePanel;
    InfoPanel infoPanel;
    LeaderboardEntry[] leaderboardEntries;
    Timer tick;
    int tickDelay = 10;
    boolean lPressed;
    static int windowX;
    static int windowY;

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
        setUndecorated(true);
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

        //pack(); //important to understand size of window is size of panel

        setLocationRelativeTo(null);
        setVisible(true);

        // create and populate leaderboard entries
        leaderboardEntries = new LeaderboardEntry[10];
        for (int i=0; i<leaderboardEntries.length; i++) {
            leaderboardEntries[i] = new LeaderboardEntry(String.valueOf(i), i*10);
        }

        // create panels after frame is visible so getWidth()/getHeight() return meaningful values
        gamePanel = new GamePanel(getWidth(), getHeight(), leaderboardEntries);
        menuPanel = new MainMenuPanel(getWidth(), getHeight());
        leaderboardPanel = new LeaderboardPanel(getWidth(), getHeight(), leaderboardEntries); 
        pausePanel = new PausePanel(getWidth(), getHeight(), gamePanel.map);
        infoPanel = new InfoPanel(getWidth(), getHeight());

        switchTo(menuPanel);

        // start main timer after panels exist to avoid actionPerformed running on null panels
        tick = new Timer(tickDelay, this);
        tick.start();

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
            } else if (menuPanel.menuOption.equals("info")) {
                menuPanel.tick.stop();
                infoPanel.reset();
                switchTo(infoPanel);
            }
        } else if (activePanel == gamePanel) {
            if (!gamePanel.panelActive && !gamePanel.pauseMenuActive) {
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
        } else if (activePanel == infoPanel) {
            if (!infoPanel.panelActive) {
                infoPanel.tick.stop();
                menuPanel.reset();
                switchTo(menuPanel);
            }
        }
        if (activePanel == gamePanel) {
            if (gamePanel.pauseMenuActive) {
                System.out.println("Switching to pause menu");
                gamePanel.tick.stop();
                pausePanel.reset();
                pausePanel.map = gamePanel.map;
                pausePanel.dirMap = gamePanel.dirMap;
                pausePanel.playerLocation = gamePanel.player.getPlayerLocation(gamePanel.map);
                switchTo(pausePanel);
            }
        }
        else if (activePanel == pausePanel) {
            if (gamePanel.pauseMenuActive && pausePanel.menuOption.equals("resume")) {
                gamePanel.tick.start();
                gamePanel.wPressed = false;
                gamePanel.aPressed = false;
                gamePanel.sPressed = false;
                gamePanel.dPressed = false;
                gamePanel.uPressed = false;
                gamePanel.iPressed = false;
                gamePanel.oPressed = false;
                gamePanel.jPressed = false;
                gamePanel.kPressed = false;
                gamePanel.lPressed = false;
                gamePanel.qPressed = false;
                switchTo(gamePanel);
                gamePanel.pauseMenuActive = false;
                gamePanel.panelActive = true;
            }
            if (gamePanel.pauseMenuActive && pausePanel.menuOption.equals("menu")) {
                menuPanel.reset();
                switchTo(menuPanel);
                gamePanel.pauseMenuActive = false;
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

private class KeyHandler extends KeyAdapter {
    /**
             * handle key presses
             * @param e the key event triggered
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    lPressed = true;
                }
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    lPressed = false;
                }
            }
}    
}


