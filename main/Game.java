import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class Game {
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
			public void run() {
                //ANONYMOUS
				System.out.println("Run GameWindow");

                new GameWindow();
                
			}
		});
    } 
}
