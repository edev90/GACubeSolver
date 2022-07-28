package UI;

// used for animation stuff

public class Event {

    public int actionType = 0;
    public int frameIndex = 0;
    public boolean isMaxFrame = false;

    private Event() {}

    public Event(int actionType) {
        this.actionType = actionType;
    }

    public void doEvent() {

    }

    public Event clone() {
        Event clone = new Event();
        clone.actionType = this.actionType;
        clone.frameIndex = this.frameIndex;
        clone.isMaxFrame = this.isMaxFrame;
        return clone;
    }
}
