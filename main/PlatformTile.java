/**
 * Platform.java
 * Child class of Tile, creates a platform that the Player can stand on.
 * Created by Tanush, Liam, Erik
 */
import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height, int assignedMapColorIndex) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = false;
        isCollidable = true;
    }

    /**
     * return all data of object in a form of an integer array
     * @return  int array that contains all the object's relevant information
     */
    public int[] returnParams() {
        int[] params = new int[6];
        params[0] = 01;
        params[1] = x;
        params[2] = y;
        params[3] = width;
        params[4] = height;
        params[5] = assignedMapColorIndex;
        return params;
    }
}
