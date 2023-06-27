package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.provider.IUserProvider;
import org.example.provider.UserProvider;

import static org.example.mapper.ActivityMapper.ActivityToDocument;
import static org.example.mapper.UserMapper.UserToDocument;

import java.util.ArrayList;
import java.util.List;

public class databaseClient {
    private MongoCollection<Document> userCollection;

    private MongoCollection<Document> activityCollection;
    private MongoClient mongoClient;
    private static String connectionString = "";
    private MongoDatabase database;
    private IUserProvider userProvider;

    public databaseClient() {
        Dotenv dotenv = Dotenv.configure().load();
        connectionString = dotenv.get("ConnectionString");
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.setUserCollection(database.getCollection("user"));
        this.activityCollection = database.getCollection("activity");
        userProvider = new UserProvider(userCollection);

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

    public MongoCollection<Document> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }

    public void register(User user) {
        InsertOneResult addResult = userProvider.addOneUser(UserToDocument(user));
        System.out.println("User registered successfully.");
    }

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



    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
