import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends JFrame {

    final static int ServerPort = 8818;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private JFrame clientWindow;
    private JTextArea clientTextArea;
    private JTextField clientTextEntry;
    private Socket clientSocket;

    public Client() {

        this.clientWindow = new JFrame();
        this.clientTextArea = new JTextArea();
        this.clientTextEntry = new JTextField();
        clientTextEntry.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        messageAll(actionEvent.getActionCommand());
                        clientTextEntry.setText("");
                    }
                }
        );
        this.clientWindow.add(clientTextEntry, BorderLayout.SOUTH);
        this.clientWindow.add(clientTextArea);
        this.clientWindow.setSize(500,500);
        this.clientWindow.setVisible(true);
        this.clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            this.clientSocket = new Socket("localhost", ServerPort);
            inputStream = new BufferedInputStream((clientSocket.getInputStream()));
            outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(inputStream);
           // DataOutputStream out = new DataOutputStream(outputStream);

            clientTextArea.append("You're now connected \n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while(true) {
                if (reader.ready()) {
                    clientTextArea.append(reader.readLine() + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void messageAll(String actionCommand) {
        DataOutputStream out = new DataOutputStream(outputStream);
        Writer writer = new OutputStreamWriter(out);
        try {
            writer.write(actionCommand + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Client newClient = new Client();

    }


}