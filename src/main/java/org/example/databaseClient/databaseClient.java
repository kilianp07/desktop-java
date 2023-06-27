package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.repository.IUserManager;
import org.example.repository.UserManager;

import static org.example.mapper.UserMapper.UserToDocument;

import java.util.ArrayList;
import java.util.List;

public class databaseClient {
    private MongoCollection<Document> userCollection;

    private MongoCollection<Document> activityCollection;
    private MongoClient mongoClient;
    private static String connectionString = "mongodb+srv://application-desktop:application-desktop@cluster0.mhqxhpt.mongodb.net/?retryWrites=true&w=majority";
    private MongoDatabase database;
    private IUserManager userManager;

    public databaseClient() {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.userCollection = database.getCollection("user");
        this.activityCollection = database.getCollection("activity");
        userManager = new UserManager(userCollection);

    }
    public void init() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
                MongoDatabase database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void register(User user) {
        InsertOneResult addResult = userManager.addOneUser(UserToDocument(user));
        System.out.println("User registered successfully.");
    }

    private Document ActivityToDocument(Activity activity){
        return new Document("name", activity.getName())
                .append("durationInMinutes", activity.getDurationInMinutes())
                .append("RPE", activity.getRpFeltPostEffort())
                .append("load", activity.getLoad())
                .append("date", activity.getDate());
    }
    public void createActivity(User user, Activity activity) {
        // Create an activity document
        Document activityDocument = ActivityToDocument(activity);

        // Find the user document
        Document userDocument = userCollection.find(new Document("_id", user.getObjectId())).first();

        if (userDocument != null) {
            // Get the existing activities array from the user document
            List<Document> activities = userDocument.getList("activity", Document.class, new ArrayList<>());

            // Append the new activity document to the activities array
            activities.add(activityDocument);

            // Update the activities array in the user document
            userDocument.put("activities", activities);

            // Update the user document in the user collection
            userCollection.updateOne(new Document("_id", user.getObjectId()), new Document("$set", userDocument));

            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }



    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
