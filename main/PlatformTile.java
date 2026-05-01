import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height, int assignedMapColorIndex) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = false;
        isCollidable = true;
    }
}
