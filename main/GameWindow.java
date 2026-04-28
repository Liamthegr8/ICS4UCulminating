import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class GameWindow extends JFrame {
    int windowX = 900;
    int windowY = 500;
    int tickDelay = 10;
    Timer tick;
    DrawingPanel panel;
    Player player;
    Map map;
    boolean wPressed, aPressed, sPressed, dPressed;

    //debugging
    int windowMouseX = 0;
    int windowMouseY = 0;
    JLabel playerXLabel;
    JLabel playerYLabel;
    JLabel playervxLabel;
    JLabel playervyLabel;
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
        // panel.add(windowXLabel);
        // panel.add(windowYLabel);
        panel.add(playerXLabel);
        panel.add(playerYLabel);
        panel.add(playervxLabel);
        panel.add(playervyLabel);
        panel.add(isPlayerWalledRLabel);
        panel.add(isPlayerWalledLLabel);
        panel.add(isPlayerGroundedLabel);

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

        //Events
        this.addKeyListener(new KeyHandler());
        this.addMouseMotionListener(new MouseMotionHandler());

        //Tanush Mock Collision Setup
        //map has 3x3 rooms
        //room has 10x10 tiles
        map = new Map();
        Room room1 = new Room("Room1");   
        room1.addTileAt(new PlatformTile(),0,5);
        room1.addTileAt(new PlatformTile(),1,5);
        room1.addTileAt(new PlatformTile(),2,5);
        room1.addTileAt(new PlatformTile(),3,5);
        room1.addTileAt(new PlatformTile(),4,5);
        room1.addTileAt(new PlatformTile(),5,5);
        room1.addTileAt(new PlatformTile(),6,5);
        room1.addTileAt(new PlatformTile(),7,5);
        room1.addTileAt(new PlatformTile(),8,5);
        room1.addTileAt(new PlatformTile(),9,5);

        room1.addTileAt(new PlatformTile(),4,4);
        room1.addTileAt(new PlatformTile(),4,3);

        room1.addTileAt(new PlatformTile(),6,2);

        room1.addTileAt(new PlatformTile(),9,4);
        room1.addTileAt(new PlatformTile(),9,3);
        room1.addTileAt(new PlatformTile(),9,2);


        map.addRoomAt(room1, 0, 0);
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
            if (wPressed) {
                if (player.isGrounded) {
                    player.jump();
                }
            }
            if (aPressed) {
                player.updateVelocity(-1, 0);
            }
            if (sPressed) {
                //reset player pos for testing
                player.x = 0;
                player.y = 0;
                player.vx = 0;
                player.vy = 0;
            }
            if (dPressed) {
                player.updateVelocity(1, 0);
            }

            //player handles all internally, that is what i call better code
            player.tick(map);
            

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
            
            //refresh graphics
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
            if (player.isTouchingRightWall) {
                g2.setColor(Color.YELLOW);
            } else if (player.isTouchingLeftWall) {
                g2.setColor(Color.ORANGE);
            } else if (player.isGrounded) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.RED);
            }

            g2.fillRect(player.x, player.y, player.width, player.height);

            //Tanush Code Render Tiles
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        for (int k=0; k<r.roomTiles.length; k++) {
                            for (int l=0; l<r.roomTiles.length; l++) {
                                Tile t = r.roomTiles[k][l];
                                if (t != null) {
                                    g2.setColor(Color.BLACK);
                                    g2.drawRect(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                }
                            }
                        }
                    }
                }
            }

            //Render surroundingTiles
            ArrayList<TileRef> tiles = player.surroundingTiles;
            for (int i=0; i<tiles.size(); i++) {
                TileRef t = tiles.get(i);
                if (t != null) {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(t.x, t.y, Tile.tileSize, Tile.tileSize);
                    g2.setColor(Color.RED);
                    g2.drawRect(t.x, t.y, Tile.tileSize, Tile.tileSize);
                }
            }
        }
    }

}
