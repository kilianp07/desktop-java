package org.example.controller;

import org.example.databaseClient.DatabaseClient;
import org.example.model.User.User;
import org.example.model.User.UserService;
import org.example.view.Home.HomePage;
import org.example.view.User.UserForm;
import org.example.view.User.UserSelect;

import javax.swing.*;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class MainController {
    private static DatabaseClient client = new DatabaseClient();
    private static UserService userService = new UserService();
    private static User selectedUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            checkHasUsers();
        });

    }

    public static void checkHasUsers() {
        ArrayList<User> users = userService.getUsers();
        if(users.size() == 0) {
            new UserForm();
        } else {
            new UserSelect().setOptions(users);
        }
    }

    public static void setSelectedUser(User user) {
        selectedUser = user;
        new HomePage();

        System.out.println("Selected user: " + user.getName() + " " + user.getSurname());
    }

    public static User getSelectedUser() {
        return selectedUser;
    }

    public static void openUserForm() {
        new UserForm();
    }

    public void register(User user) {
        client.init();
        client.register(user);
        selectedUser = user;
        new HomePage();
    }

}