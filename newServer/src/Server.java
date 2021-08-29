import java.io.*;
import java.util.*;
import java.net.*;



// Server class
public class Server {

    private ArrayList<ClientManager> clientList;
    public ServerSocket serverSocket;
    private int clientNumber;

    public Server()
    {
        clientList = new ArrayList<ClientManager>();
        clientNumber = 0;
        try {
            serverSocket = new ServerSocket(8818);
            System.out.println("Server is now listening on port 8818");
            while (true) {
                Socket connection = null;
                try{
                   connection = serverSocket.accept();
                   System.out.println("Client has connected " + connection.getLocalPort());
                   Writer out = new OutputStreamWriter(connection.getOutputStream());
                   out.write("You are Client #" + clientNumber);
                   out.flush();
                   String userName = "Client" + clientNumber;
                   ClientManager clientConnection = new ClientManager(userName, connection, connection.getOutputStream(), connection.getInputStream());
                   Thread t = new Thread(clientConnection);
                   clientList.add(clientConnection);
                   //out.write(clientList.toString());
                   //out.flush();
                   clientConnection.setUserList(clientList);
                   t.start();
                   clientNumber++;
                } catch(IOException ex) {
                    ex.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

    }





}