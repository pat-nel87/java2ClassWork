import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThreadHandler extends Thread{

   // public ObjectOutput output;
    private Socket socket;
    private ArrayList<ServerThreadHandler> threadsList;
    private PrintWriter output;

    public ServerThreadHandler(Socket socket, ArrayList<ServerThreadHandler> threads) {
        this.socket = socket;
        this.threadsList = threads;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                String outputString = input.readLine();
                if(outputString.equals("exit")) {
                    break;
                }
                sendToAllClients(outputString);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void sendToAllClients(String outString) {
        for (ServerThreadHandler srvThread : threadsList) {
            srvThread.output.println(outString);
        }

    }


}
