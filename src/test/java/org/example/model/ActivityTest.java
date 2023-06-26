package org.example.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityTest {

    @Test
    public void testActivityProperties() {
        // Create an Activity instance
        String name = "Running";
        int durationInMinutes = 30;
        int rpFeltPostEffort = 7;
        Activity activity = new Activity(name, durationInMinutes, rpFeltPostEffort);

        // Verify the activity's properties
        Assertions.assertEquals(name, activity.getName());
        Assertions.assertEquals(durationInMinutes, activity.getDurationInMinutes());
        Assertions.assertEquals(rpFeltPostEffort, activity.getRpFeltPostEffort());
        Assertions.assertEquals(durationInMinutes * rpFeltPostEffort, activity.getLoad());
    }

    @Test
    public void testLoadCalculation() {
        // Create an Activity instance
        String name = "Cycling";
        int durationInMinutes = 45;
        int rpFeltPostEffort = 6;
        Activity activity = new Activity(name, durationInMinutes, rpFeltPostEffort);

        // Modify the duration and RPE
        int newDuration = 60;
        int newRPE = 5;
        activity.setDurationInMinutes(newDuration);
        activity.setRpFeltPostEffort(newRPE);

        // Verify the updated load calculation
        Assertions.assertEquals(newDuration * newRPE, activity.getLoad());
    }
}
