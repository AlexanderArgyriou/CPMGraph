package models.networks;

import constants.Constants;
import models.activities.Activity;
import models.activities.ActivityAbstract;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AonNetwork implements AonNetworkIf {
    private Graph<ActivityAbstract, DefaultEdge> simpleDirectedGraph = new SimpleDirectedGraph<>( DefaultEdge.class );
    private GraphPath<ActivityAbstract, DefaultEdge> criticalPath;
    private double averageProjectDuration = 0.0d;
    private double projectFluctuation = 0.0d;
    private double projectStandardDeviation = 0.0d;

    public AonNetwork() {
    }

    public AonNetwork(Graph<ActivityAbstract, DefaultEdge> simpleDirectedGraph) {
        this.simpleDirectedGraph = simpleDirectedGraph;
    }

    public void addActivity(ActivityAbstract activity, List<ActivityAbstract> before) {
        simpleDirectedGraph.addVertex( activity );
        beforeActivities( activity, before );
    }

    private void beforeActivities(ActivityAbstract activity, List<ActivityAbstract> before) {
        for ( ActivityAbstract a : before ) {
            if ( simpleDirectedGraph.containsVertex( a ) ) {
                simpleDirectedGraph.addEdge( a, activity );
            } else {
                simpleDirectedGraph.addVertex( a );
                simpleDirectedGraph.addEdge( a, activity );
            }
        }
    }

    public Graph<ActivityAbstract, DefaultEdge> getSimpleDirectedGraph() {
        return simpleDirectedGraph;
    }

    public void setSimpleDirectedGraph(Graph<ActivityAbstract, DefaultEdge> simpleDirectedGraph) {
        this.simpleDirectedGraph = simpleDirectedGraph;
    }

    public GraphPath<ActivityAbstract, DefaultEdge> getCriticalPath() {
        return criticalPath;
    }

    public void setCriticalPath(GraphPath<ActivityAbstract, DefaultEdge> criticalPath) {
        this.criticalPath = criticalPath;
    }

    @Override
    public Graph<ActivityAbstract, DefaultEdge> toGraph() {
        return simpleDirectedGraph;
    }

    @Override
    public void traverse() {
        List<ActivityAbstract> vertexes = new ArrayList<>( simpleDirectedGraph.vertexSet() );
        for ( ActivityAbstract a : vertexes ) {
            System.out.println( Constants.CURR.get() + a );
            int count = 1;
            for ( ActivityAbstract ac : Graphs.successorListOf( simpleDirectedGraph, a ) ) {
                System.out.println( count++ + " " + ac );
            }
            System.out.println();
            System.out.println( Constants.SEPARATOR );
        }
        printCP();
        System.out.println( Constants.AVG_PRJ_DUR.get() + averageProjectDuration );
        System.out.println( Constants.PRJ_FL.get() + projectFluctuation );
        System.out.println( Constants.PRJ_STD_EV.get() + projectStandardDeviation );
    }

    private void printCP() {
        System.out.println( Constants.CP.get() + criticalPath.getVertexList().stream()
                .map( v -> ( ( Activity ) v ).getName() )
                .collect( Collectors.toList() ) );
    }

    @Override
    public void calc() {
        Iterator<ActivityAbstract> iterator = new BreadthFirstIterator<>( simpleDirectedGraph );// bfs traverse
        List<ActivityAbstract> vertexes = new ArrayList<>();
        while ( iterator.hasNext() ) {
            ActivityAbstract a = iterator.next();
            if ( !isStart( a ) ) {
                calcExpectedTime( a );
                calcFluctuation( a );
            }
            vertexes.add( a );
            calcEarlierStart( a );
            calcEarlierCompletion( a );
        }
        Collections.reverse( vertexes ); // reversed bfs
        for ( ActivityAbstract a : vertexes ) {
            calcSlowerEnd( a );
            calcSlowerStart( a );
        }
        calcSlack();
        calcCriticalPath();
        calcAverageProjectDuration();
        calcProjectFluctuation();
        calcProjectStandardDeviation();
    }

    private void calcProjectStandardDeviation() {
        projectStandardDeviation = BigDecimal.valueOf( Math.sqrt( projectFluctuation ) )
                .setScale( 2, RoundingMode.HALF_UP )
                .doubleValue();
    }

    private void calcProjectFluctuation() {
        for ( ActivityAbstract a : criticalPath.getVertexList() ) {
            projectFluctuation += a.getFluctuation();
        }
    }

    private void calcAverageProjectDuration() {
        for ( ActivityAbstract a : criticalPath.getVertexList() ) {
            averageProjectDuration += a.getExpectedTime();
        }
    }

    private boolean isStart(ActivityAbstract a) {
        return a instanceof Activity && "Start".equals( ( ( Activity ) a ).getName() );
    }

    private void calcFluctuation(ActivityAbstract a) {
        a.setFluctuation( ( Math.pow( ( ( double ) ( a.getOptimisticCompletionPrediction() - a.getPessimisticCompletionPrediction() ) / 6 ), 2 ) ) );
    }

    private void calcExpectedTime(ActivityAbstract a) {
        a.setExpectedTime( ( double ) ( a.getPessimisticCompletionPrediction() + 4 * a.getMostPossibleCompletion() + a.getOptimisticCompletionPrediction() ) / 6 );
    }

    private void calcCriticalPath() {
        Iterator<ActivityAbstract> iterator = new BreadthFirstIterator<>( simpleDirectedGraph );
        AllDirectedPaths<ActivityAbstract, DefaultEdge> paths = new AllDirectedPaths<>( simpleDirectedGraph );
        List<ActivityAbstract> vertexes = new ArrayList<>();
        while ( iterator.hasNext() ) {
            vertexes.add( iterator.next() );
        }
        GraphPath<ActivityAbstract, DefaultEdge> longestPath = paths.getAllPaths( vertexes.get( 0 ), vertexes.get( vertexes.size() - 1 ), true, null )
                .stream()
                .sorted( (GraphPath<ActivityAbstract, DefaultEdge> path1, GraphPath<ActivityAbstract, DefaultEdge> path2) -> Integer.valueOf( path2.getLength() ).compareTo( path1.getLength() ) )
                .findFirst()
                .get();
        setCriticalPath( longestPath );
    }

    private void calcSlack() {
        for ( ActivityAbstract a : simpleDirectedGraph.vertexSet() ) {
            a.setSlack( a.getSlowerStart() - a.getEarlierStart() );
            // a.setSlack( a.getSlowerCompletion() - a.getEarlierCompletion() );
        }
    }

    private void calcSlowerStart(ActivityAbstract a) {
        a.setSlowerStart( a.getSlowerCompletion() - a.getDuration() );
    }

    private void calcSlowerEnd(ActivityAbstract a) {
        List<ActivityAbstract> after = Graphs.successorListOf( simpleDirectedGraph, a );
        if ( !after.isEmpty() ) {
            a.setSlowerCompletion( Collections.min( after.stream()
                    .map( ActivityAbstract::getSlowerStart )
                    .collect( Collectors.toList() ) ) ); // min of BEj( successor nodes )
        } else {
            a.setSlowerCompletion( a.getEarlierCompletion() ); // last node( end of project )
        }
    }

    private void calcEarlierCompletion(ActivityAbstract a) {
        a.setEarlierCompletion( a.getDuration() + a.getEarlierStart() ); // E
    }

    private void calcEarlierStart(ActivityAbstract a) {
        List<ActivityAbstract> before = Graphs.predecessorListOf( simpleDirectedGraph, a );
        if ( !before.isEmpty() ) {
            a.setEarlierStart( Collections.max( before.stream()
                    .map( ActivityAbstract::getEarlierCompletion )
                    .collect( Collectors.toList() ) ) ); // max of EOj( predecessor nodes )
        } else {
            a.setEarlierStart( a.getDuration() ); // start of project
        }
    }

    @Override
    public void printExpectedProjectDuration(double probabilityOfCompletion) {
        System.out.println( Constants.PROJ_DUR.get() + probabilityOfCompletion * 100 + "% -> " + findExpectedProjectDuration( probabilityOfCompletion ) ); // x = z * σ + μ
    }

    @Override
    public void printProbabilityOfProjectCompletionBetween(double start, double end) {
        System.out.println( Constants.PROB_START_END.get() + start + " - " + end + " -> " + findProbabilityOfProjectCompletionBetween( start, end ) * 100 + "%" );
    }

    public double findProbabilityOfProjectCompletionBetween(double start, double end) {
        return BigDecimal.valueOf( findProbabilityOfProjectCompletion( end ) - findProbabilityOfProjectCompletion( start ) )
                .setScale( 2, RoundingMode.HALF_UP )
                .doubleValue();
    }

    public double findExpectedProjectDuration(double probabilityOfCompletion) {
        return BigDecimal.valueOf( new NormalDistribution().inverseCumulativeProbability( probabilityOfCompletion ) * projectStandardDeviation + averageProjectDuration )
                .setScale( 2, RoundingMode.HALF_UP )
                .doubleValue();
    }

    @Override
    public void printProbabilityOfProjectCompletion(double givenTime) {
        System.out.println( Constants.PROBAB.get() + givenTime + " -> " + findProbabilityOfProjectCompletion( givenTime ) * 100 + "%" ); // ( given time - expected time / standard deviation ) then from normal dist array we find the prob.
    }

    public double findProbabilityOfProjectCompletion(double givenTime) {
        return BigDecimal.valueOf( new NormalDistribution().cumulativeProbability( ( givenTime - averageProjectDuration ) / projectStandardDeviation ) )
                .setScale( 4, RoundingMode.HALF_UP )
                .doubleValue();
    }

    @Override
    public void printVertexesBFS() {
        Iterator<ActivityAbstract> iterator = new BreadthFirstIterator<>( simpleDirectedGraph );
        while ( iterator.hasNext() ) {
            System.out.println( iterator.next() );
        }
        printCP();
        System.out.println( Constants.AVG_PRJ_DUR.get() + averageProjectDuration );
        System.out.println( Constants.PRJ_FL.get() + projectFluctuation );
        System.out.println( Constants.PRJ_STD_EV.get() + projectStandardDeviation );
    }
}
