import javax.swing.*;
import java.net.Socket;

public class ConversationManager implements Runnable {

    private JFrame conversationWindow;
    private JTextArea conversationDialogue;
    private JTextField conversationEntry;
    private Socket socket;
    private MessagePacket currentMessage;



    @Override
    public void run() {

    }
}
