package src;

import java.util.ArrayList;
import java.util.List;

/**
 * this class store all information in a map
 */
public class PacmanMap {

    private final static int FINAL_WIDTH = 20;

    private final static int FINAL_HEIGHT = 11;

    private int width;

    private  int height;

    ArrayList<ActorPosition> actorPositions = new ArrayList<>();

    ArrayList<ActorPosition> portalPositions = new ArrayList<>();

    private String gameMap;

    public PacmanMap(ArrayList<ActorPosition> actorPositions, ArrayList<ActorPosition> portalPositions, String gameMap) {
        this.width = FINAL_WIDTH;
        this.height = FINAL_HEIGHT;
        this.actorPositions = actorPositions;
        this.portalPositions = portalPositions;
        this.gameMap = gameMap;
    }

    public ArrayList<ActorPosition> getActorPositions() {
        return actorPositions;
    }

    public ArrayList<ActorPosition> getPortalPositions() {
        return portalPositions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getGameMap() {
        return gameMap;
    }
}
