package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessengerWindow extends JPanel implements MessageListener {

    private final ChatClient client;
    private final String login;

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> chatsList = new JList<>();
    private JTextField inputField = new JTextField();

    public MessengerWindow(ChatClient client, String login) {

        this.client = client;
        this.login = login;

        client.addMessageListener(this);


        setLayout(new BorderLayout());
        add(new JScrollPane(chatsList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String msgText = inputField.getText();
                    client.msg(login, msgText);
                    listModel.addElement("Me: " + msgText);
                    inputField.setText("");
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onMessage(String sentFrom, String message) {
        if (login.equalsIgnoreCase(sentFrom)) {
            String line = sentFrom + "> " + message;
            listModel.addElement(line);
        }
    }
}