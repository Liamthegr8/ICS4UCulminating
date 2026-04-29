import java.util.ArrayList;
public class Room {
    String roomName;
    ArrayList<Tile> roomTiles;
    static final int roomDimensions = 50; //10
    int roomSize = roomDimensions*Tile.tileSize;

    public Room(String roomName){
        this.roomName = roomName;
        this.roomTiles = new ArrayList<Tile>();
    }

    public Room(String roomName, ArrayList<Tile> data){
        this.roomName = roomName;
        this.roomTiles = data;
    }

    // void loadFullRoom(Tile[][] data) {
    //     roomTiles = data;
    // }

    void addTileAt(Tile tile) {
        roomTiles.add(tile);
    }
    void removeTileAt(Tile tile) {
        roomTiles.remove(tile);
    }

}
