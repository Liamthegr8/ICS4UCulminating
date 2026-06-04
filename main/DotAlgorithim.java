import java.util.*;

//old unused
public class DotAlgorithim {
    String[][] testOut;
    boolean[][] dotAlgRoom;

    Boolean Alg1;
    Boolean Alg2;
    Boolean Alg3;
    
    int posx;
    int posy;
    int side;
    
    int up;
    int down;
    int left;
    int right;

    int upWeight;
    int downWeight;
    int leftWeight;
    int rightWeight;
    
    boolean basementWeights;
    boolean enterBasement;
    int basementTime;

    boolean repeatWeights;
    int preDir;
    int dirReps;

    boolean hasForceUp;

    Random r = new Random();

    int alg1x,alg1y;
    int alg2x,alg2y;
    int alg3x,alg3y;

    
    DotAlgorithim(){
        alg1x=3;
        alg1y=3;
        alg2x=3;
        alg2y=3;
        alg3x=3;
        alg3y=3;

        Alg1=false;
        Alg2=false;
        Alg3=false;
        //current position
        posx=3;
        posy=3;
        //inital side
        side=0;
        //for weights
        up=0;
        down=0;
        left=0;
        right=0;

        testOut = new String[7][7];
        dotAlgRoom = new boolean[7][7];
        
        enterBasement=false;
        basementTime=0;
        hasForceUp = false;

        //previous direction moved 0:up 1:right 2:down 3:left
        preDir=1;
        //times this direction has been repeated
        dirReps=1;
    }
    public void pickSide(){
        if(r.nextBoolean()){
            side =1;
            preDir=1;
            dirReps=1;
        }else {
            side =-1;
            preDir=3;
            dirReps=1;
        }
    }
    
    public void run(int y, int x){
        basementTime =0;
        enterBasement =false;
        //setsup visual graph for testing and prints out
        textSetup();
        dotAlgRoom[3][3]=true;
        testOut[3][3]="S ";
        printOut();

        //chooses side alg will start on and sets positon to it
        
        posx = x;
        posy = y;

        dotAlgRoom[posy][posx]=true;
        //makes changes to visual graph from boolean array
        booleanToString();
        printOut();
        
        //sets unuccoppied dierection weights to 8 and occupied to 0
        for (int i =0; i!=-1;i++){
            try{
            if(!dotAlgRoom[posy][posx+1]){
                right = 8;
                
            }else right =0;
            }catch(Exception e){right = 0;}
            
            try{
            if(!dotAlgRoom[posy][posx-1]){
                left = 8;
                
            }else left =0;
            }catch(Exception e){left = 0;}

            try{
            if(!dotAlgRoom[posy-1][posx]){
                up = 8;
                
            }else up =0;
            }catch(Exception e){up = 0;}

            try{
            if(!dotAlgRoom[posy+1][posx]){
                down = 8;
            
            }else down =0;
            }catch(Exception e){down = 0;}

            //apply Direction Weights
            if(preDir==0){
                up =up- 2*dirReps;
            }
            if(up<0){up=0;}
        
            if(preDir==1){
                right -= 2*dirReps;
            }
            if(right<0){right=0;}

            if(preDir==2){
                down -= 2*dirReps;
            }
            if(down<0){down=0;}

            if(preDir==3){
                left -= 2*dirReps;
            }
            if(left<0){left=0;}


            //basement weights
            if(posy>dotAlgRoom.length/2){
                right = right/2;
                left = left/2;
            }
            if(posy-1>dotAlgRoom.length/2){
                up = up/2;   
            }
            if(posy+1>dotAlgRoom.length/2){
                //preventing alg 3 from forming naturally on second round
                if(Alg1||Alg2||Alg3){
                down=0;
                }
                else down = down/2;
            }

            //force up
            if(posx==3){
                if(posy>dotAlgRoom.length/2){
                    up=0;
                    left=0;
                    right=0;
                }else{
                    if(!Alg2){
                    down=0;
                    left=0;
                    right=0;
                    hasForceUp=true;
                    }
                    else if(!hasForceUp){
                        down=0;
                    left=0;
                    right=0;
                    hasForceUp=true;
                    }
                }
            }
            //keeping edge case of being cutoff in center
            if(Alg2&&hasForceUp){
                if(side==1){
                    if(posx==4){
                        left=0;
                    }
                }
                else{
                    if(posx==2){
                        right=0;
                    }
                }
            }
            //forceup for if already alg1 and on it's tile
            if(posx==0||posx==dotAlgRoom[3].length-1){
                if(posy<dotAlgRoom.length/2+1&&Alg1){
                    down=0;
                }
            }
            //force alg 1 for already alg 2
            if(Alg2&& posy==0){
                System.out.println("triggered");
                if(side==1){
                    left=0;
                }else {right =0;}
            }
            


            //selecting which direction path moves randomly according to weights provided earlier
            int dirRan = r.nextInt(up+down+right+left);
            System.out.printf("\nup: %d right: %d down: %d left: %d selected: %d\n",up,right,down,left,dirRan);
            if(dirRan<up){
                System.out.println("up");
                posy--;
                if(preDir==0){
                    dirReps++;
                }else{
                    preDir=0;
                    dirReps=1;
                }
            }else if(dirRan<up+right){
                System.out.println("right");
                posx++;
                if(preDir==1){
                    dirReps++;
                }else{
                    preDir=1;
                    dirReps=1;
                }
            }else if(dirRan<right+up+down){
                System.out.println("down");
                posy++;
                if(preDir==2){
                    dirReps++;
                }else{
                    preDir=2;
                    dirReps=1;
                }
            }else if(dirRan<right+up+down+left){
                System.out.println("left");
                posx--;
                if(preDir==3){
                    dirReps++;
                }else{
                    preDir=3;
                    dirReps=1;
                }
            }else{System.out.println("error");}

            dotAlgRoom[posy][posx]=true;
            booleanToString();
            printOut();

            if(posy>dotAlgRoom.length/2){
                enterBasement=true;
                basementTime++;
                System.out.println("basement: "+basementTime);
            }

            //breaks for if algorthim is selected
            //alg 2 star
            if(posy==0&&!Alg2){
                Alg2 = true;
                alg2x=posx;
                alg2y=posy;
                break;
            }
            //alg1 ^
            if(posx==0||posx==dotAlgRoom[3].length-1){
                if(posy<dotAlgRoom.length/2+1&&!Alg1){
                    Alg1=true;
                    alg1x=posx;
                    alg1y=posy;
                    break;
                }
            }
            //alg3 # at bottom
            if(posy==dotAlgRoom.length-1){
                Alg3=true;
                alg3x=posx;
                alg3y=posy;
                break;
            }
            //alg3 time out
            if(basementTime ==5){
                Alg3=true;
                alg3x=posx;
                alg3y=posy;
                break;
            }
            //alg 1 renter
            if(enterBasement&&posy<=dotAlgRoom.length/2){
                Alg1=true;
                alg1x=posx;
                alg1y=posy;
                break;
            }

        }
        if(Alg1){
            System.out.println("alg1");
        }
        if(Alg2){
            System.out.println("alg2");
        }
        if(Alg3){
            System.out.println("alg3");
        }
        booleanToString();
        printOut();

    }
    
    


