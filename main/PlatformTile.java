public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height) {
        super(x, y, width, height);
        killPlayer = false;
        isCollidable = true;
        //texture = loadImage("main\\resources\\Block.png");
    }
}
