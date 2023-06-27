package org.example.view.User;

import org.example.controller.MainController;
import org.example.model.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserSelect extends JDialog {
    private JList<String> elementList;
    private ArrayList<User> userList;
    private JButton selectButton;
    private JButton createUserButton;

    public UserSelect() {

        // Calculate the center coordinates
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;

        // Set the JFrame location to the center
        setLocation(centerX, centerY);

        // Set up the JFrame
        setTitle("Select existing user");
        setPreferredSize(new Dimension(300, 200));
        setLayout(new BorderLayout());

        // Create the list model with hardcoded elements
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Element 1");
        listModel.addElement("Element 2");
        listModel.addElement("Element 3");

        // Create the JList with the list model
        elementList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(elementList);
        add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        selectButton = new JButton("Select");
        createUserButton = new JButton("Register new user");
        buttonPanel.add(selectButton);
        buttonPanel.add(createUserButton);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainController.openUserForm();
                setVisible(false);
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("list:"+userList.get(0).getObjectId());
                String selectedElement = elementList.getSelectedValue();
                int selectedIndex = elementList.getSelectedIndex();
                User selectedUser = userList.get(selectedIndex);
                if (selectedElement != null) {
                    MainController.setSelectedUser(selectedUser);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(UserSelect.this, "No element selected");
                }
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);

        // Pack and display the JFrame
        pack();
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    public void setOptions(ArrayList<User> users) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (User user : users) {
            listModel.addElement(user.getName() + " " + user.getSurname());
        }
        elementList.setModel(listModel);
        userList = users;
    }
}
