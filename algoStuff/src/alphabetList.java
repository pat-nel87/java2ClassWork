import java.lang.reflect.Array;
import java.util.*;

public class alphabetList {

    public LinkedList alphaList;
    private Hashtable alphaFind;
    private Hashtable alphaFilter;
    private Array alphaArray;

    alphabetList() {
        this.alphaList = new LinkedList();
        this.alphaFind = new Hashtable();
        this.alphaFilter = new Hashtable();
    }

    private void removeDuplicate() {
        int j = alphaList.size() - 1;
        for (int i = 0; i < alphaList.size() - 1; i++) {
            while(j != i) {
                if (alphaList.get(i).equals(alphaList.get(j))) {
                    alphaList.remove(j);
                }
                j--;
            }
            j = alphaList.size() - 1;
        }
    }

    public void firstMethod(String name, int reference) {
        alphaList.add(reference, name);
        removeDuplicate();
        Collections.sort(alphaList);
        System.out.println(alphaList);
    }

    public static void main(String[] args) {
        String name1 = "Nelson";
        String name2 = "Bob";
        String name3 = "Groucho";

        alphabetList test = new alphabetList();
        test.firstMethod(name1, 0);
        test.firstMethod(name2,1);
        test.firstMethod(name1,2);
        test.firstMethod(name3,3);
        test.firstMethod(name1,4);

    }



}
