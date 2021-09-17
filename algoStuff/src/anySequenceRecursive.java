public class anySequenceRecursive {

    private CharSequence inputSequence;
    private StringBuilder outputBuilder;
    private boolean tf;
    private int counter;
    private int length;
    private int indication;

    anySequenceRecursive(CharSequence inputSequence) {


        this.counter = 0;
        this.indication = 0;
        this.inputSequence = inputSequence;
        this.length = inputSequence.length();
        this.outputBuilder = new StringBuilder();
        this.tf = false;

    }

    public void forwardsAndBackwards() {

        if (length == indication)  {
            if(!tf) {
                /* first base case
                switches recursive method to printing
                the char stream backwards using tf
                as a flag.
                * */
                tf = true;
                length = inputSequence.length();
                counter = inputSequence.length()-1;
                indication = (inputSequence.length() * -1) + 1;
            }
        }
        if (counter == -1) {
            /*
            2nd base case occurs when backwards printout is over
            returns the charstream backwards
             */
            getOutput();
            System.exit(0);
        }
        System.out.println(inputSequence.charAt(counter));
        if(!tf) {
            ++counter;
        } else {
            outputBuilder.append(inputSequence.charAt(counter));
            --counter;
        }
        --length;
        forwardsAndBackwards();
    }

    public CharSequence getOutput() {
        System.out.println("final CharSequence Returned: " + outputBuilder);
        return this.outputBuilder; }

    public static void main(String[] args) {

        anySequenceRecursive test = new anySequenceRecursive("watermelon");
        test.forwardsAndBackwards();

    }





}
