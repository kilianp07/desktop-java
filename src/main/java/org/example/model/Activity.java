package org.example.model;
public class Activity {
    private String name;
    private int durationInMinutes;
    private int rpFeltPostEffort;
    private int load;

    public Activity(String name, int durationInMinutes, int rpFeltPostEffort) {
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.rpFeltPostEffort = rpFeltPostEffort;
        this.load = durationInMinutes * rpFeltPostEffort;
    }

    // Getters and setters for the properties

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        this.load = durationInMinutes * rpFeltPostEffort; // Recalculate the load when duration changes
    }

    public int getRpFeltPostEffort() {
        return rpFeltPostEffort;
    }

    public void setRpFeltPostEffort(int rpFeltPostEffort) {
        this.rpFeltPostEffort = rpFeltPostEffort;
        this.load = durationInMinutes * rpFeltPostEffort; // Recalculate the load when RPE changes
    }

    public int getLoad() {
        return load;
    }
}

