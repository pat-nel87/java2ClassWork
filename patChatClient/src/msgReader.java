import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class msgReader extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private chatClient client;

    public msgReader(Socket socket, chatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream inputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);

                if (client.getUserHandle() != null) {
                    System.out.println("{{" + client.getUserHandle() + "}}---> " );
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }
}
