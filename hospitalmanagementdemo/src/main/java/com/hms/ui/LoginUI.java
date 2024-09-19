package com.hms.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.hms.dao.UserDAO;

public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginUI() {
        setTitle("Hospital Management System - Login");
        setSize(300, 200);
        setLayout(new FlowLayout());

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(20);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        btnLogin.addActionListener(this::loginAction);
        btnRegister.addActionListener(e -> new RegisterUI().setVisible(true));

        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        add(btnRegister);

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ImageIcon i1 = new ImageIcon((ClassLoader.getSystemResource("home.jpg")));
//        Image i2 = i1.getImage().getScaledInstance(700, 700, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        JLabel label = new JLabel(i3);
//        label.setBounds(0, 0, 700, 700);
//        add(label);




}

    private void loginAction(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        boolean isAuthenticated = UserDAO.authenticate(username, password);

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new MainUI().setVisible(true);
            dispose(); // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }
}
