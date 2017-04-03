package PrologIntegrations.Parsers;

import Models.*;
import Models.Garbages.Bottle;
import Models.Garbages.Garbage;
import Models.MapFields.GrassField;
import Models.MapFields.House;
import Models.MapFields.RoadField;

public class PrologToJavaModelParser {

    public PrologToJavaModelParser() {}

    public House parseHouseObject(String textToParse) {
        textToParse = textToParse.substring(textToParse.indexOf("(") + 1);
        textToParse = textToParse.substring(0, textToParse.indexOf(")"));

        String[] housePositionParams = textToParse.split(",");

        return new House(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public GrassField parseGrassFieldObject(String textToParse) {
        textToParse = textToParse.substring(textToParse.indexOf("(") + 1);
        textToParse = textToParse.substring(0, textToParse.indexOf(")"));

        String[] housePositionParams = textToParse.split(",");

        return new GrassField(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public GarbageTruck parseGarbageTruckObject(String textToParse) {
        textToParse = textToParse.substring(textToParse.indexOf("(") + 1);
        textToParse = textToParse.substring(0, textToParse.indexOf(")"));

        String[] housePositionParams = textToParse.split(",");

        return new GarbageTruck(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public RoadField parseRoadFieldObject(String textToParse) {
        textToParse = textToParse.substring(textToParse.indexOf("(") + 1);
        textToParse = textToParse.substring(0, textToParse.indexOf(")"));

        String[] housePositionParams = textToParse.split(",");

        return new RoadField(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public Garbage parseGarbageObject(String textToParse) {

        String garbageType = textToParse.substring(0, textToParse.indexOf("("));

        String [] splittedTextToParse = textToParse.split("\\),");

        int garbageAmount = Integer.parseInt(splittedTextToParse[1].substring(0,splittedTextToParse[1].indexOf(")")));

        if(garbageType.contains("bottles")) return new Bottle(garbageAmount);

        return new Garbage(garbageAmount);
    }
}
