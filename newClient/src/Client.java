import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends JFrame {

    final static int ServerPort = 8818;
    private OutputStream outputStream;
    private InputStream inputStream;
    private JFrame clientWindow;
    private JTextArea clientTextArea;
    private Socket clientSocket;

    public Client() {

        this.clientWindow = new JFrame();
        this.clientTextArea = new JTextArea();
        this.clientWindow.add(clientTextArea);
        this.clientWindow.setSize(500,500);
        this.clientWindow.setVisible(true);
        this.clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            this.clientSocket = new Socket("localhost", ServerPort);
            clientTextArea.append("You're now connected \n");
            InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream(), "UTF8");
            while(true) {
                if (reader.ready()) {
                    clientTextArea.append("Reader Ready");
                    int t;
                    StringBuilder str = new StringBuilder();
                    while ((t = reader.read()) != -1) {
                        char r = (char) t;
                        str.append(r);
                    }
                    clientTextArea.append(str.toString());
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {
       Client newClient = new Client();

    }


}