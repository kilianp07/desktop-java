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
    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
