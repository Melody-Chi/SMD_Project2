package src.utility;

import ch.aplu.jgamegrid.Location;
import src.ActorPosition;
import src.PacmanMap;


import java.util.*;

/** this class provide a static function MapCheck
 *  make it static helps to use function directly without create the new class
 */
public class LevelCheck {
    /**
     * this function check if content of map is valid
     * @param level the string of current level name
     * @param gameMap the map of this level
     * @return ture if valid, false if not
     */
    public static boolean MapCheck(String level, PacmanMap gameMap) {
        // ensure all the function is tested
        boolean result=true;
        if (!onePacman(level, gameMap)){
            result=false;
        }
        if(!twoPortal(level, gameMap)){
            result=false;
        }
        if (result){
            if(!isAccessible(level, gameMap)){
                result=false;
            }
        }
        return result;
    }



    /**
     * this function if there is exactly one starting point for PacMan
     * @param level the string of current level name
     * @param gameMap the map of this level
     * @return ture if valid, false if not
     */
    public static boolean onePacman(String level, PacmanMap gameMap) {
        ArrayList<ActorPosition> actors = gameMap.getActorPositions();
        int pacNum=0;
        // loop actors and count number of PacActor
        for (int i = 0; i < actors.size(); i++) {
            ActorPosition actor=actors.get(i);
            if(actor.getActorName().equals("PacTile")){
                pacNum+=1;
            }
        }
        if(pacNum==0){
            System.out.printf("%s no start for PacMan\n", level);
            return false;
        }
        if (pacNum>1){
            System.out.printf("%s multiple start for PacMan\n", level);
            return false;
        }
        return true;
    }



    /**
     * this function check if each portal appear in pairs
     * @param level the string of current level name
     * @param gameMap the map of this level
     * @return ture if valid, false if not
     */
    public static boolean twoPortal(String level, PacmanMap gameMap) {
        ArrayList<ActorPosition> portals=gameMap.getPortalPositions();
        HashMap<String, Integer> map=new HashMap<>();
        boolean result=true;
        // loop all portals and record amount of each portals
        for (int i=0; i<portals.size(); i++){
            ActorPosition portal=portals.get(i);
            if (map.containsKey(portal.getActorName())){
                map.put(portal.getActorName(), map.getOrDefault(portal.getActorName(),0) + 1);
            } else {
                map.put(portal.getActorName(), 1);
            }
        }

        // check if any portals number is not two
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue()!=2){
                System.out.printf("%s portal %s count is not 2:", level, entry.getKey());
                result=false;
                for (int i=0; i<portals.size(); i++){
                    ActorPosition portal=portals.get(i);
                    if(portal.getActorName().equals(entry.getKey())){
                        System.out.printf(" (%d,%d);", portal.getX()+1, portal.getY()+1);
                    }
                }
                System.out.printf("\n");
            }
        }
        return result;
    }




    /**
     * this function check if all elements are accessible and if num of elements is more than 2
     * @param level the string of current level name
     * @param gameMap the map of this level
     * @return ture if valid, false if not
     */
    public static boolean isAccessible(String level, PacmanMap gameMap) {
        ArrayList<ActorPosition> actors = gameMap.getActorPositions();
        ActorPosition pacActor=null;
        for (int i = 0; i < actors.size(); i++) {
            pacActor=actors.get(i);
            if(pacActor.getActorName().equals("PacTile")){
                break;
            }
        }
        boolean result=true;
        int row = pacActor.getY();
        int col = pacActor.getX();
        String grid = gameMap.getGameMap();
        int Width= gameMap.getWidth(), Height= gameMap.getHeight();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[grid.length()];
        // make start point visited
        visited[row * Width + col] = true;
        int count = 0;
        // calculate the amount or pills and golds
        for (int i = 0; i < grid.length(); i++) {
            if (grid.charAt(i) == 'g' || grid.charAt(i) == '.') {
                count++;
            }
        }
        if (count<2){
            System.out.printf("%s amount of pills/gold less than 2\n", level);
            result=false;
        }
        // store the index in the string map
        queue.add(row * Width  + col);
        // apply bfs to search all location
        while (!queue.isEmpty()) {

            int index = queue.poll();
            // convert index in the string map to x and y index
            row = index / Width;
            col = index % Width;

            // when reach a pill or gold, decline the amount
            if (grid.charAt(index) == 'g' || grid.charAt(index) == '.') {
                count--;
            }

            if (count == 0) {
                return result;
            }

            int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow <Height && newCol >= 0 && newCol < Width && grid.charAt(newRow * Width  + newCol) != 'x' && !visited[newRow * Width + newCol]) {
                    visited[newRow * Width  + newCol] = true;
                    queue.add(newRow * Width  + newCol);
                    Location next=new Location(newCol, newRow), newnext;
                    newnext=CheckPortals.checkProtals(next, gameMap.getPortalPositions());
                    if(newnext.getX()!=next.getX() && newnext.getY()!=next.getY() && !visited[newnext.getY() * Width  + newnext.getX()]){
                        queue.add(newnext.getY() * Width  + newnext.getX());
                    }

                }
            }
        }
        // if there is still pills or gold, means not accessible
        boolean pillWrong=false;
        for (int i=0; i<grid.length(); i++){
            if (!visited[i]){
                if(grid.charAt(i) == '.'){
                    if(!pillWrong){
                        System.out.printf("%s Pill not accessible: ", level);
                        pillWrong=true;
                    }
                    System.out.printf(" (%d,%d)", 1+i% Width, 1+i/Width);
                }

            }
        }
        if(pillWrong) {
            System.out.printf("\n");
        }

        boolean goldWrong=false;
        for (int i=0; i<grid.length(); i++){
            if (!visited[i]){
                if(grid.charAt(i) == 'g'){
                    if(!goldWrong){
                        System.out.printf("%s Gold not accessible: ", level);
                        goldWrong=true;
                    }
                    System.out.printf(" (%d,%d)", 1+i% Width, 1+i/Width);
                }

            }
        }
        if(goldWrong) {
            System.out.printf("\n");
        }

        return false;
    }
}
