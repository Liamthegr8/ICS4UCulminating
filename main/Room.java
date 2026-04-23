import java.util.*;
public class Room {
    String roomName;
    ArrayList<Tile> roomTiles;
    public Room(String roomName){
        this.roomName = roomName;
        this.roomTiles = roomTiles;


    }

    void loadFullRoom(ArrayList<Tile> data) {
        roomTiles = data;
    }

    void addTileAt(Tile tile) {
        roomTiles.add(tile);
    }
    void removeTileAtIndex(int index) {
        roomTiles.remove(index);
    }

}
