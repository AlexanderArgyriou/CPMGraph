package samples;

import factories.ActivityFactory;
import factories.AonFactory;
import factories.PredecessorFactory;
import models.activities.ActivityAbstract;
import models.networks.AonNetworkIf;

import java.util.Collections;

public class AonNetworkProvider {
    private AonNetworkProvider() {
    }

    public static AonNetworkIf constructNetwork() {
        AonNetworkIf aon = AonFactory.newAonNetwork();

        ActivityAbstract start = ActivityFactory.constructActivity( "Start" );
        ActivityAbstract a = ActivityFactory.constructActivity( "A", 2 );
        ActivityAbstract b = ActivityFactory.constructActivity( "B", 3 );
        ActivityAbstract c = ActivityFactory.constructActivity( "C", 2 );
        ActivityAbstract d = ActivityFactory.constructActivity( "D", 4 );
        ActivityAbstract e = ActivityFactory.constructActivity( "E", 4 );
        ActivityAbstract f = ActivityFactory.constructActivity( "F", 3 );
        ActivityAbstract g = ActivityFactory.constructActivity( "G", 5 );
        ActivityAbstract h = ActivityFactory.constructActivity( "H", 2 );

        aon.addActivity( start, Collections.emptyList() );
        aon.addActivity( a, PredecessorFactory.constructPredecessors( start ) );
        aon.addActivity( b, PredecessorFactory.constructPredecessors( start ) );
        aon.addActivity( c, PredecessorFactory.constructPredecessors( a ) );
        aon.addActivity( d, PredecessorFactory.constructPredecessors( a, b ) );
        aon.addActivity( e, PredecessorFactory.constructPredecessors( c ) );
        aon.addActivity( f, PredecessorFactory.constructPredecessors( c ) );
        aon.addActivity( g, PredecessorFactory.constructPredecessors( d, e ) );
        aon.addActivity( h, PredecessorFactory.constructPredecessors( f, g ) );

        return aon;
    }
}
