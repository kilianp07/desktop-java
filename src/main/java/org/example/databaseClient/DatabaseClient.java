package org.example.databaseClient;

import com.mongodb.*;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;



public class DatabaseClient {
    private MongoCollection<Document> userCollection;

    private MongoClient mongoClient;
    private static String connectionString = "";
    private MongoDatabase database;


    public DatabaseClient() {
        Dotenv dotenv = Dotenv.configure().load();
        connectionString = dotenv.get("ConnectionString");
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("DESKTOP_YNOV_DATABASE");
        userCollection = database.getCollection("user");

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
