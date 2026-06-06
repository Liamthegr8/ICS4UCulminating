public class Direction {
    int[][] roomDir;
    int[][] roomType;
    int mapHeight=15;
    int mapWidth=19;
    Direction(){
        
        roomDir= new int[mapHeight][mapWidth];
        roomType= new int[mapHeight][mapWidth];

       
    }
    public void findDirection(boolean[][] roomMap, boolean mergeT){
        findDirection(roomMap,roomMap,mergeT);
    }
    public int directionBooleanToInt (boolean up, boolean right, boolean down, boolean left, boolean mergeT, int x, int y){
        int dir=0;
        if (up) {
            if (right) {
                if (down) {
                    if (left) {
                        dir=1;
                    } else {
                        dir=3;
                    }
                } else {
                    if (left) {
                        dir=2;
                    } else {
                        dir=8;
                    }
                }
            } else {
                if (down) {
                    if (left) {
                        dir=5;
                    } else {
                        dir=6;
                    }
                } else {
                    if (left) {
                        dir=11;
                    } else {
                        dir=12;
                    }
                }
            }
        } else {
            if (right) {
                if (down) {
                    if (left) {
                        dir =4;
                    } else {
                        dir=9;
                    }
                } else {
                    if (left) {
                        dir=7;
                    } else {
                        dir=13;
                    }
                }
            } else {
                if (down) {
                    if (left) {
                        dir=10;
                    } else {
                        dir=14;
                    }
                } else {
                    if (left) {
                        dir=15;
                    } else {
                        dir=0;
                    }
                }
            }
        }

        if(mergeT){
            if(dir==1||dir==2||dir==4){
                dir=7;
            }
            if(dir==3||dir==5){
                int dirUp =roomDir[y-1][x];
                if(dirUp==6||dirUp==9||dirUp==10){
                    dir= directionBooleanToInt (up, right, false, left, mergeT, x,  y);
                }else{
                    dir= directionBooleanToInt (false, right, down, left, mergeT, x,  y);
                }
            }
        }


        return dir;
    }
    public void findDirection(boolean[][] roomMap, boolean[][] checkRoomMap, boolean mergeT){
        findDirection(roomMap, checkRoomMap, checkRoomMap, mergeT);
    }
    public void findDirection(boolean[][] roomMap, boolean[][] checkRoomMap,boolean[][] checkRoomMap1, boolean mergeT){
        
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                if(roomMap[i][j]){
                    findDirection(i, j, checkRoomMap, checkRoomMap1, mergeT);
                }    
            }
        }
    }
    public void findDirection(int y, int x, boolean[][] checkRoomMap, boolean mergeT){
        findDirection(y, x, checkRoomMap, checkRoomMap, mergeT);
    }
    public void findDirection(int y, int x, boolean[][] checkRoomMap, boolean[][] checkRoomMap1, boolean mergeT){
        boolean up = false;
        boolean right = false;
        boolean down = false;
        boolean left = false;
        try {
            if (checkRoomMap[y - 1][x]||checkRoomMap1[y - 1][x]) {
                up = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkRoomMap[y + 1][x]||checkRoomMap1[y + 1][x]) {
                down = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkRoomMap[y][x - 1]||checkRoomMap1[y][x - 1]) {
                left = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (checkRoomMap[y][x + 1]||checkRoomMap1[y][x + 1]) {
                right = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        // symplification for filling this thing out
        roomDir[y][x] = directionBooleanToInt(up, right, down, left, mergeT,x , y);
    }
    /**
     * merges the map to the room
     * @param roomMap
     * @param y
     * @param x
     * @param mergeT
     */
    public void merge(boolean[][] roomMap, int y, int x,  boolean mergeT){
        int dir = roomDir[y][x];
        boolean[][] room = new boolean[mapHeight][mapWidth];
        room[y][x]=true;
        if(dir==1||dir==2||dir==3||dir==5||dir==6||dir==8||dir==11||dir==12){
            //up
            if(roomMap[y-1][x]){
                findDirection(y-1, x, roomMap, room, mergeT);
            }
        }
        if(dir==1||dir==2||dir==3||dir==4||dir==7||dir==8||dir==9||dir==13){
            //rihgt
            if(roomMap[y][x+1]){
                findDirection(y, x+1, roomMap, room, mergeT);
            }
        }
        if(dir==1||dir==3||dir==4||dir==5||dir==6||dir==9||dir==10||dir==14){
            //down
            if(roomMap[y+1][x]){
                findDirection(y+1, x, roomMap, room, mergeT);
            }
        }
        if(dir==1||dir==2||dir==4||dir==5||dir==7||dir==10||dir==11||dir==15){
            //left
            if(roomMap[y][x-1]){
                findDirection(y, x-1, roomMap, room, mergeT);
            }
        }
    }
    public void printOutDir(){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                switch(roomDir[i][j]){
                    case 0-> System.out.print("  ");
                    case 1-> System.out.print("+ ");
                    case 2-> System.out.print("W ");
                    case 3-> System.out.print("E ");
                    case 4-> System.out.print("M ");
                    case 5-> System.out.print("3 ");
                    case 6-> System.out.print("| ");
                    case 7-> System.out.print("- ");
                    case 8-> System.out.print("L ");
                    case 9-> System.out.print("F ");
                    case 10-> System.out.print("7 ");
                    case 11-> System.out.print("j ");
                    case 12-> System.out.print("^ ");
                    case 13-> System.out.print("> ");
                    case 14-> System.out.print("v ");
                    case 15-> System.out.print("< ");
                }
            }
            System.out.println();
        }
    }
}
