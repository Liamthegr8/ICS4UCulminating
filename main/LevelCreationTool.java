import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LevelCreationTool extends JFrame {
    int windowX = 500;
    int windowY = 500;
    int tickDelay = 10;
    Timer tick;
    DrawingPanel panel;
    Player player;
    Map map;
    Room roomData;
    boolean qPressed, wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed; //removed pPressed

    //debugging
    int windowMouseX = 0;
    int windowMouseY = 0;

    JLabel mouseXLabel = new JLabel();
    JLabel mouseYLabel = new JLabel();
    


    public static void main(String[] args) {
         new LevelCreationTool();
	}

    LevelCreationTool() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Level Creation Tool");
        //More custom stuff
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //IGNORED DUE TO JPANELPACK
        this.setResizable(true);
        //this.setUndecorated(true);  //hides the title bar of JFrame

        panel = new DrawingPanel();
        panel.add(mouseXLabel);
        panel.add(mouseYLabel);

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

        //Events
        this.addKeyListener(new KeyHandler());
        this.addMouseMotionListener(new MouseMotionHandler());

        resetGame();
    }

    private void resetGame() {
        // player = new Player(0,500,30,30); //resets to constructors

        //Tanush Mock Collision Setup
        map = new Map();
        map.setMapTileColor(0, Color.BLUE);
        map.setMapTileColor(1, Color.RED);

        roomData = new Room("NONAME");
        
        // roomData.addTileAt(new SpikeTile(0,450,500,49,0));
        // roomData.addTileAt(new MovingPlatformTile(100, 400,  100, 50, 1, 200, 400, 1));

        map.addRoomAt(roomData, 0, 0); //refresh on edits
    }

    public void saveToFile() {
        File file = new File("main\\assets\\levelCreator.txt");
        try {
            FileWriter out = new FileWriter(file);
            BufferedWriter write = new BufferedWriter(out);
            for (Tile t: roomData.roomTiles) {
                write.write();
                write.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("error");
        }
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
        //mouseclick and release
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
            if (qPressed) {
                resetGame();
            }
            
            

            


            mouseXLabel.setText("mx:" + windowMouseX);
            mouseYLabel.setText("my:" + windowMouseY);
            
            //refresh graphics
            this.repaint();

            //tiles tick - not run due to CONCERN OF MOVING PLATFORMS
            // for (int i=0; i<map.mapRooms.length; i++) {
            //     for (int j=0; j<map.mapRooms.length; j++) {
            //         Room r = map.mapRooms[i][j];
            //         if (r != null) {
            //             for (Tile tile: r.roomTiles) {
            //                     if (tile != null) {
            //                         tile.tick(player, i, j);
            //                     }
            //             }
            //         }
            //     }
            // }	
        }

        @Override
        public void paintComponent(Graphics g) {
            //Advanced Graphics
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Turn on antialiasing
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            

            //Tanush Code Render Tiles
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        for (Tile tile: r.roomTiles) {
                                Tile t = tile;
                                if (t != null) {
                                    g2.setColor(Color.BLACK);
                                    g2.setStroke(new BasicStroke(1));

                                    if (t.assignedMapColorIndex >= 0 && t.assignedMapColorIndex<map.assignedTileColors.length && map.assignedTileColors[t.assignedMapColorIndex] != null) {    
                                        Color tileColor = map.assignedTileColors[t.assignedMapColorIndex];
                                        g2.setColor(tileColor);
                                        g2.fillRect(i*Room.roomWidth + (t.x), j*Room.roomHeight + (t.y), t.width, t.height);  
                                    } else {
                                        if (t.assignedMapColorIndex == -2) { //for transparent tiles
                                            //no fill or draw
                                        } else { //assumes -1 - black
                                            g2.setColor(Color.BLACK);
                                            g2.fillRect(i*Room.roomWidth + (t.x), j*Room.roomHeight + (t.y), t.width, t.height);  
                                        }
                                    }
                                    
                                    
                                    
                                    //Images
                                    // if (t.getScaledImage() == null) {
                                    //     g2.drawRect(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY), t.width, t.height);
                                    // }
                                    // else {
                                    // g2.drawImage(t.scaledImage, i*Room.roomWidth + t.x + t.imageXOffset + camX, j*Room.roomHeight + t.y + t.imageYOffset + camY, null);
                                    // }
                                }
                        }
                    }
                }
            }

            //DEBUG
            //Render Map bounds
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        g2.setColor(Color.GRAY);
                        g2.setStroke(new BasicStroke(5));
                        g2.drawRect(i*Room.roomWidth, j*Room.roomHeight, Room.roomWidth, Room.roomHeight);
                    }
                }
            }   }
            }

}  
   