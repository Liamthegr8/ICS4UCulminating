public class Tile {
    boolean killPlayer;
    boolean isCollidable;
    static final int tileSize = 50;
    int x,y;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        killPlayer = false;
        isCollidable = false;
    }

 
}

