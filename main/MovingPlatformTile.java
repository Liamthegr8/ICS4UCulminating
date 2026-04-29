public class MovingPlatformTile extends Tile{
    int iniX, iniY, endX, endY;
    int moveSpeed;
    boolean moveForward;
    int movePlayerX, movePlayerY;
    MovingPlatformTile(int iniX, int iniY, int endX, int endY, int width, int height, int moveSpeed) {
        super(iniX, iniY, width, height);
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
        if (moveForward) {
            if (x < endX) {
                x += moveSpeed;
                if (hitEndpoint()) moveForward = false;
                movePlayerX = moveSpeed;
            }
            if (x > endX) {
                x -= moveSpeed;
                if (hitEndpoint()) moveForward = false;
                movePlayerX = -moveSpeed;
            }
            if (y < endY) {
                y += moveSpeed;
                if (hitEndpoint()) moveForward = false;
                movePlayerY = moveSpeed;
            }
            if (y > endY) {
                y -= moveSpeed;
                if (hitEndpoint()) moveForward = false;
                movePlayerY = -moveSpeed;
            }
        }
        if (moveForward != true) {
            if (x < iniX) {
                x += moveSpeed;
                if (hitStartpoint()) moveForward = true;
                movePlayerX = moveSpeed;
            }
            if (x > iniX) {
                x -= moveSpeed;
                if (hitStartpoint()) moveForward = true;
                movePlayerX = -moveSpeed;
            }
            if (y < iniY) {
                y += moveSpeed;
                if (hitStartpoint()) moveForward = true;
                movePlayerY = moveSpeed;
            }
            if (y > iniY) {
                y -= moveSpeed;
                if (hitStartpoint()) moveForward = true;
                movePlayerY = -moveSpeed;
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
