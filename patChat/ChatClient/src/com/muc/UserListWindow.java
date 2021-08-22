package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UserListWindow extends JPanel implements UserStateListener {

    private final ChatClient client;
    private JList<String> usersListGUI;
    private DefaultListModel<String> usersListModel;

    public UserListWindow(ChatClient client) {
        this.client = client;
        this.client.addUserStateListener(this);

        usersListModel = new DefaultListModel<>();
        usersListGUI = new JList<>(usersListModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(usersListGUI), BorderLayout.CENTER);

        usersListGUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String login = usersListGUI.getSelectedValue();
                    MessengerWindow messageWindow = new MessengerWindow(client, login);

                    JFrame f = new JFrame("Message: " + login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(500, 500);

                    f.getContentPane().add(messageWindow, BorderLayout.CENTER);
                    f.setVisible(true);

                }
            }
        });
        /*if (client.connect()) {
            try {
                client.login("guest",  "guest");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } */
    }


    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 8818);

        UserListWindow userListWindow = new UserListWindow(client);
        JFrame frame = new JFrame("Friends");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        frame.getContentPane().add(userListWindow, BorderLayout.CENTER);
        frame.setVisible(true);

       if (client.connect()) {
           try {
               client.login("guest", "guest");
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }

    // the callbacks!
    @Override
    public void online(String login) {
        usersListModel.addElement(login);
    }

    @Override
    public void offline(String login) {
        usersListModel.removeElement(login);
    }
}
