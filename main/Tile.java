import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class Tile {
    boolean killPlayer;
    boolean isCollidable;
    //static final int tileSize = 50;
    int x,y,width,height;
    BufferedImage texture;
    Image scaledImage;
    int imageXOffset, imageYOffset;

    Tile(int x, int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        killPlayer = false;
        isCollidable = false;
        imageXOffset = 0;
        imageYOffset = 0;
    }

    Tile(Tile tileData) { //this is specifically for getsurroundingtiles collisions
        this.x = tileData.x;
        this.y = tileData.y;
        this.width = tileData.width;
        this.height = tileData.height;
        this.killPlayer = tileData.killPlayer;
        this.isCollidable = tileData.isCollidable;
    }

    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
        //DEBUG
        //if (img == null) System.out.println("null");
        //else System.out.printf("w=%d, h=%d%n",img.getWidth(), img.getHeight());
        return img;
    }

    Image getScaledImage() {
        if (texture == null) {
            return null;
        }
        scaledImage = texture.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    void tick(Player player) {}

    void copyTile() {}

    void importTile() {}
}

