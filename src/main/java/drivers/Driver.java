package drivers;

import models.networks.AonNetworkIf;
import samples.AonNetworkProvider;

public class Driver {
    public static void main(String[] args) {
        AonNetworkIf aonNetwork = AonNetworkProvider.constructNetwork();
        aonNetwork.calc();
        aonNetwork.printVertexesBFS();
        aonNetwork.printProbabilityOfProjectCompletion( 16.0d ); // probability of project completion in x weeks
        aonNetwork.printProbabilityOfProjectCompletion( 12.0d );
        aonNetwork.printExpectedProjectDuration( 0.99d ); // expected project duration when prob of completion is 99%
        aonNetwork.printProbabilityOfProjectCompletionBetween( 12.0d, 16.0d ); // probability of project completion in between 12 & 16 weeks
    }
}
