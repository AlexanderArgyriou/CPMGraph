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
        ActivityAbstract a = ActivityFactory.constructActivity( "A", 2, 1, 3 );
        ActivityAbstract b = ActivityFactory.constructActivity( "B", 3, 2, 4 );
        ActivityAbstract c = ActivityFactory.constructActivity( "C", 2, 1, 3 );
        ActivityAbstract d = ActivityFactory.constructActivity( "D", 4, 2, 6 );
        ActivityAbstract e = ActivityFactory.constructActivity( "E", 4, 1, 7 );
        ActivityAbstract f = ActivityFactory.constructActivity( "F", 2, 1, 9 );
        ActivityAbstract g = ActivityFactory.constructActivity( "G", 4, 3, 11 );
        ActivityAbstract h = ActivityFactory.constructActivity( "H", 2, 1, 3 );

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
