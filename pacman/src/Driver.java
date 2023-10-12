package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.Properties;

public class Driver {

    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        if(args.length == 1){

            if(args[0].endsWith(".xml")){
                new Controller(0, args[0]);

            }else{
                new Controller(1, args[0]);
            }
        }
        else if(args.length == 0){
            new Controller();
        }else{
            System.out.println("Wrong");
        }
    }
}
