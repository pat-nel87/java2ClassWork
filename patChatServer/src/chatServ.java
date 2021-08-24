import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class chatServ {

    private int chatPort;
    private Set<String> userHandles = new HashSet<>();
    private Set<threadForUser> userThreads = new HashSet<>();

    public chatServ(int chatPort) { this.chatPort = chatPort;  }

    public void runServ() {
        try (ServerSocket serverSocket = new ServerSocket(chatPort)) {
            System.out.println("Server is ready for connections on port " + chatPort + "\n");
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection established\n");

                threadForUser newUser = new threadForUser(socket, this);
                userThreads.add(newUser);
                newUser.run();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /* if (args.length < 1) {
            System.out.println("args less than 1");
            System.exit(0);
        } */
        int port = 8818;

        chatServ server = new chatServ(port);
        server.runServ();
    }

    void groupMsg(String msg, threadForUser dontSend) {
        for (threadForUser user : userThreads) {
                user.sendMsg(msg);
        }
    }

    void addUser(String name) { userHandles.add(name); }

    void deleteUser(String name, threadForUser user) {
        boolean deleted = userHandles.remove(name);
        if (deleted) {
            userThreads.remove(user);
        }
    }

    Set<String> getUsers() { return this.userHandles; }

    boolean checkUsers() { return !this.userHandles.isEmpty(); }



}
