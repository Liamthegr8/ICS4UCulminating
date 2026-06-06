import java.util.*;
public class GeneralAlgorithim {
    String[][] testOut;
    boolean[][] booleanMap;
    boolean[][] preBooleanMap;

    int mapWidth;
    int mapHeight;

    int pathLength;

    Boolean canExit;
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

    boolean upRepW;
    boolean rightRepW;
    boolean downRepW;
    boolean leftRepW;
    int preDir;
    int dirReps;

    boolean hasForceUp;

    Random r = new Random();

    boolean Alg1Placed;
    boolean Alg2Placed;
    boolean Alg3Placed;
    boolean R1Placed;
    boolean R2Placed;
    boolean R3Placed;
    boolean APlaced;
    boolean BPlaced;
    boolean R4Placed;
    boolean R5Placed;

    int startx,starty;
    int alg1x,alg1y;
    int alg2x,alg2y;
    int alg3x,alg3y;
    int r1x,r1y;
    int r2x,r2y;
    int r3x,r3y;
    int ay,ax;
    int bx,by;
    int r4x,r4y;
    int r5x,r5y;

    boolean ifUpRestrict;
    int upRestrict;
    boolean ifRightRestrict;
    int rightRestrict;
    boolean ifDownRestrict;
    int downRestrict;
    boolean ifLeftRestrict;
    int leftRestrict;

