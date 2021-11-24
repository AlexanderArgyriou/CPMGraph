package factories;

import models.networks.AonNetwork;
import models.networks.AonNetworkIf;

public class AonFactory {
    private AonFactory() {
    }

    public static AonNetworkIf newAonNetwork() {
        return new AonNetwork();
    }
}
