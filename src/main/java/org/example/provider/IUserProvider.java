package org.example.provider;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.User.User;

import java.util.ArrayList;

public interface IUserProvider {
    InsertOneResult addOneUser(User user);
    User getUserById(ObjectId userId);
    UpdateResult updateUserById(ObjectId userId, User newEntity);
    ArrayList<User> getAllUser();
}
