package src;

/**
 * this class stores position and name of actor
 */
public class ActorPosition {
    private String actorName;
    private int x;
    private int y;

    public ActorPosition(String actorName, int x, int y) {
        this.actorName = actorName;
        this.x = x;
        this.y = y;
    }

    public String getActorName() {
        return actorName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
