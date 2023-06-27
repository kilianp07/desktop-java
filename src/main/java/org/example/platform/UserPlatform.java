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
import static org.example.mapper.UserMapper.UserToDocument;

public class UserPlatform implements IUserPlatform {
    private IUserProvider userProvider;

    public UserPlatform(MongoCollection<Document> userCollection) {

        this.userProvider = new UserProvider(userCollection);
    }

    @Override
    public void register(User user) {
        InsertOneResult addResult = userProvider.addOneUser(UserToDocument(user));
        System.out.println("User registered successfully.");
    }

    @Override
    public void addActivityToUser(User user, Activity activity) {
        // Find the user document
        Document userDocument = userProvider.getUserDocumentById(user.getObjectId());

        if (userDocument != null) {
            // Get the existing activities array from the user document
            List<Document> activities = userDocument.getList("activity", Document.class, new ArrayList<>());

            // Append the new activity document to the activities array
            activities.add(ActivityToDocument(activity));

            // Update the activities array in the user document
            userDocument.put("activities", activities);

            // Update the user document in the user collection
            userProvider.updateUserById(user.getObjectId(), userDocument);

            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
