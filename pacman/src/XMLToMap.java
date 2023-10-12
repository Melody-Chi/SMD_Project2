package src;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Extract the information from the xml file and store it in PacmanMap, and return the map to RunMap
 */
public class XMLToMap {


    public static PacmanMap XMLToMapFunction(String xmlFile) {
        StringBuilder result = new StringBuilder();
        ArrayList<ActorPosition> actorPositions = new ArrayList<>();
        ArrayList<ActorPosition> portalPositions = new ArrayList<>();

        Map<String, String> cellLetter = new HashMap<>();
        cellLetter.put("PathTile", " ");
        cellLetter.put("PillTile", ".");
        cellLetter.put("WallTile", "x");
        cellLetter.put("GoldTile", "g");
        cellLetter.put("IceTile", "i");
        cellLetter.put("PacTile", " ");
        cellLetter.put("TX5Tile", " ");
        cellLetter.put("TrollTile", " ");
        cellLetter.put("PortalWhiteTile", "a");
        cellLetter.put("PortalYellowTile", "b");
        cellLetter.put("PortalDarkGoldTile", "c");
        cellLetter.put("PortalDarkGrayTile", "d");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFile));

            document.getDocumentElement().normalize();

            // Get all the 'row' elements
            NodeList rowList = document.getElementsByTagName("row");

            // Iterate through each 'row' element
            for (int i = 0; i < rowList.getLength(); i++) {
                Element rowElement = (Element) rowList.item(i);

                // Get all the 'cell' elements within the current 'row'
                NodeList cellList = rowElement.getElementsByTagName("cell");

                // Iterate through each 'cell' element
                for (int j = 0; j < cellList.getLength(); j++) {
                    Element cellElement = (Element) cellList.item(j);
                    String cellValue = cellElement.getTextContent();

                    // Get the corresponding character for the cell type
                    // Map the cell value to the corresponding replacement
                    String cell = cellLetter.getOrDefault(cellValue, cellValue);
                    result.append(cell); // Append mapped cell value to the cells string

                    if(cellValue.equals("PacTile") || cellValue.equals("TrollTile") || cellValue.equals("TX5Tile")) {
                        actorPositions.add(new ActorPosition(cellValue, j, i));
                    }

                    if(cellValue.equals("PortalWhiteTile") || cellValue.equals("PortalYellowTile") ||
                            cellValue.equals("PortalDarkGoldTile") || cellValue.equals("PortalDarkGrayTile")){
                        portalPositions.add(new ActorPosition(cellValue, j, i));
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return new PacmanMap(actorPositions, portalPositions, result.toString());
    }
}

