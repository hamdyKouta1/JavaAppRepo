/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;

public class DualChatUDP {

    public static JDBC jMsg = new JDBC();
    public static String s_p = null;
    public static String l_p = null;
    public static Timestamp time;
    public static SoundMe sound = new SoundMe();
    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static int serverPort;

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

            socket = new DatagramSocket(port);
            System.out.println("Server listening on port " + port + "...");
            byte[] recevieData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(recevieData, recevieData.length);
            socket.receive(receivePacket);
            serverAddress = receivePacket.getAddress();
            serverPort = receivePacket.getPort();
            System.out.println("Client connected: " + serverAddress + ":" + serverPort);

            System.out.println("Connection established.");

            myChatWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startClient(String host, int port) {
        try {
            socket = new DatagramSocket();
            System.out.println("Connected to server on port " + port);
            serverAddress = InetAddress.getByName(host);
            serverPort = port;
            byte[] sendData = "hello".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Connection ///////////////////// established.");

           myChatWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void myChatWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat App");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    byte[] sendData = userMessage.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                    socket.send(sendPacket);
                    SwingUtilities.invokeLater(() -> {
                        chatArea.append("you: " + userMessage + "\n");
                    });
                    time = new Timestamp(System.currentTimeMillis());
                    jMsg.saveChatMsgUDP(s_p, l_p, userMessage, time);

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
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        socket.receive(receivePacket);

                        String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

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
