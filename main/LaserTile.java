
public class LaserTile extends Tile{
    int counter;
    
    LaserTile(int x, int y, int width, int height, int assignedMapColorIndex, int startDelay, int duration, int frequency) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = true;
        isCollidable = true;
        enabled = false;
        this.startDelay = startDelay;
        this.duration = duration;
        this.frequency = frequency;
        counter = 0 - startDelay; //so that it starts with the delay

    }

    public void tick() {
        if (enabled == false) {
            counter++;
            if (counter >= frequency) {
                enabled = true;
            }
        }
        if (enabled == true) {
            counter++;
            if (counter >= duration + frequency) {
                enabled = false;
                counter = 0;
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
