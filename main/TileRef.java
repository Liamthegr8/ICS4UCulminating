public class TileRef extends Tile {
    Tile tileData;
    int x;
    int y;
    //width
    //height
    
    //from TileData:
    //static tileSize
    //killPlayer
    //isCollidable
    
    TileRef(Tile tile, int x, int y) {
        this.x = x;
        this.y = y;
        tileData = tile;
        killPlayer = tileData.killPlayer;
        isCollidable = tileData.isCollidable;
    }
}
