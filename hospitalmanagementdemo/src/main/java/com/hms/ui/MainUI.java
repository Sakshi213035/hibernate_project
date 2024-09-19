package com.hms.ui;


import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    public MainUI() {
        setTitle("Hospital Management System");
        setSize(400, 300);
        setLayout(new FlowLayout());

        JButton btnManageDoctors = new JButton("Manage Doctors");
        JButton btnManagePatients = new JButton("Manage Patients");
        JButton btnManageAppointments = new JButton("Manage Appointments");

        btnManageDoctors.addActionListener(e -> new DoctorUI().setVisible(true));
        btnManagePatients.addActionListener(e -> new PatientUI().setVisible(true));
        btnManageAppointments.addActionListener(e -> new AppointmentUI().setVisible(true));

        add(btnManageDoctors);
        add(btnManagePatients);
        add(btnManageAppointments);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
