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
        //spikeWidth = width;
        //spikeHeight = height;
        //super(x + (int) ((int)width * 0.3), y + (int) ((int)width * 0.4), (int) ((int)width * 0.4), (int) ((int)height * 0.6));
        super(x,y, width, height);
        isCollidable = true;
        killPlayer = true;
        texture = loadImage("main\\resources\\Spike.png");
    }

    @Override
    Image getScaledImage() {
        if (texture == null) {
            return null;
        }
        scaledImage = texture.getScaledInstance((int)(width*scaleFactor), (int)(height*scaleFactor), Image.SCALE_SMOOTH);
        imageXOffset = -(int)((width*scaleFactor - width)/2);
        imageYOffset = -(int)(height*scaleFactor - height);
        return scaledImage;
    }

}
