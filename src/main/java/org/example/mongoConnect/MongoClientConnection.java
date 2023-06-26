package org.example.mongoConnect;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.model.User.User;

public class MongoClientConnection {
    private MongoCollection<Document> userCollection;

    private MongoClient mongoClient;

    private static String connectionString = "mongodb+srv://application-desktop:application-desktop@cluster0.mhqxhpt.mongodb.net/?retryWrites=true&w=majority";
    private MongoDatabase database;

    public MongoClientConnection() {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        this.userCollection = database.getCollection("user");
    }
    public void main(String[] args) {

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(this.connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                return database;
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return null;
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
