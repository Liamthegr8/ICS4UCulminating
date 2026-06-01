public class LaserTile extends Tile{
    LaserTile(int x, int y, int width, int height, int assignedMapColorIndex, int) {
        super(x, y, width, height, assignedMapColorIndex);
        killPlayer = true;
        isCollidable = true;
    }

    /**
     * return all data of object in a form of an integer array
     * @return  int array that contains all the object's relevant information
     */
    public int[] returnParams() {
        int[] params = new int[6];
        params[0] = 01;
        params[1] = x;
        params[2] = y;
        params[3] = width;
        params[4] = height;
        params[5] = assignedMapColorIndex;
        return params;
    }
}
