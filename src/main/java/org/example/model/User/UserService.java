package org.example.model.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.databaseClient.DatabaseClient;

import java.util.ArrayList;

public class UserService {
    private static DatabaseClient dbClient = new DatabaseClient();

    public void register(User user) {
        dbClient.register(user);
    }

    public ArrayList<User> getUsers() {
        return dbClient.getUsers();
    }
}
