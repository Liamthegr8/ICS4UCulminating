/**
 * Tile.java
 * Parent abstract class responsible for giving all tile objects common properties, such as size, position, and effects on player.
 * Created by Tanush, Liam, Erik
 */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Random;

public abstract class Tile {
    boolean killPlayer;
    boolean isCollidable;
    boolean winCondition;
    boolean ability1, ability2, ability3, ability4, ability5;
    boolean enabled;
    boolean overideDisabledCollisions;
    //static final int tileSize = 50;
    int x,y,width,height;
    BufferedImage texture;
    Image scaledImage;
    URL url;
    int imageXOffset, imageYOffset;
    int startDelay, duration, frequency;
    int assignedMapColorIndex;
    int tileID;
    int ability;

    Tile(int x, int y,int width,int height, int assignedMapColorIndex) { //current tileID randomized, only manually set in 2nd contsructor
        Random r = new Random();
        this.tileID = r.nextInt(10000);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        killPlayer = false;
        isCollidable = false;
        winCondition = false;
        ability1 = false;
        ability2 = false;
        ability3 = false;
        ability4 = false;
        ability5 = false;
        enabled = true;
        imageXOffset = 0;
        imageYOffset = 0;
        this.assignedMapColorIndex = assignedMapColorIndex;
        overideDisabledCollisions = false;
        
        // if (customTexturePath != null) {
        //     texture = loadImage(customTexturePath);
        //     getScaledImage();
        // }
        winCondition=false;
    }

    Tile(Tile tileData) { //this is method is used for getsurroundingtiles collisions
        this.tileID = tileData.tileID;
        this.x = tileData.x; //could be real or relative
        this.y = tileData.y; //could be real or relative
        this.width = tileData.width;
        this.height = tileData.height;
        this.killPlayer = tileData.killPlayer;
        this.isCollidable = tileData.isCollidable;
        this.winCondition = tileData.winCondition;
        //new
        this.ability1 = tileData.ability1;
        this.ability2 = tileData.ability2;
        this.ability3 = tileData.ability3;
        this.ability4 = tileData.ability4;
        this.ability5 = tileData.ability5;
        this.enabled = tileData.enabled;
        this.startDelay = tileData.startDelay;
        this.duration = tileData.duration;
        this.frequency = tileData.frequency;
        this.overideDisabledCollisions = tileData.overideDisabledCollisions;
        // this.winCondition = tileData.winCondition;
    }

    /**
     * load image from give file path and return found image
     * @param filename  the name of the file with its relative location to main on pc
     * @return  the loaded image
     */
    BufferedImage loadImage(String filename) { //UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED
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

    /**
     * scale image to specific size based on object's parameters(variables)
     * @return  the scaled image
     */
    Image getScaledImage() { //UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED UNUSED
        if (texture == null) {
            return null;
        }
        scaledImage = texture.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return scaledImage;
    }

    //UNUSED, IGNORE
    void tick(Player player, int i, int j) {}

    // void copyTile(Tile tileData) { //use 2nd constructor
    //} 

    // static void importTile(Tile tileData) { //JUST create a new tile for now using constructor 2
    //     new Tile(tileData);
    // } 

    public abstract int[] returnParams();
}
