public class Tile {
    boolean killPlayer;
    boolean isCollidable;
    //static final int tileSize = 50;
    int x,y,width,height;

    Tile(int x, int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        killPlayer = false;
        isCollidable = false;
    }

 
}

