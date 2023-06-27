package org.example.repository;

import org.example.model.Activity.Activity;

import java.util.Date;
import java.util.List;

public interface IActivityManager {
    List<Activity> getBetweenDates(String userID, Date startDate, Date endDate);
}
