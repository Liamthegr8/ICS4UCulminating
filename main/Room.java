import java.util.*;
public class Room {
    String roomName;
    Tile[][] roomTiles;
    static final int roomDimensions = 3;
    int roomSize = roomDimensions*Tile.tileSize;

    public Room(String roomName){
        this.roomName = roomName;
        this.roomTiles = new Tile[roomDimensions][roomDimensions];

    }

    void loadFullRoom(Tile[][] data) {
        roomTiles = data;
    }

    void addTileAt(Tile tile, int x, int y) {
        roomTiles[x][y] = tile;
    }
    void removeTileAt(int x, int y) {
        roomTiles[x][y] = null;
    }

}
