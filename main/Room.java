import java.util.ArrayList;
import java.awt.*;

public class Room {
    String roomName;
    ArrayList<Tile> roomTiles;
    //static final int roomDimensions = 50; //10
    static final int roomWidth = 500; //roomDimensions*Tile.tileSize;
    static final int roomHeight = 500; //roomDimensions*Tile.tileSize;
    Color[] assignedTileColors = new Color[10]; //max 10 for testing
    Color[] enterRoomTransitionTileColors = new Color[assignedTileColors.length];
    Color[] leaveRoomTransitionTileColors = new Color[assignedTileColors.length];
    int colorTransitionSpeed = 10;
    // add colorsVaraiblesOnRoomEnter?

    public Room(String roomName){
        this.roomName = roomName;
        this.roomTiles = new ArrayList<Tile>();

        //populate to black
        for (int i=0; i<assignedTileColors.length; i++) {
            assignedTileColors[i] = Color.BLACK;
        }
    }

    public Room(String roomName, ArrayList<Tile> data){
        this.roomTiles = data;
        //populate to black
        for (int i=0; i<assignedTileColors.length; i++) {
            assignedTileColors[i] = Color.BLACK;
        }
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

    void setEnterRoomColor(int index, Color color) {
        if (index >= 0 && index < assignedTileColors.length) { //stop errors
            enterRoomTransitionTileColors[index] = color;
        } else {
            System.out.println("color variable out of bounds");
        }
    }

    void setLeaveRoomColor(int index, Color color) {
        if (index >= 0 && index < assignedTileColors.length) { //stop errors
            leaveRoomTransitionTileColors[index] = color;
        } else {
            System.out.println("color2 variable out of bounds");
        }
    }

    //void tick, check if their I and J aligns with player location, if so apply the changetilescolor
    void tick(Player player, int xIndex, int yIndex) {
        if (player.playerLocation != null && xIndex == player.playerLocation[0] && yIndex == player.playerLocation[1]) {
            for (int i=0; i<assignedTileColors.length; i++) {
                assignedTileColors[i] = enterRoomTransitionTileColors[i];
            }
            // for (int i=0; i<assignedTileColors.length; i++) {
            //     if (assignedTileColors[i] != null && enterRoomTransitionTileColors[i] != null) {
            //         Color targetColor = enterRoomTransitionTileColors[i];
            //         Color currentColor = assignedTileColors[i];
            //         // boolean increaseRed = false;
            //         // boolean increaseGreen = false;
            //         // boolean increaseBlue = false;
            //         if (currentColor.getRed() == targetColor.getRed()) {
            //             //already done red shift
            //         } else if (currentColor.getRed() < targetColor.getRed()) {
            //             currentColor = new Color(currentColor.getRed() + colorTransitionSpeed, currentColor.getGreen(), currentColor.getBlue());
            //             //check overdo
            //             if (currentColor.getRed() > targetColor.getRed()) {
            //                 currentColor = new Color(targetColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            //             }
            //         } else {
            //             currentColor = new Color(currentColor.getRed() - colorTransitionSpeed, currentColor.getGreen(), currentColor.getBlue());
            //             //check overdo
            //             if (currentColor.getRed() < targetColor.getRed()) {
            //                 currentColor = new Color(targetColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            //             }
            //         }
            //         if (currentColor.getGreen() == targetColor.getGreen()) {
            //             //already done green shift
            //         } else if (currentColor.getGreen() < targetColor.getGreen()) {
            //             currentColor = new Color(currentColor.getRed(), currentColor.getGreen() + colorTransitionSpeed, currentColor.getBlue());
            //             //check overdo
            //             if (currentColor.getGreen() > targetColor.getGreen()) {
            //                 currentColor = new Color(currentColor.getRed(), targetColor.getGreen(), currentColor.getBlue());
            //             }
            //         } else {
            //             currentColor = new Color(currentColor.getRed(), currentColor.getGreen() - colorTransitionSpeed, currentColor.getBlue());
            //             //check overdo
            //             if (currentColor.getGreen() < targetColor.getGreen()) {
            //                 currentColor = new Color(currentColor.getRed(), targetColor.getGreen(), currentColor.getBlue());
            //             }
            //         }
            //         if (currentColor.getBlue() == targetColor.getBlue()) {
            //             //already done blue shift
            //         } else if (currentColor.getBlue() < targetColor.getBlue()) {
            //             currentColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue() + colorTransitionSpeed);
            //             //check overdo
            //             if (currentColor.getBlue() > targetColor.getBlue()) {
            //                 currentColor = new Color(currentColor.getRed(), currentColor.getGreen(), targetColor.getBlue());
            //             }
            //         } else {
            //             currentColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue() - colorTransitionSpeed);
            //             //check overdo
            //             if (currentColor.getBlue() < targetColor.getBlue()) {
            //                 currentColor = new Color(currentColor.getRed(), currentColor.getGreen(), targetColor.getBlue());
            //             }
            //         }
            //     }
            // }
        } else {
            for (int i=0; i<assignedTileColors.length; i++) {
                assignedTileColors[i] = leaveRoomTransitionTileColors[i];
            }
        }
    }

}
