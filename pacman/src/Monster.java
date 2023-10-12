// Monster.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import src.utility.CheckPortals;

import java.awt.Color;
import java.util.*;

/**
 * this abstract class is parent class of all monsters class
 */
public abstract class Monster extends Actor
{
  protected Game game;
  private MonsterType type;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private final int listLength = 10;
  private boolean stopMoving = false;
  private int seed = 0;
  private Random randomiser = new Random(0);


  public Monster(Game game, MonsterType type)
  {
    super("sprites/" + type.getImageName());
    this.game = game;
    this.type = type;
  }

  public void stopMoving(int seconds) {
    this.stopMoving = true;
    Timer timer = new Timer(); // Instantiate Timer Object
    int SECOND_TO_MILLISECONDS = 1000;
    final Monster monster = this;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
      }
    }, seconds * SECOND_TO_MILLISECONDS);
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

  public void act()
  {
    if (stopMoving) {
      return;
    }
    walkApproach();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  /**
   * this class select a random location that accessible
   * @return a random accessible location
   */
  protected Location randomWalk(){

    double oldDirection = getDirection();

    Location next;

    int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
    int[] turnList={1,0,-1,2};
    next = getNextMoveLocation();
    for (int i:turnList) {
      setDirection(oldDirection);
      turn(i*sign * 90);
      next = getNextMoveLocation();
      if (canMove(next)) {

        return next;
      }
    }
    return next;


  }

  protected void clear(){
    Location next = new Location(-1,-1);
    setLocation(next);
    game.getGameCallback().monsterLocationChanged(this);
  }

  protected abstract void walkApproach();


  public MonsterType getType() {
    return type;
  }

  protected void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  protected boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }

  protected boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if (c.equals(Color.gray) || location.getX() >= game.getNumHorzCells()
          || location.getX() < 0 || location.getY() >= game.getNumVertCells() || location.getY() < 0)
      return false;
    else
      return true;
  }
}
