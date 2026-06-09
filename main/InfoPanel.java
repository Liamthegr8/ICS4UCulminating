import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class InfoPanel extends JPanel implements ActionListener {
    int windowX;
    int windowY;
    boolean panelActive;

    Timer tick;
    int tickDelay = 10;
    boolean wPressed, aPressed, sPressed, dPressed, uPressed, iPressed, oPressed, jPressed, kPressed, lPressed;
    Button[] buttons;
    int selectedButton;
    boolean selectButton;


    InfoPanel(int windowWidth, int windowHeight) {
        windowX = windowWidth;
        windowY = windowHeight;
        initialSetup();
    }

    void initialSetup() {
        selectedButton = 0;
        selectButton = false;
        panelActive = false;

        //this.setPreferredSize(new Dimension(windowX,windowY));

        tick = new Timer(tickDelay, this);
        tick.start();
        addKeyListener(new KeyHandler());

        buttons = new Button[1];
        // buttons[0] = new Button(windowX/2, 100, 200, 50, "Play", "play");
        // buttons[1] = new Button(windowX/2, 200, 200, 50, "Leaderboard", "leaderboard");
        buttons[0] = new Button((windowX/2)-100, 900, 200, 50, "Back to menu", "menu");

    }

    void reset() {
        tick.restart();
        tick.start();
    
        selectedButton = 0;
        selectButton = false;
        panelActive = true;

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
                    case "menu":
                        //System.out.println("Start button pressed");
                        panelActive = false;
                        break;
                    default:
                        //System.out.println("No action designated to button");
                        break;
                }
                
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
        g2.setFont(new Font("Arial", Font.BOLD, 70));
        g2.drawString("How to Play", windowX/2-170, 100);

        //controls
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("Controls", (windowX/2), 250);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("WASD to move and navigate menu", (windowX/2), 300);
        g2.drawString("U to Jump/Select", (windowX/2), 350);
        g2.drawString("I to Dash", (windowX/2), 400);
        g2.drawString("J to Dash through walls", (windowX/2), 450);
        g2.drawString("L to Pause Game", (windowX/2), 500);
        g2.drawString("K to Gravity Flip", (windowX/2), 550);
        g2.drawString("O to Parachute", (windowX/2), 600);

        
        // g2.drawString("WASD to move", (windowX/2), 450);
        // g2.drawString("U to Jump", (windowX/2), 550);
        // g2.drawString("I to Dash", (windowX/2), 650);
        // g2.drawString("J to Dash through walls", (windowX/2), 750);


        //rules scheme
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("Rules", (windowX/2)-800, 250);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("You have 4 lives. Your goal is to collect a set", (windowX/2)-800, 300);
        g2.drawString("of relics that unlocks special abilities that help", (windowX/2)-800, 325);
        g2.drawString("you navigate through the course. To win the game,", (windowX/2)-800, 350);
        g2.drawString("you aim to reach the end game room in the", (windowX/2)-800, 375);
        g2.drawString("lowest amount of time possible, which is at", (windowX/2)-800, 400);
        g2.drawString("the top of the map.", (windowX/2)-800, 425);
        g2.drawString("GOOD LUCK!", (windowX/2)-800, 500);


        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));


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
}