import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height, int assignedColorRoomIndex) {
        super(x, y, width, height, assignedColorRoomIndex);
        killPlayer = false;
        isCollidable = true;
    }
}
