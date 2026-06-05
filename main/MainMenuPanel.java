import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class MainMenuPanel extends JPanel implements ActionListener {
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


    MainMenuPanel(int windowWidth, int windowHeight) {
        windowX = windowWidth;
        windowY = windowHeight;
        initialSetup();

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

        buttons = new Button[3];
        buttons[0] = new Button(windowX/2, 100, 200, 50, "Play", "play");
        buttons[1] = new Button(windowX/2, 200, 200, 50, "Leaderboard", "leaderboard");
        buttons[2] = new Button(windowX/2, 300, 200, 50, "Quit", "quit");

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
                    case "play":
                        //System.out.println("Start button pressed");
                        menuOption = "play";
                        break;
                    case "quit":
                        //System.out.println("Quit button pressed");
                        System.exit(0);
                        break;
                    case "leaderboard":
                        //System.out.println("Quit button pressed");
                        menuOption = "leaderboard";
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
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.drawString("Main Menu", windowX/2, 50);


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

    // void action() {
        
    // }
}