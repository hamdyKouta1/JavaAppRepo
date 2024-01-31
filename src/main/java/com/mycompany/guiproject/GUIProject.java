package com.mycompany.guiproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GUIProject extends JFrame {

    private SoundMe sound = new SoundMe();
    private Windows myWin = new Windows();

    public GUIProject() {

        setTitle("Main Window");
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(MyColors.backgroundColor);
        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        setIconImage(image.getImage());

        JLabel label = new JLabel("Hello, Please Choose Which Service Would You Like To Use !", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label.setForeground(MyColors.backgroundColor);
        label.setBackground(MyColors.lightGreen);
        
        JLabel labelEnd = new JLabel("Powered By : ( Hamdy Kouta , Mohamed El-Rays , Rawan Naissam )   /   ITI TELECOM APLLICATION DEVELOPMENT", SwingConstants.CENTER);
        labelEnd.setFont(new Font("Times New Roman", Font.BOLD, 12));
        labelEnd.setForeground(MyColors.backgroundColor);
        labelEnd.setBackground(MyColors.lightGreen);


        JButton smsButton = myWin.createRoundedButton("SMS", (ActionEvent e) -> {
            sound.playNotificationSound(MyConstants.click_sound);
            myWin.openSMSWindow();
        });

        JButton callButton = myWin.createRoundedButton("CALL", (ActionEvent e) -> {
            sound.playNotificationSound(MyConstants.click_sound);
            myWin.openCallWindow();
        });

        JButton chatButton = myWin.createRoundedButton("Chat TCP", (ActionEvent e) -> {
            sound.playNotificationSound(MyConstants.click_sound);
            myWin.openChatWindow();
        });

        JButton chatButtonUDP = myWin.createRoundedButton("Chat UDP", (ActionEvent e) -> {
            sound.playNotificationSound(MyConstants.click_sound);
            myWin.openChatWindowUDP();
        });

        JButton addUserButton = myWin.createRoundedButton("Add User", (ActionEvent e) -> {
            sound.playNotificationSound(MyConstants.click_sound);
            myWin.openAddUSerWindow();
        });

        setLayout(new BorderLayout());

        add(label, BorderLayout.NORTH);
       

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));
        
        buttonPanel.setBackground(MyColors.backgroundColor);
        buttonPanel.setBounds(0,0,800,600);
        buttonPanel.add(callButton);
        buttonPanel.add(smsButton);
        buttonPanel.add(chatButton);
        buttonPanel.add(chatButtonUDP);
        buttonPanel.add(addUserButton);
        
       
        
        add(buttonPanel, BorderLayout.CENTER); 
        add(labelEnd, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIProject();
        });
    }
}
