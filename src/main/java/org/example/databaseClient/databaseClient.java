package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.model.User.User;

public class databaseClient {
    private MongoCollection<Document> userCollection;
    private MongoClient mongoClient;
    private static String connectionString = "mongodb+srv://application-desktop:application-desktop@cluster0.mhqxhpt.mongodb.net/?retryWrites=true&w=majority";
    private MongoDatabase database;

    public databaseClient() {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.userCollection = database.getCollection("user");
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
        Document userDocument = new Document("name",user.getName())
                .append("surname", user.getSurname())
                .append("birthdate", user.getBirthdate())
                .append("sex", user.getSex());

        userCollection.insertOne(userDocument);
        System.out.println("User registered successfully.");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
