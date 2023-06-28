package org.example.view.Activity;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.example.controller.MainController;
import org.example.model.Activity.Activity;
import org.example.databaseClient.DatabaseClient;

public class ActivityForm {
    private JFrame frame;
    private JTextField nameField;
    private JTextField durationField;
    private JTextField dateField;
    private JTextField rpeField;

public ActivityForm() {
    frame = new JFrame("New activity");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);
    frame.setLayout(new GridLayout(5, 2));

    // Calculate the center coordinates
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int centerX = (screenSize.width - frame.getWidth()) / 2;
    int centerY = (screenSize.height - frame.getHeight()) / 2;

    // Set the JFrame location to the center
    frame.setLocation(centerX, centerY);

    JLabel nameLabel = new JLabel("Name:");
    nameField = new JTextField();

    JLabel durationLabel = new JLabel("Duration (minutes):");
    durationField = new JTextField();

    JLabel dateLabel = new JLabel("Date:");
    dateField = new JTextField();

    JLabel rpeLabel = new JLabel("PEF (Post-Exercise Feel):");
    rpeField = new JTextField();

    JButton saveButton = new JButton("Saving");
    saveButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String durationText = durationField.getText();
            String dateText = dateField.getText();
            String rpeText = rpeField.getText();

            if (name.isEmpty() || durationText.isEmpty() || rpeText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all mandatory fields.", "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int duration = 0;
            try {
                duration = Integer.parseInt(durationText);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Please enter a valid duration (whole number).", "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            int rpe = 0;
            try {
                rpe = Integer.parseInt(rpeText);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Please enter a valid PEF (whole number).", "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Activity activity = new Activity(name, duration, rpe, new Date());
            MainController.newActivity(activity);

            frame.dispose();
        }
    });

    frame.add(nameLabel);
    frame.add(nameField);
    frame.add(durationLabel);
    frame.add(durationField);
    frame.add(rpeLabel);
    frame.add(rpeField);
    frame.add(new JLabel());
    frame.add(saveButton);

    frame.setVisible(true);
}
}
