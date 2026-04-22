import java.awt.Rectangle;

public abstract class Tile extends Rectangle{
    boolean killPlayer;


    Tile(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    abstract void checkCollision();
}
