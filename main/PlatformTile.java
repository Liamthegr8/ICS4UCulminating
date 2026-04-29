public class PlatformTile extends Tile {
    
    PlatformTile(int x, int y) {
        super(x,y);
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