    public boolean getAlg1(){
        return Alg1;
    }
    
    public void printOut(){
        System.out.println();
         for(String[] i:testOut){
            for (String j:i){
                System.out.print(j);
                
            }
            System.out.println();
        }
    }
    public void booleanToString(){
        for(int i =0; i<dotAlgRoom.length; i++){
            for (int j =0; j<dotAlgRoom[i].length; j++){
                if(dotAlgRoom[i][j]){
                    testOut[i][j]="O ";
                }
                
            }

        }
        testOut[alg1y][alg1x]="1 ";
        testOut[alg2y][alg2x]="2 ";
        testOut[alg3y][alg3x]="3 ";
    }
    public void plainTextSetup(){
        for(int i =0; i<dotAlgRoom.length; i++){
            for (int j =0; j<dotAlgRoom[i].length; j++){
                
                    testOut[i][j]="_ ";
                
            }
        }
    }
    public void textSetup(){
        plainTextSetup();
        for(int i =0; i<dotAlgRoom[3].length; i++){
            testOut[i][3]="X " ;
        }
        for(int i =0; i<dotAlgRoom.length; i++){
            testOut[0][i]="* " ;
        }
        for(int i =(dotAlgRoom.length/2+1); i<dotAlgRoom.length; i++){
            for(int j =0; j<dotAlgRoom.length; j++){
            testOut[i][j]="+ " ;
            }
        }
        for(int i =(dotAlgRoom.length/2+1); i<dotAlgRoom.length; i++){
            
            testOut[i][3]="# " ;

        }
        for(int i =0; i<(dotAlgRoom.length/2+1); i++){
            
            testOut[i][0]="^ " ;
            testOut[i][dotAlgRoom.length-1]="^ " ;
        }
        for(int i =0; i<dotAlgRoom.length; i++){
            testOut[dotAlgRoom.length-1][i]="# " ;
        }
        
    }

    
}
