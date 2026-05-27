import java.util.*;
public class Algorithim {
    static String[][] testOut = new String[7][7];
    static boolean[][] dotAlgRoom = new boolean[7][7];

    public static void main(String[] args) {
        
        Random r = new Random();
        dotAlgRoom[3][3]=true;

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
        testOut[3][3]="S ";
        printOut();

        int posx = 3;
        int side;
        if(r.nextBoolean()){
            side =1;
        }else {side =-1;}
        int posy = 3+side;

        dotAlgRoom[posx][posy]=true;
        booleanToString();
        printOut();
        int up;
        int down;
        int left;
        int right;

        while (true){
            try{
            if(dotAlgRoom[posx+1][posy]){
                right = 8;
            }else right =0;
            }catch(Exception e){right = 0;}
            
            try{
            if(dotAlgRoom[posx-1][posy]){
                left = 8;
            }else left =0;
            }catch(Exception e){left = 0;}

            try{
            if(dotAlgRoom[posx][posy-1]){
                up = 8;
            }else up =0;
            }catch(Exception e){up = 0;}

            try{
            if(dotAlgRoom[posx][posy+1]){
                down = 8;
            }else down =0;
            }catch(Exception e){down = 0;}
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
}
