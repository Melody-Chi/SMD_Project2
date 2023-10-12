package src;

import ch.aplu.jgamegrid.Location;
import src.utility.CheckPortals;

/**
 * this class indicate Troll type of monster
 */
public class Tx5 extends Monster {
    public Tx5(Game game, MonsterType type) {
        super(game, type);
        this.stopMoving(5);
    }

    /**
     * this function implement it own walking pattern
     */
    protected void walkApproach() {

        double oldDirection = getDirection();
        Location pacLocation = game.pacActor.getLocation();
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);
        if (!isVisited(next) && canMove(next)){
            next=CheckPortals.checkProtals(next, game.getNowMap().getPortalPositions());
            setLocation(next);
        } else
        {
            setDirection(oldDirection);
            next=randomWalk();
            next=CheckPortals.checkProtals(next, game.getNowMap().getPortalPositions());
            setLocation(next);
        }

        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }
}
