package org.example.platform;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.provider.IUserProvider;
import org.example.provider.UserProvider;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActivityPlatform implements IActivityPlatform {
    private IUserProvider userProvider;

    public ActivityPlatform(MongoCollection<Document> userCollection) {

        this.userProvider = new UserProvider(userCollection);
    }

    @Override
    public List<Activity> getActivitiesBetweenDates(ObjectId userId, Date startDate, Date endDate) {
        User user = userProvider.getUserById(userId);

        if (user == null){
            return Collections.emptyList();
        }

        List<Activity> activities = user.getActivityList();

        return activities;
    }
}
