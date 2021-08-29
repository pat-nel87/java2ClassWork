import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class runServer3 {

    public static void main(String[] args) {

        ArrayList<ServerThreadHandler> threads = new ArrayList<ServerThreadHandler>();
        try (ServerSocket serverSocket = new ServerSocket(8818)) {
            while(true) {
                Socket socket = serverSocket.accept();
                ServerThreadHandler serverThread = new ServerThreadHandler(socket, threads);
                threads.add(serverThread);
                serverThread.start();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }



}
