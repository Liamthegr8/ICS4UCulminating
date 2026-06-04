 /**
 * LevelCreationTool.java
 * Allows for the creation of rooms through the use of mouse and keyboard, and saves them to txt.
 * Created by Tanush, Liam, Erik
 */
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
    int windowX = 1200;
    int windowY = 800;
    int tickDelay = 10;
    Timer tick;
    DrawingPanel panel;
    Player player;
    Map map;
    Room roomData;
    boolean qPressed, wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed, mPressed; //removed pPressed
    boolean snapToGrid = false;

    //debugging
    int windowMouseX = 0;
    int windowMouseY = 0;
    boolean leftMouseClicked = false;
    boolean rightMouseClicked = false;
    boolean leftMouseHeld = false;
    boolean rightMouseHeld = false;
    

    JLabel mouseXLabel = new JLabel();
    JLabel mouseYLabel = new JLabel();
    JLabel objLabel = new JLabel();
    
    //creation tool
    Scanner sc = new Scanner(System.in);
    int rectStartX = -100;
    int rectStartY = -100;
    int rectEndX = -100;
    int rectEndY = -100;
    int digSelected = -1;
    String digInput = "00"; //2 digit
    int digCounter = 0; //to track 00 
    int objectChosen;

    String roomName = "name";
    FileHandle x = new FileHandle();

    public static void main(String[] args) {
         new LevelCreationTool();
	}

    LevelCreationTool() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Level Creation Tool");
        //More custom stuff
        this.setResizable(true);
        //this.setUndecorated(true);

        panel = new DrawingPanel();
        panel.add(mouseXLabel);
        panel.add(mouseYLabel);
        panel.add(objLabel);

        this.add(panel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        gameSetup();
    }

    /**
     * set up the creation tool, events, timer
     */
    private void gameSetup() {
        //Set up timer
        tick = new Timer(tickDelay, panel);
        tick.start();

        //Events
        this.addKeyListener(new KeyHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
        this.addMouseListener(new MouseMotionHandler());

        resetGame();
    }

    /**
     * reset maps
     */
    private void resetGame() {
        map = new Map();
        map.setMapTileColor(0, Color.BLUE);
        map.setMapTileColor(1, Color.RED);

        roomData = new Room("NONAME");
        //roomData = x.findRoom("name");
        map.addRoomAt(roomData, 0, 0);
        player = new Player(-100, -10000, 1, 1);
        
        // roomData.addTileAt(new SpikeTile(0,450,500,49,0));
        // roomData.addTileAt(new MovingPlatformTile(100, 400,  100, 50, 1, 200, 400, 1));

        //map.addRoomAt(roomData, 0, 0);
    }

    /**
     * save the created level to a text file in a specific format, that can be read by the game
     */
    public void saveToFile() {
        File file = new File("main\\assets\\levelCreator.txt");
        System.out.println("method runs");
        System.out.println("What is the room name?");
        roomName = sc.nextLine();
        try {
            FileWriter out = new FileWriter(file);
            BufferedWriter write = new BufferedWriter(out);
            //System.out.println("Writers created");
            write.write("(");
            write.write(roomName);
            write.write(")");
            //System.out.println("roomname written");
            for (Tile t: roomData.roomTiles) {
                System.out.println("yes");
                int[] param = t.returnParams();
                write.write("_");
                if (param[0] < 10) write.write("0"); //to make sure tileID is 2 digits for reading purposes
                write.write(String.valueOf(param[0]));
                write.write("<");
                //System.out.println("tile start");
                for (int i = 1; i < param.length; i++) {
                    write.write(String.valueOf(param[i]));
                    if (i != (param.length - 1)) {
                        write.write(",");
                        //System.out.println("tile written");
                    }
                }
                write.write(">");
            }
            write.write(";");
            write.newLine();
            write.close();
            out.close();
            System.out.println("fini");
        }
        catch (IOException e) {
            System.out.println("error");
        }
    }

    // handle keyboard input
    class KeyHandler extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            switch(e.getKeyChar()) {
                case '0':
                    digSelected = 0;
                    break;
                case '1':
                    digSelected = 1;
                    break;
                case '2':
                    digSelected = 2;
                    break;
                case '3':
                    digSelected = 3;
                    break;
                case '4':
                    digSelected = 4;
                    break;
                case '5':
                    digSelected = 5;
                    break;
                case '6':
                    digSelected = 6;
                    break;
                case '7':
                    digSelected = 7;
                    break;
                case '8':
                    digSelected = 8;
                    break;
                case '9':
                    digSelected = 9;
                    break;
            }
            // System.out.println(digSelected);
        }
        
        
        // @Override
        // public void keyTyped(KeyEvent e) {
        //     switch(e.getKeyChar()) {
        //         case '0':
        //             digInput = digInput.substring(1) + "0";
        //             digCounter--;
        //             break;
        //         case '1':
        //             digInput = digInput.substring(1) + "1";
        //             digCounter++;
        //             break;
        //         case '2':
        //             digInput = digInput.substring(1) + "2";
        //             digCounter++;
        //             break;
        //         case '3':
        //             digInput = digInput.substring(1) + "3";
        //             digCounter++;
        //             break;
        //         case '4':
        //             digInput = digInput.substring(1) + "4";
        //             digCounter++;
        //             break;
        //         case '5':
        //             digInput = digInput.substring(1) + "5";
        //             digCounter++;
        //             break;
        //         case '6':
        //             digInput = digInput.substring(1) + "6";
        //             digCounter++;
        //             break;
        //         case '7':
        //             digInput = digInput.substring(1) + "7";
        //             digCounter++;
        //             break;
        //         case '8':
        //             digInput = digInput.substring(1) + "8";
        //             digCounter++;
        //             break;
        //         case '9':
        //             digInput = digInput.substring(1) + "9";
        //             digCounter++;
        //             break;
        //     }
        //     System.out.println(digInput);
        //     System.out.println(digCounter);
        // }
        /**
         * handle key presses
         * @param e the key event triggered
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
                saveToFile();
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
                if (snapToGrid) snapToGrid = false;
                else snapToGrid = true;
                if (snapToGrid) System.out.println("Snap = on");
                else System.out.println("Snap = off");
                
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                System.out.println("Enter default variation:");
                System.out.println("1: Left Right Straight");
                System.out.println("2: Left Up Bend");
                System.out.println("3: Right Up Bend");
                System.out.println("4: Left Down Bend");
                System.out.println("5: Right Down Bend");
                System.out.println("6: Left Up Right T Junction");
                System.out.println("7: Left Down Right T Junction");
                System.out.println("8: Left Up Down T Junction");
                System.out.println("9: Up Down Right T Junction");
                System.out.println("0: Cross Junction");
                int variation = sc.nextInt();
                switch (variation) {
                    case 1:
                        roomData.addTileAt(new PlatformTile(0, Room.roomHeight - 100, Room.roomWidth, 100, 0));
                        roomData.addTileAt(new PlatformTile(0, 0, Room.roomWidth, 100, 0));

                        break;
                    case 2:
                        roomData.addTileAt(new PlatformTile(0, Room.roomHeight - 50, 500, 50, 0));
                        break;
                    case 3:
                        roomData.addTileAt(new PlatformTile(0, Room.roomHeight - 50, 500, 50, 0));
                        break;
                    case 4:
                        roomData.addTileAt(new PlatformTile(0, Room.roomHeight - 50, 500, 50, 0));
                        break;
                    case 5:
                        roomData.addTileAt(new PlatformTile(0, Room.roomHeight - 50, 500, 50, 0));
                        break;
                }
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
            if (e.getKeyCode() == KeyEvent.VK_M) {
                mPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                qPressed = true;
            } 
        }
        /**
         * handle key releases
         * @param e the key event triggered
         */
        @Override
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
            if (e.getKeyCode() == KeyEvent.VK_M) {
                mPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                qPressed = false;
            }
        }
    }

    // handle mouse movement and clicks
    class MouseMotionHandler extends MouseAdapter {
        /**
         * handle mouse movement
         * @param e the mouse event triggered
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            windowMouseX = e.getX()-8;
            windowMouseY = e.getY()-31;
        }
        /**
         * handle mouse dragging
         * @param e the mouse event triggered
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            windowMouseX = e.getX()-8;
            windowMouseY = e.getY()-31;
        }
        /**
         * handle mouse clicks
         * @param e the mouse event triggered
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            //add tile at mouse location
            if (SwingUtilities.isLeftMouseButton(e)) {
                leftMouseClicked = true;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                rightMouseClicked = true;
            }
        }
        /**
         * handle mouse presses
         * @param e the mouse event triggered
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                leftMouseHeld = true;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                rightMouseHeld = true;
            }
        }
        /**
         * handle mouse releases
         * @param e the mouse event triggered
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                leftMouseHeld = false;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                rightMouseHeld = false;
            }
        }
    }

    private class DrawingPanel extends JPanel implements ActionListener {
        DrawingPanel() {
            this.setPreferredSize(new Dimension(windowX,windowY));
            setUndecorated(true);
        }

        int[] getBoxDetails(int rectStartX, int rectEndX, int rectStartY, int rectEndY) {
            //create tile with rect coords
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;

            if (rectEndX-rectStartX>0 && rectEndY-rectStartY>0) { //++
                x = rectStartX;
                y = rectStartY;
                width = rectEndX-rectStartX;
                height = rectEndY-rectStartY;
            }
            else if (rectEndX-rectStartX>0 && rectEndY-rectStartY<0) { //+-
                x = rectStartX;
                y = rectEndY;
                width = rectEndX-rectStartX;
                height = rectStartY-rectEndY;
            }
            else if (rectEndX-rectStartX<0 && rectEndY-rectStartY<0) { //--
                x = rectEndX;
                y = rectEndY;
                width = rectStartX-rectEndX;
                height = rectStartY-rectEndY;
            }
            else if (rectEndX-rectStartX<0 && rectEndY-rectStartY>0) { //-+
                x = rectEndX;
                y = rectStartY;
                width = rectStartX-rectEndX;
                height = rectEndY-rectStartY;
            }
            int[] returnDimensions = new int[4];
            returnDimensions[0] = x;
            returnDimensions[1] = y;
            returnDimensions[2] = width;
            returnDimensions[3] = height;
            return returnDimensions;
        }

        /**
         * main loop
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (qPressed) {
                resetGame();
            }
            if (uPressed) {
                objectChosen = 1;
            }
            if (iPressed) {
                objectChosen = 2;
            }
            if (oPressed) {
                objectChosen = 3;
            }
            if (jPressed) {
                objectChosen = 4;
            }
            if (kPressed) {
                objectChosen = 5;
            }
            if (lPressed) {
                objectChosen = 99;
            }
            if (mPressed) {
                objectChosen = 98;
            }

            if (leftMouseHeld) {
                if (rectStartX < 0) { //start box corner
                    if (snapToGrid) {
                        rectStartX = windowMouseX - (windowMouseX%10);
                        rectStartY = windowMouseY - (windowMouseY%10);
                    }
                    else {
                        rectStartX = windowMouseX;
                        rectStartY = windowMouseY;
                    }
                   
                    //System.out.println(windowMouseX + " " + windowMouseY);
                } else { //end box corner
                    if (snapToGrid) {
                        rectEndX = windowMouseX - (windowMouseX%10);
                        rectEndY = windowMouseY - (windowMouseY%10);
                    }
                    else {
                        rectEndX = windowMouseX;
                        rectEndY = windowMouseY;
                    }
                    //System.out.println(windowMouseX + " " + windowMouseY);
                }
            } else { // once released mouse
                if (rectStartX >= 0 && rectEndX >= 0) { //check if box was drawn previously
                    int[] boxDimensions = new int[4];
                    boxDimensions = getBoxDetails(rectStartX, rectEndX, rectStartY, rectEndY);
                    
                    System.out.println("Enter mapColorIndex:");
                    int assignedMapColorIndex = sc.nextInt();
                    if (objectChosen == 1) {
                        roomData.addTileAt(new PlatformTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex));
                    }
                    else if (objectChosen == 2) {
                        roomData.addTileAt(new SpikeTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex));
                    }
                    else if (objectChosen == 3) {
                        System.out.println(boxDimensions[0]);
                        System.out.println(boxDimensions[1]);
                        System.out.println("Enter end pos(x)");
                        int endX = sc.nextInt();
                        System.out.println("Enter end pos(y)");
                        int endY = sc.nextInt();
                        System.out.println("Enter movespeed");
                        int mSpeed = sc.nextInt();
                        roomData.addTileAt(new MovingPlatformTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex, endX, endY, mSpeed));
                    }
                    else if (objectChosen == 4) {
                        roomData.addTileAt(new SawTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex));
                    }
                    else if (objectChosen == 5) {
                        System.out.println("Enter start delay");
                        int startDelay = sc.nextInt();
                        System.out.println("Enter duration");
                        int duration = sc.nextInt();
                        System.out.println("Enter frequency");
                        int frequency = sc.nextInt();
                        roomData.addTileAt(new LaserTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex, startDelay, duration, frequency));
                    }
                    else if (objectChosen == 98) {
                        int relicType = 0;
                        System.out.println("Enter relic type (int)");
                        relicType = sc.nextInt();
                        roomData.addTileAt(new RelicTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], relicType, assignedMapColorIndex));
                    }
                    else if (objectChosen == 99) {
                        roomData.addTileAt(new WinTile(boxDimensions[0], boxDimensions[1], boxDimensions[2], boxDimensions[3], assignedMapColorIndex));
                    }
                    
                    System.out.println(boxDimensions[0]);
                    System.out.println(boxDimensions[1]);
                    System.out.println(boxDimensions[2]);
                    System.out.println(boxDimensions[3]);
                    
                }
                rectStartX = -100;
                rectStartY = -100;
                rectEndX = -100;
                rectEndY = -100;
                
            }
            
            

            


            mouseXLabel.setText("mx:" + windowMouseX);
            mouseYLabel.setText("my:" + windowMouseY);
            if (objectChosen == 1) {
                objLabel.setText("Platform");
            }
            if (objectChosen == 2) {
                objLabel.setText("Spike");
            }
            if (objectChosen == 3) {
                objLabel.setText("Moving Platform");
            }
            if (objectChosen == 4) {
                objLabel.setText("Saw");
            }
            if (objectChosen == 5) {
                objLabel.setText("Laser");
            }
            if (objectChosen == 98) {
                objLabel.setText("Relic");
            }
            if (objectChosen == 99) {
                objLabel.setText("Win Tile");
            }
            
            //refresh graphics
            this.repaint();
            leftMouseClicked = false;
            rightMouseClicked = false;

            // tiles tick - not run due to CONCERN OF MOVING PLATFORMS

                        for (Tile tile: roomData.roomTiles) {
                                if (tile != null) {
                                    tile.tick(player, 0, 0);
                                }
                        }
                    }


        /**
         * render graphics, tiles, and mouse selection box
         * @param g the Graphics object
         */
        @Override
        public void paintComponent(Graphics g) {
            //Advanced Graphics
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            //Turn on antialiasing
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (snapToGrid) {
                g2.setColor(Color.BLACK);
                for(int i = 0; i < roomData.roomWidth; i += 10) {
                    g2.drawLine(i, 0, i, roomData.roomHeight);
                }
                for(int i = 0; i < roomData.roomHeight; i += 10) {
                    g2.drawLine(0, i, roomData.roomWidth, i);
                }
            }

            

            //Tanush Code Render Tiles
                        for (Tile tile: roomData.roomTiles) {
                            System.out.println("a");
                            Tile t = tile;
                            if (t != null) {
                                g2.setColor(Color.BLACK);
                                g2.setStroke(new BasicStroke(1));

                                if (t.assignedMapColorIndex >= 0 && t.assignedMapColorIndex<map.assignedTileColors.length && map.assignedTileColors[t.assignedMapColorIndex] != null) {    
                                    Color tileColor = map.assignedTileColors[t.assignedMapColorIndex];
                                    g2.setColor(tileColor);
                                    g2.fillRect(t.x, t.y, t.width, t.height);  
                                } else {
                                    if (t.assignedMapColorIndex == -2) { //for transparent tiles
                                        //no fill or draw
                                    } else { //assumes -1 - black
                                        g2.setColor(Color.BLACK);
                                        g2.fillRect(t.x, t.y, t.width, t.height);  
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

            //what is mouse selecting
            g2.setColor(Color.MAGENTA);
            // System.out.println(rectEndX-rectStartX);
            // System.out.println();
            // System.out.println(rectEndY-rectStartY);
            // System.out.println();
            
            if (rectStartX>=0) {
                if (rectEndX-rectStartX>0 && rectEndY-rectStartY>0) { //++
                    g2.fillRect(rectStartX, rectStartY, rectEndX-rectStartX, rectEndY-rectStartY);
                }
                else if (rectEndX-rectStartX>0 && rectEndY-rectStartY<0) { //+-
                    g2.fillRect(rectStartX, rectEndY, rectEndX-rectStartX, rectStartY-rectEndY);
                }
                else if (rectEndX-rectStartX<0 && rectEndY-rectStartY<0) { //--
                    g2.fillRect(rectEndX, rectEndY, rectStartX-rectEndX, rectStartY-rectEndY);
                }
                else if (rectEndX-rectStartX<0 && rectEndY-rectStartY>0) { //-+
                    g2.fillRect(rectEndX, rectStartY, rectStartX-rectEndX, rectEndY-rectStartY);
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
            }   
        }
    }
}  
   