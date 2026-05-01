import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;

public class Tile {
    boolean killPlayer;
    boolean isCollidable;
    //static final int tileSize = 50;
    int x,y,width,height;
    BufferedImage texture;
    Image scaledImage;
    URL url;
    int imageXOffset, imageYOffset;

    Tile(int x, int y,int width,int height, String customTexturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        killPlayer = false;
        isCollidable = false;
        imageXOffset = 0;
        imageYOffset = 0;

        if (customTexturePath != null) {
            texture = loadImage(customTexturePath);
            getScaledImage();
        }
    }

    Tile(Tile tileData) { //this is specifically for getsurroundingtiles collisions
        this.x = tileData.x;
        this.y = tileData.y;
        this.width = tileData.width;
        this.height = tileData.height;
        this.killPlayer = tileData.killPlayer;
        this.isCollidable = tileData.isCollidable;
    }

    BufferedImage loadImage(String filename) {
        url = this.getClass().getResource("/assets/" + filename);
        BufferedImage img = null;
       if (url != null) {
	try {
		img = ImageIO.read(url);
	} catch (IOException e) {
		e.printStackTrace();
	}
} else {
	JOptionPane.showMessageDialog(null, "An image failed to load: " + filename , "ERROR", JOptionPane.ERROR_MESSAGE);
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
        scaledImage = texture.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return scaledImage;
    }

    void tick(Player player) {}

    void copyTile() {}

    void importTile() {}
}

