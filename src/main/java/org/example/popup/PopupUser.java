package org.example.popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.example.model.User;
import org.example.mongoConnect.MongoClientConnection;

public class PopupUser {

    private JFrame frame;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField birthDateField;
    private JComboBox<String> sexComboBox;

    public PopupUser() {
        frame = new JFrame("Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 2));

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

                User user = new User(lastName, firstName, birthDate, sex);

                MongoDatabase database = MongoClientConnection.connectToMongoClient();

                MongoCollection<Document> collection = database.getCollection("name_collection");

                Document document = new Document();
                document.put("name", user.getName());
                document.put("surname", user.getSurname());
                document.put("birthdate", user.getBirthdate());
                document.put("sex", user.getSex());

                collection.insertOne(document);

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