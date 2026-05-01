public class test {
    public static void main(String[] args) {
        FileHandle x = new FileHandle();
        System.out.println(x.findRoom("def"));
        System.out.println(x.findRoom("abc"));
        int test1 = x.tileParameter("0123,034>", 1);
        int test2 = x.tileParameter("0123,034>", 2);
        System.out.println(test1);
        System.out.println(test2);
        System.out.println("GRAND TEST");
        x.roomStringToRoomObject("abc", x.findRoom("abc"));
        
    }
}
