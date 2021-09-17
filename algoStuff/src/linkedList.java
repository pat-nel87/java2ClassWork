public class linkedList {

    public Node head;
    public int traversalReference;

    public void setHead(Node newHead) { this.head = newHead; }
    public Node returnHead() {return this.head; }

    public void setTraversalReference(int newReference) { this.traversalReference = newReference; }
    public int getTraversalReference() { return this.traversalReference; }

    public void firstMethod(String name, linkedList list) {
        Node newNode = new Node(name);

    }

    public void addNode(Node newNode) {

    }

    public class Node {
        public String name;
        public Node next = null;

        public Node(String name) {
            this.name = name;
        }

        public Node(String name, Node next) {
            this.name = name;
            this.next = next;
        }
    }
}
