package org.example.databaseClient;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class databaseClientTest {

    private static final String DATABASE_NAME = "DESKTOP_YNOV_DATABASE";
    private static final String USER_COLLECTION_NAME = "user";
    private static final String ACTIVITY_COLLECTION_NAME = "activity";

    private databaseClient client;

    @BeforeEach
    public void setup() {
        client = new databaseClient();
        client.init();
    }

    @AfterEach
    public void cleanup() {
        MongoCollection<Document> userCollection = client.getMongoClient().getDatabase(DATABASE_NAME).getCollection(USER_COLLECTION_NAME);
        userCollection.deleteMany(new Document());

        MongoCollection<Document> activityCollection = client.getMongoClient().getDatabase(DATABASE_NAME).getCollection(ACTIVITY_COLLECTION_NAME);
        activityCollection.deleteMany(new Document());
    }

    @Test
    public void testRegisterUser() {
        // Create a user object
        User user = new User("John", "Doe", new Date(), "Male");

        // Register the user
        client.register(user);

        // Retrieve the user document from the database
        MongoCollection<Document> userCollection = client.getMongoClient().getDatabase(DATABASE_NAME).getCollection(USER_COLLECTION_NAME);
        Document userDocument = userCollection.find().first();

        // Assert that the user document is not null and contains the expected values
        assertNotNull(userDocument);
        assertEquals("John", userDocument.getString("name"));
        assertEquals("Doe", userDocument.getString("surname"));
        // Add assertions for other user properties (birthdate, sex) if needed
    }

    @Test
    public void testCreateActivityForUser() {
        // Create a user
        User user = new User("John", "Doe", new Date(), "Male");

        // Register the user
        client.register(user);

        // Create an activity
        Activity activity = new Activity("Running", 30, 8, new Date());

        // Create the activity for the user
        client.createActivity(user, activity);

        // Verify that the activity is associated with the user
        // You may need to modify this assertion based on your actual implementation
        // Verify that the user document contains the activity
    }
}
