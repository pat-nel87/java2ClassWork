import java.io.*;
import java.net.Socket;

public class ClientRun implements Runnable {

    private Socket socket;
    //private BufferedReader input;
    // private PrintWriter output;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientRun(Socket s) throws IOException {
        this.socket = s;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }
    @Override
    public void run() {

        try {
            while(true) {
                String response = input.toString();
                output.writeObject(response);
            }
        } catch (NullPointerException | IOException nullPointerException) {
            System.out.println("Testing 1234");
            //e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
