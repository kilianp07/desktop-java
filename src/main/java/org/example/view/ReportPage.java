package org.example.view;

import org.example.model.Activity.Activity;
import org.example.stats.ActivityStats;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReportPage extends JFrame {

    private ActivityStats activityStats;
    private DefaultListModel<String> listModel;
    private JList<String> numberList;

    public ReportPage(ActivityStats activityStats) {
        this.activityStats = activityStats;
        setTitle("Week stats");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));

        // Create a list model and JList
        listModel = new DefaultListModel<>();
        numberList = new JList<>(listModel);

        // Add some sample data
        listModel.addElement("Total load: "+activityStats.getTotalLoad());
        listModel.addElement("Monotony: "+activityStats.getMonotony());
        listModel.addElement("Constraint: "+activityStats.getConstraint());
        listModel.addElement("Fitness: "+activityStats.getFitness());

        // Create a JScrollPane to hold the JList
        JScrollPane scrollPane = new JScrollPane(numberList);

        // Set the layout and add the components
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

}
