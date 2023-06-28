package org.example.mapper;

import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

import static org.example.mapper.ActivityMapper.ActivityToDocument;
import static org.example.mapper.ActivityMapper.DocumentToActivity;

public class UserMapper {
    public static Document UserToDocument(User user){
        List<Document> activities = new ArrayList<>();
        for (Activity activity : user.getActivityList())
        {
            activities.add(ActivityToDocument(activity));
        }
        return new Document()
                .append("name", user.getName())
                .append("surname", user.getSurname())
                .append("birthdate", user.getBirthdate())
                .append("sex", user.getSex())
                .append("activities", activities);
    }

    public static User DocumentToUser(Document userDocument){
        User newUser = new User();
        newUser.setObjectId(userDocument.getObjectId("_id"));
        newUser.setName(userDocument.getString("name"));
        newUser.setSurname(userDocument.getString("surname"));
        newUser.setBirthdate(userDocument.getDate("birthdate"));
        newUser.setSex(userDocument.getString("sex"));

        List<Activity> activities = new ArrayList<>();
        for (Document document : userDocument.getList("activities", Document.class)) {
            activities.add(DocumentToActivity(document));
        }

        newUser.setActivityList(activities);

        return newUser;
    }
}
