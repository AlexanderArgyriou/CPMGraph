package models.activities;

public abstract class ActivityAbstract {
    private int duration = 0;
    private int earlierStart = 0;
    private int earlierCompletion = 0;
    private int slowerStart = 0;
    private int slowerCompletion = 0;
    private int slack = 0;

    public ActivityAbstract() {
    }

    public ActivityAbstract(int duration) {
        this.duration = duration;
    }

    public ActivityAbstract(int duration, int earlierStart,
                            int earlierCompletion, int slowerStart,
                            int slowerCompletion, int slack) {
        this.duration = duration;
        this.earlierStart = earlierStart;
        this.earlierCompletion = earlierCompletion;
        this.slowerStart = slowerStart;
        this.slowerCompletion = slowerCompletion;
        this.slack = slack;
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
                '}';
    }
}
