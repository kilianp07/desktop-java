package org.example.databaseClient;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.model.User.User;
import org.example.platform.IUserPlatform;
import org.example.platform.UserPlatform;
import org.example.provider.UserProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.example.mapper.UserMapper.UserToDocument;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoClientConnectionTest {
    private static final String DATABASE_NAME = "DESKTOP_YNOV_DATABASE";
    private static final String COLLECTION_NAME = "user";

    private DatabaseClient mongoClientConnection;
    private MongoDatabase testDatabase;
    private MongoCollection<Document> testUserCollection;

    private MongoClient mongoClient;
    private IUserPlatform userPlatform;

    @BeforeEach
    public void setUp() {
        // Create a new instance of MongoClientConnection
        mongoClientConnection = new DatabaseClient();
        mongoClient = mongoClientConnection.getMongoClient();
        this.testDatabase = mongoClient.getDatabase(DATABASE_NAME);
        testUserCollection = testDatabase.getCollection(COLLECTION_NAME);
        userPlatform = new UserPlatform(testDatabase.getCollection("users"));
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test collection after each test
        testUserCollection.drop();
    }

    @Test
    public void testRegisterUser() {
        // Test data
        // Randomize data to avoid duplicate key errors
        String name = "testName" + new Date().getTime();
        String surname = "testSurname" + new Date().getTime();
        Date birthdate = new Date();
        String sex = "testSex" + new Date().getTime();


        // Call the register method
        userPlatform.register(new User(name, surname, birthdate, sex, new ArrayList<>()));

        // Retrieve the inserted document from the test collection
        Bson projectionFields = Projections.fields(
                Projections.include("name", name),
                Projections.excludeId());

        Document doc = testUserCollection.find()
                .first();

        // Assertion
        assertNotNull(doc, "The inserted document is null.");


        // Perform additional assertions on the inserted document
        assertEquals(name, doc.getString("name"));
        assertEquals(surname, doc.getString("surname"));
        assertEquals(birthdate, doc.getDate("birthdate"));
        assertEquals(sex, doc.getString("sex"));
    }
}
