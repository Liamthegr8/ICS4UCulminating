/**
 * GameWindow.java
 * Handles most of the game activities, such as player input, timing, and level creation.
 * Created by Tanush, Liam, Erik
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.*;

    class GamePanel extends JPanel implements ActionListener {
        static int windowX; //not accurate anymore, uses window ref now
        static int windowY;
        int tickDelay = 10;
        int camX, camY;
        double camTransitionSpeed;
        Timer tick;
        Player player;
        Map map;
        boolean wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed;
        boolean qPressed; //testing buttons - may be removed better
        boolean antiHoldDash =true;
        boolean antiHoldJump =true;
        boolean panelActive;
        boolean inMenu;
        boolean pauseMenuActive = false;
        double[] tempLeaderboard;
        int[][] dirMap;

        //debugging (labels for various statistics)
        int windowMouseX = 0;
        int windowMouseY = 0;
        int runtime = 0;
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

        GamePanel(int windowWidth, int windowHeight, double[] leaderboard) {
            windowX = windowWidth;
            windowY = windowHeight;
            tempLeaderboard = leaderboard;
            //this.setPreferredSize(new Dimension(windowX,windowY));
            
            // this.setSize(windowX,windowY); //IGNORED DUE TO JPANELPACK

            //debugging elements
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
            // // this.add(windowXLabel);
            // // this.add(windowYLabel);
            // this.add(playerXLabel);
            // this.add(playerYLabel);
            // this.add(playervxLabel);
            // this.add(playervyLabel);
            // this.add(isPlayerWalledRLabel);
            // this.add(isPlayerWalledLLabel);
            // this.add(isPlayerGroundedLabel);
            // this.add(playerLocLabel);
            
            gameSetup();
        }

        /**
         *setup initial game (first time window runs)
        */
        private void gameSetup() {
            // panelActive = true;
            //Set up timer
            tick = new Timer(tickDelay, this);
            // tick.setInitialDelay(1000);
            tick.start();

            //Events
            this.addKeyListener(new KeyHandler());
            this.addMouseMotionListener(new MouseMotionHandler());
            //this.addMouseListener(new MouseMotionHandler());
            this.setBackground(Color.BLACK);

        }

        void addToLeaderboard(double score) {
            readLeaderboard();

            for (int i=0; i<tempLeaderboard.length; i++) {
                if (score < tempLeaderboard[i]) {
                    for (int j=tempLeaderboard.length-1; j>i; j--) {
                        tempLeaderboard[j] = tempLeaderboard[j-1];
                    }
                    tempLeaderboard[i] = score;
                    break;
                }
            }
            saveLeaderboard();
            readLeaderboard(); //this line for debug
        }

        void readLeaderboard() {
            double[] leaderboardData = new double[tempLeaderboard.length];
            //setup file reader
            File leaderboardFile = new File("main\\assets\\leaderboard.txt");
            FileReader in;
            BufferedReader readFile;
            String lineOfText;

            try {
                in = new FileReader(leaderboardFile);
                readFile = new BufferedReader(in);

                for (int i=0; i<leaderboardData.length; i++) {
                    lineOfText = readFile.readLine();
                    if (lineOfText != null) {
                        leaderboardData[i] = Double.parseDouble(lineOfText);
                        System.out.println("Read from file: " + lineOfText);
                    } else {
                        leaderboardData[i] = 100000; //default value if file has less entries than expected
                    }
                }
                // while ((lineOfText = readFile.readLine()) != null){
                //     System.out.println(lineOfText);
                // }
                readFile.close();
                in.close();
                tempLeaderboard = leaderboardData; //doesent update game leaderboard for some reason!!!

            } catch (FileNotFoundException e) {
                System.out.println("File does not exist or could not be found.");
                System.err.println("FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Problem reading file.");
                System.err.println("IOException: " + e.getMessage());
            }
        }

        void clearFile(String filePath) {
                // Instantiating with append=false (or leaving it blank) wipes the file
            try (FileWriter fw = new FileWriter(filePath, false)) {
                // No need to write anything; opening it already cleared the file
                System.out.println("File contents successfully deleted.");
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
        

        void saveLeaderboard() {
            //uses tempLeaderboard data
            File leaderboardFile = new File("main\\assets\\leaderboard.txt");
            FileWriter out;
            BufferedWriter writeFile;
            clearFile("main\\assets\\leaderboard.txt");
            try {
                out = new FileWriter(leaderboardFile);
                writeFile = new BufferedWriter(out);
                for (int i=0; i<tempLeaderboard.length; i++) {
                    String data = String.valueOf(tempLeaderboard[i]);
                    writeFile.write(data);
                    writeFile.newLine();
                }

                writeFile.close();
                out.close();
                System.out.println("Data written to file.");
            } catch (IOException e) {
                System.out.println("Problem reading file.");
                System.err.println("IOException: " + e.getMessage());
            }
        }

        /**
         *resets main game variables upon e.g. death or by command, also allows for map to regenerate
        */
        void resetGame() {
            boolean testingMap = false;
            wPressed = false;
            aPressed = false;
            sPressed = false;
            dPressed = false;
            uPressed = false;
            iPressed = false;
            oPressed = false;
            jPressed = false;
            kPressed = false;
            lPressed = false;

            tick.restart();
            runtime = 0;
            tick.start();

            panelActive = true;
            inMenu = false;
            camX = 0;
            camY = 0;
            camTransitionSpeed = 0.1;

            //player = new Player(0,0,18,30);
            if(testingMap){
            player = new Player(0,500,18,18); //resets to constructors
            }else{
             //player = new Player(1200*9 + 600,500*10+250,18,30);
             player = new Player(11400 ,8670,18,18);
            }
            //Tanush Mock Collision Setup
            map = new Map();
            map.setMapTileColor(0, Color.BLUE);
            map.setMapTileColor(1, Color.RED);

            FileHandle x = new FileHandle();
            DirectionKeyStructure directionMap = new DirectionKeyStructure();
            directionMap = x.findDirectionStructure();
            if(testingMap){
            Room algTest = x.findRoom(directionMap.generate(7,0));
            Room room1 = x.findRoom("RoomTest1");
            //Room room2 = new Room("Room2");
            Room room2 = x.findRoom("middleCrossPillar");
            Room room3= x.findRoom("RoomTest3");
            Room room4= x.findRoom("RoomTest4");
            Room hole= x.findRoom("FloorOpenTest");
            Room Runway= x.findRoom("Runway");
            Room winning = x.findRoom("Win");
            Room test = x.findRoom("name");
            Room straight =x.findRoom("movingCross");

            //create room to test
            //room2.setEnterRoomTransitionColor(0, Color.RED);
            //room2.setEnterRoomTransitionColor(1, Color.GRAY); 
            //room2.addTileAt(new SpikeTile(0,450,500,49,0));
            
            //room2.addTileAt(new MovingPlatformTile(100, 400,  100, 50, 1, 200, 400, 1));

            //room1.addTileAt(new MovingPlatformTile(250, -150, 400, -300, 100, 50, 1));
            map.addRoomAt(test, 0, 0);
            map.addRoomAt(algTest, 1, 0);
            map.addRoomAt(room1, 0, 1);
            map.addRoomAt(room2, 1, 1);
            map.addRoomAt(room3, 2, 1);
            map.addRoomAt(room4, 3, 1);
            map.addRoomAt(room3, 4, 1);
            map.addRoomAt(room3, 5, 1);
            map.addRoomAt(hole, 3, 0);
            map.addRoomAt(Runway, 4, 0);
            map.addRoomAt(Runway, 5, 0);
            map.addRoomAt(Runway, 2, 0);
            map.addRoomAt(winning,6,1);
            map.addRoomAt(straight, 0, 2);
            map.addRoomAt(straight, 1, 3);
            map.addRoomAt(straight, 2, 4);
            map.addRoomAt(straight, 3, 5);
            map.addRoomAt(straight,4, 6);
            map.addRoomAt(straight,5, 7);
            map.addRoomAt(straight,6, 8);
            map.addRoomAt(straight, 7, 9);
            map.addRoomAt(straight, 8, 10);
            map.addRoomAt(straight, 9, 10);
            winning.addTileAt(new RelicTile(200,250,50,50,1,1));
            }
            else {
                Algorithim alg = new Algorithim();
                for(int i =0; i<alg.directionMap.length; i++){
                     for (int j =0; j<alg.directionMap[i].length; j++){
                        map.addRoomAt(x.findRoom(directionMap.generate(alg.directionMap[i][j],alg.roomTypeMap[i][j])),j,i);
                     }
                }
                dirMap = new int[15][19];
                dirMap = alg.directionMap;
                Room straightSpawn =x.findRoom("straightSpawn");
                map.addRoomAt(straightSpawn, 9, 10);
                // Room test =x.findRoom("ElevatorDodgeLeftUp");
                // map.addRoomAt(test, 10, 10);
                // Room test2 = x.findRoom("LaserWallClimbRightDown");
                // map.addRoomAt(test2, 8, 10);
         }
        
        }

        // handle keyboard input
        private class KeyHandler extends KeyAdapter {
            /**
             * handle key presses
             * @param e the key event triggered
             */
            @Override
            public void keyPressed(KeyEvent e) {
                //no panel repaint here. otherwise the graphics will update when key pressed instantly. not upon fixed tick delay
                //if repainted, this can be exploited to speed up time
                //Checks if any of the viable buttons are pressed
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    wPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    aPressed = true;
                    //this needs to be here as it needs to be able to be updated even if a or d is being held so it always updates to most recently pressed
                    player.directionFaced=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    sPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    dPressed = true;
                    //this needs to be here as it needs to be able to be updated even if a or d is being held so it always updates to most recently pressed
                    player.directionFaced=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    if (antiHoldJump){
                    uPressed = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    if(antiHoldDash){
                        iPressed = true;
                    }
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
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    if (!player.abilities[0]) {
                        player.abilities[0] = true;
                        player.abilities[1] = true;
                        player.abilities[2] = true;
                        player.abilities[3] = true;
                        player.abilities[4] = true;
                    }
                    else {
                        player.abilities[0] = false;
                        player.abilities[1] = false;
                        player.abilities[2] = false;
                        player.abilities[3] = false;
                        player.abilities[4] = false;
                    }
                
                }
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    if(!player.godMode){
                    player.godMode =true;}
                    else player.godMode=false;
                }
            }
            /**
             * handle key releases
             * @param e the key event triggered
             */
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
                    antiHoldJump = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    iPressed = false;
                    antiHoldDash = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    oPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    jPressed = false;
                    antiHoldDash = true;
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
        }

        /**
         * game's main gameloop and timer
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            runtime++;
            //ticks down cooldowns
            //nogravity
            
            //player movements inputs
            //can turn into inputActions fuction
            if (uPressed) {
                player.bufferTime = 10;
                antiHoldJump = false;
                uPressed = false;
            }
            if (aPressed) {
                if(player.isGrounded){
                    player.updateVelocity(-10*player.friction, 0);
                }else{
                    player.updateVelocity(-10*player.airFriction, 0);
                }
                
                
            }
            if (sPressed) {
                player.fastFall = true;
            }
            else {
                player.fastFall = false;
            }
            if (qPressed) {
                resetGame();
            }
            if (lPressed) {
                pauseMenuActive = true;
                panelActive = false;
                lPressed = false;
            }
            if (dPressed) {
                if(player.isGrounded){
                    player.updateVelocity(10*player.friction, 0);
                }else{
                    player.updateVelocity(10*player.airFriction, 0);
                }
            }
            if (iPressed) {
                player.dash(wPressed, aPressed, sPressed, dPressed);
                iPressed=false;
                antiHoldDash = false;
            }
            if (jPressed) {
                player.dashPastWall(wPressed, aPressed, sPressed, dPressed);
                jPressed = false;
                antiHoldDash = false;
            }
            if (kPressed) {
                player.gravityShift();
                kPressed = false;
             }
            if (oPressed) {
                player.floating = true;
             }
            else {  
                player.floating = false;
            }

            //insert death on out of bounds below: (future)
            
            //tiles tick
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        for (Tile tile: r.roomTiles) {
                                if (tile != null) {
                                    tile.tick(player, i, j);
                                }
                        }
                    }
                }
            }

            //player tick
            player.tick(map);
            //System.out.printf("Rels: %b,%b,%b,%b,%b", player.abilities[0],player.abilities[1],player.abilities[2],player.abilities[3],player.abilities[4]);
            //System.out.println();
            //check death
            if (player.isDead) {
                //resetGame();
                addToLeaderboard(runtime);
                panelActive = false;
            }

            //room tick
            for (int i=0; i<map.mapRooms.length; i++) {
                for (int j=0; j<map.mapRooms.length; j++) {
                    Room r = map.mapRooms[i][j];
                    if (r != null) {
                        r.tick(map, player, i, j);
                    }
                }
            }

            updateCamera();      

            //debugging
            // windowXLabel.setText("Window MouseX: " + String.valueOf(windowMouseX));
            // windowYLabel.setText("Window MouseY: " + String.valueOf(windowMouseY));
            // playerXLabel.setText("Player x: " + String.valueOf(player.x));
            // playerYLabel.setText("Player y: " + String.valueOf(player.y));
            // String roundedvx = String.format("%.1f", player.vx);
            // String roundedvy = String.format("%.1f", player.vy);
            // playervxLabel.setText("Player vx: " + roundedvx);
            // playervyLabel.setText("Player vy: " + roundedvy);
            // //isPlayerGroundedLabel.setText("Player Grounded: " + String.valueOf(player.isGrounded));
            // isPlayerGroundedLabel.setText("runtime: " + String.valueOf(runtime));
            // isPlayerWalledRLabel.setText("Player R Walled: " + String.valueOf(player.isTouchingRightWall));
            // isPlayerWalledLLabel.setText("Player L Walled: " + String.valueOf(player.isTouchingLeftWall));
            // if (player.playerLocation != null) {
            //     playerLocLabel.setText("Location:" + "[" + player.playerLocation[0] + " , " + player.playerLocation[1] + "]");
            // } else {
            //     playerLocLabel.setText("Location: null");
            // }
            
            //refresh graphics
            this.repaint();	
        }

        /**
         *update camera variable to track player, non dynamic fixed transitions
         */
        void updateCamera() {
            //based on window vars
            int targetCamX = -player.x + (windowX/2) - (player.width/2);
            int targetCamY = -player.y + (windowY/2) - (player.height/2);
            camX += (targetCamX - camX) * camTransitionSpeed;
            camY += (targetCamY - camY) * camTransitionSpeed;
        }

        /**
         * render graphics, tiles, and mouse selection box
         * @param g the Graphics object
         */
        @Override
        public void paintComponent(Graphics g) {
            //Advanced Graphics
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Turn on antialiasing
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            

            

            //Tanush Code Render Tiles
            // for (int i=0; i<map.mapRooms.length; i++) {
            //     for (int j=0; j<map.mapRooms.length; j++) {
            //         Room r = map.mapRooms[i][j];
            //         if (r != null) {
            //             for (Tile tile: player.drawnTiles) {
            //                     Tile t = tile;
            //                     if (t != null) {
            //                         g2.setColor(Color.BLACK);
            //                         g2.setStroke(new BasicStroke(1));

            //                         if (!(t instanceof SpikeTile) && t.assignedMapColorIndex >= 0 && t.assignedMapColorIndex<map.assignedTileColors.length && map.assignedTileColors[t.assignedMapColorIndex] != null) {    
            //                             Color tileColor = map.assignedTileColors[t.assignedMapColorIndex];
            //                             g2.setColor(tileColor);
            //                             g2.fillRect(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY), t.width, t.height);
                                          
            //                         } else {
            //                             if (t instanceof SpikeTile) { //for SPIKES tiles
            //                                 Color tileColor = map.assignedTileColors[t.assignedMapColorIndex];
            //                                 g2.setColor(tileColor);
            //                                 Polygon triangle = new Polygon();
            //                                 triangle.addPoint(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY) + t.height);
            //                                 triangle.addPoint(i*Room.roomWidth + (t.x+camX) + t.width, j*Room.roomHeight + (t.y+camY) + t.height);
            //                                 triangle.addPoint(i*Room.roomWidth + (t.x+camX) + t.width/2, j*Room.roomHeight + (t.y+camY));
            //                                 g2.fillPolygon(triangle); 
            //                             } else { //assumes black
            //                                 g2.setColor(Color.BLACK);
            //                                 g2.fillRect(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY), t.width, t.height); 
            //                             }
            //                         }
                                    
                                    
                                    
            //                         //Images
            //                         // if (t.getScaledImage() == null) {
            //                         //     g2.drawRect(i*Room.roomWidth + (t.x+camX), j*Room.roomHeight + (t.y+camY), t.width, t.height);
            //                         // }
            //                         // else {
            //                         // g2.drawImage(t.scaledImage, i*Room.roomWidth + t.x + t.imageXOffset + camX, j*Room.roomHeight + t.y + t.imageYOffset + camY, null);
            //                         // }
            //                     }
            //             }
            //         }
            //     }
            // }

            for (Tile tile: player.drawnTiles) {
                                if (tile != null) {
                                    g2.setColor(Color.BLACK);
                                    g2.setStroke(new BasicStroke(1));
 
                                        if (tile.tileID == 02) { //for SPIKES tiles
                                            Color tileColor = map.assignedTileColors[tile.assignedMapColorIndex];
                                            g2.setColor(tileColor);
                                            Polygon triangle = new Polygon();
                                            triangle.addPoint((tile.x+camX), (tile.y+camY) + tile.height);
                                            triangle.addPoint((tile.x+camX) + tile.width, (tile.y+camY) + tile.height);
                                            triangle.addPoint((tile.x+camX) + tile.width/2, (tile.y+camY));
                                            g2.fillPolygon(triangle); 
                                        } 
                                        else if (tile.tileID == 05) {
                                            if (tile.enabled) {
                                                Color tileColor = map.assignedTileColors[tile.assignedMapColorIndex];
                                                g2.setColor(tileColor);
                                                g2.fillRect((tile.x+camX), (tile.y+camY), tile.width, tile.height);
                                            }
                                        } else if (tile.tileID == 98) { //RELICS render
                                            g2.drawImage(tile.texture, (tile.x+camX), (tile.y+camY), tile.width, tile.height,  null);
                                                // g2.setColor(tileColor);
                                                // g2.fillRect((tile.x+camX), (tile.y+camY), tile.width, tile.height);
                                            
                                        }
                                        else if (tile.assignedMapColorIndex >= 0 && tile.assignedMapColorIndex<map.assignedTileColors.length && map.assignedTileColors[tile.assignedMapColorIndex] != null) {    
                                            Color tileColor = map.assignedTileColors[tile.assignedMapColorIndex];
                                            g2.setColor(tileColor);
                                            g2.fillRect((tile.x+camX), (tile.y+camY), tile.width, tile.height);
                                        }
                                        else if (!(tile.tileID == 02) && !(tile.tileID == 05) && tile.assignedMapColorIndex >= 0 && tile.assignedMapColorIndex<map.assignedTileColors.length && map.assignedTileColors[tile.assignedMapColorIndex] != null) {    
                                        Color tileColor = map.assignedTileColors[tile.assignedMapColorIndex];
                                        g2.setColor(tileColor);
                                        g2.fillRect((tile.x+camX), (tile.y+camY), tile.width, tile.height);
                                          
                                        }
                                        else { //assumes black
                                            g2.setColor(Color.WHITE);
                                            g2.fillRect((tile.x+camX), (tile.y+camY), tile.width, tile.height); 
                                        }
                                    }
                                }

            //DEBUG
            //Render Map bounds
            // for (int i=0; i<map.mapRooms.length; i++) {
            //     for (int j=0; j<map.mapRooms.length; j++) {
            //         Room r = map.mapRooms[i][j];
            //         if (r != null) {
            //             g2.setColor(Color.GRAY);
            //             g2.setStroke(new BasicStroke(5));
            //             g2.drawRect(i*Room.roomWidth + camX, j*Room.roomHeight + camY, Room.roomWidth, Room.roomHeight);
            //         }
            //     }
            // }
            //Render surroundingTiles
            // ArrayList<Tile> tiles = player.surroundingTiles;
            // for (Tile tile: tiles) {
            //     Tile t = tile;
            //     if (t != null) {
            //         g2.setColor(Color.BLUE);
            //         //g2.fillRect(t.x+camX, t.y+camY, t.width, t.height);
            //         g2.setColor(Color.RED);
            //         g2.setStroke(new BasicStroke(3));
            //         g2.drawRect(t.x+camX, t.y+camY, t.width, t.height);
            //     }
            // }

            //Render currentRoom
            // if (player.playerLocation != null) {
            //     g2.setColor(Color.CYAN);
            //     g2.setStroke(new BasicStroke(3));
            //     g2.drawRect(player.playerLocation[0]*Room.roomWidth + camX, player.playerLocation[1]*Room.roomHeight + camY, Room.roomWidth, Room.roomHeight);
            // }

            //Player
            /* 
            if (!player.canControl){
                g2.setColor(Color.blue);
            } else if (player.isTouchingRightWall) {
                g2.setColor(Color.YELLOW);
            } else if (player.isTouchingLeftWall) {
                g2.setColor(Color.ORANGE);
            } else if (player.isGrounded) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.RED);
            }
                */
                if (player.isDash){
                g2.setColor(Color.blue);
            }else if(player.canDash){
                g2.setColor(Color.red);
            }else if(!player.canDash){
                g2.setColor(Color.cyan);
            }
            g2.fillOval(player.x+camX, player.y+camY, player.width, player.height);

            //healthbar
            for (int i = 0; i < player.playerHealth; i += 25) {
                g2.setColor(Color.RED);
                g2.fillOval(i+20, 25, 20, 20);
                g2.setColor(Color.BLACK);
                g2.drawOval(i+20, 25, 20, 20);
            }
        }
    }

//}
