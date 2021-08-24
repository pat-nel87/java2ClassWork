import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    public Server() {
        super("Pat Chat");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        sendMessage(actionEvent.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();

        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }

    public void startRunning() {
        try {
            server = new ServerSocket(8818, 100);
            while(true) {
                try{
                    waitForConnection();
                    setupStreams();
                    whileChat();
                } catch(EOFException eofException) {
                    showMessage("\n Server severed connection");
                } finally {
                    closeStreams();
                }
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void waitForConnection() throws IOException {
        showMessage("waiting for someone to connect...\n");
        connection = server.accept();
        showMessage("Now connected to " +connection.getInetAddress().getHostName());

    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams connected \n");
    }

    private void whileChat() throws IOException {
        String message = "Connection established";
        sendMessage(message);
        ableToType(true);
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);
            } catch(ClassNotFoundException classNotFoundException) {
              showMessage("\n class not found: Non-convertable to String");
            }
        } while(!message.equals("CLIENT - END"));
    }

    public void closeStreams() {
        showMessage("\n Connection is closing.... \n");
        ableToType(false);
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void sendMessage(String message) {
        try {
            output.writeObject("SERVER - " + message);
            output.flush();
            showMessage("\n SERVER -" + message);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void showMessage(final String msg) {
        SwingUtilities.invokeLater(
          new Runnable(){
              public void run() {
                  chatWindow.append(msg);
              }
          }
        );
    }

    private void ableToType(final boolean trueOrFalse) {
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run() {
                        userText.setEditable(trueOrFalse);
                    }
                }
        );

    }




}
