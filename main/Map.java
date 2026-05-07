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
    void addRoomAt(Room room, int x, int y) {
        mapRooms[x][y] = room;
    }
    //removes room from array
    void removeRoomAt(int x, int y) {
        mapRooms[x][y] = null;

    }
    void updateMiniMap() {
    }

    //changes color or smth, idk
    void setMapTileColor(int index, Color color) {
        if (index >= 0 && index < assignedTileColors.length) { //stop errors
            assignedTileColors[index] = color;
        } else {
            System.out.println("color2 variable out of bounds");
        }
    }

}

//OUTDATED COMMENT:
//matrix.add(new ArrayList<Integer>()); // Adds row 0
// matrix.get(0).add(10); // Adds 10 to row 0, column 0
// matrix.get(0).add(20); // Adds 20 to row 0, column 1

//int value = matrix.get(0).get(1); // Retrieves 20
//matrix.get(0).set(1, 50); // Changes 20 to 50
