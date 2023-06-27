package org.example.mapper;

import org.bson.Document;
import org.example.model.Activity.Activity;

public class ActivityMapper {
    public static Document ActivityToDocument(Activity activity){
        return new Document("name", activity.getName())
                .append("durationInMinutes", activity.getDurationInMinutes())
                .append("RPE", activity.getRpFeltPostEffort())
                .append("load", activity.getLoad())
                .append("date", activity.getDate());
    }

}
