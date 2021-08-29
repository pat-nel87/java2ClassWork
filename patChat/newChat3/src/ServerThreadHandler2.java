import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThreadHandler2 extends Thread{

    private Socket socket;
    private ArrayList<ServerThreadHandler2> threadsList;
    public ObjectOutputStream output;
    private ObjectInputStream input;

    public ServerThreadHandler2(Socket socket, ArrayList<ServerThreadHandler2> threads) {
        this.socket = socket;
        this.threadsList = threads;
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

            while(true) {
                String outputString = (String) input.readObject();
                if(outputString.equals("exit")) {
                    break;
                }
                sendToAllClients(outputString);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void sendToAllClients(String outString) throws IOException {
        for (ServerThreadHandler2 srvThread : threadsList) {
            srvThread.output.writeObject(outString);
        }

    }


}
