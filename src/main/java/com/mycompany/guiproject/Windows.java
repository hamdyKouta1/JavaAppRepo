package com.mycompany.guiproject;

import static com.mycompany.guiproject.DualChatTCP.sound;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Windows {

    public void openAddUSerWindow() {
        
        JDBC jdbsAddUSer = new JDBC();
        
        JFrame smsFrame = new JFrame("ADD USER Window");
        smsFrame.setSize(400, 300);
        smsFrame.setResizable(false);
        
        JPanel smsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));

        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        smsFrame.setIconImage(image.getImage());

        JTextField userNameField = createColoredTextField();
        JTextField SIDField = createColoredTextField();
        JTextField AuthField = createColoredTextField();

        JButton submitButton = createRoundedButton("Submit", (ActionEvent e) -> {
            String userName = userNameField.getText();

            String SID = SIDField.getText();

            String AUTH = AuthField.getText();

          
            if (SID.isEmpty() || userName.isEmpty() || AUTH.isEmpty()) {
                
                sound.playNotificationSound(MyConstants.err2);
                
                userNameField.setText("can not send Empty field");
                
                SIDField.setText("");
                
                AuthField.setText("");
            } else {
                
                sound.playNotificationSound(MyConstants.click_sound);
                
                jdbsAddUSer.addUser(userName, SID, AUTH);

                userNameField.setText("");
                SIDField.setText("");
                AuthField.setText("");
            }
        });

        smsPanel.setLayout(new GridLayout(4, 2));

        JLabel u_n = new JLabel("User Name:");
        u_n.setForeground(Color.WHITE);
        smsPanel.add(u_n);
        smsPanel.add(userNameField);

        JLabel s_i_d = new JLabel("SID:");
        s_i_d.setForeground(Color.WHITE);
        smsPanel.add(s_i_d);
        smsPanel.add(SIDField);

        JLabel auth_tok = new JLabel("Auth Token:");
        auth_tok.setForeground(Color.WHITE);
        smsPanel.add(auth_tok);
        smsPanel.add(AuthField);

        smsPanel.add(submitButton);
        smsPanel.setBackground(MyColors.backgroundColor);

        smsFrame.add(smsPanel);
        smsFrame.setLocationRelativeTo(null);
        smsFrame.setVisible(true);
    }

    public void openChatWindowUDP() {
        JFrame smsFrame = new JFrame("CHAT UDP Window");
        smsFrame.setSize(400, 300);
        smsFrame.setResizable(false);

        JPanel smsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));

        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        smsFrame.setIconImage(image.getImage());

        JTextField serverPortField = createColoredTextField();
        JTextField listenPortField = createColoredTextField();
        JTextField ipField = createColoredTextField();

        JButton submitButton = createRoundedButton("Submit", (ActionEvent e) -> {

            String serverPortText = serverPortField.getText();
            int sp_num = Integer.parseInt(serverPortText);

            String listenPortText = listenPortField.getText();
            int lp_num = Integer.parseInt(listenPortText);

            String ipText = ipField.getText();

            sound.playNotificationSound(MyConstants.click_sound);

            DualChatUDP.init(sp_num, lp_num, ipText);
        });

        smsPanel.setLayout(new GridLayout(4, 2));

        JLabel serverPort = new JLabel("server Port:");
        serverPort.setForeground(Color.WHITE);
        smsPanel.add(serverPort);
        smsPanel.add(serverPortField);

        JLabel listenPort = new JLabel("listen Port:");
        listenPort.setForeground(Color.WHITE);
        smsPanel.add(listenPort);
        smsPanel.add(listenPortField);

        JLabel ip = new JLabel("IP:");
        ip.setForeground(Color.WHITE);
        smsPanel.add(ip);
        smsPanel.add(ipField);

        smsPanel.add(submitButton);
        smsPanel.setBackground(MyColors.backgroundColor);

        smsFrame.add(smsPanel);
        smsFrame.setLocationRelativeTo(null);
        smsFrame.setVisible(true);
    }

    public void openChatWindow() {
        JFrame smsFrame = new JFrame("CHAT Window");
        smsFrame.setSize(400, 300);
        smsFrame.setResizable(false);

        JPanel smsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));

        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        smsFrame.setIconImage(image.getImage());

        JTextField serverPortField = createColoredTextField();
        JTextField listenPortField = createColoredTextField();
        JTextField ipField = createColoredTextField();

        JButton submitButton = createRoundedButton("Submit", (ActionEvent e) -> {

            String serverPortText = serverPortField.getText();
            int sp_num = Integer.parseInt(serverPortText);

            String listenPortText = listenPortField.getText();
            int lp_num = Integer.parseInt(listenPortText);

            String ipText = ipField.getText();

            sound.playNotificationSound(MyConstants.click_sound);

            DualChatTCP.init(sp_num, lp_num, ipText);
        });

        smsPanel.setLayout(new GridLayout(4, 2));

        JLabel serverPort = new JLabel("server Port:");
        serverPort.setForeground(Color.WHITE);
        smsPanel.add(serverPort);
        smsPanel.add(serverPortField);

        JLabel listenPort = new JLabel("listen Port:");
        listenPort.setForeground(Color.WHITE);
        smsPanel.add(listenPort);
        smsPanel.add(listenPortField);

        JLabel ip = new JLabel("IP:");
        ip.setForeground(Color.WHITE);
        smsPanel.add(ip);
        smsPanel.add(ipField);

        smsPanel.add(submitButton);
        smsPanel.setBackground(MyColors.backgroundColor);

        smsFrame.add(smsPanel);
        smsFrame.setLocationRelativeTo(null);
        smsFrame.setVisible(true);
    }

    public void openSMSWindow() {
        JFrame smsFrame = new JFrame("SMS Window");
        smsFrame.setSize(400, 300);
        smsFrame.setResizable(false);

        JPanel smsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));

        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        smsFrame.setIconImage(image.getImage());

        JTextField userField = createColoredTextField();
        JTextField toField = createColoredTextField();
        JTextField fromField = createColoredTextField();
        JTextField msgField = createColoredTextField();

        JButton submitButton = createRoundedButton("Submit", (ActionEvent e) -> {

            String userText = userField.getText();
            String toText = toField.getText();
            String fromText = fromField.getText();
            String msgText = msgField.getText();

            sound.playNotificationSound(MyConstants.click_sound);

            Action.SMSUser(userText, toText, fromText, msgText);
        });

        smsPanel.setLayout(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("User:");
        userLabel.setForeground(Color.WHITE);
        smsPanel.add(userLabel);
        smsPanel.add(userField);

        JLabel to = new JLabel("To:");
        to.setForeground(Color.WHITE);
        smsPanel.add(to);
        smsPanel.add(toField);

        JLabel from = new JLabel("From:");
        from.setForeground(Color.WHITE);
        smsPanel.add(from);
        smsPanel.add(fromField);

        JLabel msgLabel = new JLabel("Message:");
        msgLabel.setForeground(Color.WHITE);
        smsPanel.add(msgLabel);
        smsPanel.add(msgField);

        smsPanel.add(submitButton);
        smsPanel.setBackground(MyColors.backgroundColor);

        smsFrame.add(smsPanel);
        smsFrame.setLocationRelativeTo(null);
        smsFrame.setVisible(true);
    }

    public void openCallWindow() {
        JFrame smsFrame = new JFrame("Call Window");

        smsFrame.setSize(400, 300);
        smsFrame.setResizable(false);

        JPanel smsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 900, 50));

        ImageIcon image = new ImageIcon(MyConstants.iti_img);
        smsFrame.setIconImage(image.getImage());

        JTextField userField = createColoredTextField();
        JTextField toField = createColoredTextField();
        JTextField fromField = createColoredTextField();
        JTextField msgField = createColoredTextField();

        JButton submitButton = createRoundedButton("Submit", (ActionEvent e) -> {

            String userText = userField.getText();
            String toText = toField.getText();
            String fromText = fromField.getText();
            String msgText = msgField.getText();

            sound.playNotificationSound(MyConstants.click_sound);

            Action.CallUser(userText, toText, fromText, msgText);

        });

        smsPanel.setLayout(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("User:");
        userLabel.setForeground(Color.WHITE);
        smsPanel.add(userLabel);
        smsPanel.add(userField);

        JLabel to = new JLabel("To:");
        to.setForeground(Color.WHITE);
        smsPanel.add(to);
        smsPanel.add(toField);

        JLabel from = new JLabel("From:");
        from.setForeground(Color.WHITE);
        smsPanel.add(from);
        smsPanel.add(fromField);

        JLabel msgLabel = new JLabel("Message:");
        msgLabel.setForeground(Color.WHITE);
        smsPanel.add(msgLabel);
        smsPanel.add(msgField);

        smsPanel.add(submitButton);

        smsPanel.setBackground(MyColors.backgroundColor);

        smsFrame.add(smsPanel);
        smsFrame.setLocationRelativeTo(null);
        smsFrame.setVisible(true);
    }

    public JTextField createColoredTextField() {
        JTextField textField = new JTextField();
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.BOLD, 25));
        textField.setBackground(MyColors.textFormColor);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBackground(MyColors.activeTextFormColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBackground(MyColors.textFormColor);
            }
        });
        return textField;
    }

    public JButton createRoundedButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(MyColors.buttonColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(300, 50));
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.addActionListener(actionListener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(MyColors.lightGreen);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(MyColors.buttonColor);
            }
        });
        return button;
    }

}
