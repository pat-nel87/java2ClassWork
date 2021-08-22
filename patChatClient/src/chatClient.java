import javax.imageio.IIOException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class chatClient {

    private String server;
    private int chatPort;
    private String userHandle;

    public chatClient(String server, int chatPort) {
        this.server = server;
        this.chatPort = chatPort;
    }

    public void run() {
        try {
            Socket socket = new Socket(server, chatPort);

            System.out.println("Connection Established");

            new msgReader(socket, this).start();
            new msgWriter(socket, this).start();
        } catch (UnknownHostException ex) {
        } catch (IOException ioException) {

        }
    }

    void setHandle(String userHandle) { this.userHandle = userHandle; }
    String getUserHandle() { return this.userHandle; }

    public static void main(String[] args) {

        int serverPort = 8818;
        chatClient client = new chatClient("localhost", serverPort);
        client.run();

    }



    }



