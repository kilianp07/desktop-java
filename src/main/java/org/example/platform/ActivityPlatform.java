package org.example.platform;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.provider.IUserProvider;
import org.example.provider.UserProvider;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.example.mapper.ActivityMapper.ActivityToDocument;

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

    @Override
    public void addActivityToUser(User user, Activity activity) {
        if (user != null) {
            List<Activity> activities = user.getActivityList() == null ? new ArrayList<>() :  user.getActivityList();
            activities.add(activity);
            user.setActivityList(activities);

            userProvider.updateUserById(user.getObjectId(), user);
            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
