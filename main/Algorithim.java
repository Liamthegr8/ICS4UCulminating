import java.util.*;
public class Algorithim {
    static String[][] testOut = new String[7][7];
    static boolean[][] dotAlgRoom = new boolean[7][7];
    

    
    static boolean Alg1=false;
    static boolean Alg2=false;
    static boolean Alg3=false;

    public static void main(String[] args) {
        //current position
        int posx=3;
        int posy=3;
        //inital side
        int side=0;
        //for weights
        int up=0;
        int down=0;
        int left=0;
        int right=0;
        
        boolean enterBasement=false;
        int basementTime=0;

        //previous direction moved 0:up 1:right 2:down 3:left
        int preDir=1;
        //times this direction has been repeated
        int dirReps=1;

        Random r = new Random();

        //setsup visual graph for testing and prints out
        textSetup();
        dotAlgRoom[3][3]=true;
        testOut[3][3]="S ";
        printOut();

        //chooses side alg will start on and sets positon to it
        if(r.nextBoolean()){
            side =1;
            preDir=1;
            dirReps=1;
        }else {
            side =-1;
            preDir=3;
            dirReps=1;
        }
        posx = 3+side;
        posy = 3;

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
                down = down/2;
            }

            //force up
            if(posx==3){
                left=0;
                right=0;
                if(posy>dotAlgRoom.length/2){
                    up=0;
                }else{
                    down=0;
                }
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
            if(posy==0){
                Alg2 = true;
                break;
            }
            if(posx==0||posx==dotAlgRoom[3].length-1){
                if(posy<dotAlgRoom.length/2+1){
                    Alg1=true;
                    break;
                }
            }
            if(posy==dotAlgRoom.length-1){
                Alg3=true;
                break;
            }
            if(basementTime ==5){
                Alg3=true;
                break;
            }
            if(enterBasement&&posy<=dotAlgRoom.length/2){
                Alg1=true;
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


    }
    
    
    public static void printOut(){
        System.out.println();
         for(String[] i:testOut){
            for (String j:i){
                System.out.print(j);
                
            }
            System.out.println();
        }
    }
    public static void booleanToString(){
        for(int i =0; i<dotAlgRoom.length; i++){
            for (int j =0; j<dotAlgRoom[i].length; j++){
                if(dotAlgRoom[i][j]){
                    testOut[i][j]="O ";
                }
                
            }
        }
    }
    public static void textSetup(){
        for(int i =0; i<dotAlgRoom.length; i++){
            for (int j =0; j<dotAlgRoom[i].length; j++){
                
                    testOut[i][j]="f ";
                
            }
        }
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
