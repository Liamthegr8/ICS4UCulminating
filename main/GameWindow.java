import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class GameWindow extends JFrame {
    int windowX = 500;
    int windowY = 500;
    int tickDelay = 10;
    Timer tick;
    DrawingPanel panel;
    Player player;

    boolean wPressed, aPressed, sPressed, dPressed;

    public static void main(String[] args) {
	}

    GameWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gaming Window");
        //More custom stuff
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //IGNORED DUE TO JPANELPACK
        this.setResizable(true);
        //this.setUndecorated(true);  //hides the title bar of JFrame

        panel = new DrawingPanel();
        this.add(panel);
        // this.setSize(windowX,windowY); //IGNORED DUE TO JPANELPACK
        this.pack(); //important to understand size of window is size of panel

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        gameSetup();
    }

    private void gameSetup() {
        //Set up timer
        tick = new Timer(tickDelay, panel);
        // tick.setInitialDelay(1000);
        tick.start();

        player = new Player(30,30);
        this.addKeyListener(new KeyHandler());
    }

    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //code
            //no panel repaint here. otherwise the graphics will update when key pressed instantly. not upon fixed tick delay
            //is repainted, this can be exploited to speed up time
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
            }
        }
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = false;
            }
        }
    }


    private class DrawingPanel extends JPanel implements ActionListener {
        DrawingPanel() {
            this.setPreferredSize(new Dimension(windowX,windowY));
        }

        //GAMELOOP & Timer
        @Override
        public void actionPerformed(ActionEvent e) {
            //update movements/logics
            if (wPressed) {
                //if player is TOUCHING GROUND
                player.jump();
            }
            if (aPressed) {
                player.applyVelocity(-1, 0);
            }
            if (sPressed) {
                player.applyVelocity(0, 1);
            }
            if (dPressed) {
                player.applyVelocity(1, 0);
            }
            player.tick();
            
            //update graphics every tick
            this.repaint();	
        }

        @Override
        public void paintComponent(Graphics g) {
            //Advanced Graphics
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Turn on antialiasing
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //code
            g2.setColor(Color.RED);
            g2.fillRect(player.x, player.y, player.width, player.height);

        }
    }

}
