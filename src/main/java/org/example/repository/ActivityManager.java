package org.example.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.mapper.ActivityMapper;
import org.example.model.Activity.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActivityManager implements IActivityManager {

    private MongoCollection<Document> userCollection;

    public ActivityManager(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
    @Override
    public List<Activity> getBetweenDates(String userID, Date startDate, Date endDate) {
        // Create the date range query
        Document query = new Document("_id", userID)
                .append("activities.date", new Document("$gte", startDate).append("$lte", endDate));

        // Execute the query and retrieve the user document
        Document userDocument = (Document) userCollection.find(query).first();

        if (userDocument == null){
            // User not found
            return Collections.emptyList();
        }

        // Retrieve the activities array from the user document
        List<Document> activityDocuments = userDocument.getList("activities", Document.class);

        // Convert the activity documents to Activity objects
        List<Activity> activities = new ArrayList<>();
        for (Document activityDocument : activityDocuments) {
            activities.add(ActivityMapper.DocumentToActivity(activityDocument));
        }
        return activities;
    }
}
