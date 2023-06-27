package org.example.mapper;

import org.bson.Document;
import org.example.model.User.User;

public abstract class UserMapper {
    public static Document UserToDocument(User user){
        return new Document()
                .append("name", user.getName())
                .append("surname", user.getSurname())
                .append("birthdate", user.getBirthdate())
                .append("sex", user.getSex());
    }
}
