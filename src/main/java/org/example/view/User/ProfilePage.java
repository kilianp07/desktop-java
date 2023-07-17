package org.example.view.User;

import org.example.controller.MainController;
import org.example.model.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilePage extends JFrame {

    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField birthdateField;
    private final JComboBox<String> sexComboBox;

    public ProfilePage() {
        // Create the frame
        setTitle("Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2));

        User selectedUser = MainController.getSelectedUser();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(selectedUser.getName());
        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField(selectedUser.getSurname());
        JLabel birthdateLabel = new JLabel("Birthdate:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(selectedUser.getBirthdate());
        birthdateField = new JTextField(formattedDate);
        String[] sexOptions = {"Male", "Female"};
        sexComboBox = new JComboBox<>(sexOptions);
        sexComboBox.setSelectedItem(selectedUser.getSex());
        JLabel sexLabel = new JLabel("Sex:");

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(surnameLabel);
        mainPanel.add(surnameField);
        mainPanel.add(birthdateLabel);
        mainPanel.add(birthdateField);
        mainPanel.add(sexLabel);
        mainPanel.add(sexComboBox);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserProfile();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveUserProfile() {
        User selectedUser = MainController.getSelectedUser();
        selectedUser.setName(nameField.getText());
        selectedUser.setSurname(surnameField.getText());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date birthdate = dateFormat.parse(birthdateField.getText());
            selectedUser.setBirthdate(birthdate);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(ProfilePage.this, "Invalid birthdate format!");
            return;
        }

        String sex = (String) sexComboBox.getSelectedItem();
        selectedUser.setSex(sex);
        MainController.updateUser(selectedUser);

        dispose();
    }
}
