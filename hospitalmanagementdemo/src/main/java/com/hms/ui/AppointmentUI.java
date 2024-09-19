package com.hms.ui;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.hms.dao.AppointmentDAO;
import com.hms.dao.PatientDAO;
import model.Appointment;
import model.Patient;

public class AppointmentUI extends JFrame {
    private JTextField txtDoctorName;
    private JTextField txtAppointmentDate;
    private JTextField txtAppointmentTime;
    private JComboBox<String> comboPatient;
    private JTable tblAppointments;
    private DefaultTableModel appointmentTableModel;

    public AppointmentUI() {
        setTitle("Manage Appointments");
        setSize(700, 400);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JLabel lblPatient = new JLabel("Patient:");
        comboPatient = new JComboBox<>();
        JLabel lblDoctorName = new JLabel("Doctor Name:");
        txtDoctorName = new JTextField();
        JLabel lblAppointmentDate = new JLabel("Appointment Date (YYYY-MM-DD):");
        txtAppointmentDate = new JTextField();
        JLabel lblAppointmentTime = new JLabel("Appointment Time (HH:MM):");
        txtAppointmentTime = new JTextField();

        formPanel.add(lblPatient);
        formPanel.add(comboPatient);
        formPanel.add(lblDoctorName);
        formPanel.add(txtDoctorName);
        formPanel.add(lblAppointmentDate);
        formPanel.add(txtAppointmentDate);
        formPanel.add(lblAppointmentTime);
        formPanel.add(txtAppointmentTime);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Table Panel
        String[] columnNames = {"ID", "Patient Name", "Doctor Name", "Date", "Time"};
        appointmentTableModel = new DefaultTableModel(columnNames, 0);
        tblAppointments = new JTable(appointmentTableModel);
        JScrollPane scrollPane = new JScrollPane(tblAppointments);

        // Populate table with existing appointments
        loadAppointments();
        loadPatients();  // Load patients into combo box for appointment creation

        // Add action listeners
        btnSave.addActionListener(this::saveAppointment);
        btnUpdate.addActionListener(this::updateAppointment);
        btnDelete.addActionListener(this::deleteAppointment);
        btnClear.addActionListener(e -> clearForm());

        tblAppointments.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedAppointment();
            }
        });

        // Add panels to frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Load appointments into the table
    private void loadAppointments() {
        appointmentTableModel.setRowCount(0);  // Clear the table
        List<Appointment> appointments = AppointmentDAO.getAllAppointments();
        for (Appointment appointment : appointments) {
            Object[] row = new Object[]{
                    appointment.getId(),
                    appointment.getPatient().getName(),
                    appointment.getDoctorName(),
                    appointment.getDate(),
                    appointment.getTime()
            };
            appointmentTableModel.addRow(row);
        }
    }

    // Load patients into the combo box
    private void loadPatients() {
        List<Patient> patients = PatientDAO.getAllPatients();
        comboPatient.removeAllItems();
        for (Patient patient : patients) {
            comboPatient.addItem(patient.getName() + " - " + patient.getId());
        }
    }

    // Save a new appointment
    private void saveAppointment(ActionEvent e) {
        Appointment appointment = new Appointment();

        String selectedPatient = (String) comboPatient.getSelectedItem();
        int patientId = Integer.parseInt(selectedPatient.split(" - ")[1]);  // Extract patient ID from combo box
        Patient patient = PatientDAO.getPatientById(patientId);

        appointment.setPatient(patient);
        appointment.setDoctorName(txtDoctorName.getText());
        appointment.setDate(txtAppointmentDate.getText());
        appointment.setTime(txtAppointmentTime.getText());

        AppointmentDAO.saveAppointment(appointment);
        JOptionPane.showMessageDialog(this, "Appointment saved successfully!");

        loadAppointments();  // Reload appointments to reflect the new appointment
        clearForm();
    }

    // Update an existing appointment
    private void updateAppointment(ActionEvent e) {
        int selectedRow = tblAppointments.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update.");
            return;
        }

        int appointmentId = (int) tblAppointments.getValueAt(selectedRow, 0);  // Get the appointment ID from the selected row

        Appointment appointment = AppointmentDAO.getAppointmentById(appointmentId);
        if (appointment != null) {
            String selectedPatient = (String) comboPatient.getSelectedItem();
            int patientId = Integer.parseInt(selectedPatient.split(" - ")[1]);  // Extract patient ID from combo box
            Patient patient = PatientDAO.getPatientById(patientId);

            appointment.setPatient(patient);
            appointment.setDoctorName(txtDoctorName.getText());
            appointment.setDate(txtAppointmentDate.getText());
            appointment.setTime(txtAppointmentTime.getText());

            AppointmentDAO.updateAppointment(appointment);
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");

            loadAppointments();  // Reload appointments to reflect the update
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Appointment not found!");
        }
    }

    // Delete an existing appointment
    private void deleteAppointment(ActionEvent e) {
        int selectedRow = tblAppointments.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.");
            return;
        }

        int appointmentId = (int) tblAppointments.getValueAt(selectedRow, 0);  // Get the appointment ID from the selected row
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this appointment?");
        if (confirm == JOptionPane.YES_OPTION) {
            AppointmentDAO.deleteAppointment(appointmentId);
            JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");

            loadAppointments();  // Reload appointments to reflect the deletion
            clearForm();
        }
    }

    // Clear form fields
    private void clearForm() {
        comboPatient.setSelectedIndex(-1);
        txtDoctorName.setText("");
        txtAppointmentDate.setText("");
        txtAppointmentTime.setText("");
        tblAppointments.clearSelection();
    }

    // Load selected appointment details into form
    private void loadSelectedAppointment() {
        int selectedRow = tblAppointments.getSelectedRow();
        if (selectedRow != -1) {
            String patientName = tblAppointments.getValueAt(selectedRow, 1).toString();
            String doctorName = tblAppointments.getValueAt(selectedRow, 2).toString();
            String date = tblAppointments.getValueAt(selectedRow, 3).toString();
            String time = tblAppointments.getValueAt(selectedRow, 4).toString();

            comboPatient.setSelectedItem(patientName);
            txtDoctorName.setText(doctorName);
            txtAppointmentDate.setText(date);
            txtAppointmentTime.setText(time);
        }
    }
}
