/**
 * Map.java
 * Class containing the 2D array of rooms needed for level storage.
 * Created by Tanush, Liam, Erik
 */
import java.awt.*;

public class Map {
    //Map is a 2d array of room objects
    Room[][] mapRooms;
    static final int mapWidth = 20;
    static final int mapHeight = 20;
    //int mapWidthSize = mapWidth*Room.roomWidth;
    //int mapHeightSize = mapHeight*Room.roomHeight;
    static int assignedColorsLength = 10;
    Color[] assignedTileColors = new Color[assignedColorsLength]; //max 10 for testing
    
    Map() {
        mapRooms = new Room[mapWidth][mapHeight];
    }

    //adds room to array
    /**
     * add a room to map at specific location
     * @param room  the room that is to be added to the map's array
     * @param x     x location of room within Map
     * @param y     y location of room within Map
     */
    void addRoomAt(Room room, int x, int y) {
        mapRooms[x][y] = room;
    }
    //removes room from array
    /**
     * remove a room from map at specific location
     * @param x     x location of room within Map
     * @param y     y location of room within Map
     */
    void removeRoomAt(int x, int y) {
        mapRooms[x][y] = null;

    }
    // UNUSED
    void updateMiniMap() {
    }

    /**
     * set an array value to a color for set amount of tiles to posses
     * @param index a number used to assign the color to specific tiles (a reference number/location for color)
     * @param color the Color object to assign
     */
    void setMapTileColor(int index, Color color) {
        if (index >= 0 && index < assignedTileColors.length) { //stop errors
            assignedTileColors[index] = color;
        } else {
            System.out.println("color2 variable out of bounds");
        }
    }

}