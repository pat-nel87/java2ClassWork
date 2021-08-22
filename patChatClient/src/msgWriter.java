import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class msgWriter extends Thread {

    private PrintWriter printWriter;
    private Socket socket;
    private chatClient client;

    public msgWriter(Socket socket, chatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {

        Console console = System.console();

        //String userHandle = console.readLine("\n Enter a User Handle: ");
        String userHandle = "test1";
        client.setHandle(userHandle);
        printWriter.println(userHandle);

        String readMsg;

        do {
            readMsg = console.readLine("{{" + userHandle + "}}---> ");
            printWriter.println(readMsg);
        } while (!readMsg.equals("logoff"));

        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
