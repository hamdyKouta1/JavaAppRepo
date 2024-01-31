package com.mycompany.guiproject;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;


public class DualChatTCP {

    private static DataInputStream dataIn;
    private static DataOutputStream dataOut;
    public static JDBC jMsg = new JDBC();
    public static String s_p = null;
    public static String l_p = null;
    public static Timestamp time;
    public static SoundMe sound =new SoundMe();

   

    public static void init(int startPort, int listenPort, String ip) {
        s_p = String.valueOf(startPort);
        l_p = String.valueOf(listenPort);
          if ("0".equals(ip)) {
            new Thread(() -> startServer(startPort)).start();
        } else {
            new Thread(() -> startClient(ip, listenPort)).start();
        }
    }

    private static void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port + "...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("Connection established.");

            myChatWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connected to server on port " + port);

            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());

            System.out.println("Connection established.");

            myChatWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void myChatWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat App");

            frame.setSize(400, 300);
             Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int centerX = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
            int centerY = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
            
            frame.setLocation(centerX, centerY);
            
            ImageIcon image = new ImageIcon(MyConstants.iti_img);
            frame.setIconImage(image.getImage());

            JTextArea chatArea = new JTextArea();
            chatArea.setEditable(false);
            chatArea.setBackground(MyColors.activeTextFormColor);
            chatArea.setFont(new Font("Arial", Font.BOLD, 15));

            JTextField messageField = new JTextField();
            messageField.setBackground(MyColors.backgroundColor);
            messageField.setForeground(Color.WHITE);
            messageField.setFont(new Font("Arial", Font.BOLD, 15));
            JButton sendButton = new JButton("Send");
            sendButton.setBackground(MyColors.buttonColor);
            sendButton.setForeground(Color.WHITE);

            JPanel inputPanel = new JPanel((new FlowLayout(FlowLayout.CENTER)));

            inputPanel.setLayout(new BorderLayout());
            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);

            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
            frame.add(inputPanel, BorderLayout.SOUTH);

            sendButton.addActionListener(e -> {
                try {
                    String userMessage = messageField.getText();
                    dataOut.writeUTF(userMessage);
                    SwingUtilities.invokeLater(() -> {
                        chatArea.append("you: " + userMessage + "\n");
                    });
                    time = new Timestamp(System.currentTimeMillis());
                    jMsg.saveChatMsg(s_p, l_p, userMessage, time);

                    if (userMessage.equalsIgnoreCase("exit")) {
                        System.out.println("Chat ended by you.");
                        System.exit(0);
                    }
                    sound.playNotificationSound(MyConstants.send_sound);


                    messageField.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            frame.setVisible(true);

            new Thread(() -> {
                try {
                    while (true) {
                        String receivedMessage = dataIn.readUTF();

                        SwingUtilities.invokeLater(() -> {
                            chatArea.append("Other: " + receivedMessage + "\n");
                        });

                        if (receivedMessage.equalsIgnoreCase("exit")) {
                            System.out.println("Chat ended by the other side.");
                            System.exit(0);
                        }
                        sound.playNotificationSound(MyConstants.rec_sound);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }


}
