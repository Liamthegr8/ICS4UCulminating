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
    Map map;
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

        //Tanush Mock Collision Setup
        map = new Map();
        Room room1 = new Room("Room1");   
        room1.addTileAt(new PlatformTile(),0,0);
        room1.addTileAt(new PlatformTile(),1,0);
        room1.addTileAt(new PlatformTile(),2,0);

        Room room2 = new Room("Room2");   
        room2.addTileAt(new PlatformTile(),0,0);
        room2.addTileAt(new PlatformTile(),1,0);
        room2.addTileAt(new PlatformTile(),2,0);

        Room room3 = new Room("Room3");
        room3.addTileAt(new PlatformTile(),2,0);
        room3.addTileAt(new PlatformTile(),2,2);

        map.addRoomAt(room1, 0, 1);
        map.addRoomAt(room2, 1, 2);
        map.addRoomAt(room3, 1, 1);
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
            //movements inputs
            if (wPressed) {
                if (player.isGrounded) {
                    player.jump();
                }
            }
            if (aPressed) {
                player.applyVelocity(-1, 0);
            }
            // if (sPressed) {
            //     player.applyVelocity(0, 1);
            // }
            if (dPressed) {
                player.applyVelocity(1, 0);
            }

            //ai tells me that applying tick before collision causes to lose the player jitter
            player.tick();
            
            //collisions
            //ASSUMES ALL TILES OF ANY TYPE ARE COLLIDABLE
            boolean isPlayerGrounded = false;
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        for (int k=0; k<r.roomTiles.length; k++) {
                            for (int l=0; l<r.roomTiles.length; l++) {
                                Tile t = r.roomTiles[k][l];
                                if (t != null) {
                                    Rectangle tile = new Rectangle(i*r.roomSize + k*Tile.tileSize, j*r.roomSize + l*Tile.tileSize, Tile.tileSize, Tile.tileSize);
                                    
                                    if (player.intersects(tile)) {
                                        //for the lengths/2 I could have done + velocity instead, this seemed more intuitive FOR NOW, given CURRENT SIZES

                                        //check if player is on top of tile
                                        if (player.y + player.height <= tile.y + Tile.tileSize/2) {
                                            isPlayerGrounded = true;
                                            player.y = tile.y - player.height;
                                        }

                                        //check if player is hitting above
                                        if (tile.y + Tile.tileSize <= player.y + player.height/2) {
                                            player.y = tile.y + Tile.tileSize;
                                            player.velY = 0;
                                        }
                                    }
                                    
                                    if (player.intersects(tile)) {
                                        //check right
                                        if (player.x + player.width <= tile.x + Tile.tileSize/2) {
                                            player.x = tile.x - player.width;
                                            player.velX = 0;
                                        }

                                        //check left
                                        if (tile.x + Tile.tileSize <= player.x + player.width/2) {
                                            player.x = tile.x + Tile.tileSize;
                                            player.velX = 0;
                                        }
                                    }
                                
                                }
                            }
                        }
                    }
                }
            }
            player.isGrounded = isPlayerGrounded;
            
            //ERICS CODE - update graphics every tick
            // if (plat1.checkCollision(plat1, player) && player.y < plat1.y) {
            //     player.isGrounded = true;
            //     player.y = plat1.y - player.height;
            // }
            // else if (plat2.checkCollision(plat2, player) && player.y < plat2.y) {
            //     player.isGrounded = true;
            //     player.y = plat2.y - player.height;
            // }
            // else {
            //     player.isGrounded = false;
            // }


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

            // ERICS CODE
            //g2.setColor(Color.BLACK);
            // g2.fillRect(plat1.getRelX(), plat1.getRelY(), plat1.width, plat1.height);
            // g2.fillRect(plat2.getRelX(), plat2.getRelY(), plat2.width, plat2.height);

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

        }
    }

}
