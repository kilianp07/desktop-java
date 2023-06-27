package org.example.repository;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

public interface IUserManager {
    InsertOneResult addOneUser(Document userDocument);
    Document getUserDocumentById(ObjectId userId);
    UpdateResult updateUserById(ObjectId userId, Document newEntity);
}
