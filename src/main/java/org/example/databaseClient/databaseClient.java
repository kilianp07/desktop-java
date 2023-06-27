package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.example.platform.IUserPlatform;
import org.example.platform.UserPlatform;


import java.util.ArrayList;
import java.util.Date;

public class DatabaseClient {
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> activityCollection;
    private MongoClient mongoClient;
    private static String connectionString = "";
    private MongoDatabase database;
    private IUserPlatform userPlatform;

    public DatabaseClient() {

        Dotenv dotenv = Dotenv.configure().load();
        connectionString = dotenv.get("ConnectionString");
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.setUserCollection(database.getCollection("user"));
        this.activityCollection = database.getCollection("activity");
        userPlatform = new UserPlatform(userCollection);

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
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public ArrayList<User> getUsers(){
        FindIterable<Document> documents = userCollection.find();
        ArrayList<User> users = new ArrayList<>();
        for (Document document : documents) {
            String username = document.getString("name");
            String surname = document.getString("surname");
            Date birthdate = document.getDate("birthdate");
            String sex = document.getString("sex");
            ArrayList<Activity> activityList = (ArrayList<Activity>) document.getList("activityList", Activity.class);
            User user = new User(username, surname, birthdate, sex, activityList);
            users.add(user);
        }
        return users;
    }
}
