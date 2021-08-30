import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    private String userName;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    Socket socket;
    boolean loggedOn;
    private ArrayList<ClientManager> userList;
    private JFrame serverMessageFrame;
    private JTextArea serverMessageDiag;
    private JTextField Response;

    public ClientManager(String userName, Socket socket, OutputStream out, InputStream in ) {
        this.userName = userName;
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(out);
        this.dataInputStream = new DataInputStream(in);
        this.loggedOn = true;
        this.serverMessageFrame = new JFrame();
        serverMessageFrame.setSize(200, 200);
        serverMessageFrame.setVisible(false);
        this.serverMessageDiag = new JTextArea();
        this.Response = new JTextField();
        Response.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        writeToOutputStream(actionEvent.getActionCommand());
                        serverMessageDiag.append("Server: " + actionEvent.getActionCommand());
                        Response.setText("");
                    }
                }
        );
        serverMessageFrame.add(serverMessageDiag);
        serverMessageFrame.add(Response, BorderLayout.SOUTH);
        serverMessageFrame.setTitle("Message from " + userName);
        serverMessageDiag.append(" \n");
    }

    @Override
    public void run() {
        getUserList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));

        while(true) {
            //Checks for strings from client to display as messages.
            try {
               /*
               Deprecated method for checking for messages,
               serializing the messagePackets works better
               thus far.
               //if(reader.ready()) {
                //    serverMessageFrame.setVisible(true);
                //    serverMessageDiag.append(userName + ": " + reader.readLine() + "\n");
                //} //else {
                */
                        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
                        MessagePacket newMessage = (MessagePacket) objIn.readObject();

                        handleMessagePacket(newMessage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public void handleMessagePacket(MessagePacket messageIn) {
        /*
        routes the messages to the reciever via
        the packetHeader integer
        case 1, send message directly to server
        case 2, sends message through server from client to another.
        */
        switch (messageIn.getPacketHeader()) {
            case 1: {
                //System.out.println("Message is for Server");
                serverMessageFrame.setVisible(true);
                serverMessageDiag.append(messageIn.getSender() + ": " + messageIn.getMessage() + "\n");
                break;
            }
            case 2: {
                break;
            }
        }
    }


    public void setUserList(ArrayList<ClientManager> users) { this.userList = users; }
    public void getUserList() {
        writeToOutputStream("\n Current Users Online: ");

        for (ClientManager clients : userList) {
        writeToOutputStream(clients.userName);
        }
    }

    public void writeToOutputStream(String message) {
        Writer writer = new OutputStreamWriter(this.dataOutputStream);
        try {
            writer.write("\n" + message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
