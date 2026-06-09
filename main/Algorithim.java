public class Algorithim {
    int[][] directionMap;
    int[][] roomTypeMap;
    Algorithim() {
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

        GeneralAlgorithim triAlg = new GeneralAlgorithim(10, 7, 3);
        triAlg.pickSide(x,genAlg.alg1x);
        System.out.println(triAlg.side);

        triAlg.startPosition(genAlg.startx,genAlg.starty);
        triAlg.alg1Position(genAlg.alg1x,genAlg.alg1y);
        triAlg.alg2Position(genAlg.alg2x,genAlg.alg2y);
        triAlg.alg3Position(genAlg.alg3x,genAlg.alg3y);

        
        triAlg.setIfRepeatingWeights(false,false,false,false);
        triAlg.setDirectionWeights(1,1,1,1);
        
        
        
        
        if(triAlg.side==1){
            triAlg.joinAlgWithPositons(genAlg.booleanMap, 0, 0);
            triAlg.setMovementRestrictions(-1,-1,5, (triAlg.startx+1));
            triAlg.startSetup(false,false);
            triAlg.run(genAlg.alg1y,genAlg.alg1x);
        }else{
            triAlg.joinAlgWithPositons(genAlg.booleanMap, 0, 3);
            triAlg.setMovementRestrictions(-1,(triAlg.startx-1),5, -1);
            triAlg.startSetup(false,false);
            triAlg.run(genAlg.alg1y,genAlg.alg1x+3);
        }
        int relic1Dir=triAlg.preDir;
        triAlg.relic1Position(triAlg.posx,triAlg.posy);
        triAlg.booleanToString("O ");
        triAlg.printOut();

        GeneralAlgorithim alg3 = new GeneralAlgorithim(13, 8, -1);
        
        alg3.side=triAlg.side;
        alg3.startPosition(triAlg.startx, triAlg.starty);
        alg3.alg1Position(triAlg.alg1x, triAlg.alg1y);
        alg3.alg2Position(triAlg.alg2x, triAlg.alg2y);
        alg3.alg3Position(triAlg.alg3x, triAlg.alg3y);
        alg3.relic1Position(triAlg.r1x, triAlg.r1y);
        
        if (alg3.side==1){
            alg3.joinAlgWithPositons(triAlg.booleanMap, 0, 1);

        }
        else{
            alg3.joinAlgWithPositons(triAlg.booleanMap, 0, 2);
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


        GeneralAlgorithim starAlg = new GeneralAlgorithim(17, 8, -1);
        starAlg.pickSide(10,alg3.alg2x);
        starAlg.preDir = 0;
        starAlg.startPosition(alg3.startx,alg3.starty);
        starAlg.alg1Position(alg3.alg1x,alg3.alg1y);
        starAlg.alg2Position(alg3.alg2x,alg3.alg2y);
        starAlg.alg3Position(alg3.alg3x,alg3.alg3y);
        starAlg.relic1Position(alg3.r1x,alg3.r1y);
        starAlg.relic3Position(alg3.r3x,alg3.r3y);
        starAlg.APosition(alg3.ax,alg3.ay);
       
        
        if(starAlg.side==1){
            starAlg.joinAlgWithPositons(alg3.booleanMap, 0, 0);
            starAlg.startSetup(false,false);
            starAlg.setIfRepeatingWeights(false,true,false,true);
            starAlg.setDirectionWeights(0,3,3,3);
            

            System.out.println(starAlg.startx+4);
            for(int i = starAlg.startx+4; i>starAlg.alg2x;i--){
                starAlg.booleanMap[starAlg.alg2y][i]=true;
            }

            starAlg.posx=starAlg.startx+4;
            starAlg.setMovementRestrictions(-1,-1,starAlg.starty+1,starAlg.posx);
        }else{
            starAlg.joinAlgWithPositons(alg3.booleanMap, 0, 4);
            starAlg.startSetup(false,false);
            starAlg.setIfRepeatingWeights(false,true,false,true);
            starAlg.setDirectionWeights(0,3,3,3);
            

            System.out.println(starAlg.startx-4);
            for(int i = starAlg.startx-4; i<starAlg.alg2x;i++){
                starAlg.booleanMap[starAlg.alg2y][i]=true;
            }

            starAlg.posx=starAlg.startx-4;
            starAlg.setMovementRestrictions(-1,starAlg.posx,starAlg.starty+1,-1);
        }
       
        starAlg.booleanToString("O ");
        //alg2.printOut();
        starAlg.setYEndpoint(starAlg.starty+1);
        starAlg.run(starAlg.alg2y,starAlg.posx);
        starAlg.relic2Position(starAlg.posx,starAlg.posy);
        starAlg.booleanToString("O ");
        starAlg.printOut();

        GeneralAlgorithim dotAlg3 = new GeneralAlgorithim(13, 8, -1);
        dotAlg3.plainTextSetup();
        if(alg3.side==1){
            dotAlg3.joinAlgWithPositons(triAlg.preBooleanMap, 0, 1);
        }else{
            dotAlg3.joinAlgWithPositons(triAlg.preBooleanMap, 0, 2);
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
        
        //dotAlg3.printOut();

        //yes its out of order
        //originally 3 would of been generated later but there is a single 1/9000 edge case which made it so it has to generate before*
        GeneralAlgorithim triAlg2 = new GeneralAlgorithim(17, 8, -1);
        triAlg2.plainTextSetup();
        if(starAlg.side==1){
            triAlg2.joinAlgWithPositons(alg3.preBooleanMap, 0, 0);
        }else{
            triAlg2.joinAlgWithPositons(alg3.preBooleanMap, 0, 4);
        }
        triAlg2.startPosition(starAlg.startx,starAlg.starty);
        triAlg2.alg1Position(starAlg.alg1x,starAlg.alg1y);
        triAlg2.alg2Position(starAlg.alg2x,starAlg.alg2y);
        triAlg2.alg3Position(starAlg.alg3x,starAlg.alg3y);
        triAlg2.relic1Position(starAlg.r1x,starAlg.r1y);
        triAlg2.relic2Position(starAlg.r2x,starAlg.r2y);
        triAlg2.relic3Position(starAlg.r3x,starAlg.r3y);
        triAlg2.APosition(starAlg.ax,starAlg.ay);

        triAlg2.booleanToString("* ", starAlg.booleanMap);
        triAlg2.booleanToString("# ", starAlg.preBooleanMap);
        triAlg2.booleanToString("^ ");
        
        //triAlg2.printOut();

        //yes its out of order
        //originally 3 would of been generated later but there is a single 1/9000 edge case which made it so it has to generate before*
        GeneralAlgorithim dotAlg2 = new GeneralAlgorithim(17, 8, -1);
        dotAlg2.plainTextSetup();
        if(starAlg.side==1){
            dotAlg2.joinAlgWithPositons(dotAlg3.booleanMap, 0, 0);
        }else{
            dotAlg2.joinAlgWithPositons(dotAlg3.preBooleanMap, 0, 4);
        }
        dotAlg2.startPosition(starAlg.startx,starAlg.starty);
        dotAlg2.alg1Position(starAlg.alg1x,starAlg.alg1y);
        dotAlg2.alg2Position(starAlg.alg2x,starAlg.alg2y);
        dotAlg2.alg3Position(starAlg.alg3x,starAlg.alg3y);
        dotAlg2.relic1Position(starAlg.r1x,starAlg.r1y);
        dotAlg2.relic2Position(starAlg.r2x,starAlg.r2y);
        dotAlg2.relic3Position(starAlg.r3x,starAlg.r3y);
        dotAlg2.APosition(starAlg.ax,starAlg.ay);

        dotAlg2.booleanToString("* ", starAlg.booleanMap);
        dotAlg2.booleanToString("# ", starAlg.preBooleanMap);
        dotAlg2.booleanToString("^ ", triAlg2.booleanMap);
        dotAlg2.booleanToString(". ");
        
        //dotAlg2.printOut();
        

        GeneralAlgorithim balg = new GeneralAlgorithim(19, 15, -1);
        balg.startPosition(starAlg.startx,starAlg.starty);
        balg.alg1Position(starAlg.alg1x,starAlg.alg1y);
        balg.alg2Position(starAlg.alg2x,starAlg.alg2y);
        balg.alg3Position(starAlg.alg3x,starAlg.alg3y);
        balg.relic1Position(starAlg.r1x,starAlg.r1y);
        balg.relic2Position(starAlg.r2x,starAlg.r2y);
        balg.relic3Position(starAlg.r3x,starAlg.r3y);
        balg.APosition(starAlg.ax,starAlg.ay);
        
        balg.joinAlgWithPositons(starAlg.booleanMap, 7, 1);
        balg.textSetup();
        balg.booleanToString("O ");
        balg.printOut();
        for(int i=balg.starty-4;!balg.booleanMap[i][balg.startx] ; i++){
            balg.booleanMap[i][balg.startx] =true;
        }
        balg.BPosition(balg.startx,balg.starty-4);
        balg.booleanToString("O ");
        balg.printOut();


        GeneralAlgorithim tilAlgF = new GeneralAlgorithim(19, 15, -1);
        tilAlgF.side=triAlg.side;
        tilAlgF.preDir = 2;
        tilAlgF.startPosition(balg.startx,balg.starty);
        tilAlgF.alg1Position(balg.alg1x,balg.alg1y);
        tilAlgF.alg2Position(balg.alg2x,balg.alg2y);
        tilAlgF.alg3Position(balg.alg3x,balg.alg3y);
        tilAlgF.relic1Position(balg.r1x,balg.r1y);
        tilAlgF.relic2Position(balg.r2x,balg.r2y);
        tilAlgF.relic3Position(balg.r3x,balg.r3y);
        tilAlgF.APosition(balg.ax,balg.ay);
        tilAlgF.BPosition(balg.bx,balg.by);
        tilAlgF.joinAlgWithPositons(balg.booleanMap, 0, 0);
        tilAlgF.startSetup(false,false);
        tilAlgF.setIfRepeatingWeights(true,false,false,false);
        tilAlgF.posy=balg.by;
        if(tilAlgF.side==1){
            tilAlgF.setDirectionWeights(2,3,0,0);

            System.out.println(tilAlgF.startx+3);
            for(int i = tilAlgF.startx+1; i<tilAlgF.startx+3;i++){
                tilAlgF.booleanMap[tilAlgF.posy][i]=true;
            }
            tilAlgF.posx=tilAlgF.startx+3;

            tilAlgF.setMovementRestrictions(-1,-1,-1,-1);
            tilAlgF.setXEndpoint(tilAlgF.mapWidth-2);
        }else{
            tilAlgF.setDirectionWeights(2,0,0,3);

            System.out.println(tilAlgF.startx-3);
            for(int i = tilAlgF.startx-1; i>tilAlgF.startx-3;i--){
                tilAlgF.booleanMap[tilAlgF.posy][i]=true;
            }
            tilAlgF.posx=tilAlgF.startx-3;

            tilAlgF.setMovementRestrictions(-1,-1,-1,-1);
            tilAlgF.setXEndpoint(1);
        }
       
        tilAlgF.booleanToString("O ");
        
        tilAlgF.run(tilAlgF.posy,tilAlgF.posx);
        
        tilAlgF.relic4Position(tilAlgF.posx,tilAlgF.posy);
        tilAlgF.booleanToString("O ");
        tilAlgF.printOut();

        GeneralAlgorithim atAlgF = new GeneralAlgorithim(19, 15, -1);
        atAlgF.side=starAlg.side;
        atAlgF.preDir = 2;
        atAlgF.startPosition(balg.startx,balg.starty);
        atAlgF.alg1Position(balg.alg1x,balg.alg1y);
        atAlgF.alg2Position(balg.alg2x,balg.alg2y);
        atAlgF.alg3Position(balg.alg3x,balg.alg3y);
        atAlgF.relic1Position(balg.r1x,balg.r1y);
        atAlgF.relic2Position(balg.r2x,balg.r2y);
        atAlgF.relic3Position(balg.r3x,balg.r3y);
        atAlgF.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        atAlgF.APosition(balg.ax,balg.ay);
        atAlgF.BPosition(balg.bx,balg.by);
        atAlgF.joinAlgWithPositons(tilAlgF.booleanMap, 0, 0);
        atAlgF.startSetup(false,false);
        atAlgF.setIfRepeatingWeights(true,false,false,false);
        atAlgF.posy=balg.by-1;
        atAlgF.booleanMap[atAlgF.posy][balg.bx]=true;
        atAlgF.setDirectionWeights(2,3,0,3);
        atAlgF.setYEndpoint(1);
        if(atAlgF.side==1){
            atAlgF.posx=atAlgF.startx+1;
            atAlgF.booleanMap[atAlgF.posy][atAlgF.posx]=true;
            

            atAlgF.setMovementRestrictions(-1,atAlgF.posx+2,-1,atAlgF.posx);
        }else{
            atAlgF.posx=atAlgF.startx-1;
            atAlgF.booleanMap[atAlgF.posy][atAlgF.posx]=true;

            atAlgF.setMovementRestrictions(-1,atAlgF.posx,-1,atAlgF.posx-2);
        }
       
        atAlgF.booleanToString("O ");
        
        atAlgF.run(atAlgF.posy,atAlgF.posx);
        atAlgF.relic5Position(atAlgF.posx,atAlgF.posy);
        atAlgF.booleanToString("O ");
        atAlgF.printOut();

        GeneralAlgorithim endAlg = new GeneralAlgorithim(19, 15, -1);
        endAlg.startPosition(balg.startx,balg.starty);
        endAlg.side=triAlg.side;
        endAlg.posy=balg.by-2;
        endAlg.posx=balg.bx;
        endAlg.joinAlgWithPositons(atAlgF.booleanMap, 0, 0);
        endAlg.booleanMap[endAlg.posy][endAlg.posx]=true;
        if(endAlg.side==1){
            endAlg.posx++;
        }else endAlg.posx--;

        for(int i =endAlg.posy; i>=0;i-- ){
            endAlg.posy=i;
            endAlg.booleanMap[endAlg.posy][endAlg.posx]=true;
        }

        endAlg.textSetup();
        endAlg.booleanToString("e ");
        endAlg.printOut();













        GeneralAlgorithim starAlgF = new GeneralAlgorithim(19, 15, -1);
        starAlgF.plainTextSetup();
        
        starAlgF.joinAlgWithPositons(balg.preBooleanMap, 0, 0);
        
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
        
        //starAlgF.printOut();

        GeneralAlgorithim hashAlgF = new GeneralAlgorithim(19, 15, -1);
        hashAlgF.plainTextSetup();
        
        hashAlgF.joinAlgWithPositons(starAlg.preBooleanMap, 7, 1);
        
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
        
        //hashAlgF.printOut();

        GeneralAlgorithim triAlgF = new GeneralAlgorithim(19, 15, -1);
        triAlgF.plainTextSetup();
        
        triAlgF.joinAlgWithPositons(triAlg2.booleanMap, 7, 1);
        
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
        
        //triAlgF.printOut();

        GeneralAlgorithim endAlgIsolated = new GeneralAlgorithim(19, 15, -1);

        endAlgIsolated.plainTextSetup();
        endAlgIsolated.joinAlgWithPositons(endAlg.booleanMap, 0, 0);
        endAlgIsolated.startPosition(balg.startx,balg.starty);
        endAlgIsolated.alg1Position(balg.alg1x,balg.alg1y);
        endAlgIsolated.alg2Position(balg.alg2x,balg.alg2y);
        endAlgIsolated.alg3Position(balg.alg3x,balg.alg3y);
        endAlgIsolated.relic1Position(balg.r1x,balg.r1y);
        endAlgIsolated.relic2Position(balg.r2x,balg.r2y);
        endAlgIsolated.relic3Position(balg.r3x,balg.r3y);
        endAlgIsolated.APosition(balg.ax,balg.ay);
        endAlgIsolated.BPosition(balg.bx,balg.by);
        endAlgIsolated.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        endAlgIsolated.relic5Position(atAlgF.r5x,atAlgF.r5y);

        

        GeneralAlgorithim dotAlgF = new GeneralAlgorithim(19, 15, -1);
        dotAlgF.plainTextSetup();
        
        dotAlgF.joinAlgWithPositons(dotAlg2.booleanMap, 7, 1);
        
        dotAlgF.startPosition(balg.startx,balg.starty);
        dotAlgF.alg1Position(balg.alg1x,balg.alg1y);
        dotAlgF.alg2Position(balg.alg2x,balg.alg2y);
        dotAlgF.alg3Position(balg.alg3x,balg.alg3y);
        dotAlgF.relic1Position(balg.r1x,balg.r1y);
        dotAlgF.relic2Position(balg.r2x,balg.r2y);
        dotAlgF.relic3Position(balg.r3x,balg.r3y);
        dotAlgF.APosition(balg.ax,balg.ay);
        dotAlgF.BPosition(balg.bx,balg.by);
        dotAlgF.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        dotAlgF.relic5Position(atAlgF.r5x,atAlgF.r5y);

        

        
        dotAlgF.booleanMap[dotAlgF.alg1y][dotAlgF.alg1x]=false;
        

        
        
        //endAlgIsolated.printBooleanOnly("X ");
        endAlgIsolated.isolateMap(atAlgF.booleanMap);
        //endAlgIsolated.printBooleanOnly("+ ");
        
        //alg5.printBooleanOnly("X ");
        atAlgF.isolateMap(tilAlgF.booleanMap);
        //alg5.printBooleanOnly("+ ");

        //alg4.printBooleanOnly("X ");
        tilAlgF.isolateMap(balg.booleanMap);
        //alg4.printBooleanOnly("L ");

        //balg.printBooleanOnly("X ");
        balg.isolateMap(starAlgF.booleanMap);
        //balg.printBooleanOnly("B ");

        starAlgF.booleanMap[starAlgF.alg2y][starAlgF.alg2x]=false;

        //starAlgF.printBooleanOnly("X ");
        starAlgF.isolateMap(hashAlgF.booleanMap);
        //starAlgF.printBooleanOnly("* ");

        //hashAlgF.printBooleanOnly("X ");
        hashAlgF.isolateMap(triAlgF.booleanMap);
        //hashAlgF.printBooleanOnly("# ");

        //triAlgF.printBooleanOnly("X ");
        triAlgF.isolateMap(dotAlgF.booleanMap);
        //triAlgF.printBooleanOnly("^ ");

        //dotAlgF.printBooleanOnly("X ");

        triAlgF.booleanMap[triAlgF.r1y][triAlgF.r1x]=false;
        starAlgF.booleanMap[starAlgF.r2y][starAlgF.r2x]=false;
        hashAlgF.booleanMap[hashAlgF.r3y][hashAlgF.r3x]=false;
        hashAlgF.booleanMap[hashAlgF.ay][hashAlgF.ax]=false;
        balg.booleanMap[balg.by][balg.bx]=false;
        tilAlgF.booleanMap[tilAlgF.r4y][tilAlgF.r4x]=false;
        atAlgF.booleanMap[atAlgF.r5y][atAlgF.r5x]=false;

        dotAlgF.printBooleanOnly(". ");
        triAlgF.printBooleanOnly("^ ");
        hashAlgF.printBooleanOnly("# ");
        starAlgF.printBooleanOnly("* ");
        balg.printBooleanOnly("b ");
        tilAlgF.printBooleanOnly("L ");
        atAlgF.printBooleanOnly("+ ");


        
        dotAlgF.booleanToString("L ", tilAlgF.booleanMap);
        dotAlgF.booleanToString("b ", balg.booleanMap);
        dotAlgF.booleanToString("* ", starAlgF.booleanMap);
        dotAlgF.booleanToString("# ", hashAlgF.booleanMap);
        dotAlgF.booleanToString("^ ", triAlgF.booleanMap);
        dotAlgF.booleanToString(". ");
        dotAlgF.booleanToString("+ ", atAlgF.booleanMap);
        dotAlgF.booleanToString("e ", endAlgIsolated.booleanMap);

        endAlg.printBooleanOnly("X ");
        dotAlgF.printOut();

        //time for pillars!!!!

        GeneralAlgorithim pillarAlg = new GeneralAlgorithim(19, 15, -1);
        
        pillarAlg.startPosition(balg.startx,balg.starty);
        pillarAlg.alg1Position(balg.alg1x,balg.alg1y);
        pillarAlg.alg2Position(balg.alg2x,balg.alg2y);
        pillarAlg.alg3Position(balg.alg3x,balg.alg3y);
        pillarAlg.relic1Position(balg.r1x,balg.r1y);
        pillarAlg.relic2Position(balg.r2x,balg.r2y);
        pillarAlg.relic3Position(balg.r3x,balg.r3y);
        pillarAlg.APosition(balg.ax,balg.ay);
        pillarAlg.BPosition(balg.bx,balg.by);
        pillarAlg.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        pillarAlg.relic5Position(atAlgF.r5x,atAlgF.r5y);
        pillarAlg.plainTextSetup();

        pillarAlg.booleanToString("~ ", tilAlgF.booleanMap);
        pillarAlg.booleanToString("b ", balg.booleanMap);
        pillarAlg.booleanToString("* ", starAlgF.booleanMap);
        pillarAlg.booleanToString("# ", hashAlgF.booleanMap);
        pillarAlg.booleanToString("^ ", triAlgF.booleanMap);
        pillarAlg.booleanToString(". ", dotAlgF.booleanMap);
        pillarAlg.booleanToString("@ ", atAlgF.booleanMap);
        pillarAlg.booleanToString("e ", endAlgIsolated.booleanMap);

        for(int i = pillarAlg.r4y+1; !endAlg.booleanMap[i][pillarAlg.r4x];i++){
            pillarAlg.booleanMap[i][pillarAlg.r4x]=true;
            }
        
        if(triAlg.side==1){
            for(int j = 0; j<3;j++){
            for(int i = pillarAlg.r4y+1; i!=pillarAlg.starty+1;i++){
            pillarAlg.booleanMap[i][pillarAlg.r4x-j]=true;
            }
        }
            for(int i = pillarAlg.bx;i !=pillarAlg.r4x;i++){
                for(int j = pillarAlg.by; !endAlg.booleanMap[j][i];j--){
                    pillarAlg.booleanMap[j][i]=true;
                }
            }
        }else{
            for(int j = 0; j<3;j++){
            for(int i = pillarAlg.r4y+1; i!=pillarAlg.starty+1;i++){
            pillarAlg.booleanMap[i][pillarAlg.r4x+j]=true;
            }
        }
            for(int i = pillarAlg.bx;i !=pillarAlg.r4x;i--){
                for(int j = pillarAlg.by; !endAlg.booleanMap[j][i];j--){
                    pillarAlg.booleanMap[j][i]=true;
                }
            }
        }
        for(int i = 0; !endAlg.booleanMap[i][pillarAlg.bx];i++){
            pillarAlg.booleanMap[i][pillarAlg.bx]=true;
        }
        pillarAlg.booleanMap[pillarAlg.by-1][pillarAlg.bx+tilAlgF.side]=true;
        for(int i = 0; !endAlg.booleanMap[i][pillarAlg.bx+2*tilAlgF.side];i++){
            pillarAlg.booleanMap[i][pillarAlg.bx+2*tilAlgF.side]=true;
        }

    //j!=pillarAlg.r2x+2*starAlg.side
        for(int j = pillarAlg.bx+starAlg.side; j!=-1&&j!=pillarAlg.mapWidth; j+= starAlg.side){
            for(int i = 0; !endAlg.booleanMap[i][j]&&i!=pillarAlg.starty+1;i++){
                pillarAlg.booleanMap[i][j]=true;
            }
        }

        pillarAlg.booleanToString("| ");
        pillarAlg.printOut();
        pillarAlg.isolateMap(tilAlgF.booleanMap);
        pillarAlg.printOut();

        GeneralAlgorithim emptyAlg = new GeneralAlgorithim(19, 15, -1);
        emptyAlg.startPosition(balg.startx,balg.starty);
        emptyAlg.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        emptyAlg.plainTextSetup();

        for(int j = pillarAlg.bx+3*triAlg.side; j!=-1&&j!=pillarAlg.mapWidth; j+= triAlg.side){
            for(int i = 0; !endAlg.booleanMap[i][j]&&i!=pillarAlg.starty+1;i++){
                emptyAlg.booleanMap[i][j]=true;
            }
        }
        pillarAlg.booleanToString("  ", emptyAlg.booleanMap);
        pillarAlg.isolateMap(emptyAlg.booleanMap);
        pillarAlg.booleanToString("~ ", tilAlgF.booleanMap);
        pillarAlg.printOut();



         GeneralAlgorithim dotAlgFB = new GeneralAlgorithim(19, 15, -1);
        dotAlgFB.plainTextSetup();
        
        dotAlgFB.joinAlg(dotAlgF.booleanMap, 0, 0);
        dotAlgFB.joinAlg(balg.booleanMap, 0, 0);
        
        dotAlgFB.startPosition(balg.startx,balg.starty);
        dotAlgFB.alg1Position(balg.alg1x,balg.alg1y);
        dotAlgFB.alg2Position(balg.alg2x,balg.alg2y);
        dotAlgFB.alg3Position(balg.alg3x,balg.alg3y);
        dotAlgFB.relic1Position(balg.r1x,balg.r1y);
        dotAlgFB.relic2Position(balg.r2x,balg.r2y);
        dotAlgFB.relic3Position(balg.r3x,balg.r3y);
        dotAlgFB.APosition(balg.ax,balg.ay);
        dotAlgFB.BPosition(balg.bx,balg.by);
        dotAlgFB.relic4Position(tilAlgF.r4x,tilAlgF.r4y);
        dotAlgFB.relic5Position(atAlgF.r5x,atAlgF.r5y);


         Direction direction= new Direction();
        direction.findDirection(dotAlgFB.booleanMap,false);
        direction.printOutDir();

        direction.findDirection(starAlgF.booleanMap,true);
        direction.printOutDir();
        direction.findDirection(dotAlgFB.alg2y ,dotAlgFB.alg2x ,starAlgF.booleanMap ,dotAlgFB.booleanMap ,false);
        direction.merge(starAlgF.booleanMap,dotAlgFB.alg2y,dotAlgFB.alg2x,true);
        direction.printOutDir();

        direction.findDirection(dotAlgFB.r2y ,dotAlgFB.r2x ,starAlgF.booleanMap,true);
        direction.merge(starAlgF.booleanMap,dotAlgFB.r2y,dotAlgFB.r2x,true);
        direction.printOutDir();

        direction.findDirection(triAlgF.booleanMap,false);
        direction.findDirection(dotAlgFB.alg1y ,dotAlgFB.alg1x ,triAlgF.booleanMap ,dotAlgFB.booleanMap ,false);
        direction.merge(dotAlgFB.booleanMap,dotAlgFB.alg1y,dotAlgFB.alg1x,false);
        direction.printOutDir();
        
        System.out.println(relic1Dir);
        relic1Dir=2+relic1Dir-4*((relic1Dir)/2);
        System.out.println(relic1Dir);

        direction.roomDir[dotAlgFB.r1y][dotAlgFB.r1x]=relic1Dir+12;
        direction.merge(triAlgF.booleanMap,dotAlgFB.r1y,dotAlgFB.r1x,false);
        direction.printOutDir();

       direction.findDirection(hashAlgF.booleanMap,true);
        direction.findDirection(dotAlgFB.alg3y ,dotAlgFB.alg3x ,hashAlgF.booleanMap ,dotAlgFB.booleanMap ,false);
        direction.merge(hashAlgF.booleanMap,dotAlgFB.alg3y,dotAlgFB.alg3x,true);
        direction.printOutDir();

        direction.findDirection(dotAlgFB.r3y ,dotAlgFB.r3x ,hashAlgF.booleanMap,true);
        direction.merge(hashAlgF.booleanMap,dotAlgFB.r3y,dotAlgFB.r3x,true);
        direction.printOutDir();

        direction.findDirection(dotAlgFB.r3y-1 ,dotAlgFB.r3x,dotAlgFB.booleanMap ,hashAlgF.booleanMap,true);
        //r3 is just going to be left as corner due to unique behavior of it's relic room

        //if(direction.roomDir[hashAlgF.r3y][hashAlgF.r3x]==8){
            //direction.roomDir[hashAlgF.r3y][hashAlgF.r3x]=13;
        //}

        direction.roomDir[hashAlgF.ay][hashAlgF.ax]=4;
        direction.merge(hashAlgF.booleanMap,hashAlgF.ay,hashAlgF.ax,true);
        direction.printOutDir();

        direction.findDirection(tilAlgF.booleanMap,false);
        direction.findDirection(dotAlgFB.by ,dotAlgFB.bx ,tilAlgF.booleanMap ,dotAlgFB.booleanMap ,false);
        direction.merge(tilAlgF.booleanMap,dotAlgFB.by,dotAlgFB.bx,false);
        direction.printOutDir();

        direction.findDirection(dotAlgFB.r4y ,dotAlgFB.r4x ,tilAlgF.booleanMap,false);
        direction.merge(tilAlgF.booleanMap,dotAlgFB.r4y,dotAlgFB.r4x,false);
        direction.printOutDir();
        

        direction.findDirection(atAlgF.booleanMap,false);
        direction.findDirection(dotAlgFB.by ,dotAlgFB.bx ,atAlgF.booleanMap ,dotAlgFB.booleanMap ,false);
        direction.merge(atAlgF.booleanMap,dotAlgFB.by,dotAlgFB.bx,false);
        direction.printOutDir();

        direction.findDirection(dotAlgFB.r5y ,dotAlgFB.r5x ,atAlgF.booleanMap,false);
        direction.merge(atAlgF.booleanMap,dotAlgFB.r5y,dotAlgFB.r5x,false);
        direction.printOutDir();

        direction.findDirection(endAlgIsolated.booleanMap,false);

        if(triAlg.side==1){
            direction.roomDir[hashAlgF.by][hashAlgF.bx]=3;
            direction.roomDir[hashAlgF.by-1][hashAlgF.bx]=5;
            direction.roomDir[hashAlgF.by-2][hashAlgF.bx]=9;
            direction.roomDir[hashAlgF.r3y][hashAlgF.r3x]=13;
            direction.roomDir[hashAlgF.r3y-1][dotAlgFB.r4x]=11;
        }else{
            direction.roomDir[hashAlgF.by][hashAlgF.bx]=5;
            direction.roomDir[hashAlgF.by-1][hashAlgF.bx]=3;
            direction.roomDir[hashAlgF.by-2][hashAlgF.bx]=10;
            direction.roomDir[hashAlgF.r3y][hashAlgF.r3x]=15;
            direction.roomDir[hashAlgF.r3y-1][dotAlgFB.r4x]=8;
        }
        direction.findDirection(7, 9,dotAlgFB.booleanMap,starAlgF.booleanMap, false);
        //direction.roomDir[10][9]=7;
        
        direction.printOutDir();

        directionMap=direction.roomDir;
        roomTypeMap= new int[15][19];

         for(int i =0; i<15; i++){
            for (int j =0; j<19; j++){

                    roomTypeMap[i][j]=0;
            }

        }
        
        //abilMapAdd(triAlgF.booleanMap,1);
        //abilMapAdd(starAlgF.booleanMap,2);
        //abilMapAdd(hashAlgF.booleanMap,3);
        //abilMapAdd(balg.booleanMap,6);
        //abilMapAdd(tilAlgF.booleanMap,4);
        //abilMapAdd(atAlgF.booleanMap,5);
        abilMapAdd(pillarAlg.booleanMap,1);
        abilMapAdd(emptyAlg.booleanMap,2);
        

        for (int[] i : roomTypeMap) {
            for (int j : i) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
    public void abilMapAdd(boolean[][] map, int key){
        for(int i =0; i<15; i++){
            for (int j =0; j<19; j++){
                if(map[i][j]){
                    roomTypeMap[i][j]=key;
                }
                
            }

        }
    }
}

