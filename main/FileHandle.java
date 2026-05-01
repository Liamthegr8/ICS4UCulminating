import java.io.*;
import java.util.*;
public class FileHandle{
    String words;
    String line;
    File room;
    FileReader readStream;
    BufferedReader in;

    FileHandle() {
        
    }

    public void readAll() {
        iniReader();
        try {
			
			System.out.println("reading from thingy now");
			
			while((words=in.readLine())!=null) {
				System.out.println(words);
			}
			in.close();
			readStream.close();
		}
        catch(IOException e) {
			System.out.println("Error accessing file");
			System.err.println("IOException: "+ e.getMessage());
		}
        closeReader();
    }

    public String findRoom(String roomName) {
        try {
            room = new File("main\\assets\\roomData.txt");
            readStream = new FileReader(room);
	        in = new BufferedReader(readStream);
            while((line = in.readLine()) != null){
                if (line.contains(roomName)) {
                    return line;
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
    private void iniReader() {
        try {
            room = new File("roomData.txt");
            readStream = new FileReader(room);
	        in = new BufferedReader(readStream);
        }
        catch(IOException e) {
			System.out.println("Error accessing file");
			System.err.println("IOException: "+ e.getMessage());
		}
    }
    private void closeReader(){
        try {
            in.close();
            readStream.close();
        }
        catch(IOException e) {
			System.out.println("Error accessing file");
			System.err.println("IOException: "+ e.getMessage());
		} 
    }
}
