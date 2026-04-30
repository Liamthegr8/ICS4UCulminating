import java.util.ArrayList;
public class Room {
    String roomName;
    ArrayList<Tile> roomTiles;
    //static final int roomDimensions = 50; //10
    static final int roomWidth = 500; //roomDimensions*Tile.tileSize;
    static final int roomHeight = 500; //roomDimensions*Tile.tileSize;

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
        //should check if the tile is within room dimensions only
        if (true) {
            
        }
        roomTiles.add(tile);
    }
    void removeTileAt(int index) {
        roomTiles.remove(index);
    }

}
