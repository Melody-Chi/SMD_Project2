package src.utility;

/**
 * The 指定的 folder is indeed a directory.
 * 至少有一个 file in the directory.
 * 每个map file 有唯一的编号 in its filename.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** this class provide a static function isValidGame
 *  make it static helps to use function directly without create the new class
 */
public class GameCheck {

    /**
     * this method check if the provided maps has valid form
     * @param folder contains all maps
     * @return ture if valid, false if not
     */
    public static boolean isValidGame(File folder) {
        // check if directory valid
        if (!folder.isDirectory()) {
            logFailure(folder.getName(), "is not a directory");
            return false;
        }

        // check if maps found
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            logFailure(folder.getName(), "no maps found");
            return false;
        }

        // check whether it has same map
        HashMap<String, File> mapFiles = new HashMap<>();
        for (File file : files) {
            if (isValidMapFileName(file.getName())) {
                String mapNumber = getMapNumberFromFileName(file.getName());
                if (mapFiles.containsKey(mapNumber)) {
                    logFailure(folder.getName(), "multiple maps at same level: " + file.getName() + "; "
                            + mapFiles.get(mapNumber).getName());
                    return false;
                } else {
                    mapFiles.put(mapNumber, file);
                }
            }
        }

        if (mapFiles.size() == 0) {
            logFailure(folder.getName(), "no correctly named map files found");
            return false;
        }

        return true;
    }

    /**
     * this function check if maps name valid
     * @param fileName the name of file
     * @return boolean shows valid or not
     */
    private static boolean isValidMapFileName(String fileName) {
        Pattern pattern = Pattern.compile(".*\\.xml$");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    /**
     * this function find xml map
     * @param fileName the name of file
     * @return xml if found, null if not
     */
    private static String getMapNumberFromFileName(String fileName) {
        Pattern pattern = Pattern.compile(".*\\.xml$");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    /**
     * this function print the error message
     * @param folderName name of folder
     * @param message the provided error message
     */
    private static void logFailure(String folderName, String message) {
        try (FileWriter logFile = new FileWriter("gamecheck.log", true)) {
            logFile.write("Game " + folderName + " – " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



