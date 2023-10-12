package src.auto_move;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacActor;
import src.utility.CheckPortals;

import java.util.Random;

import java.util.*;

/**
 * this class provide automove for PacActor
 */
public class AutoMove implements NextMove {

    private Random randomiser;
    private Location targetPill=null;

    /**
     * this function find the closest location of pills or golds
     * @param pacman the class of pacman
     * @param game current game
     * @return Location of closest pill or gold
     */
    private Location closestPillLocation(PacActor pacman, Game game) {
        int currentDistance = 1000;
        Location currentLocation = null;
        List<Location> pillAndItemLocations = game.getPillAndItemLocations();
        for (Location location: pillAndItemLocations) {
            int distanceToPill = location.getDistanceTo(pacman.getLocation());
            if (distanceToPill < currentDistance) {
                currentLocation = location;
                currentDistance = distanceToPill;
            }
        }

        return currentLocation;
    }

    /**
     * this function implement the method in interface NextMove, use bfs to find path to pill
     * @param pacman the class of pacman
     * @param game current game
     * @return location of next movement
     */
    @Override
    public Location getNext(PacActor pacman, Game game) {
        // apply closestPillLocation to find a target
        if (targetPill==null){
            targetPill=closestPillLocation(pacman, game);
        }
        Location actLocation=pacman.getLocation();
        String grid = game.getNowMap().getGameMap();
        int Width= game.getNowMap().getWidth(), Height= game.getNowMap().getHeight();
        Queue<int[]> queue = new LinkedList<>();
        boolean[] visited = new boolean[grid.length()];
        // make start point visited
        visited[actLocation.getY() * Width + actLocation.getX()] = true;
        Location traget;
        // store the index in the string map
        queue.add(new int[]{actLocation.getY() * Width + actLocation.getX()});
        // use bfs to find path
        while (!queue.isEmpty()) {

            int[] index = queue.poll();
            // convert index in the string map to x and y index
            int row = index[0] / Width;
            int col = index[0] % Width;

            // when reach the target, return the first step in the path
            if (row==targetPill.getY() && col==targetPill.getX()){
                traget=new Location(index[1]% Width,index[1] / Width);
                // if next step reach the target, find a new target
                if (index[0]==index[1]) {

                    targetPill=null;
                }
                Location.CompassDirection compassDir =
                        pacman.getLocation().get4CompassDirectionTo(traget);
                pacman.setDirection(compassDir);
                return traget;
            }

            // indicate four directions
            int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : dirs) {

                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow <Height && newCol >= 0 && newCol < Width && grid.charAt(newRow * Width  + newCol) != 'x' && !visited[newRow * Width + newCol]) {
                    visited[newRow * Width  + newCol] = true;
                    int[] array=new int[2];
                    Location next=new Location(newCol, newRow), newnext;
                    newnext= CheckPortals.checkProtals(next, game.getNowMap().getPortalPositions());
                    // store the first step in this path
                    if(index.length==1){
                        array[1]=newnext.getY() * Width  + newnext.getX();
                    } else {
                        array[1]=index[1];
                    }
                    // store current location of this path
                    array[0]=newnext.getY() * Width  + newnext.getX();
                    queue.add(array);

                }
            }
        }
        // if not found, apply random walk
        double oldDirection = pacman.getDirection();

        randomiser = pacman.getRandom();
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
        pacman.turn(sign * 90);  // Try to turn left/right
        traget = pacman.getNextMoveLocation();
        if (pacman.canMove(traget)) {
                return traget;
            } else {
                pacman.setDirection(oldDirection);
                traget = pacman.getNextMoveLocation();
                if (pacman.canMove(traget)) // Try to move forward
                {
                    return traget;
                } else {
                    pacman.setDirection(oldDirection);
                    pacman.turn(-sign * 90);  // Try to turn right/left
                    traget = pacman.getNextMoveLocation();
                    if (pacman.canMove(traget)) {
                        return traget;
                    } else {
                        pacman.setDirection(oldDirection);
                        pacman.turn(180);  // Turn backward
                        traget = pacman.getNextMoveLocation();
                        return traget;
                    }
                }
            }
    }
}


