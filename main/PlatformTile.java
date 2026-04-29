public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y, int width, int height) {
        super(x, y, width, height);
        killPlayer = false;
        isCollidable = true;
    }

    // public boolean checkCollision(Tile p, Player player) {
    //     return player.intersects(p);
    // }

    //using real location, not relative to room 
    // public int getRelX() {
    //     return x;
    // }
    // public int getRelY() {
    //     return y;
    // }
}
