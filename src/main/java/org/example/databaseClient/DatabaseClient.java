package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.repository.ActivityManager;
import org.example.repository.IActivityManager;
import org.example.repository.IUserManager;
import org.example.repository.UserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Date;
import static org.example.mapper.ActivityMapper.ActivityToDocument;
import static org.example.mapper.UserMapper.UserToDocument;

public class DatabaseClient {
    private MongoCollection<Document> userCollection;

    private MongoClient mongoClient;
    private static String connectionString = "";
    private MongoDatabase database;
    private IUserManager userManager;

    private IActivityManager activityManager;

    public DatabaseClient() {
        Dotenv dotenv = Dotenv.configure().load();
        connectionString = dotenv.get("ConnectionString");
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.userCollection = database.getCollection("user");
        userManager = new UserManager(userCollection);
        this.activityManager = new ActivityManager(userCollection);

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

    public ObjectId register(User user) {
        InsertOneResult addResult = userManager.addOneUser(UserToDocument(user));
        System.out.println("User registered successfully.");
        return addResult.getInsertedId().asObjectId().getValue();
    }

    public void addActivityToUser(User user, Activity activity) {
        // Find the user document
        Document userDocument = userManager.getUserDocumentById(user.getObjectId());

        if (userDocument != null) {
            // Get the existing activities array from the user document
            List<Document> activities = userDocument.getList("activity", Document.class, new ArrayList<>());

            // Append the new activity document to the activities array
            activities.add(ActivityToDocument(activity));

            // Update the activities array in the user document
            userDocument.put("activities", activities);

            // Update the user document in the user collection
            userManager.updateUserById(user.getObjectId(), userDocument);

            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public List<Activity> getActivitiesBetweenDates(String userID, Date startDate, Date endDate) {
        return activityManager.getBetweenDates(userID, startDate, endDate);
    }

    public ArrayList<User> getUsers(){
        FindIterable<Document> documents = userCollection.find();
        ArrayList<User> users = new ArrayList<>();
        for (Document document : documents) {
            ObjectId objectId = document.getObjectId("_id");
            String username = document.getString("name");
            String surname = document.getString("surname");
            Date birthdate = document.getDate("birthdate");
            String sex = document.getString("sex");
            ArrayList<Activity> activityList = (ArrayList<Activity>) document.getList("activityList", Activity.class);
            User user = new User(objectId,username, surname, birthdate, sex, activityList);
            System.out.println("objid"+objectId);
            System.out.println("username"+user.getName());
            users.add(user);
        }
        return users;
}
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void newActivity(User selectedUser, Activity activity) {
        Document userDocument = userManager.getUserDocumentById(selectedUser.getObjectId());
        System.out.println(selectedUser);
        if (userDocument != null) {
            List<Document> activities = userDocument.getList("activity", Document.class, new ArrayList<>());
            activities.add(ActivityToDocument(activity));
            userDocument.put("activities", activities);
            userManager.updateUserById(selectedUser.getObjectId(), userDocument);
            System.out.println("Activity created and associated with the user successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
