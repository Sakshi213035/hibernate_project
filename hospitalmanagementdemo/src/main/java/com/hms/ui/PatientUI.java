package com.hms.ui;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.hms.dao.PatientDAO;
import model.Patient;

public class PatientUI extends JFrame {
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtGender;
    private JTextField txtAddress;
    private JTextField txtPhoneNumber;
    private JTable tblPatients;
    private DefaultTableModel patientTableModel;

    public PatientUI() {
        setTitle("Manage Patients");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField();
        JLabel lblAge = new JLabel("Age:");
        txtAge = new JTextField();
        JLabel lblGender = new JLabel("Gender:");
        txtGender = new JTextField();
        JLabel lblAddress = new JLabel("Address:");
        txtAddress = new JTextField();
        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField();

        formPanel.add(lblName);
        formPanel.add(txtName);
        formPanel.add(lblAge);
        formPanel.add(txtAge);
        formPanel.add(lblGender);
        formPanel.add(txtGender);
        formPanel.add(lblAddress);
        formPanel.add(txtAddress);
        formPanel.add(lblPhoneNumber);
        formPanel.add(txtPhoneNumber);

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
        String[] columnNames = {"ID", "Name", "Age", "Gender", "Address", "Phone Number"};
        patientTableModel = new DefaultTableModel(columnNames, 0);
        tblPatients = new JTable(patientTableModel);
        JScrollPane scrollPane = new JScrollPane(tblPatients);

        // Populate table with existing patients
        loadPatients();

        // Add action listeners
        btnSave.addActionListener(this::savePatient);
        btnUpdate.addActionListener(this::updatePatient);
        btnDelete.addActionListener(this::deletePatient);
        btnClear.addActionListener(e -> clearForm());

        tblPatients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedPatient();
            }
        });

        // Add panels to frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Load patients into the table
    private void loadPatients() {
        patientTableModel.setRowCount(0); // Clear the table
        List<Patient> patients = PatientDAO.getAllPatients();
        for (Patient patient : patients) {
            Object[] row = new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getAddress(),
                    patient.getPhoneNumber()
            };
            patientTableModel.addRow(row);
        }
    }

    // Save a new patient
    private void savePatient(ActionEvent e) {
        Patient patient = new Patient();
        patient.setName(txtName.getText());
        patient.setAge(Integer.parseInt(txtAge.getText()));
        patient.setGender(txtGender.getText());
        patient.setAddress(txtAddress.getText());
        patient.setPhoneNumber(txtPhoneNumber.getText());

        PatientDAO.savePatient(patient);
        JOptionPane.showMessageDialog(this, "Patient saved successfully!");

        loadPatients();  // Reload patients to reflect the new patient
        clearForm();
    }

    // Update an existing patient
    private void updatePatient(ActionEvent e) {
        int selectedRow = tblPatients.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update.");
            return;
        }

        int patientId = (int) tblPatients.getValueAt(selectedRow, 0);  // Get the patient ID from the selected row

        Patient patient = PatientDAO.getPatientById(patientId);
        if (patient != null) {
            patient.setName(txtName.getText());
            patient.setAge(Integer.parseInt(txtAge.getText()));
            patient.setGender(txtGender.getText());
            patient.setAddress(txtAddress.getText());
            patient.setPhoneNumber(txtPhoneNumber.getText());

            PatientDAO.updatePatient(patient);
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");

            loadPatients();  // Reload patients to reflect the update
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Patient not found!");
        }
    }

    // Delete an existing patient
    private void deletePatient(ActionEvent e) {
        int selectedRow = tblPatients.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
            return;
        }

        int patientId = (int) tblPatients.getValueAt(selectedRow, 0);  // Get the patient ID from the selected row
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?");
        if (confirm == JOptionPane.YES_OPTION) {
            PatientDAO.deletePatient(patientId);
            JOptionPane.showMessageDialog(this, "Patient deleted successfully!");

            loadPatients();  // Reload patients to reflect the deletion
            clearForm();
        }
    }

    // Clear form fields
    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        txtGender.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        tblPatients.clearSelection();
    }

    // Load selected patient details into form
    private void loadSelectedPatient() {
        int selectedRow = tblPatients.getSelectedRow();
        if (selectedRow != -1) {
            txtName.setText(tblPatients.getValueAt(selectedRow, 1).toString());
            txtAge.setText(tblPatients.getValueAt(selectedRow, 2).toString());
            txtGender.setText(tblPatients.getValueAt(selectedRow, 3).toString());
            txtAddress.setText(tblPatients.getValueAt(selectedRow, 4).toString());
            txtPhoneNumber.setText(tblPatients.getValueAt(selectedRow, 5).toString());
        }
    }
}
