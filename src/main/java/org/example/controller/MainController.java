package org.example.controller;

import org.bson.types.ObjectId;
import org.example.databaseClient.DatabaseClient;
import org.example.model.Activity.Activity;
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
    }

    public static User getSelectedUser() {
        return selectedUser;
    }

    public static void openUserForm() {
        new UserForm();
    }

    public static void newActivity(Activity activity) {
        client.init();
        client.newActivity(selectedUser,activity);
    }

    public static void register(User user) {
        client.init();
        ObjectId id = client.register(user);
        selectedUser = user;
        selectedUser.setObjectId(new ObjectId(String.valueOf(id)));
        System.out.println("Registered user: " + id);
        new HomePage();
    }

}