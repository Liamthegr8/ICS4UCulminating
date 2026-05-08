import java.util.ArrayList;
import java.awt.*;

public class Room {
    String roomID;
    ArrayList<Tile> roomTiles;
    //static final int roomDimensions = 50; //10
    static final int roomWidth = 500; //roomDimensions*Tile.tileSize;
    static final int roomHeight = 500; //roomDimensions*Tile.tileSize;
    Color[] enterRoomTransitionTileColors = new Color[Map.assignedColorsLength];
    int colorTransitionSpeed = 10;

    public Room(String roomID){
        this.roomID = roomID;
        this.roomTiles = new ArrayList<Tile>();

        // //populate to black
        // for (int i=0; i<assignedTileColors.length; i++) {
        //     assignedTileColors[i] = Color.BLACK;
        // }
    }

    public Room(String roomID, ArrayList<Tile> data){
        this.roomID = roomID;
        this.roomTiles = data;
    }

    // void loadFullRoom(Tile[][] data) {
    //     roomTiles = data;
    // }

    void addTileAt(Tile tile) {
        //should check if the tile is within room dimensions only
        
        
        if (tile.x >= 0 && tile.x + tile.width <= roomWidth && tile.y >= 0 && tile.y + tile.height <= roomHeight) {
            roomTiles.add(tile);
        } else {
            System.out.println("Tile out of room bounds");
            return;
        }    
    }
    void removeTileAt(int index) {
        roomTiles.remove(index);
    }

    void setEnterRoomTransitionColor(int index, Color color) {
        if (index >= 0 && index < enterRoomTransitionTileColors.length) { //stop errors
            enterRoomTransitionTileColors[index] = color;
        } else {
            System.out.println("color variable out of bounds");
        }
    }

    //void tick, check if their I and J aligns with player location, if so apply the changetilescolor
    void tick(Map map, Player player, int xIndex, int yIndex) {
        if (player.playerLocation != null && xIndex == player.playerLocation[0] && yIndex == player.playerLocation[1]) {
            for (int i=0; i<Map.assignedColorsLength; i++) {
                if (enterRoomTransitionTileColors[i] != null) {
                    map.assignedTileColors[i] = enterRoomTransitionTileColors[i];
                }
            }
        }
    }

}
