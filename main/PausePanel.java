import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PausePanel extends JPanel implements ActionListener {
    int windowX = 900;
    int windowY = 500;
    boolean startGame;
    String menuOption;
    //boolean activeMenu;

    Timer tick;
    int tickDelay = 10;
    boolean wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed;
    //JLabel titleLabel;
    Button[] buttons;
    int selectedButton;
    boolean selectButton;
    Map map;
    int[] playerLocation;
    int[][] dirMap;


    PausePanel(int windowWidth, int windowHeight, Map map) {
        windowX = windowWidth;
        windowY = windowHeight;
        initialSetup();
        this.map = map;

    }

    void initialSetup() {
        menuOption = "";
        startGame = false;
        selectedButton = 0;
        selectButton = false;
        //activeMenu = true;

        //this.setPreferredSize(new Dimension(windowX,windowY));

        tick = new Timer(tickDelay, this);
        tick.start();
        addKeyListener(new KeyHandler());

        buttons = new Button[2];
        buttons[0] = new Button((windowX/3)*2-100, 200, 200, 50, "Resume", "resume");
        buttons[1] = new Button((windowX/3)*2-100, 300, 200, 50, "Main Menu", "menu");
    }

    void reset() {
        tick.restart();
        tick.start();
        
        startGame = false;
        menuOption = "";
        selectedButton = 0;
        selectButton = false;
        //activeMenu = true;

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
    }

    private class KeyHandler extends KeyAdapter {
            /**
             * handle key presses
             * @param e the key event triggered
             */
            // @Override
            // public void keyTyped(KeyEvent e) {
            //     if (e.getKeyCode() == KeyEvent.VK_W) {
            //         wPressed = true;
            //         System.out.println("W pressed");
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_A) {
            //         aPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_S) {
            //         sPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_D) {
            //         dPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_U) {
            //         uPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_I) {
            //         iPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_O) {
            //         oPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_J) {
            //         jPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_K) {
            //         kPressed = true;
            //     }
            //     if (e.getKeyCode() == KeyEvent.VK_L) {
            //         lPressed = true;
            //     }
            // }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S && !sPressed) {
                sPressed = true;
                selectedButton++;
            }
            if (e.getKeyCode() == KeyEvent.VK_W && !wPressed) {
                wPressed = true;
                selectedButton--;
            }
            if (e.getKeyCode() == KeyEvent.VK_U && !uPressed) {
                uPressed = true;
                selectButton = true;
            }
            }
            /**
             * handle key releases
             * @param e the key event triggered
             */
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) wPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_S) sPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_U) uPressed = false;
            }
        }

    /**
     * game's main gameloop and timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (uPressed) {
            selectButton = true;
        }

        if (selectedButton < 0) {
            selectedButton = buttons.length - 1;
        }
        if (selectedButton >= buttons.length) {
            selectedButton = 0;
        }

        for (int i = 0; i < buttons.length; i++) {
            if (selectButton && i == selectedButton) {
                selectButton = false;
                // buttons[i].action();
                switch (buttons[i].action) {
                    case "resume":
                        //System.out.println("Start button pressed");
                        menuOption = "resume";
                        break;
                    case "menu":
                        //System.out.println("Main Menu button pressed");
                        menuOption = "menu";
                        break;
                    default:
                        //System.out.println("No action designated to button");
                        break;
                }
                //activeMenu = false;
                
            }
        }
        
        // wPressed = false;
        // aPressed = false;
        // sPressed = false;
        // dPressed = false;
        // uPressed = false;
        // iPressed = false;
        // oPressed = false;
        // jPressed = false;
        // kPressed = false;
        // lPressed = false;


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

        //title text render
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        g2.drawString("Paused", (windowX/2)-100, 150);


        //button render
        for (Button button : buttons) {
            if (button != null) {
                g2.setColor(Color.GRAY);
                g2.fill(button);
                g2.setColor(Color.BLACK);
                g2.draw(button);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int textX = button.x + (button.width - fm.stringWidth(button.text)) / 2;
                int textY = button.y + ((button.height - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(button.text, textX, textY);

                if (button == buttons[selectedButton]) {
                    g2.setColor(Color.YELLOW);
                    g2.setStroke(new BasicStroke(3));
                    g2.draw(button);
                }
            }
        }

        if (map != null && playerLocation != null && dirMap != null) {
            for (int j = 0; j < dirMap.length; j++) {
            for (int k = 0; k < dirMap[j].length; k++) {
                
                if (j == playerLocation[1] && k == playerLocation[0]) {
                    g2.setColor(Color.YELLOW);
                }
                else {
                    g2.setColor(Color.BLACK);
                }
                if (dirMap[j][k] == 12 || dirMap[j][k] == 13 || dirMap[j][k] == 14 || dirMap[j][k] == 15) {
                    g2.setColor(Color.BLUE);
                    if (j == playerLocation[1] && k == playerLocation[0]) {
                    g2.setColor(Color.YELLOW);
                }
                }
                if (map.mapRooms[j][k] != null) {
                    if (dirMap[j][k] != 0) {
                        g2.fillRoundRect(100 + k*40, 150 + j*40, 40, 40, 16, 16);
                    }
                }
            }
        }
        }

    }
}

class Button extends Rectangle {
    String text;
    String action;
    Button(int x, int y, int width, int height, String text, String action) {
        super(x, y, width, height);
        this.text = text;
        this.action = action;
    }

    // void action() {
        
    // }
}