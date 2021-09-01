import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client extends JFrame {

    final static int ServerPort = 8818;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private JFrame clientWindow;
    private JPanel clientPanel;
    private JList clientList;
    private DefaultListModel<String> clientListModel;
    private JTextArea clientTextArea;
    private JTextField clientTextEntry;
    private Socket clientSocket;
    private JFrame newMessageFrame;
    private JTextArea newMessageDiag;
    private JTextField newMessageEntry;
    private String myUserName;
    private UserSessionManager myUserSession;
    private ArrayList<ClientConversationManager> conversations;


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
        this.clientPanel = new JPanel();
        this.clientListModel = new DefaultListModel<>();
        this.clientList = new JList<>(clientListModel);
        this.conversations = new ArrayList<>();
        clientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() > 1) {
                    String temp = (String) clientList.getSelectedValue();
                    //System.out.println(clientList.getSelectedValue());
                    startConversation(temp);
                }
            }
        });
        this.clientPanel.add(clientTextArea, BorderLayout.SOUTH);
        this.clientPanel.add(new JScrollPane(clientList), BorderLayout.CENTER);
        //this.clientWindow.add(clientTextEntry, BorderLayout.SOUTH);
        this.clientWindow.add(clientPanel);
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
                    Object newObj = objIn.readObject();
                    if (newObj instanceof MessagePacket) {
                        MessagePacket newMessage = (MessagePacket) newObj;
                        handleMessagePacket(newMessage);
                    }
                    if (newObj instanceof UserSessionManager) {
                        System.out.println("User list received");
                        setMyUserSession((UserSessionManager) newObj);
                        updateClientList();
                    }
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

    private void startConversation(String temp) {
        ClientConversationManager newConversation = new ClientConversationManager(myUserName ,temp, clientSocket);
        conversations.add(newConversation);
        Thread t = new Thread(newConversation);
        t.start();

    }

    private void updateClientList() {
        ArrayList temp = myUserSession.getUsersList();
        for (int i = 0; i < temp.size(); i++) {
            clientListModel.addElement((String) temp.get(i));
            System.out.println((String)temp.get(i));
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
                break;
            }
            case 2: {
                System.out.println("We also caught the bacon \n");
                handleConversation(messageIn);
                break;
            }
        }
    }

    public void handleConversation(MessagePacket newMessage) {
        for (ClientConversationManager convos : conversations) {
            if(convos.getOtherClient().equals(newMessage.getSender())) {
                convos.addMessage(newMessage.getMessage(), newMessage.getSender());
            }
        }
    }


    public void setMyUserName(String name) { this.myUserName = name; }
    public String getMyUserName() { return this.myUserName; }

    private void setMyUserSession(UserSessionManager userSession) { this.myUserSession = userSession; }
    public UserSessionManager getMyUserSession() { return this.myUserSession; }

    public static void main(String[] args) {
       Client newClient = new Client();

    }

}