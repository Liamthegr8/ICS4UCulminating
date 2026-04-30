import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height, String texture) {
        super(x, y, width, height, texture);
        killPlayer = false;
        isCollidable = true;
        //texture = loadImage("main\\resources\\Block.png");
    }
}
