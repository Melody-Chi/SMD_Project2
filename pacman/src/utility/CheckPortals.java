package src.utility;

import ch.aplu.jgamegrid.Location;
import src.ActorPosition;


import java.util.ArrayList;

/** this class provide a static function checkProtals
 *  make it static helps to use function directly without create the new class
 */

public class CheckPortals {


    /**
     * this function check if the given location is a portal
     * @param next the location the need to be checked
     * @param portalPositions all the portals in this map
     * @return location of other side if it is a portal, else return the given location
     */
    public static Location checkProtals(Location next, ArrayList<ActorPosition> portalPositions){
        Location newnext=next;
        // loop all the portals
        for (int i = 0; i < portalPositions.size(); i++) {
            ActorPosition portal = portalPositions.get(i);
            if(next.getX()==portal.getX() && next.getY()== portal.getY()){
                String protalName=portal.getActorName();
                for (int n = 0; n < portalPositions.size(); n++) {
                    ActorPosition newPortal=portalPositions.get(n);
                    if (i!=n && newPortal.getActorName().equals(protalName)){
                        newnext=(new Location(newPortal.getX(), newPortal.getY()));
                        break;
                    }
                }
            }
        }
        return newnext;
    }

}