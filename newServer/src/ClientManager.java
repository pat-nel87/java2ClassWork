import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    final String userName;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    Socket socket;
    boolean loggedOn;
    private UserSessionManager usersOnline;
    private JFrame serverMessageFrame;
    private JTextArea serverMessageDiag;
    private JTextField Response;
    private ArrayList<ClientManager> clientsList;

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
                        String message =actionEvent.getActionCommand();
                        serverMessageDiag.append("Server: " + message + "\n");
                        try {
                            sendMessagePacket(message, "Server", userName, 1, socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Response.setText("");

                        // writeToOutputStream(actionEvent.getActionCommand());
                        /*serverMessageDiag.append("Server: " + actionEvent.getActionCommand());
                        Response.setText(""); */
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
        while(true) {
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
               Object newObj = objIn.readObject();
               if (newObj instanceof MessagePacket) {
                 //  MessagePacket newMessage = (MessagePacket) objIn.readObject();
                    MessagePacket newMessage = (MessagePacket) newObj;
                    handleMessagePacket(newMessage);
               }
               if (newObj instanceof UserSessionManager) {
                   setUserList((UserSessionManager) newObj);

               }
            } catch (EOFException e) {
                e.printStackTrace();
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public void handleMessagePacket(MessagePacket messageIn) throws IOException {
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
                /* Response.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                // writeToOutputStream(actionEvent.getActionCommand());
                                String message =actionEvent.getActionCommand();
                                serverMessageDiag.append("Server: " + message );
                                try {
                                    sendMessagePacket(message, "Server", userName, 1, socket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Response.setText("");
                            }
                        }
                ); */
                break;
            }
            case 2: {
                System.out.println("Bacon trapped and ready for crisping");
                System.out.println("\n " + messageIn.getMessage());
                clientToClient(messageIn);

                break;
            }
        }
    }

    private void clientToClient(MessagePacket messageIn) throws IOException {
        System.out.println(messageIn.getSendTo());
        String sendTo = messageIn.getSendTo();
        System.out.println(sendTo + "\n");

        String message = messageIn.getMessage();
        String sender = messageIn.getSender();
        for (ClientManager clients : clientsList ) {
            System.out.println(clients.userName + "\n");
            String clientCheck = (String) clients.userName;
            if (clientCheck.equals(sendTo))
                {
                 //   System.out.println("If statement hits");
                    sendMessagePacket(messageIn.getMessage(), messageIn.getSender(), sendTo, 2, clients.socket);
                }
        }

    }

    public void sendMessagePacket(String message, String userName, String sendTo, int packetHeader, Socket socket) throws IOException {
        /* creates and sends a messagePacket to desired location  */
        MessagePacket newMessage = new MessagePacket(message, userName, sendTo, packetHeader);
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.writeObject(newMessage);
        objOut.flush();

    }


    public void setUserList(UserSessionManager usersOnline ) { this.usersOnline = usersOnline; }
    public void getUserList() {
      //  writeToOutputStream("\n Current Users Online: ");

    //    for (ClientManager clients : userList) {
    //    writeToOutputStream(clients.userName);
    //    }
    }

    public void setClientsList(ArrayList clientsList) {
        this.clientsList = clientsList;
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
