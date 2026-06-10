import java.awt.*;
import javax.swing.*;
import java.io.*;
public class RelicTile extends Tile{

    


    RelicTile(int x, int y, int width, int height, int ability, int assignedMapColorIndex) {
        super(x, y, width, height, assignedMapColorIndex);
        tileID = 98;    
        killPlayer = false;
        isCollidable = true;
        this.ability = ability;
        switch (ability) {
            case 1:
                ability1 = true;
                texture = loadImage("parachute.png");
                break;
            case 2:
                ability2 = true;
                texture = loadImage("parachute.png");
                break;
            case 3:
                ability3 = true;
                texture = loadImage("parachute.png");
                break;
            case 4:
                ability4 = true;
                texture = loadImage("parachute.png");
                break;
            case 5:
                ability5 = true;
                texture = loadImage("parachute.png");
                break;
            default:
                System.out.println("Invalid ability for relic tile: " + ability);
                break;
        }
    }

    /**
     * return all data of object in a form of an integer array
     * @return  int array that contains all the object's relevant information
     */
    public int[] returnParams() {
        int[] params = new int[7];
        params[0] = 98;
        params[1] = x;
        params[2] = y;
        params[3] = width;
        params[4] = height;
        params[5] = ability; //added by eric
        params[6] = assignedMapColorIndex;
        return params;
    }
}
