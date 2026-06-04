/**
 * Room.java
 * Class that stores all the data pertaining to each individual room. Objects within are stored in an arraylist.
 * Created by Tanush, Liam, Erik
 */
import java.util.ArrayList;
import java.awt.*;

public class Room {
    String roomID;
    ArrayList<Tile> roomTiles;
    //static final int roomDimensions = 50; //10
    static final int roomWidth = 1200; //roomDimensions*Tile.tileSize;
    static final int roomHeight = 800; //roomDimensions*Tile.tileSize;
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

    /**
     * add a tile to the specified room
     * @param tile  the tile that is to be added to the room's arraylist
     */
    void addTileAt(Tile tile) {
        //should check if the tile is within room dimensions only
        
        if (tile.x >= 0 && tile.x + tile.width <= roomWidth && tile.y >= 0 && tile.y + tile.height <= roomHeight) {
            roomTiles.add(tile);
        } else {
            System.out.println("Tile out of room bounds");
            return;
        }    
    }
    
    /**
     * remove specific tile from room
     * @param index the location of the tile within the room's arraylist
     */
    void removeTileAt(int index) {
        roomTiles.remove(index);
    }

    /**
     * define what color a set amount of tiles are to posses upon the player entering the room 
     * @param index a number used to assign the color to specific tiles (a reference number/location for the color)
     * @param color the Color object to assign
     */
    void setEnterRoomTransitionColor(int index, Color color) {
        if (index >= 0 && index < enterRoomTransitionTileColors.length) { //stop errors
            enterRoomTransitionTileColors[index] = color;
        } else {
            System.out.println("color variable out of bounds");
        }
    }

    /**
     * change the assigned tile colors when player enters the room
     * @param map   reference to main map
     * @param player    reference to player
     * @param xIndex    x location of room within Map
     * @param yIndex    y location of room within Map
     */
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