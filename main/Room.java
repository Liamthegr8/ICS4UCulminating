import java.util.*;
public class Room {
    String roomName;
    ArrayList<Tile> roomTiles;
    public Room(String roomName , ArrayList<Tile> roomTiles){
        this.roomName = roomName;
        this.roomTiles = roomTiles;
    }

    // void loadFullRoom(ArrayList<Tile> data) {
    //     roomTiles = data;
    // }
}


//addTileAt(tile)
//removeTileAtIndex(int index)
//loadFullRoom()
