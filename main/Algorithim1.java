import java.util.*;
//old
public class Algorithim1 {

    String[][] testOut;
    boolean[][] algRoom1;
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
        
    boolean enterBasement;
    int basementTime;
    int preDir;
    int dirReps;

    boolean hasForceUp;

    Random r = new Random();

    int alg1x,alg1y;
    int alg2x,alg2y;
    int alg3x,alg3y;
    int relic1x=-1,relic1y=-1;
    boolean relic1spot;
    
    Algorithim1(int x1 ,int y1 ,int x2,int y2,int x3, int y3 ){
        alg1x=x1;
        alg1y=y1;
        alg2x=x2;
        alg2y=y2;
        alg3x=x3;
        alg3y=y3;

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

        testOut = new String[7][10];
        dotAlgRoom = new boolean[7][10];
        algRoom1 = new boolean[7][10];
        
        enterBasement=false;
        basementTime=0;
        hasForceUp = false;

        //previous direction moved 0:up 1:right 2:down 3:left
        preDir=1;
        //times this direction has been repeated
        dirReps=1;
    }
    public void sideToArray(boolean[][] dotAlg,int startX){
        
        if (startX>dotAlg.length/2){
            side =1;
            for(int i =0; i<dotAlg.length-1; i++){
            for (int j =0; j<dotAlg[i].length-1; j++){
                
                    algRoom1[j][i]=dotAlg[j][i];
                    dotAlgRoom[j][i]=dotAlg[j][i];
                
            }
        }
        }else{
            side =-1;
            for(int i =0; i<dotAlg.length-1; i++){
            for (int j =0; j<dotAlg[i].length-1; j++){
                    //System.out.println(i+" "+j);
                    algRoom1[j][i+3]=dotAlg[j][i];
                    dotAlgRoom[j][i+3]=dotAlg[j][i];
                
            }
            alg1x+=3;
            alg2x+=3;
            alg3x+=3;
        }
        }

        
    }
    
    public void run(int y, int x){
        
        
        //setsup visual graph for testing and prints out
        textSetup();
        //algRoom1[3][3]=true;
        printOut();

        //chooses side alg will start on and sets positon to it
        
        posx = x;
        if(posx==-1){
            posx+=3;
        }
        posy = y;

        algRoom1[posy][posx]=true;
        //makes changes to visual graph from boolean array
        booleanToString();
        printOut();
        
        //sets unuccoppied dierection weights to 8 and occupied to 0
        for (int i =0; i<3;i++){
            try{
            if(!algRoom1[posy][posx+1]){
                right = 8;
                
            }else right =0;
            }catch(Exception e){right = 0;}
            
            try{
            if(!algRoom1[posy][posx-1]){
                left = 8;
                
            }else left =0;
            }catch(Exception e){left = 0;}

            try{
            if(!algRoom1[posy-1][posx]){
                up = 8;
                
            }else up =0;
            }catch(Exception e){up = 0;}

            try{
            if(!algRoom1[posy+1][posx]){
                down = 8;
            
            }else down =0;
            }catch(Exception e){down = 0;}



            
            
            


            //selecting which direction path moves randomly according to weights provided earlier
            if(up+down+right+left==0){
                System.out.println("exit");
                break;
            }
            int dirRan = r.nextInt(up+down+right+left);
            System.out.printf("\nup: %d right: %d down: %d left: %d selected: %d\n",up,right,down,left,dirRan);
            if(dirRan<up){
                System.out.println("up");
                posy--;
                
            }else if(dirRan<up+right){
                System.out.println("right");
                posx++;
                
            }else if(dirRan<right+up+down){
                System.out.println("down");
                posy++;
                
            }else if(dirRan<right+up+down+left){
                System.out.println("left");
                posx--;
                
            }else{System.out.println("error");}

            algRoom1[posy][posx]=true;
            booleanToString();
            printOut();


            
            
            

        }
        relic1spot=true;
        relic1x=posx;
        relic1y=posy;
        booleanToString();
        printOut();

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
        for(int i =0; i<algRoom1.length; i++){
            for (int j =0; j<algRoom1[i].length; j++){
                if(dotAlgRoom[i][j]){
                    testOut[i][j]="O ";
                }else if(algRoom1[i][j]){
                    testOut[i][j]="X ";
                }
                
            }

        }
        System.out.println(alg1x);
        testOut[alg1y][alg1x]="1 ";
        testOut[alg2y][alg2x]="2 ";
        testOut[alg3y][alg3x]="3 ";
        if(relic1spot){
        testOut[relic1y][relic1x]="R ";}

    }
    public void textSetup(){
        for(int i =0; i<algRoom1.length; i++){
            for (int j =0; j<algRoom1[i].length; j++){
                
                    testOut[i][j]="f ";
                
            }
        }
        for(int i =0; i<algRoom1[5].length; i++){
            //testOut[i][3]="X " ;
        }
        for(int i =0; i<algRoom1.length; i++){
            //testOut[0][i]="* " ;
        }
        for(int i =(algRoom1.length/2+1); i<algRoom1.length; i++){
            for(int j =0; j<algRoom1.length; j++){
            //testOut[i][j]="+ " ;
            }
        }
        for(int i =(algRoom1.length/2+1); i<algRoom1.length; i++){
            
            //testOut[i][3]="# " ;

        }
        for(int i =0; i<(algRoom1.length/2+1); i++){
            
            //testOut[i][0]="^ " ;
            //testOut[i][algRoom1.length-1]="^ " ;
        }
        for(int i =0; i<algRoom1.length; i++){
            //testOut[algRoom1.length-1][i]="# " ;
        }
        
    }

    
}

