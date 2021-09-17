import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class Node {
    Node head;
    Node next = null;
    Node previous = null;
    int reference;
    String name;

    public Node(int reference, String name) {
        this.head = this;
        this.reference = reference;
        this.name = name;
    }

    void addNode(int reference, String name) {
        Node end = new Node(reference, name);
        System.out.println(end.reference);
        Node currentNode = this;
        System.out.println(currentNode.reference);
        while(currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = end;
        currentNode.next.previous = currentNode;
    }

    void traverseList() {
        Node currentNode = head;
        while(currentNode.next != null) {
            System.out.println(currentNode.name + " " + currentNode.reference);
            currentNode = currentNode.next;
            if (currentNode.next == null) {
                System.out.println(currentNode.name + " " + currentNode.reference + " " + currentNode.previous.name);
            }
        }
    }
    void swapNodeForward() {
        Node currentNode = this;
        try {
            Node temp = currentNode.next;
            currentNode.next = currentNode;
            currentNode = temp;
        } catch(NullPointerException exception) {

        }
    }


    public static void main(String[] args) {
        String name1 = "Nelson";
        String name2 = "Bob";
        String name3 = "Groucho";

        Node head = new Node(32, name1);
        head.addNode(12, name2);
        head.addNode(82, name3);
        head.traverseList();


    }

}
