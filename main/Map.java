import java.util.ArrayList;

public class Map {
    Room[][] mapRooms;
    static final int mapDimensions = 3;
    int mapSize = mapDimensions*Room.roomDimensions*Tile.tileSize;
    
    Map() {
        mapRooms = new Room[mapDimensions][mapDimensions];
    }

    void addRoomAt(Room room, int x, int y) {
        mapRooms[x][y] = room;
    }
    void removeRoomAt(int x, int y) {
        mapRooms[x][y] = null;

    }
    void updateMiniMap() {
    }

    

}

//OUTDATED COMMENT:
//matrix.add(new ArrayList<Integer>()); // Adds row 0
// matrix.get(0).add(10); // Adds 10 to row 0, column 0
// matrix.get(0).add(20); // Adds 20 to row 0, column 1

//int value = matrix.get(0).get(1); // Retrieves 20
//matrix.get(0).set(1, 50); // Changes 20 to 50
