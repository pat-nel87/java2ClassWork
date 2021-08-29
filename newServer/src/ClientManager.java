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

    public ClientManager(String userName, Socket socket, OutputStream out, InputStream in) {
        this.userName = userName;
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(out);
        this.dataInputStream = new DataInputStream(in);
        this.loggedOn = true;
    }

    @Override
    public void run() {
        getUserList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
        while(true) {
            try {
                if(reader.ready()) {
                   System.out.println(reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
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
