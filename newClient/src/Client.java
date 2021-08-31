import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client extends JFrame {

    final static int ServerPort = 8818;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private JFrame clientWindow;
    private JTextArea clientTextArea;
    private JTextField clientTextEntry;
    private Socket clientSocket;
    private JFrame newMessageFrame;
    private JTextArea newMessageDiag;
    private JTextField newMessageEntry;
    private String myUserName;

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

        this.newMessageFrame = new JFrame();
        this.newMessageDiag = new JTextArea();
        this.newMessageEntry = new JTextField();
        this.newMessageFrame.add(newMessageDiag);
        this.newMessageFrame.add(newMessageEntry, BorderLayout.SOUTH);
        this.newMessageFrame.setTitle("Talking to Server");
        newMessageEntry.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String message = actionEvent.getActionCommand();
                        newMessageDiag.append(myUserName + ": " + message + "\n");
                        newMessageEntry.setText("");
                        try {
                            sendMessage(getMyUserName(), message, "Server", 1 );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        this.newMessageFrame.setSize(200, 200);
        this.newMessageFrame.setVisible(false);

        try {
            this.clientSocket = new Socket("localhost", ServerPort);
           // inputStream = new BufferedInputStream((clientSocket.getInputStream()));
           // outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
           // DataInputStream in = new DataInputStream(inputStream);
           // DataOutputStream out = new DataOutputStream(outputStream);

            clientTextArea.append("You're now connected \n");
           // BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String test1 = "test1";
            String test2 = "test2";
            String test3 = "test3";
            sendMessage(test1, test2, test3, 1);

            while(true) {
               /* if (reader.ready()) {
                    clientTextArea.append(reader.readLine() + "\n");
                } */
                try {
                    ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
                    MessagePacket newMessage = (MessagePacket) objIn.readObject();
                    handleMessagePacket(newMessage);

                } catch (EOFException ex) {
                    ex.printStackTrace();
                    break;
                } catch (StreamCorruptedException s) {
                    s.printStackTrace();
                    continue;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
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

    private void sendMessage(String userName, String message, String sendTo, int packetHeader) throws IOException {
        /* Sends Message to Server to be re routed to proper client.
         PacketHeader sends integers 1 or 2
         1 means message is for server
         2 means message is for another client
         Server will handle request accordingly
        */
        ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        MessagePacket newMessage = new MessagePacket(message, userName, sendTo, packetHeader);
        objOut.writeObject(newMessage);
        objOut.flush();
    }
    public void handleMessagePacket(MessagePacket messageIn) {
        /*
        routes the messages to the reciever via
        the packetHeader integer
        case 1, message is from Server
        case 2, message is from other client.
        */
        switch (messageIn.getPacketHeader()) {
            case 1: {
                if (messageIn.getActiveMessage() == false) {
                    messageIn.setActiveMessage(true);
                    setMyUserName(messageIn.getSendTo());
                }
                //System.out.println("Message is for Server");
                newMessageFrame.setVisible(true);
                newMessageDiag.append(messageIn.getSender() + ": " + messageIn.getMessage() + "\n");

            }
            case 2: {
                break;
            }
        }
    }

    public void setMyUserName(String name) { this.myUserName = name; }
    public String getMyUserName() { return this.myUserName; }


    public static void main(String[] args) {
       Client newClient = new Client();

    }

}