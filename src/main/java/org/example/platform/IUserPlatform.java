package org.example.platform;

import org.example.model.Activity.Activity;
import org.example.model.User.User;

import java.util.ArrayList;

public interface IUserPlatform {
    void register(User user);
    void addActivityToUser(User user, Activity activity);
    ArrayList<User> getAllUsers();
}
