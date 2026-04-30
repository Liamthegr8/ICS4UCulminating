import java.awt.Rectangle;

public class MovingPlatformTile extends Tile{
    int iniX, iniY, endX, endY;
    int moveSpeed;
    boolean moveForward;
    boolean playerOnTile;
    MovingPlatformTile(int iniX, int iniY, int endX, int endY, int width, int height, int moveSpeed) {
        super(iniX, iniY, width, height, null);
        this.iniX = iniX;
        this.iniY = iniY;
        this.endX = endX;
        this.endY = endY;
        moveForward = true;
        this.moveSpeed = moveSpeed;
        killPlayer = false;
        isCollidable = true;
    }
    
    @Override
    void tick(Player player) {
        Rectangle platformBounds = new Rectangle(x-1, y-1, width+2, height+2);
        //determine if on top, bottom or side
        // perform action (move player with platform)
        if (platformBounds.intersects(player.getBounds())) {
            playerOnTile = true;
        }
        else {
            playerOnTile = false;
        }
        
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

    private boolean hitEndpoint() {
        if (x == endX && y == endY) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean hitStartpoint() {
        if (x == iniX && y == iniY) {
            return true;
        }
        else {
            return false;
        }
    }
}
