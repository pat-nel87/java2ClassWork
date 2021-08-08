// recursive palindromic string checker 
public class palString {

    public static int count;
    public static int revCount;
    public static boolean conditionCheck;

    public void palCheck(String input) {
        if (conditionCheck == false) {
            System.out.println("Not a palindrome!");
        } else if (count == input.length()) {
            System.out.println("Is a palindrome!");
        }
        else if (input.charAt(count) == input.charAt(revCount - 1)) {
            revCount--;
            count++;
            palCheck(input);
        } else {
            conditionCheck = false;
            palCheck(input);
        }

    }
    palString(String input){
        this.revCount = input.length();
        this.count = 0;
        this.conditionCheck = true;
        palCheck(input);
    }

    public static void main(String[] args) {

        palString test = new palString("racecar");

    }

}
