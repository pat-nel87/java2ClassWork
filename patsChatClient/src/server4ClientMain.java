import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class server4ClientMain extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public server4ClientMain() {

        super("pat chat client console");
        userText = new JTextField();
        userText.setEditable(true);
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


        try (Socket socket = new Socket("localhost", 8818)) {
            //reading the input from server
            input = new ObjectInputStream(socket.getInputStream());

            //returning the output to the server : true statement is to flush the buffer otherwise
            //we have to do it manuallyy
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

            //taking the user input
            //Scanner scanner = new Scanner(System.in);
            //String userInput;
            String response;
            String clientName = "empty";

            ClientRun clientRun = new ClientRun(socket);


            new Thread(clientRun).start();
            //loop closes when user enters exit command

            do {

                    showMessage("Enter your name ");
                    clientName = output.toString();

                    if (output.equals("exit")) {
                        break;

                }
                else {
                    String message = ( "(" + clientName + ")" + " message : " );
                    showMessage(message);

                    output.writeObject(message + " " + input.toString());
                    if (input.equals("exit")) {
                        //reading the input from server
                        break;
                    }
                }

            } while (!input.equals("exit"));




        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getStackTrace());
        }
    }

    private void sendMethod(String message) {
        try {

            this.output.flush();
            showMessage("\n" + message);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        } catch(NullPointerException nullPointerException) {
            showMessage(message);
            nullPointerException.printStackTrace();
        }

    }

    private void showMessage(final String msg) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(msg);
                        chatWindow.append("\n");
                    }
                }
        );
    }


}