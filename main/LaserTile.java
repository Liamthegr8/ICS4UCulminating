
public class LaserTile extends Tile{
    
    LaserTile(int x, int y, int width, int height, int assignedMapColorIndex, int startDelay, int duration, int frequency) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = true;
        isCollidable = true;
        tileID = 05;
        enabled = false;
        this.duration = duration;
        this.frequency = frequency;
        counter = 0 - startDelay;
    }

    public void tick(Player player, int roomX, int roomY) {
        if (!enabled) {
            counter++;
            if (counter == frequency) {
                enabled = true;
                counter = 0;
                //System.out.println("laser enabled");
            }
        } 
        else {
            counter++;
            if (counter == duration) {
                enabled = false;
                counter = 0;
                //System.out.println("laser disabled");
            }
        }
    }

    /**
     * return all data of object in a form of an integer array
     * @return  int array that contains all the object's relevant information
     */
    public int[] returnParams() {
        int[] params = new int[9];
        params[0] = 05;
        params[1] = x;
        params[2] = y;
        params[3] = width;
        params[4] = height;
        params[5] = startDelay;
        params[6] = duration;
        params[7] = frequency;
        params[8] = assignedMapColorIndex;
        return params;
    }
}
