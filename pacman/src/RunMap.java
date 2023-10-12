package src;

import src.utility.LevelCheck;
import src.utility.GameCheck;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * this class check the given maps, and load them if vaild
 */
public class RunMap {

    private GameCheck gameCheck = new GameCheck();


    /**
     * this function read and check the maps from folder
     * @param folderName the name of folder
     * @return a array of maps if success, else return null
     */
    public ArrayList<PacmanMap> loadAndCheckMaps(String folderName){
        ArrayList<PacmanMap> maps = new ArrayList<>();

        File folder = new File(folderName);
        File[] files = folder.listFiles();
        List<File> validMaps = new ArrayList<>();

        for (File file : files){
            validMaps.add(file);
        }
        boolean ValidFolder = gameCheck.isValidGame(folder);
        if(!ValidFolder) return null;

        for(File file: validMaps){
            if(file.isFile()){
                PacmanMap map = XMLToMap.XMLToMapFunction(file.getPath());
                boolean validMap = LevelCheck.MapCheck(file.getName(), map);
                if(!validMap) return null;
                else maps.add(map);
            }
        }

        return maps;
    }







}
