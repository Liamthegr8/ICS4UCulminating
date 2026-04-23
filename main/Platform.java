public class Platform extends Tile {
    
    Platform(int x, int y, int width, int height) {
        super(x,y,width,height);
        super.killPlayer = false;
    }

    public boolean checkCollision(Tile p, Player player) {
        return player.intersects(p);
    }

    public int getRelX() {
        return x;
    }
    public int getRelY() {
        return y;
    }
}
