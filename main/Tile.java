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

    Tile(Tile tileData) {
        this.x = tileData.x;
        this.y = tileData.y;
        this.width = tileData.width;
        this.height = tileData.height;
        this.killPlayer = tileData.killPlayer;
        this.isCollidable = tileData.isCollidable;
    }
}

