package drivers;

import models.networks.AonNetworkIf;
import samples.AonNetworkProvider;

public class Driver {
    public static void main(String[] args) {
        AonNetworkIf aonNetwork = AonNetworkProvider.constructNetwork();
        aonNetwork.calc();
        aonNetwork.printVertexesBFS();
    }
}
