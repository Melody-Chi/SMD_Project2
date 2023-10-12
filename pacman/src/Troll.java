package src;

import ch.aplu.jgamegrid.Location;
import src.utility.CheckPortals;

/**
 * this class indicate Troll type of monster
 */
public class Troll extends Monster {
    public Troll(Game game, MonsterType type) {
        super(game, type);
    }

    /**
     * this function implement it own walking pattern
     */
    protected void walkApproach() {
        Location next=randomWalk();
        next= CheckPortals.checkProtals(next, game.getNowMap().getPortalPositions());
        setLocation(next);
        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }
}
