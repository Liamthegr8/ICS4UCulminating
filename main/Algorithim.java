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
        {

        }

        if(genAlg.Alg3){
            genAlg.dirReps=0;
            genAlg.Alg1=true;
            genAlg.basementWeights=false;
            boolean done=false;

            if (genAlg.side==1){

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
            }else{

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
        genAlg.Alg1=false; 


        }
        genAlg.side *= -1;
        if(genAlg.side ==1){
            genAlg.preDir=1;
            genAlg.dirReps=1;
        }else {
            genAlg.preDir=3;
            genAlg.dirReps=1;
        }
        genAlg.run(y/2,x/2+genAlg.side);

        if(!genAlg.Alg3){
            genAlg.Alg1=false;
            genAlg.Alg2=false;
            genAlg.run(y/2+1,x/2);
        }

        GeneralAlgorithim alg1 = new GeneralAlgorithim(10, 7, 3);
        alg1.pickSide(x,genAlg.alg1x);
        System.out.println(alg1.side);

        alg1.startPosition(genAlg.startx,genAlg.starty);
        alg1.alg1Position(genAlg.alg1x,genAlg.alg1y);
        alg1.alg2Position(genAlg.alg2x,genAlg.alg2y);
        alg1.alg3Position(genAlg.alg3x,genAlg.alg3y);

        
        alg1.setIfRepeatingWeights(false,false,false,false);
        alg1.setDirectionWeights(1,1,1,1);
        
        
        
        
        if(alg1.side==1){
            alg1.joinAlg(genAlg.booleanMap, 0, 0);
            alg1.setMovementRestrictions(-1,-1,5, (alg1.startx+1));
            alg1.startSetup(false,false);
            alg1.run(genAlg.alg1y,genAlg.alg1x);
        }else{
            alg1.joinAlg(genAlg.booleanMap, 0, 3);
            alg1.setMovementRestrictions(-1,(alg1.startx-1),5, -1);
            alg1.startSetup(false,false);
            alg1.run(genAlg.alg1y,genAlg.alg1x+3);
        }
        alg1.relic1Position(alg1.posx,alg1.posy);
        alg1.booleanToString("O ");
        alg1.printOut();

        GeneralAlgorithim alg3 = new GeneralAlgorithim(13, 8, -1);
        
        alg3.side=alg1.side;
        alg3.startPosition(alg1.startx, alg1.starty);
        alg3.alg1Position(alg1.alg1x, alg1.alg1y);
        alg3.alg2Position(alg1.alg2x, alg1.alg2y);
        alg3.alg3Position(alg1.alg3x, alg1.alg3y);
        alg3.relic1Position(alg1.r1x, alg1.r1y);
        
        if (alg3.side==1){
            alg3.joinAlg(alg1.booleanMap, 0, 1);

        }
        else{
            alg3.joinAlg(alg1.booleanMap, 0, 2);
        }
        alg3.textSetup(); 
        alg3.posx=alg3.alg3x;
        alg3.posy=alg3.alg3y;
        while(alg3.posy!=6){
            if (!alg3.booleanMap[alg3.posy+1][alg3.posx]){
                alg3.posy++;
                alg3.booleanMap[alg3.posy][alg3.posx]=true;
            }
            else{
                alg3.posx-=alg3.side;
                alg3.booleanMap[alg3.posy][alg3.posx]=true;
            }  
        }
        alg3.booleanToString("O ");
        alg3.printOut();
        if(alg3.side==1){
            for(int i = alg3.posx; i<alg3.mapWidth;i++){
                alg3.booleanMap[6][i]=true;
            }
            for(int i =0; i<5;i++){
                alg3.booleanMap[7][i+alg3.startx]=true; 
                
            }
            alg3.APosition(4+alg3.startx, 6);
        }else{
            for(int i = alg3.posx; i>=0;i--){
                alg3.booleanMap[6][i]=true;
            }
            for(int i =0; i<5;i++){
                alg3.booleanMap[7][alg3.startx-i]=true; 
            }
            alg3.APosition(alg3.startx-4, 6);
        }
        alg3.relic3Position(alg3.startx,7);
        alg3.booleanToString("O ");
        alg3.printOut();


        GeneralAlgorithim alg2 = new GeneralAlgorithim(17, 8, -1);
        alg2.pickSide(10,alg3.alg2x);
        alg2.preDir = 0;
        alg2.startPosition(alg3.startx,alg3.starty);
        alg2.alg1Position(alg3.alg1x,alg3.alg1y);
        alg2.alg2Position(alg3.alg2x,alg3.alg2y);
        alg2.alg3Position(alg3.alg3x,alg3.alg3y);
        alg2.relic1Position(alg3.r1x,alg3.r1y);
        alg2.relic3Position(alg3.r3x,alg3.r3y);
        alg2.APosition(alg3.ax,alg3.ay);
       
        
        if(alg2.side==1){
            alg2.joinAlg(alg3.booleanMap, 0, 0);
            alg2.startSetup(false,false);
            alg2.setIfRepeatingWeights(false,true,false,true);
            alg2.setDirectionWeights(0,3,3,3);
            

            System.out.println(alg2.startx+4);
            for(int i = alg2.startx+4; i>alg2.alg2x;i--){
                alg2.booleanMap[alg2.alg2y][i]=true;
            }

            alg2.posx=alg2.startx+4;
            alg2.setMovementRestrictions(-1,-1,alg2.starty+1,alg2.posx);
        }else{
            alg2.joinAlg(alg3.booleanMap, 0, 4);
            alg2.startSetup(false,false);
            alg2.setIfRepeatingWeights(false,true,false,true);
            alg2.setDirectionWeights(0,3,3,3);
            

            System.out.println(alg2.startx-4);
            for(int i = alg2.startx-4; i<alg2.alg2x;i++){
                alg2.booleanMap[alg2.alg2y][i]=true;
            }

            alg2.posx=alg2.startx-4;
            alg2.setMovementRestrictions(-1,alg2.posx,alg2.starty+1,-1);
        }
       
        alg2.booleanToString("O ");
        alg2.printOut();
        alg2.setYEndpoint(alg2.starty+1);
        alg2.run(alg2.alg2y,alg2.posx);
        alg2.relic2Position(alg2.posx,alg2.posy);
        alg2.booleanToString("O ");
        alg2.printOut();

        GeneralAlgorithim dotAlg3 = new GeneralAlgorithim(13, 8, -1);
        dotAlg3.plainTextSetup();
        if(alg3.side==1){
            dotAlg3.joinAlg(alg1.preBooleanMap, 0, 1);
        }else{
            dotAlg3.joinAlg(alg1.preBooleanMap, 0, 2);
        }
        dotAlg3.startPosition(alg3.startx,alg3.starty);
        dotAlg3.alg1Position(alg3.alg1x,alg3.alg1y);
        dotAlg3.alg2Position(alg3.alg2x,alg3.alg2y);
        dotAlg3.alg3Position(alg3.alg3x,alg3.alg3y);
        dotAlg3.relic1Position(alg3.r1x,alg3.r1y);
        dotAlg3.relic2Position(alg3.r2x,alg3.r2y);
        dotAlg3.relic3Position(alg3.r3x,alg3.r3y);
        dotAlg3.APosition(alg3.ax,alg3.ay);

        dotAlg3.booleanToString("# ", alg3.booleanMap);
        dotAlg3.booleanToString("^ ", alg3.preBooleanMap);
        dotAlg3.booleanToString(". ");
        
        dotAlg3.printOut();

        //yes its out of order
        //originally 3 would of been generated later but there is a single 1/9000 edge case which made it so it has to generate before*
        GeneralAlgorithim triAlg2 = new GeneralAlgorithim(17, 8, -1);
        triAlg2.plainTextSetup();
        if(alg2.side==1){
            triAlg2.joinAlg(alg3.preBooleanMap, 0, 0);
        }else{
            triAlg2.joinAlg(alg3.preBooleanMap, 0, 4);
        }
        triAlg2.startPosition(alg2.startx,alg2.starty);
        triAlg2.alg1Position(alg2.alg1x,alg2.alg1y);
        triAlg2.alg2Position(alg2.alg2x,alg2.alg2y);
        triAlg2.alg3Position(alg2.alg3x,alg2.alg3y);
        triAlg2.relic1Position(alg2.r1x,alg2.r1y);
        triAlg2.relic2Position(alg2.r2x,alg2.r2y);
        triAlg2.relic3Position(alg2.r3x,alg2.r3y);
        triAlg2.APosition(alg2.ax,alg2.ay);

        triAlg2.booleanToString("* ", alg2.booleanMap);
        triAlg2.booleanToString("# ", alg2.preBooleanMap);
        triAlg2.booleanToString("^ ");
        
        triAlg2.printOut();

        //yes its out of order
        //originally 3 would of been generated later but there is a single 1/9000 edge case which made it so it has to generate before*
        GeneralAlgorithim dotAlg2 = new GeneralAlgorithim(17, 8, -1);
        dotAlg2.plainTextSetup();
        if(alg2.side==1){
            dotAlg2.joinAlg(dotAlg3.booleanMap, 0, 0);
        }else{
            dotAlg2.joinAlg(dotAlg3.preBooleanMap, 0, 4);
        }
        dotAlg2.startPosition(alg2.startx,alg2.starty);
        dotAlg2.alg1Position(alg2.alg1x,alg2.alg1y);
        dotAlg2.alg2Position(alg2.alg2x,alg2.alg2y);
        dotAlg2.alg3Position(alg2.alg3x,alg2.alg3y);
        dotAlg2.relic1Position(alg2.r1x,alg2.r1y);
        dotAlg2.relic2Position(alg2.r2x,alg2.r2y);
        dotAlg2.relic3Position(alg2.r3x,alg2.r3y);
        dotAlg2.APosition(alg2.ax,alg2.ay);

        dotAlg2.booleanToString("* ", alg2.booleanMap);
        dotAlg2.booleanToString("# ", alg2.preBooleanMap);
        dotAlg2.booleanToString("^ ", triAlg2.booleanMap);
        dotAlg2.booleanToString(". ");
        
        dotAlg2.printOut();
        

        GeneralAlgorithim balg = new GeneralAlgorithim(19, 15, -1);
        balg.startPosition(alg2.startx,alg2.starty);
        balg.alg1Position(alg2.alg1x,alg2.alg1y);
        balg.alg2Position(alg2.alg2x,alg2.alg2y);
        balg.alg3Position(alg2.alg3x,alg2.alg3y);
        balg.relic1Position(alg2.r1x,alg2.r1y);
        balg.relic2Position(alg2.r2x,alg2.r2y);
        balg.relic3Position(alg2.r3x,alg2.r3y);
        balg.APosition(alg2.ax,alg2.ay);
        
        balg.joinAlg(alg2.booleanMap, 7, 1);
        balg.textSetup();
        balg.booleanToString("O ");
        balg.printOut();
        for(int i=balg.starty-4;!balg.booleanMap[i][balg.startx] ; i++){
            balg.booleanMap[i][balg.startx] =true;
        }
        balg.BPosition(balg.startx,balg.starty-4);
        balg.booleanToString("O ");
        balg.printOut();


        GeneralAlgorithim alg4 = new GeneralAlgorithim(19, 15, -1);
        alg4.side=alg1.side;
        alg4.preDir = 2;
        alg4.startPosition(balg.startx,balg.starty);
        alg4.alg1Position(balg.alg1x,balg.alg1y);
        alg4.alg2Position(balg.alg2x,balg.alg2y);
        alg4.alg3Position(balg.alg3x,balg.alg3y);
        alg4.relic1Position(balg.r1x,balg.r1y);
        alg4.relic2Position(balg.r2x,balg.r2y);
        alg4.relic3Position(balg.r3x,balg.r3y);
        alg4.APosition(balg.ax,balg.ay);
        alg4.BPosition(balg.bx,balg.by);
        alg4.joinAlg(balg.booleanMap, 0, 0);
        alg4.startSetup(false,false);
        alg4.setIfRepeatingWeights(true,false,false,false);
        alg4.posy=balg.by;
        if(alg4.side==1){
            alg4.setDirectionWeights(2,3,0,0);

            System.out.println(alg4.startx+3);
            for(int i = alg4.startx+1; i<alg4.startx+3;i++){
                alg4.booleanMap[alg4.posy][i]=true;
            }
            alg4.posx=alg4.startx+3;

            alg4.setMovementRestrictions(-1,-1,-1,-1);
            alg4.setXEndpoint(alg4.mapWidth-2);
        }else{
            alg4.setDirectionWeights(2,0,0,3);

            System.out.println(alg4.startx-3);
            for(int i = alg4.startx-1; i>alg4.startx-3;i--){
                alg4.booleanMap[alg4.posy][i]=true;
            }
            alg4.posx=alg4.startx-3;

            alg4.setMovementRestrictions(-1,-1,-1,-1);
            alg4.setXEndpoint(1);
        }
       
        alg4.booleanToString("O ");
        alg4.printOut();
        
        alg4.run(alg4.posy,alg4.posx);
        alg4.relic4Position(alg4.posx,alg4.posy);
        alg4.booleanToString("O ");
        alg4.printOut();

        GeneralAlgorithim alg5 = new GeneralAlgorithim(19, 15, -1);
        alg5.side=alg2.side;
        alg5.preDir = 2;
        alg5.startPosition(balg.startx,balg.starty);
        alg5.alg1Position(balg.alg1x,balg.alg1y);
        alg5.alg2Position(balg.alg2x,balg.alg2y);
        alg5.alg3Position(balg.alg3x,balg.alg3y);
        alg5.relic1Position(balg.r1x,balg.r1y);
        alg5.relic2Position(balg.r2x,balg.r2y);
        alg5.relic3Position(balg.r3x,balg.r3y);
        alg5.relic4Position(alg4.r4x,alg4.r4y);
        alg5.APosition(balg.ax,balg.ay);
        alg5.BPosition(balg.bx,balg.by);
        alg5.joinAlg(balg.booleanMap, 0, 0);
        alg5.startSetup(false,false);
        alg5.setIfRepeatingWeights(true,false,false,false);
        alg5.posy=balg.by-1;
        alg5.booleanMap[alg5.posy][balg.bx]=true;
        alg5.setDirectionWeights(2,3,0,3);
        alg5.setYEndpoint(1);
        if(alg5.side==1){
            alg5.posx=alg5.startx+1;
            alg5.booleanMap[alg5.posy][alg5.posx]=true;
            

            alg5.setMovementRestrictions(-1,alg5.posx+2,-1,alg5.posx);
        }else{
            alg5.posx=alg5.startx-1;
            alg5.booleanMap[alg5.posy][alg5.posx]=true;

            alg5.setMovementRestrictions(-1,alg5.posx,-1,alg5.posx-2);
        }
       
        alg5.booleanToString("O ");
        alg5.printOut();
        
        alg5.run(alg5.posy,alg5.posx);
        alg5.relic5Position(alg5.posx,alg5.posy);
        alg5.booleanToString("O ");
        alg5.printOut();













        GeneralAlgorithim starAlgF = new GeneralAlgorithim(19, 15, -1);
        starAlgF.plainTextSetup();
        
        starAlgF.joinAlg(balg.preBooleanMap, 0, 0);
        
        starAlgF.startPosition(balg.startx,balg.starty);
        starAlgF.alg1Position(balg.alg1x,balg.alg1y);
        starAlgF.alg2Position(balg.alg2x,balg.alg2y);
        starAlgF.alg3Position(balg.alg3x,balg.alg3y);
        starAlgF.relic1Position(balg.r1x,balg.r1y);
        starAlgF.relic2Position(balg.r2x,balg.r2y);
        starAlgF.relic3Position(balg.r3x,balg.r3y);
        starAlgF.APosition(balg.ax,balg.ay);
        starAlgF.BPosition(balg.bx,balg.by);

        starAlgF.booleanToString("b ", balg.booleanMap);
        starAlgF.booleanToString("* ");
        
        starAlgF.printOut();

        GeneralAlgorithim hashAlgF = new GeneralAlgorithim(19, 15, -1);
        hashAlgF.plainTextSetup();
        
        hashAlgF.joinAlg(alg2.preBooleanMap, 7, 1);
        
        hashAlgF.startPosition(balg.startx,balg.starty);
        hashAlgF.alg1Position(balg.alg1x,balg.alg1y);
        hashAlgF.alg2Position(balg.alg2x,balg.alg2y);
        hashAlgF.alg3Position(balg.alg3x,balg.alg3y);
        hashAlgF.relic1Position(balg.r1x,balg.r1y);
        hashAlgF.relic2Position(balg.r2x,balg.r2y);
        hashAlgF.relic3Position(balg.r3x,balg.r3y);
        hashAlgF.APosition(balg.ax,balg.ay);
        hashAlgF.BPosition(balg.bx,balg.by);

        hashAlgF.booleanToString("b ", balg.booleanMap);
        hashAlgF.booleanToString("* ", starAlgF.booleanMap);
        hashAlgF.booleanToString("# ");
        
        hashAlgF.printOut();

        GeneralAlgorithim triAlgF = new GeneralAlgorithim(19, 15, -1);
        triAlgF.plainTextSetup();
        
        triAlgF.joinAlg(triAlg2.booleanMap, 7, 1);
        
        triAlgF.startPosition(balg.startx,balg.starty);
        triAlgF.alg1Position(balg.alg1x,balg.alg1y);
        triAlgF.alg2Position(balg.alg2x,balg.alg2y);
        triAlgF.alg3Position(balg.alg3x,balg.alg3y);
        triAlgF.relic1Position(balg.r1x,balg.r1y);
        triAlgF.relic2Position(balg.r2x,balg.r2y);
        triAlgF.relic3Position(balg.r3x,balg.r3y);
        triAlgF.APosition(balg.ax,balg.ay);
        triAlgF.BPosition(balg.bx,balg.by);

        triAlgF.booleanToString("b ", balg.booleanMap);
        triAlgF.booleanToString("* ", starAlgF.booleanMap);
        triAlgF.booleanToString("# ", hashAlgF.booleanMap);
        triAlgF.booleanToString("^ ");
        
        triAlgF.printOut();

        GeneralAlgorithim dotAlgF = new GeneralAlgorithim(19, 15, -1);
        dotAlgF.plainTextSetup();
        
        dotAlgF.joinAlg(dotAlg2.booleanMap, 7, 1);
        
        dotAlgF.startPosition(balg.startx,balg.starty);
        dotAlgF.alg1Position(balg.alg1x,balg.alg1y);
        dotAlgF.alg2Position(balg.alg2x,balg.alg2y);
        dotAlgF.alg3Position(balg.alg3x,balg.alg3y);
        dotAlgF.relic1Position(balg.r1x,balg.r1y);
        dotAlgF.relic2Position(balg.r2x,balg.r2y);
        dotAlgF.relic3Position(balg.r3x,balg.r3y);
        dotAlgF.APosition(balg.ax,balg.ay);
        dotAlgF.BPosition(balg.bx,balg.by);
        dotAlgF.relic4Position(alg4.r4x,alg4.r4y);
        dotAlgF.relic5Position(alg5.r5x,alg5.r5y);

        dotAlgF.booleanToString("X ", alg5.booleanMap);
        dotAlgF.booleanToString("L ", alg4.booleanMap);
        dotAlgF.booleanToString("b ", balg.booleanMap);
        dotAlgF.booleanToString("* ", starAlgF.booleanMap);
        dotAlgF.booleanToString("# ", hashAlgF.booleanMap);
        dotAlgF.booleanToString("^ ", triAlgF.booleanMap);
        dotAlgF.booleanToString(". ");
        
        dotAlgF.printOut();

        
    }
}

