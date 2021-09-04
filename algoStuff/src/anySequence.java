import java.util.LinkedList;

public class anySequence {

    private CharSequence inputSequence;
    private StringBuilder outputBuilder;

    anySequence(CharSequence inputSequence) {

        this.inputSequence = inputSequence;
        this.outputBuilder = new StringBuilder();

    }
     public CharSequence outputForwardsAndBackwards() {
        for(int i = 0; i < inputSequence.length(); i++) {
            System.out.println(inputSequence.charAt(i) + "\n");
        }
        for(int i=inputSequence.length()-1; i > -1; i--) {
            System.out.println(inputSequence.charAt(i) + "\n");
            outputBuilder.append(inputSequence.charAt(i));
        }

        return outputBuilder;
    }

    public static void main(String[] args) {

        anySequence test = new anySequence("hello");
        test.outputForwardsAndBackwards();

    }


}
