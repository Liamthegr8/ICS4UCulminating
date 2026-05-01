import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class SpikeTile extends Tile{
    //int spikeWidth, spikeHeight;
    double scaleFactor = 2.3;

    SpikeTile(int x, int y, int width, int height) {
        super(x,y, width, height, -2);
        isCollidable = true;
        killPlayer = true;
    }

    @Override
    Image getScaledImage() { //UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED
        if (texture == null) {
            return null;
        }
        scaledImage = texture.getScaledInstance((int)(width*scaleFactor), (int)(height*scaleFactor), Image.SCALE_DEFAULT);
        imageXOffset = -(int)((width*scaleFactor - width)/2);
        imageYOffset = -(int)(height*scaleFactor - height);
        return scaledImage;
    }

}
