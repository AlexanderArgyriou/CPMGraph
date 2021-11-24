package factories;

import models.activities.ActivityAbstract;

import java.util.Arrays;
import java.util.List;

public class PredecessorFactory {
    private PredecessorFactory() {
    }

    public static List<ActivityAbstract> constructPredecessors(ActivityAbstract... activities) {
        return Arrays.asList( activities );
    }
}
