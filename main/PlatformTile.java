import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height, int assignedMapColorIndex) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = false;
        isCollidable = true;
    }

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
