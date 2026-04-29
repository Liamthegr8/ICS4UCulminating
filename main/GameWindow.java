import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class GameWindow extends JFrame {
    int windowX = 900;
    int windowY = 500;
    int tickDelay = 10;
    int camX;
    int camY;
    Timer tick;
    DrawingPanel panel;
    Player player;
    Map map;
    boolean wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed; //removed pPressed
    boolean qPressed; //testing buttons

    //debugging
    int windowMouseX = 0;
    int windowMouseY = 0;
    JLabel playerXLabel;
    JLabel playerYLabel;
    JLabel playervxLabel;
    JLabel playervyLabel;
    JLabel playerLocLabel;
    JLabel windowXLabel;
    JLabel windowYLabel;
    JLabel isPlayerGroundedLabel;
    JLabel isPlayerWalledRLabel;
    JLabel isPlayerWalledLLabel;



    public static void main(String[] args) {
         new GameWindow(); //temp non threaded for testing
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

        //debugging elements
        windowXLabel = new JLabel();
        windowYLabel = new JLabel();
        playerXLabel = new JLabel();
        playerYLabel = new JLabel();
        playervxLabel = new JLabel();
        playervyLabel = new JLabel();
        isPlayerGroundedLabel = new JLabel();
        isPlayerWalledRLabel = new JLabel();
        isPlayerWalledLLabel = new JLabel();
        playerLocLabel = new JLabel();
        // panel.add(windowXLabel);
        // panel.add(windowYLabel);
        panel.add(playerXLabel);
        panel.add(playerYLabel);
        panel.add(playervxLabel);
        panel.add(playervyLabel);
        panel.add(isPlayerWalledRLabel);
        panel.add(isPlayerWalledLLabel);
        panel.add(isPlayerGroundedLabel);
        panel.add(playerLocLabel);

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

        //Events
        this.addKeyListener(new KeyHandler());
        this.addMouseMotionListener(new MouseMotionHandler());

        resetGame();
    }

    private void resetGame() {
        camX = 0;
        camY = 0;
        player = new Player(30,30); //resets to constructors

        //Tanush Mock Collision Setup
        map = new Map();
        Room room1 = new Room("Room1");   
        // room1.addTileAt(new PlatformTile(0,250, 50, 50));
        // room1.addTileAt(new PlatformTile(50,250, 50, 50));
        // room1.addTileAt(new PlatformTile(100,250, 50, 50));
        // room1.addTileAt(new PlatformTile(150,250, 50, 50));
        // room1.addTileAt(new PlatformTile(200,250, 50, 50));
        // room1.addTileAt(new PlatformTile(250,250, 50, 50));
        // room1.addTileAt(new PlatformTile(300,250, 50, 50));
        // room1.addTileAt(new PlatformTile(350,250, 50, 50));
        // room1.addTileAt(new PlatformTile(400,250, 50, 50));
        // room1.addTileAt(new PlatformTile(450,250, 50, 50));

        // room1.addTileAt(new PlatformTile(200,200, 50, 50));
        // room1.addTileAt(new PlatformTile(200,150, 50, 50));

        // room1.addTileAt(new PlatformTile(300,100, 50, 50));

        // room1.addTileAt(new PlatformTile(450,200, 50, 50));
        // room1.addTileAt(new PlatformTile(450,150, 50, 50));
        // room1.addTileAt(new PlatformTile(450,100, 50, 50));

        //create room to test
        room1.addTileAt(new PlatformTile(0,0, 50, 50));
        room1.addTileAt(new PlatformTile(50,0, 50, 50));
        room1.addTileAt(new PlatformTile(100,0, 50, 50));
        room1.addTileAt(new PlatformTile(0,50, 50, 50));
        room1.addTileAt(new PlatformTile(50,50, 50, 50));
        room1.addTileAt(new PlatformTile(100,50, 50, 50));
        room1.addTileAt(new PlatformTile(0,100, 50, 50));
        room1.addTileAt(new PlatformTile(50,100, 50, 50));
        room1.addTileAt(new PlatformTile(100,100, 50, 50));
        
        room1.addTileAt(new PlatformTile(160,0, 200, 30));


        map.addRoomAt(room1, 0, 0);
        map.addRoomAt(room1, 1, 0);
    }

    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //no panel repaint here. otherwise the graphics will update when key pressed instantly. not upon fixed tick delay
            //if repainted, this can be exploited to speed up time
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
            if (e.getKeyCode() == KeyEvent.VK_U) {
                uPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_I) {
                iPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_O) {
                oPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {
                jPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_K) {
                kPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_L) {
                lPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                qPressed = true;
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
            if (e.getKeyCode() == KeyEvent.VK_U) {
                uPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_I) {
                iPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_O) {
                oPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {
                jPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_K) {
                kPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_L) {
                lPressed = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                qPressed = false;
            }
        }
    }

    class MouseMotionHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            windowMouseX = e.getX()-8;
            windowMouseY = e.getY()-31;
        }
    }

    private class DrawingPanel extends JPanel implements ActionListener {
        DrawingPanel() {
            this.setPreferredSize(new Dimension(windowX,windowY));
        }

        //GAMELOOP & Timer
        @Override
        public void actionPerformed(ActionEvent e) {
            //player movements inputs
            //can turn into inputActions fuction
            if (wPressed) {
                player.jump();
            }
            if (aPressed) {
                player.updateVelocity(-1, 0);
            }
            if (sPressed) {
            }
            if (qPressed) {
                resetGame();
            }
            if (dPressed) {
                player.updateVelocity(1, 0);
            }
            if (uPressed) {
                player.dash(wPressed, aPressed, sPressed, dPressed);
            }

            //player handles all internally, that is what i call better code
            player.tick(map);
            if (player.isDead) {
                resetGame();
            }
            
            updateCamera();
            

            //debugging
            windowXLabel.setText("Window MouseX: " + String.valueOf(windowMouseX));
            windowYLabel.setText("Window MouseY: " + String.valueOf(windowMouseY));
            playerXLabel.setText("Player x: " + String.valueOf(player.x));
            playerYLabel.setText("Player y: " + String.valueOf(player.y));
            String roundedvx = String.format("%.1f", player.vx);
            String roundedvy = String.format("%.1f", player.vy);
            playervxLabel.setText("Player vx: " + roundedvx);
            playervyLabel.setText("Player vy: " + roundedvy);
            isPlayerGroundedLabel.setText("Player Grounded: " + String.valueOf(player.isGrounded));
            isPlayerWalledRLabel.setText("Player R Walled: " + String.valueOf(player.isTouchingRightWall));
            isPlayerWalledLLabel.setText("Player L Walled: " + String.valueOf(player.isTouchingLeftWall));
            if (player.playerLocation != null) {
                playerLocLabel.setText("Location:" + "[" + player.playerLocation[0] + " , " + player.playerLocation[1] + "]");
            } else {
                playerLocLabel.setText("Location: null");
            }
            
            //refresh graphics
            this.repaint();	
        }

        void updateCamera() {
            //based on window vars
            camX = -player.x + (windowX/2) - (player.width/2);
            camY = -player.y + (windowY/2) - (player.height/2);
        }

        @Override
        public void paintComponent(Graphics g) {
            //Advanced Graphics
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Turn on antialiasing
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //code
            if (player.isTouchingRightWall) {
                g2.setColor(Color.YELLOW);
            } else if (player.isTouchingLeftWall) {
                g2.setColor(Color.ORANGE);
            } else if (player.isGrounded) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.RED);
            }

            g2.fillRect(player.x+camX, player.y+camY, player.width, player.height);

            //Tanush Code Render Tiles
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        for (Tile tile: r.roomTiles) {
                                Tile t = tile;
                                if (t != null) {
                                    g2.setColor(Color.BLACK);
                                    g2.drawRect(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY), t.width, t.height);
                                }
                        }
                    }
                }
            }

            //Render surroundingTiles
            ArrayList<Tile> tiles = player.surroundingTiles;
            for (Tile tile: tiles) {
                Tile t = tile;
                if (t != null) {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(t.x+camX, t.y+camY, t.width, t.height);
                    g2.setColor(Color.RED);
                    g2.drawRect(t.x+camX, t.y+camY, t.width, t.height);
                }
            }
        }
    }

}
