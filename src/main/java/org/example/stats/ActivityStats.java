package org.example.stats;

import org.bson.types.ObjectId;
import org.example.databaseClient.DatabaseClient;
import org.example.model.Activity.Activity;
import org.example.platform.ActivityPlatform;
import org.example.platform.IActivityPlatform;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ActivityStats {

    private List<Activity> activities;
    private DatabaseClient dbClient;
    private IActivityPlatform activityPlatform;
   private ObjectId userId;
    private float totalLoad;

    // Monotonie = charge hebdomadaire moyenne / l'écart type des charges de la semaine
    private float monotony;

    //Contrainte = charge hebdo*monotonie
    private float constraint;
    //Fitness = Charge - Contrainte
    private float fitness;


    ActivityStats(ObjectId userId) {

        this.userId = userId;

        this.dbClient = new DatabaseClient();
        this.dbClient.init();
        activityPlatform = new ActivityPlatform(dbClient.getUserCollection());

        Error error = getActivities();
        if (error != null) {
            System.out.println(error.getMessage());
            return;
        }
    }

    private Error getActivities(){
        // Subtract 7 days from the current date
        LocalDate previousDate = LocalDate.now().minusDays(7);

        // Convert LocalDate to Date
        Date previousDateAsDate = Date.from(previousDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        this.activities = activityPlatform.getActivitiesBetweenDates(userId, previousDateAsDate , new Date());
        boolean b = this.activities.size() == 0;
        if (b) {
            System.out.println("No activities found for user " + userId);
            return new Error("No activities found for user " + userId);
        }
        return null;
    }

    private void makeCalculations() {
        this.totalLoad = 0;
        for (Activity activity : this.activities) {
            this.totalLoad += activity.getLoad();
        }
        this.monotony = this.totalLoad / getStandardDeviation();
        this.constraint = this.totalLoad * this.monotony;
        this.fitness = this.totalLoad - this.constraint;
    }

    private float getStandardDeviation() {
        int n = activities.size();
        float mean = totalLoad / n;
        float sumOfSquaredDifferences = 0;

        for (Activity activity : activities) {
            float load = activity.getLoad();
            float difference = load - mean;
            sumOfSquaredDifferences += difference * difference;
        }

        return (float) Math.sqrt(sumOfSquaredDifferences / n);
    }

    public float getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(float totalLoad) {
        this.totalLoad = totalLoad;
    }

    public float getMonotony() {
        return monotony;
    }

    public void setMonotony(float monotony) {
        this.monotony = monotony;
    }

    public float getConstraint() {
        return constraint;
    }

    public void setConstraint(float constraint) {
        this.constraint = constraint;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
}
