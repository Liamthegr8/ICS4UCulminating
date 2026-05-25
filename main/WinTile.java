import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class WinTile extends Tile{
    //int spikeWidth, spikeHeight;
    double scaleFactor = 2.3;

    WinTile(int x, int y, int width, int height, int assignedMapColorIndex) {
        super(x,y, width, height, assignedMapColorIndex);
        isCollidable = true;
        killPlayer = false;
        winCondition = true;
    }

    /*@Override
    Image getScaledImage() { //UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED
        if (texture == null) {
            return null;
        }
        scaledImage = texture.getScaledInstance((int)(width*scaleFactor), (int)(height*scaleFactor), Image.SCALE_DEFAULT);
        imageXOffset = -(int)((width*scaleFactor - width)/2);
        imageYOffset = -(int)(height*scaleFactor - height);
        return scaledImage;
    }
   */

    /*
    *return all data of object in a form of integer array
    *@return    int array that contains all the object's relevant information
    **/
    public int[] returnParams() {
        int[] params = new int[6];
        params[0] = 99;
        params[1] = x;
        params[2] = y;
        params[3] = width;
        params[4] = height;
        params[5] = assignedMapColorIndex;
        return params;
    }
}

