import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;



// Server class
public class Server extends JFrame {

    private ArrayList<ClientManager> clientList;
    public ServerSocket serverSocket;
    private int clientNumber;
    private JFrame serverWindow;
    private JTextArea serverDialogue;

    public Server()
    {
        serverWindow = new JFrame();
        serverDialogue = new JTextArea();
        serverWindow.add(serverDialogue);
        serverWindow.setSize(500,500);
        serverWindow.setVisible(true);
        serverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientList = new ArrayList<ClientManager>();
        clientNumber = 0;

        try {
            serverSocket = new ServerSocket(8818);
            //System.out.println("Server is now listening on port 8818");
            serverDialogue.append("Server is now listening on Port 8818 \n");
            while (true) {
                Socket connection = null;
                try{
                   connection = serverSocket.accept();
                   //System.out.println("Client has connected " + connection.getLocalPort());
                   serverDialogue.append("Client has connected " + connection.getLocalSocketAddress() + "\n");
                   Writer out = new OutputStreamWriter(connection.getOutputStream());
                   out.write("\n You are Client #" + clientNumber);
                   out.flush();
                   String userName = "Client" + clientNumber;
                   ClientManager clientConnection = new ClientManager(userName, connection, connection.getOutputStream(), connection.getInputStream());
                   Thread t = new Thread(clientConnection);
                   clientList.add(clientConnection);
                   //out.write(clientList.toString());
                   //out.flush();
                   clientConnection.setUserList(clientList);
                   t.start();
                   out.flush();
                   clientNumber++;
                } catch(IOException ex) {
                    ex.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

    }





}