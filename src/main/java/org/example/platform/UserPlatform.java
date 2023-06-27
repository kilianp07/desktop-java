package org.example.platform;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.example.model.User.User;
import org.example.provider.IUserProvider;
import org.example.provider.UserProvider;

import static org.example.mapper.UserMapper.UserToDocument;

public class UserPlatform implements IUserPlatform {
    private IUserProvider userProvider;

    public UserPlatform(MongoCollection<Document> userCollection) {

        this.userProvider = new UserProvider(userCollection);
    }

    @Override
    public void register(User user) {
        InsertOneResult addResult = userProvider.addOneUser(UserToDocument(user));
        System.out.println("User registered successfully.");
    }
}
