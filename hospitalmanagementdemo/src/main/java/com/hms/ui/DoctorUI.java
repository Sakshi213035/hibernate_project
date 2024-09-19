package com.hms.ui;

// Example for DoctorUI.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.hms.dao.DoctorDAO;
import model.Doctor;

public class DoctorUI extends JFrame {
    private JTextField txtDoctorName;

    public DoctorUI() {
        setTitle("Manage Doctors");
        setSize(300, 200);
        setLayout(new FlowLayout());

        JLabel lblDoctorName = new JLabel("Doctor Name:");
        txtDoctorName = new JTextField(20);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(this::saveDoctor);

        add(lblDoctorName);
        add(txtDoctorName);
        add(btnSave);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void saveDoctor(ActionEvent e) {
        String doctorName = txtDoctorName.getText();
        Doctor doctor = new Doctor();
        doctor.setName(doctorName);

        DoctorDAO.saveDoctor(doctor);
        JOptionPane.showMessageDialog(this, "Doctor saved successfully!");
    }
}
