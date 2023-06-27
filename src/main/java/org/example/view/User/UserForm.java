package org.example.view.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sun.tools.javac.Main;
import org.example.controller.MainController;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.platform.IUserPlatform;
import org.example.platform.UserPlatform;
import org.example.databaseClient.DatabaseClient;

public class UserForm {

    private JFrame frame;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthDateField;
    private JComboBox<String> sexComboBox;

    public UserForm() {
        frame = new JFrame("Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 2));

        // Calculate the center coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - frame.getWidth()) / 2;
        int centerY = (screenSize.height - frame.getHeight()) / 2;

        // Set the JFrame location to the center
        frame.setLocation(centerX, centerY);

        JLabel lastNameLabel = new JLabel("Last name:");
        lastNameField = new JTextField();

        JLabel firstNameLabel = new JLabel("First name:");
        firstNameField = new JTextField();

        JLabel birthDateLabel = new JLabel("Birth date:");
        birthDateField = new JTextField();

        JLabel sexLabel = new JLabel("sex:");
        String[] sexeOptions = { "Male", "Female" };
        sexComboBox = new JComboBox<>(sexeOptions);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String birthDateText = birthDateField.getText();
                String sex = (String) sexComboBox.getSelectedItem();

                if (lastName.isEmpty() || firstName.isEmpty() || birthDateText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all mandatory fields.", "Input error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Date birthDate = null;

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    birthDate = dateFormat.parse(birthDateText);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Please enter the date of birth in dd/MM/yyyy format.", "Input error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = new User(null, lastName, firstName, birthDate, sex,new ArrayList<Activity>() );
                MainController.register(user);

                DatabaseClient client = new DatabaseClient();
                client.init();
                IUserPlatform userPlatform = new UserPlatform(client.getUserCollection());
                userPlatform.register(user);

                frame.dispose();
            }
        });

        frame.add(lastNameLabel);
        frame.add(lastNameField);
        frame.add(firstNameLabel);
        frame.add(firstNameField);
        frame.add(birthDateLabel);
        frame.add(birthDateField);
        frame.add(sexLabel);
        frame.add(sexComboBox);
        frame.add(new JLabel());
        frame.add(saveButton);

        frame.setVisible(true);
    }
}