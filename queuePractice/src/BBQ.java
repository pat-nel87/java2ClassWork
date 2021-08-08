import java.util.LinkedList;
import java.util.Queue;

public class BBQ {

    public static void main(String[] args) {
        Queue<String> bbqLine = new LinkedList<String>();

        bbqLine.add("Jackson");
        bbqLine.add("Tyreek");
        bbqLine.add("Susan");
        System.out.println(bbqLine);

        System.out.println(bbqLine.poll());
        System.out.println(bbqLine);

        System.out.println(bbqLine.peek());

        System.out.println(bbqLine.toArray()[0]);

    }
}
