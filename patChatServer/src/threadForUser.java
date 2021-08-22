import java.io.*;
import java.net.Socket;

public class threadForUser extends Thread {

    private Socket socket;
    private chatServ server;
    private PrintWriter printwriter;

    public threadForUser(Socket socket, chatServ server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = socket.getOutputStream();
            printwriter = new PrintWriter(outputStream, true);

            getUserList();

            String userHandle = reader.readLine();
            server.addUser(userHandle);

            String srvMsg = "New User Online: " + userHandle;
            server.groupMsg(srvMsg, this);

            String clientMsg;

            do {
                clientMsg = reader.readLine();
                srvMsg = "{{" + userHandle + "}} --> " + clientMsg;
                server.groupMsg(srvMsg, this);
            } while (!clientMsg.equals("logoff"));

            server.deleteUser(userHandle, this);
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void getUserList() {
        if (server.checkUsers()) {
            printwriter.println("Users online: " + server.getUsers());
        } else {
            printwriter.println("You are the only user online!");
        }
    }

    void sendMsg(String message) { printwriter.println(message); }

}
