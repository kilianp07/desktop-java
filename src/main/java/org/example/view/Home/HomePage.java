package org.example.view.Home;
import com.sun.tools.javac.Main;
import org.example.controller.MainController;
import org.example.view.Activity.ActivityForm;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
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
        currentUserName.setText("logged as: "+MainController.getSelectedUser().getName());

        getContentPane().add(currentUserName, BorderLayout.NORTH);

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        setVisible(true);
    }
}

