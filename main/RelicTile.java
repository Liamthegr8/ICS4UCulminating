import java.awt.*;
import javax.swing.*;
import java.io.*;
public class RelicTile extends Tile{

    int ability;


    RelicTile(int x, int y, int width, int height, int ability, int assignedMapColorIndex) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = false;
        isCollidable = true;
        this.ability = ability;
        gainAbility = true;
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
