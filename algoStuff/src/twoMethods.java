import java.util.Collections;
import java.util.LinkedList;
import

public class twoMethods {

    public LinkedList<String> myList;

    public void firstMethod(String name, LinkedList<String> listToAdd) {
        if(listToAdd.contains(name)) {
            // filters out duplicate names
        } else {
            //adds to list
            listToAdd.add(name);
            //sorts list
            Collections.sort(listToAdd);
            //assigns instance member myList to be equivalent to
            // main method's list
            myList = listToAdd;
        }
    }

    public int secondMethod(String name) {
        //returns "reference" address where name is stored
        //in LinkedList
        return myList.indexOf(name);
    }

    public twoMethods() {
        this.myList = new LinkedList<String>();

    }

    public static void main(String[] args) {
        LinkedList<String> testList = new LinkedList();
        twoMethods newTest = new twoMethods();

        String name1 = "Harpo";
        String name2 = "Zeppo";
        String name3 = "Chico";
        String name4 = "Groucho";
        String name5 = "Gummo";
        // duplicate names are discarded when attempting to add to LinkedList
        newTest.firstMethod(name1, testList);
        newTest.firstMethod(name2, testList);
        newTest.firstMethod(name1, testList);
        newTest.firstMethod(name3, testList);
        newTest.firstMethod(name2, testList);
        newTest.firstMethod(name4, testList);
        newTest.firstMethod(name2, testList);
        newTest.firstMethod(name5, testList);
        System.out.println(testList);
        System.out.println(newTest.secondMethod(name1));
    }
}
