package org.example.controller;

import org.bson.types.ObjectId;
import org.example.databaseClient.DatabaseClient;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.platform.ActivityPlatform;
import org.example.platform.IActivityPlatform;
import org.example.platform.IUserPlatform;
import org.example.platform.UserPlatform;
import org.example.view.Home.HomePage;
import org.example.view.User.UserForm;
import org.example.view.User.UserSelect;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class MainController {
    private static DatabaseClient client = new DatabaseClient();

    private static IUserPlatform userPlatform = new UserPlatform(client.getUserCollection());
    private static IActivityPlatform activityPlatform = new ActivityPlatform(client.getUserCollection());

    private static User selectedUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            checkHasUsers();
        });

    }

    public static void checkHasUsers() {

        ArrayList<User> users = userPlatform.getAllUsers();

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
        activityPlatform.addActivityToUser(selectedUser, activity);
        List<Activity>activities  = selectedUser.getActivityList();
        selectedUser.setActivityList(activities);
        new HomePage();
    }

    public static void register(User user) {
        ObjectId userId = userPlatform.register(user);
        selectedUser = user;
        selectedUser.setObjectId(userId);
        System.out.println("Registered user: " + userId);
        List<Activity>activities  = selectedUser.getActivityList();
        selectedUser.setActivityList(activities);
        new HomePage();
    }

}