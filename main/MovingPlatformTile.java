import java.awt.Rectangle;

public class MovingPlatformTile extends Tile{
    int iniX, iniY, endX, endY;
    int moveSpeed;
    boolean moveForward;
    boolean playerOnTile;
    MovingPlatformTile(int iniX, int iniY,  int width, int height, int assignedMapColorIndex, int endX, int endY, int moveSpeed) {
        super(iniX, iniY, width, height, assignedMapColorIndex);
        this.iniX = iniX;
        this.iniY = iniY;
        this.endX = endX;
        this.endY = endY;
        moveForward = true;
        this.moveSpeed = moveSpeed;
        killPlayer = false;
        isCollidable = true;
    }
    
    
    
    /**
     * update the tile position and move player with it if player is on top of tile
     * @param player    reference to player
     * @param roomX     x location of room within Map
     * @param roomY     y location of room within Map
     */
    @Override
    void tick(Player player, int roomX, int roomY) {
        Rectangle platformBounds = new Rectangle(roomX*Room.roomWidth + x-1, roomY*Room.roomHeight + y-4, width+2, 5);
        //determine if on top, bottom or side
        // perform action (move player with platform)
        if (platformBounds.intersects(player.getBounds()) == true) {
            playerOnTile = true;
        }
        else playerOnTile = false;
        
        if (moveForward) {
            if (x < endX) {
                x += moveSpeed;
                if (hitEndpoint()) moveForward = false;
                if (playerOnTile) player.x += moveSpeed; 
            }
            if (x > endX) {
                x -= moveSpeed;
                if (hitEndpoint()) moveForward = false;
                if (playerOnTile) player.x -= moveSpeed;
                
            }
            if (y < endY) {
                y += moveSpeed;
                if (hitEndpoint()) moveForward = false;
                if (playerOnTile) player.y += moveSpeed;
            }
            if (y > endY) {
                y -= moveSpeed;
                if (hitEndpoint()) moveForward = false;
                if (playerOnTile) player.y -= moveSpeed;
            }
        }
        if (moveForward != true) {
            if (x < iniX) {
                x += moveSpeed;
                if (hitStartpoint()) moveForward = true;
                if (playerOnTile) player.x += moveSpeed;
            }
            if (x > iniX) {
                x -= moveSpeed;
                if (hitStartpoint()) moveForward = true;
                if (playerOnTile) player.x -= moveSpeed;
            }
            if (y < iniY) {
                y += moveSpeed;
                if (hitStartpoint()) moveForward = true;
                if (playerOnTile) player.y += moveSpeed;
            }
            if (y > iniY) {
                y -= moveSpeed;
                if (hitStartpoint()) moveForward = true;
                if (playerOnTile) player.y -= moveSpeed;
            }
        }
    }

    /**
     * check if tile has reached endpoint
     * @return  true if tile has reached endpoint, else false
     */
    private boolean hitEndpoint() {
        if (x == endX && y == endY) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * check if tile has reached startpoint
     * @return   true if tile has reached startpoint, else false
     */
    private boolean hitStartpoint() {
        if (x == iniX && y == iniY) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * return all data of object in a form of an integer array
     * @return  int array that contains all the object's relevant information
     */
    public int[] returnParams() {
        int[] params = new int[8];
        params[0] = 03;
        params[1] = iniX;
        params[2] = iniY;
        params[3] = width;
        params[4] = height;
        params[5] = assignedMapColorIndex;
        params[6] = endX;
        params[7] = endY;
        return params;
    }
}
