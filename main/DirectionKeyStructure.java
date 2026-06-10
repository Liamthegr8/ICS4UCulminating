import java.util.*;
//kind of a cross breed of a hashmap and an arraylist
//positons dont truley matter as they are selected from randomly anyways
public class DirectionKeyStructure {
    ArrayList<Integer> dirKeyList;
    ArrayList<String> roomTypeList;
    ArrayList<String> roomNameList;

    DirectionKeyStructure(){
        dirKeyList = new ArrayList<Integer>();
        roomTypeList = new ArrayList<String>();
        roomNameList = new ArrayList<String>();
    }
    public void add(int dirKey, String roomName, String roomType){
        dirKeyList.add(dirKey);
        roomNameList.add(roomName);
        roomTypeList.add(roomType);
    }
    public String generate(){
        Random r = new Random();
        System.out.println(roomNameList.size());
        return get(r.nextInt(roomNameList.size()));
    }
    public String generate(int dirKey, int roomType){
        System.out.printf("\nkey: %d, roomType: %d\n",dirKey,roomType);
        DirectionKeyStructure roomsAvailType = new DirectionKeyStructure();
        roomsAvailType = checkRoomType(roomType);
        DirectionKeyStructure roomsAvail = new DirectionKeyStructure();
        roomsAvail = roomsAvailType.checkDirKey(dirKey);
        return roomsAvail.generate();
    }
    public String get(int index){
        return roomNameList.get(index);
    }
    public int getDirKey(String roomName){
        int i =roomNameList.indexOf(roomName);
        return dirKeyList.get(i);
    }
    public String getRoomType(String roomName){
        int i =roomNameList.indexOf(roomName);
        return roomTypeList.get(i);
    }
    public void removeRoom(String roomName){
        int i =roomNameList.indexOf(roomName);
        dirKeyList.remove(i);
        roomTypeList.remove(i);
        roomNameList.remove(i);
    }
    public DirectionKeyStructure checkDirKey(int dirKey){
        DirectionKeyStructure result = new DirectionKeyStructure();
        for(String roomName: roomNameList){
            if (dirKey== getDirKey(roomName)){
                result.add(getDirKey(roomName),roomName,getRoomType(roomName));
            }
        }
        return result;
    }
    public DirectionKeyStructure checkRoomType(int roomType){
        DirectionKeyStructure result = new DirectionKeyStructure();
        for(String roomName: roomNameList){
            String roomTypeString=getRoomType(roomName);
            for(int i =1; !roomTypeToIntEnd(roomTypeString, i-1);i++){
                if (roomType== roomTypeToInt(roomTypeString, i)){
                result.add(getDirKey(roomName),roomName,getRoomType(roomName));
                break;
                }
            }
        }
        return result;
    }
    public int roomTypeToInt(String roomTypeString, int paramIndex){
        int end =-1;
        int start =0;
        
        for(int i =0; i<paramIndex; i++){
            end++;
            start=end;
            while(roomTypeString.charAt(end) != '/'&&roomTypeString.charAt(end) != ':'){
            end++;
            }

        }
        return Integer.parseInt(roomTypeString.substring(start,end));
        
    }
    public boolean roomTypeToIntEnd(String roomTypeString, int paramIndex){
        int end =-1;
        int start =0;
        for(int i =0; i<paramIndex; i++){
            end++;
            start=end;
            while(roomTypeString.charAt(end) != '/'&&roomTypeString.charAt(end) != ':'){
            end++;
            }

        }
        if(end==-1){
            return false;
        }
        if(roomTypeString.charAt(end) == ':'){
            return true;
        }
        else return false;
        
    }
}