    int endY;
    int endX;

    

    
    GeneralAlgorithim(int x, int y ,int path){
        mapWidth =x;
        mapHeight=y;
        pathLength =path;
        endY=-1;
        endX=-1;

        ifUpRestrict=false;
        upRestrict=-1;
        ifRightRestrict=false;
        rightRestrict=-1;
        ifDownRestrict=false;
        downRestrict=-1;
        ifLeftRestrict=false;
        leftRestrict=-1;
        startx=-1;
        starty=-1;

        Alg1=false;
        Alg1Placed=false;
        Alg2=false;
        Alg3=false;
        //current position
        posx=0;
        posy=0;
        //inital side
        side=0;
        //for weights
        up=0;
        down=0;
        left=0;
        right=0;

        upWeight=0;
        downWeight=0;
        rightWeight=0;
        leftWeight=0;

        upRepW=false;
        rightRepW=false;
        downRepW=false;
        leftRepW=false;

        testOut = new String[mapHeight][mapWidth];
        booleanMap = new boolean[mapHeight][mapWidth];
        preBooleanMap = new boolean[mapHeight][mapWidth];
        
        enterBasement=false;
        basementTime=0;
        hasForceUp = false;

        //previous direction moved 0:up 1:right 2:down 3:left
        preDir=1;
        //times this direction has been repeated
        dirReps=0;
    }
    public void startPosition(int x, int y){
        startx=x;
        starty=y;
    }
    public void alg1Position(int x, int y){
        alg1x=x;
        alg1y=y;
        Alg1Placed=true;
    }
    public void alg2Position(int x, int y){
        alg2x=x;
        alg2y=y;
        Alg2Placed=true;
    }
    public void alg3Position(int x, int y){
        alg3x=x;
        alg3y=y;
        Alg3Placed=true;
    }
    public void relic1Position(int x, int y){
        r1x=x;
        r1y=y;
        R1Placed=true;
    }
    public void relic2Position(int x, int y){
        r2x=x;
        r2y=y;
        R2Placed=true;
    }
    public void relic3Position(int x, int y){
        r3x=x;
        r3y=y;
        R3Placed=true;
    }
    public void APosition(int x, int y){
        ax=x;
        ay=y;
        APlaced=true;
    }
    public void BPosition(int x, int y){
        bx=x;
        by=y;
        BPlaced=true;
    }
     public void relic4Position(int x, int y){
        r4x=x;
        r4y=y;
        R4Placed=true;
    }
    public void relic5Position(int x, int y){
        r5x=x;
        r5y=y;
        R5Placed=true;
    }
    //
    public void joinAlgWithPositons(boolean[][] oldMap, int y, int x){
        
        joinAlg(oldMap, y, x);
        startx+=x;
        starty+=y;
        if(Alg1Placed){
        alg1x+=x;
        alg1y+=y;
        }
        if(Alg2Placed){
        alg2x+=x;
        alg2y+=y;
        }
        if(Alg3Placed){
        alg3x+=x;
        alg3y+=y;
        }
        if(R1Placed){
        r1x+=x;
        r1y+=y;
        }
        if(R2Placed){
        r2x+=x;
        r2y+=y;
        }
        if(R3Placed){
        r3x+=x;
        r3y+=y;
        }
        if(APlaced){
        ax+=x;
        ay+=y;
        }
        if(BPlaced){
        bx+=x;
        by+=y;
        }
        if(R4Placed){
        r4x+=x;
        r4y+=y;
        }
        if(R5Placed){
        r5x+=x;
        r5y+=y;
        }
    }
    public void joinAlg(boolean[][] oldMap, int y, int x){
        System.out.println(y+oldMap.length);
        System.out.println(x+oldMap[0].length);
        for(int i =y; i<y+oldMap.length;i++){
            for(int j = x; j<x+oldMap[0].length;j++){
                if(oldMap[i-y][j-x]){
                preBooleanMap[i][j]=oldMap[i-y][j-x];
                booleanMap[i][j]=oldMap[i-y][j-x];
                }
            }
        }
        
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
     public void pickSide(int width,int xValue){
        if(xValue>width/2){
            side =1;
            preDir=1;
            dirReps=0;
        }else {
            side =-1;
            preDir=3;
            dirReps=0;
        }
    }
    public void startSetup(boolean canexit, boolean basement){
        //setsup visual graph for testing and prints out
        textSetup();
        //booleanMap[mapHeight/2][mapWidth/2]=true;
        //testOut[mapHeight/2][mapWidth/2]="S ";
        printOut();

        

        //basement setup
        basementTime =0;
        enterBasement =false;
        basementWeights =basement;

        canExit=canexit;
    }
    public void setDirectionWeights(int upw, int rightw, int downw, int leftw){
        upWeight=upw;
        downWeight=downw;
        leftWeight=leftw;
        rightWeight=rightw;
    }
    public void setIfRepeatingWeights(boolean upRepW,boolean rightRepW,boolean downRepW,boolean leftRepW){
        //weights
        this.upRepW=upRepW;
        this.rightRepW=rightRepW;
        this.downRepW=downRepW;
        this.leftRepW=leftRepW;
    }
    public void setMovementRestrictions(int upRestriction,int rightRestriction,int downRestriction,int leftRestriction){
        upRestrict=upRestriction;
        rightRestrict=rightRestriction;
        downRestrict=downRestriction;
        leftRestrict=leftRestriction;

        if(upRestriction==-1){
            ifUpRestrict=false;
        }else ifUpRestrict=true;
        if(rightRestriction==-1){
            ifRightRestrict=false;
        }else ifRightRestrict=true;
        if(downRestriction==-1){
            ifDownRestrict=false;
        }else ifDownRestrict=true;
        if(leftRestriction==-1){
            ifLeftRestrict=false;
        }else ifLeftRestrict=true;
    }
    public void setYEndpoint(int y){
        endY=y;
    }
    public void setXEndpoint(int x){
        endX=x;
    }
    
    public void run(int y, int x){

        posx = x;
        posy = y;

        booleanMap[posy][posx]=true;
        //makes changes to visual graph from boolean array
        booleanToString("O ");
        printOut();
        
        //sets unuccoppied dierection weights to 8 and occupied to 0
        for (int i =0; i!=pathLength;i++){
            try{
            if(!booleanMap[posy][posx+1]){
                right = rightWeight;
                
            }else right =0;
            }catch(Exception e){right = 0;}
            
            try{
            if(!booleanMap[posy][posx-1]){
                left = leftWeight;
                
            }else left =0;
            }catch(Exception e){left = 0;}

            try{
            if(!booleanMap[posy-1][posx]){
                up = upWeight;
                
            }else up =0;
            }catch(Exception e){up = 0;}

            try{
            if(!booleanMap[posy+1][posx]){
                down = downWeight;
                
            
            }else down =0;
            }catch(Exception e){down = 0;}

            //apply Direction Weights
        
            if(preDir==0&&upRepW){
                up =up- 2*dirReps;
            }
            if(up<0){up=0;}
        
            if(preDir==1&&rightRepW){
                right -= 2*dirReps;
            }
            if(right<0){right=0;}

            if(preDir==2&&downRepW){
                down -= 2*dirReps;
            }
            if(down<0){down=0;}

            if(preDir==3&&leftRepW){
                left -= 2*dirReps;
            }
            if(left<0){left=0;}

            //restrictions
         if(posy-1<upRestrict&&ifUpRestrict){
            up=0;
         }
         if(posx+1>rightRestrict&&ifRightRestrict){
            right=0;
         }
         if(posy+1>downRestrict&&ifDownRestrict){
            down=0;
         }
         if(posx-1<leftRestrict&&ifLeftRestrict){
            left=0;
         }
        

            //basement weights
        if(basementWeights){
            if(posy>booleanMap.length/2){
                right = right/2;
                left = left/2;
            }
            if(posy-1>booleanMap.length/2){
                up = up/2;   
            }
            if(posy+1>booleanMap.length/2){
                down = down/2;
            }
        }

            //force up & prevent crossing middle
            if(posx==mapWidth/2){
                if(side==1){
                    left=0;
                }else{right=0;}
                if(posy>mapHeight/2){
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
                    if(posx==mapWidth/2+1){
                        left=0;
                    }
                }
                else{
                    if(posx==mapWidth/2-1){
                        right=0;
                    }
                }
            }
            //forceup for if already alg1 and on it's tile
            if(posx==0||posx==mapWidth-1){
                if(posy<mapHeight/2+1&&Alg1){
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
            //not allow basement if alg2 done already
            if(posy==mapHeight/2){
                if(Alg1||Alg2||Alg3){
                down=0;
            }
            }
            //edge case preventing spiral of DOOM specically when alg2 spawns first and takes middle path up and alg 1 is on specifically either 2,1 or 4,1
            if(Alg2&&posy==1){
                if(booleanMap[1][3]||booleanMap[2][3]){
                if(posx==2||posx==4){
                    down=0;
                    System.out.println("day saved by anti-spiral of DOOM endevors :)");
                }
                }
            }
            //edge case ocasionaly a spiral can form if alg2 but alg 2 hasnt touched middle allowing 1 to trase up and spiral left
            if(Alg2&&posy==1){
                if(booleanMap[1][3]){
                    if(booleanMap[0][2]&&booleanMap[2][2]&&posx==1){
                        right=0;
                        System.out.println("day saved by the other anti-spiral of DOOM endevor :3");
                    }
                    if(booleanMap[0][4]&&booleanMap[2][4]&&posx==5){
                        left=0;
                        System.out.println("day saved by the other anti-spiral of DOOM endevor :3");
                    }
                }
            }
            


            //selecting which direction path moves randomly according to weights provided earlier
            if(up+down+left+right==0){
                System.out.printf("\nit got caught at: %d, %d", posx,posy);
                break;
            }
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

            booleanMap[posy][posx]=true;
            booleanToString("O ");
            printOut();

            if(basementWeights){
            if(posy>booleanMap.length/2){
                enterBasement=true;
                basementTime++;
                System.out.println("basement: "+basementTime);
            }
            }
            //breaks for if algorthim is selected
            if(canExit){
            //alg 2 star
            if(posy==0&&!Alg2){
                Alg2 = true;
                alg2x=posx;
                alg2y=posy;
                Alg2Placed=true;
                break;
            }
            //alg1 ^
            if(posx==0||posx==mapWidth-1){
                if(posy<mapHeight/2+1&&!Alg1){
                    Alg1=true;
                    alg1x=posx;
                    alg1y=posy;
                    Alg1Placed=true;
                    break;
                }
            }
            if(basementWeights){
            //alg3 # at bottom
            if(posy==mapHeight-1){
                Alg3=true;
                alg3x=posx;
                alg3y=posy;
                Alg3Placed=true;
                break;
            }
            //alg3 time out
            if(basementTime ==5){
                Alg3=true;
                alg3x=posx;
                alg3y=posy;
                Alg3Placed=true;
                break;
            }
            //alg 1 renter
            if(!Alg1&&enterBasement&&posy<=mapHeight/2){
                Alg1=true;
                alg1x=posx;
                alg1y=posy;
                Alg1Placed=true;
                enterBasement=false;
                basementTime=0;
                break;
            }
            }
        
        }
        if(posy==endY){
            System.out.println("endY exit");
            break;
        }
        if(posx==endX){
            System.out.println("endX exit");
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
        //booleanToString("O ");
        //printOut();
        


    }
    
    


    public void isolateMap(boolean[][] otherMap){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                if(otherMap[i][j]){
                    booleanMap[i][j]=false;
                }
                
            }

        }
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
    public void booleanToString(String s){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                if(booleanMap[i][j]){
                    testOut[i][j]=s;
                }
                
            }

        }
        testOut[starty][startx]="S ";
        if(Alg1Placed){testOut[alg1y][alg1x]="1 ";}
        if(Alg2Placed){testOut[alg2y][alg2x]="2 ";}
        if(Alg3Placed){testOut[alg3y][alg3x]="3 ";}
        if(R1Placed){testOut[r1y][r1x]="R ";}
        if(R2Placed){testOut[r2y][r2x]="R ";}
        if(R3Placed){testOut[r3y][r3x]="R ";}
        if(APlaced){testOut[ay][ax]="A ";}
        if(BPlaced){testOut[by][bx]="B ";}
        if(R4Placed){testOut[r4y][r4x]="R ";}
        if(R5Placed){testOut[r5y][r5x]="R ";}
    }
    public void booleanToString(String s,boolean[][] otherMap){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                if(otherMap[i][j]){
                    testOut[i][j]=s;
                }
                
            }

        }
        testOut[starty][startx]="S ";
        if(Alg1Placed){testOut[alg1y][alg1x]="1 ";}
        if(Alg2Placed){testOut[alg2y][alg2x]="2 ";}
        if(Alg3Placed){testOut[alg3y][alg3x]="3 ";}
        if(R1Placed){testOut[r1y][r1x]="R ";}
        if(R2Placed){testOut[r2y][r2x]="R ";}
        if(R3Placed){testOut[r3y][r3x]="R ";}
    }
    public void printBooleanOnly(String s){
        System.out.println();
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                if(booleanMap[i][j]){
                    System.out.print(s);
                }
                else System.out.print("_ ");
                
            }
            System.out.println();
        }
        
    }
    public void plainTextSetup(){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                
                    testOut[i][j]="_ ";
                
            }
        }
    }
    public void textSetup(){
        for(int i =0; i<mapHeight; i++){
            for (int j =0; j<mapWidth; j++){
                
                    testOut[i][j]="_ ";
                
            }
        }
        for(int i =0; i<mapHeight; i++){
            testOut[i][startx]="X " ;
        }
        for(int i =0; i<mapWidth; i++){
            testOut[starty-3][i]="- " ;
        }
        for(int i =(starty+1); i<mapHeight; i++){
            for(int j =0; j<mapWidth; j++){
            testOut[i][j]=". " ;
            }
        }
        for(int i =(starty+1); i<mapHeight; i++){
            
            testOut[i][startx]="+ " ;

        }
        for(int i =0; i<(starty+1); i++){
            
            testOut[i][startx-3]="^ " ;
            testOut[i][startx+3]="^ " ;
        }
        for(int i =0; i<mapWidth; i++){
            testOut[starty+3][i]="~ " ;
        }
        
    }

    
}

