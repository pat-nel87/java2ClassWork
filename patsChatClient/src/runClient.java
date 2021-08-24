import javax.swing.*;

public class runClient {

    public static void main(String[] args) {
        client myClient;
        myClient = new client("192.168.1.181");
        myClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myClient.runClient();

    }
}
