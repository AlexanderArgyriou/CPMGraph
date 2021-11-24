package constants;

public enum Constants {
    SEPARATOR( "-------------------------------------------------------" ),
    CURR( "Curr Node -> " ),
    CP( "Critical Path -> " ),
    AVG_PRJ_DUR( "Average project duration ->" ),
    PRJ_FL( "Project's fluctuation -> " ),
    PRJ_STD_EV( "Project's standard deviation -> " ),
    PROBAB( "Probability of completion in : " ),
    PROJ_DUR( "Expected project duration when prob of completion is " ),
    PROB_START_END( "Probability of project completion in between " );

    Constants(String s) {
    }

    public String get() {
        return this.name();
    }
}
