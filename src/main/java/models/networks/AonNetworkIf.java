package models.networks;

import models.activities.ActivityAbstract;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;

public interface AonNetworkIf {
    void calc();

    void addActivity(ActivityAbstract activity, List<ActivityAbstract> before);

    void traverse();

    Graph<ActivityAbstract, DefaultEdge> toGraph();

    void printVertexesBFS();
}
