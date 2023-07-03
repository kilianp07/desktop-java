package org.example.view.Activity;

import org.example.model.Activity.Activity;
import org.example.view.Home.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ActivityDetailWindow extends JFrame {
    private HomePage parentPage;

    public ActivityDetailWindow(Activity activity, HomePage parentPage) {
        this.parentPage = parentPage;

        // Create the frame
        setTitle("Activity Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        // Calculate the center coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;

        // Set the JFrame location to the center
        setLocation(centerX, centerY);

        // Create a panel for the activity details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the name label
        JLabel nameLabel = new JLabel("Name: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        detailsPanel.add(nameLabel, constraints);

        JLabel nameValueLabel = new JLabel(activity.getName());
        constraints.gridx = 1;
        constraints.gridy = 0;
        detailsPanel.add(nameValueLabel, constraints);

        // Add the duration label
        JLabel durationLabel = new JLabel("Duration: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        detailsPanel.add(durationLabel, constraints);

        JLabel durationValueLabel = new JLabel(activity.getDurationInMinutes() + " minutes");
        constraints.gridx = 1;
        constraints.gridy = 1;
        detailsPanel.add(durationValueLabel, constraints);

        // Add the RPF label
        JLabel rpfLabel = new JLabel("RPF: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        detailsPanel.add(rpfLabel, constraints);

        JLabel rpfValueLabel = new JLabel(activity.getRpFeltPostEffort() + " / 10");
        constraints.gridx = 1;
        constraints.gridy = 2;
        detailsPanel.add(rpfValueLabel, constraints);

        // Add the load label
        JLabel loadLabel = new JLabel("Load: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        detailsPanel.add(loadLabel, constraints);

        JLabel loadValueLabel = new JLabel(activity.getLoad() + " (product of duration and RPF)");
        constraints.gridx = 1;
        constraints.gridy = 3;
        detailsPanel.add(loadValueLabel, constraints);

        // Add the date label
        JLabel dateLabel = new JLabel("Date: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        detailsPanel.add(dateLabel, constraints);

        JLabel dateValueLabel = new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(activity.getDate()));
        constraints.gridx = 1;
        constraints.gridy = 4;
        detailsPanel.add(dateValueLabel, constraints);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        buttonPanel.add(backButton);

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void closeWindow() {
        parentPage.setVisible(true);
        dispose();
    }
}
