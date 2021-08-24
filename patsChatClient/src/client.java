import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverAddr;
    private Socket connection;

    public client(String hostname) {
        super("pat chat client console");
        serverAddr = hostname;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        sendMethod(actionEvent.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }

    public void runClient() {
        try {
            makeConnection();
            setupStreams();
            whileChat();
        } catch (EOFException eofException) {
            eofException.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeStreams();
        }
    }
    
    private String getServerAddr() { return this.serverAddr; }
    
    private void makeConnection() throws IOException {
        showMessage("Making connection... \n");
        try {
            connection = new Socket(getServerAddr(), 8818);
            showMessage("Connection Established to " + connection.getInetAddress().getHostName());
        } catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
        }
        }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now configured. \n");
    }

    private void whileChat() throws IOException {
        ableToType(true);
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n"+message);
            } catch(ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        } while (!message.equals("SERVER - END"));
    }

    private void closeStreams() {
        showMessage("\n Closing Connections \n");
        ableToType(false);
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void sendMethod(String message) {
        try {
            output.writeObject("CLIENT - " + message);
            output.flush();
            showMessage("\nCLIENT - " + message);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void showMessage(final String msg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(msg);
                    }
                }
        );
    }

    private void ableToType(final boolean trueOrFalse) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(trueOrFalse);
                    }
                }
        );

    }


}
