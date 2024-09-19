package com.hms.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.hms.dao.UserDAO;
import model.User;

public class RegisterUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public RegisterUI() {
        setTitle("Hospital Management System - Register");
        setSize(300, 200);
        setLayout(new FlowLayout());

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(20);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(this::registerAction);

        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnRegister);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void registerAction(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserDAO.saveUser(user);
        JOptionPane.showMessageDialog(this, "Registration successful!");
    }
}
