package org.example.view.Home;

import org.example.controller.MainController;
import org.example.model.Activity.Activity;
import org.example.view.Activity.ActivityDetailWindow;
import org.example.view.Activity.ActivityForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class HomePage extends JFrame {
    private ActivityDetailWindow activityDetailWindow;

    public HomePage() {
        // Create the frame
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Calculate the center coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;

        // Set the JFrame location to the center
        setLocation(centerX, centerY);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");

        JMenuItem item1 = new JMenuItem("Register an activity");
        item1.addActionListener(e -> {
            new ActivityForm();
            dispose();
        });
        JMenuItem item2 = new JMenuItem("History");
        JMenuItem item3 = new JMenuItem("Reports");

        JLabel currentUserName = new JLabel();
        JLabel listActivities = new JLabel();
        currentUserName.setText("logged as: " + MainController.getSelectedUser().getName());

        listActivities.setText("List of activities: ");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Activity activity : MainController.getSelectedUser().getActivityList()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(activity.getDate());
            listModel.addElement(activity.getName() + " at " + formattedDate);
        }
        JList<String> elementList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(elementList);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(currentUserName, BorderLayout.NORTH);
        mainPanel.add(listActivities, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align the button to the right
        JButton selectButton = new JButton("Select activity");
        buttonPanel.add(selectButton);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = elementList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Activity selectedActivity = MainController.getSelectedUser().getActivityList().get(selectedIndex);
                    openActivityDetailWindow(selectedActivity);
                } else {
                    JOptionPane.showMessageDialog(HomePage.this, "Please select an activity");
                }
            }
        });

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);

        setJMenuBar(menuBar);
        getContentPane().add(mainPanel);
        setVisible(true);
    }
    private void openActivityDetailWindow(Activity activity) {
        activityDetailWindow = new ActivityDetailWindow(activity, this);
        dispose();
    }
}
