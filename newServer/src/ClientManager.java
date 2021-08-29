import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    private String userName;
    final InputStream objectInputStream;
    final OutputStream objectOutputStream;
    Socket socket;
    boolean loggedOn;
    private ArrayList<ClientManager> userList;

    public ClientManager(String userName, Socket socket, OutputStream out, InputStream in) {
        this.userName = userName;
        this.socket = socket;
        this.objectOutputStream = out;
        this.objectInputStream = in;
        this.loggedOn = true;
    }

    @Override
    public void run() {
        getUserList();

    }

    public void setUserList(ArrayList<ClientManager> users) { this.userList = users; }
    public void getUserList() {
        writeToOutputStream("\n Current Users Online: ");

        for (ClientManager clients : userList) {
        writeToOutputStream(clients.userName);
        }
    }

    public void writeToOutputStream(String message) {
        Writer writer = new OutputStreamWriter(this.objectOutputStream);
        try {
            writer.write("\n" + message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
