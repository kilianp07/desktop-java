package org.example.provider;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.User.User;

public interface IUserProvider {
    InsertOneResult addOneUser(Document userDocument);
    User getUserById(ObjectId userId);
    UpdateResult updateUserById(ObjectId userId, Document newEntity);
}
