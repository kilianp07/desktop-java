package org.example.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.mapper.ActivityMapper;
import org.example.model.Activity.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActivityManager {

    private MongoCollection<Document> userCollection;

    public ActivityManager(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
}
