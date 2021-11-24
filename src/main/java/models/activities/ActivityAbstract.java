package models.activities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class ActivityAbstract {
    private int duration = 0;
    private int earlierStart = 0;
    private int earlierCompletion = 0;
    private int slowerStart = 0;
    private int slowerCompletion = 0;
    private int slack = 0;
    private int optimisticCompletionPrediction = 0; // a, probability for < a ( < 1% )
    private int pessimisticCompletionPrediction = 0; // b, probability for > b ( < 1% )
    private int mostPossibleCompletion = 0; // m
    private double expectedTime = 0.0d;
    private double fluctuation = 0.0d;

    public ActivityAbstract() {
    }

    public ActivityAbstract(int duration) {
        this.duration = duration;
        this.mostPossibleCompletion = duration;
    }

    public ActivityAbstract(int duration, int optimisticCompletionPrediction,
                            int pessimisticCompletionPrediction) {
        this.duration = duration;
        this.optimisticCompletionPrediction = optimisticCompletionPrediction;
        this.pessimisticCompletionPrediction = pessimisticCompletionPrediction;
        this.mostPossibleCompletion = duration;
    }

    public ActivityAbstract(int duration, int earlierStart, int earlierCompletion,
                            int slowerStart, int slowerCompletion,
                            int slack, int optimisticCompletionPrediction,
                            int pessimisticCompletionPrediction, int mostPossible,
                            double expectedTime, double fluctuation) {
        this.duration = duration;
        this.earlierStart = earlierStart;
        this.earlierCompletion = earlierCompletion;
        this.slowerStart = slowerStart;
        this.slowerCompletion = slowerCompletion;
        this.slack = slack;
        this.optimisticCompletionPrediction = optimisticCompletionPrediction;
        this.pessimisticCompletionPrediction = pessimisticCompletionPrediction;
        this.mostPossibleCompletion = mostPossible;
        this.expectedTime = expectedTime;
        this.fluctuation = fluctuation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEarlierStart() {
        return earlierStart;
    }

    public void setEarlierStart(int earlierStart) {
        this.earlierStart = earlierStart;
    }

    public int getEarlierCompletion() {
        return earlierCompletion;
    }

    public void setEarlierCompletion(int earlierCompletion) {
        this.earlierCompletion = earlierCompletion;
    }

    public int getSlowerStart() {
        return slowerStart;
    }

    public void setSlowerStart(int slowerStart) {
        this.slowerStart = slowerStart;
    }

    public int getSlowerCompletion() {
        return slowerCompletion;
    }

    public void setSlowerCompletion(int slowerCompletion) {
        this.slowerCompletion = slowerCompletion;
    }

    public int getSlack() {
        return slack;
    }

    public void setSlack(int slack) {
        this.slack = slack;
    }

    public int getOptimisticCompletionPrediction() {
        return optimisticCompletionPrediction;
    }

    public void setOptimisticCompletionPrediction(int optimisticCompletionPrediction) {
        this.optimisticCompletionPrediction = optimisticCompletionPrediction;
    }

    public int getPessimisticCompletionPrediction() {
        return pessimisticCompletionPrediction;
    }

    public void setPessimisticCompletionPrediction(int pessimisticCompletionPrediction) {
        this.pessimisticCompletionPrediction = pessimisticCompletionPrediction;
    }

    public int getMostPossibleCompletion() {
        return mostPossibleCompletion;
    }

    public void setMostPossibleCompletion(int mostPossibleCompletion) {
        this.mostPossibleCompletion = mostPossibleCompletion;
    }

    public double getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(double expectedTime) {
        this.expectedTime = BigDecimal.valueOf( expectedTime )
                .setScale( 2, RoundingMode.HALF_UP )
                .doubleValue();
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(double fluctuation) {
        this.fluctuation = BigDecimal.valueOf( fluctuation )
                .setScale( 2, RoundingMode.HALF_UP )
                .doubleValue();
    }

    public abstract void printModel();

    @Override
    public String toString() {
        return "ActivityAbstract{" +
                "duration=" + duration +
                ", earlierStart=" + earlierStart +
                ", earlierCompletion=" + earlierCompletion +
                ", slowerStart=" + slowerStart +
                ", slowerCompletion=" + slowerCompletion +
                ", slack=" + slack +
                ", optimisticCompletionPrediction=" + optimisticCompletionPrediction +
                ", pessimisticCompletionPrediction=" + pessimisticCompletionPrediction +
                ", mostPossibleCompletion=" + mostPossibleCompletion +
                ", ExpectedTime=" + expectedTime +
                ", fluctuation=" + fluctuation +
                '}';
    }
}
