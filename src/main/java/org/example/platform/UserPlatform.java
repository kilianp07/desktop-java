package org.example.platform;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.provider.IUserProvider;
import org.example.provider.UserProvider;

import java.util.ArrayList;
import java.util.List;

import static org.example.mapper.ActivityMapper.ActivityToDocument;

public class UserPlatform implements IUserPlatform {
    private IUserProvider userProvider;

    public UserPlatform(MongoCollection<Document> userCollection) {

        this.userProvider = new UserProvider(userCollection);
    }

    @Override
    public void register(User user) {
        InsertOneResult addResult = userProvider.addOneUser(user);
        System.out.println("User registered successfully.");
    }

    @Override
    public void addActivityToUser(User user, Activity activity) {
        if (user != null) {
            // Get the existing activities array from the user document
            List<Activity> activities = user.getActivityList();

            // Append the new activity document to the activities array
            activities.add(activity);

            // Update the user document in the user collection
            userProvider.updateUserById(user.getObjectId(), user);

            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return userProvider.getAllUser();
    }
}
