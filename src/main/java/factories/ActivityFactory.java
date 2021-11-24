package factories;

import models.activities.Activity;
import models.activities.ActivityAbstract;

public class ActivityFactory {
    private ActivityFactory() {
    }

    public static ActivityAbstract constructActivity(String name, int duration, int optimistic, int pessimistic) {
        return new Activity( duration, optimistic, pessimistic, name );
    }

    public static ActivityAbstract constructActivity(String name, int duration) {
        return new Activity( name, duration );
    }

    public static ActivityAbstract constructActivity(String name) {
        return new Activity( name );
    }
}
