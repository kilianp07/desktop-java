package org.example.platform;

import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;

import java.util.ArrayList;

public interface IUserPlatform {
    ObjectId register(User user);
    void addActivityToUser(User user, Activity activity);
    ArrayList<User> getAllUsers();
}
