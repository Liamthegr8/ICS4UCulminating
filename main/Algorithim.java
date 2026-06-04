public class Algorithim {
    public static void main(String[] args) {
        //DotAlgorithim dotAlg = new DotAlgorithim();
        //boolean ifrun=false;
        int x=7;
        int y=7;
        GeneralAlgorithim genAlg = new GeneralAlgorithim(x,y,-1);

        genAlg.startPosition(x/2,y/2);
        genAlg.pickSide();
        genAlg.booleanMap[y/2][x/2]=true;
        genAlg.startSetup(true,true);
        genAlg.setIfRepeatingWeights(true,true,true,true);
        genAlg.setDirectionWeights(8,8,8,8);
        genAlg.setMovementRestrictions(-1,-1,-1,-1);
        genAlg.run(y/2,x/2+genAlg.side);

        if(genAlg.Alg3){
            genAlg.Alg1=true;
            genAlg.basementWeights=false;
            int k;
            
            boolean done=false;
            if (genAlg.side==1){
                k=x-1;

         for(int i =0;i<genAlg.mapHeight;i++){ 
             for(int j=x-1;j>0;j--){
                 if(genAlg.booleanMap[i][j]){
                    done=true;
                    genAlg.booleanMap[i][j]=false;
                    System.out.println(i+" "+j);
                    genAlg.run(i,j);
                    break;
                  }
            }
            if(done)break;
        }       
            }else {
                k = 0;
        for(int i =0;i<y;i++){ 
             for(int j=0;j<x-1;j++){
                 if(genAlg.booleanMap[i][j]){
                    done=true;
                    genAlg.booleanMap[i][j]=false;
                    System.out.println(i+" "+j);
                    genAlg.run(i,j);
                    break;
                  }
            }
            if(done)break;
        } 
            }
        System.out.println(k);  
        genAlg.Alg1=false; 
        

        }
        genAlg.side *= -1;
        genAlg.run(y/2,x/2+genAlg.side);

        if(!genAlg.Alg3){
            genAlg.Alg1=false;
            genAlg.Alg2=false;
            genAlg.run(y/2+1,x/2);
        }

        GeneralAlgorithim alg1 = new GeneralAlgorithim(10, 7, 3);
        alg1.pickSide(x,genAlg.alg1x);
        alg1.startSetup(false,false);
        alg1.setIfRepeatingWeights(false,false,false,false);
        alg1.setDirectionWeights(1,1,1,1);
        alg1.setMovementRestrictions(-1,-1,-1,-1);
        
        System.out.println(alg1.side);
        alg1.startPosition(x/2,y/2);
        alg1.alg1Position(genAlg.alg1x,genAlg.alg1y);
        alg1.alg2Position(genAlg.alg2x,genAlg.alg2y);
        alg1.alg3Position(genAlg.alg3x,genAlg.alg3y);
        if(alg1.side==1){
            alg1.joinAlg(genAlg.booleanMap, 0, 0);
            alg1.run(genAlg.alg1y,genAlg.alg1x);
        }else{
            alg1.joinAlg(genAlg.booleanMap, 0, 3);
            alg1.run(genAlg.alg1y,genAlg.alg1x+3);
        }
        alg1.relic1Position(alg1.posx,alg1.posy);
        alg1.booleanToString("O ");
        alg1.printOut();
        //Algorithim1 alg1 = new Algorithim1(dotAlg.alg1x,dotAlg.alg1y,dotAlg.alg2x,dotAlg.alg2y,dotAlg.alg3x,dotAlg.alg3y);
        //alg1.sideToArray(dotAlg.dotAlgRoom,dotAlg.alg1x);
        //alg1.run(dotAlg.alg1y,dotAlg.alg1x);
        GeneralAlgorithim alg2 = new GeneralAlgorithim(15, 7, -1);
        alg2.pickSide(10,alg1.alg2x);
        alg2.startPosition(alg1.startx,alg1.starty);
        alg2.alg1Position(alg1.alg1x,alg1.alg1y);
        alg2.alg2Position(alg1.alg2x,alg1.alg2y);
        alg2.alg3Position(alg1.alg3x,alg1.alg3y);
        alg2.relic1Position(alg1.r1x,alg1.r1y);
       
        
        if(alg2.side==1){
            alg2.joinAlg(alg1.booleanMap, 0, 0);
            alg2.startSetup(false,false);
            alg2.setIfRepeatingWeights(false,true,false,true);
            alg2.setDirectionWeights(0,3,3,3);
            alg2.setMovementRestrictions(-1,-1,alg2.mapHeight/2+1,alg2.alg2x);
            System.out.println(genAlg.mapWidth/2+4+3);
            for(int i = alg2.alg2x; i<genAlg.mapWidth/2+4+3;i++){
                alg2.booleanMap[alg2.alg2y][i]=true;
                alg2.posx=i;
            }
        }else{
            alg2.joinAlg(alg1.booleanMap, 0, 5);
            alg2.startSetup(false,false);
            alg2.setIfRepeatingWeights(false,true,false,true);
            alg2.setDirectionWeights(0,3,3,3);
            alg2.setMovementRestrictions(-1,alg2.alg2x,alg2.mapHeight/2+1,-1);
            System.out.println(genAlg.mapWidth/2-4+5);

            for(int i = genAlg.mapWidth/2-4+5; i<alg2.alg2x;i++){
                alg2.booleanMap[alg2.alg2y][i]=true;
                alg2.posx=genAlg.mapWidth/2-4+5;
            }
        }
       
        alg2.booleanToString("O ");
        alg2.printOut();
        alg2.setYEndpoint(alg2.mapHeight/2+1);
        alg2.run(alg2.alg2y,alg2.posx);
        alg2.relic2Position(alg2.posx,alg2.posy);
        alg2.booleanToString("O ");
        alg2.printOut();


        GeneralAlgorithim dotAlg = new GeneralAlgorithim(15, 7, -1);
        dotAlg.plainTextSetup();
        if(alg2.side==1){
            dotAlg.joinAlg(alg1.preBooleanMap, 0, 0);
        }else{
            dotAlg.joinAlg(alg1.preBooleanMap, 0, 5);
        }
        dotAlg.startPosition(alg2.startx,alg2.starty);
        dotAlg.alg1Position(alg2.alg1x,alg2.alg1y);
        dotAlg.alg2Position(alg2.alg2x,alg2.alg2y);
        dotAlg.alg3Position(alg2.alg3x,alg2.alg3y);
        dotAlg.relic1Position(alg2.r1x,alg2.r1y);
        dotAlg.relic2Position(alg2.r2x,alg2.r2y);

        dotAlg.booleanToString("* ", alg2.booleanMap);
        dotAlg.booleanToString("^ ", alg2.preBooleanMap);
        dotAlg.booleanToString(". ");
        
        dotAlg.printOut();
    }
}

