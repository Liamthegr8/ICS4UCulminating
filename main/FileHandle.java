import java.io.*;
import java.awt.*;
public class FileHandle{
    String words;
    String line;
    File room;
    FileReader readStream;
    BufferedReader in;

    FileHandle() {
        
    }
    public Room findRoom(String roomName){
        return roomStringToRoomObject(roomName, findRoomString(roomName));
    }


    public String findRoomString(String roomName) {
        try {
            room = new File("main\\assets\\roomData.txt");
            readStream = new FileReader(room);
	        in = new BufferedReader(readStream);
            
            while((line = in.readLine()) != null){
               String name = line.substring(line.indexOf('(')+1,line.indexOf(')'));
               //System.out.println(name);
               if(name.contentEquals(roomName)){
                    return line.substring(line.indexOf(')')+1);
               }

            }
            in.close();
            readStream.close();
        }
        catch(IOException e) {
			System.out.println("Error accessing file");
			System.err.println("IOException: "+ e.getMessage());
		}
        return null;
    }
    public Room roomStringToRoomObject(String roomName, String roomData){
        int index=0;
        Room room = new Room(roomName);
        while(roomData.charAt(index)!=';'){
             if(roomData.charAt(index)=='_'){
                int tileID = Integer.parseInt(roomData.substring(index+1,index+3));
                int tileParamLength=0;
                while(roomData.charAt(index+4+tileParamLength)!='>'){
                    tileParamLength++;
                }
                String tileParam= roomData.substring(index+4,index+5+tileParamLength);
                //System.out.println(tileParam);
                switch (tileID){
                    case 0:
                                room.setEnterRoomTransitionColor(tileParameter(tileParam, 1),new Color(tileParameter(tileParam, 2),tileParameter(tileParam, 3),tileParameter(tileParam, 4)));
                                break;
                                
                    case 1:
                               room.addTileAt(new PlatformTile(tileParameter(tileParam, 1), tileParameter(tileParam, 2), tileParameter(tileParam, 3), tileParameter(tileParam, 4), tileParameter(tileParam, 5)));
                               break;
                    case 2: 
                               room.addTileAt(new SpikeTile(tileParameter(tileParam, 1), tileParameter(tileParam, 2), tileParameter(tileParam, 3), tileParameter(tileParam, 4), tileParameter(tileParam, 5)));
                               break;
                    case 3:
                               room.addTileAt(new MovingPlatformTile(tileParameter(tileParam, 1), tileParameter(tileParam, 2), tileParameter(tileParam, 3), tileParameter(tileParam, 4), tileParameter(tileParam, 5), tileParameter(tileParam, 6), tileParameter(tileParam, 7), tileParameter(tileParam, 8)));
                               break; 


                }
            }
            index++;
        }
        return room;
    }
    //tilestring is your line of text inside the <> for your tile
    //param index is which parameter do you want for your tile
    public int tileParameter(String tileString, int paramIndex){
        int end =-1;
        int start =0;
        for(int i =0; i<paramIndex; i++){
            end++;
            start=end;
            while(tileString.charAt(end) != ','&&tileString.charAt(end) != '>'){
            end++;
            }

        }
        return Integer.parseInt(tileString.substring(start,end));
        
    }

}
