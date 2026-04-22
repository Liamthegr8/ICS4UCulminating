import java.util.ArrayList;

public class Map {
    ArrayList<ArrayList<Room>> map;
    Map() {
        map = new ArrayList<>();

        //GENERATE FIXED TEST MAP
        for (int i=0; i<3; i++) {
            map.add(new ArrayList<Integer>());
        }

        ArrayList<Tile> testRoomTiles = new ArrayList<Tile>();
        testRoomTiles.add(new Tile())



        ArrayList<Room> tiles = new ArrayList<>(List.of(new Tile(), new Tile(), new Tile()));
        map.get(0).add(new Room("room1", )); // Adds data to row 0, column 0

    }

    addRoomAt(int x, int y) {
        
    }
    removeRoomAt(int x, int y) (

    )
    updateMiniMap() {
    }

    

}

//matrix.add(new ArrayList<Integer>()); // Adds row 0
// matrix.get(0).add(10); // Adds 10 to row 0, column 0
// matrix.get(0).add(20); // Adds 20 to row 0, column 1

//int value = matrix.get(0).get(1); // Retrieves 20
//matrix.get(0).set(1, 50); // Changes 20 to 50
