package org.example.platform;

import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;

import java.util.Date;
import java.util.List;

public interface IActivityPlatform {
    List<Activity> getActivitiesBetweenDates(ObjectId userId, Date startDate, Date endDate);
    void addActivityToUser(User user, Activity activity);

}
