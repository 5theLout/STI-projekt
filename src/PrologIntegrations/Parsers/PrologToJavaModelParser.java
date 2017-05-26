package PrologIntegrations.Parsers;

import Models.*;
import Models.Garbages.Bottle;
import Models.Garbages.Garbage;
import Models.MapFields.GrassField;
import Models.MapFields.House;
import Models.MapFields.GarbageColletionField;
import Models.MapFields.RoadField;
import Models.Prototypes.Drawable;

public class PrologToJavaModelParser {

    public PrologToJavaModelParser() {}

    public String[] getTwoArgumentalObjectPosition(String textToParse) {
        textToParse = textToParse.substring(textToParse.indexOf("(") + 1);
        textToParse = textToParse.substring(0, textToParse.indexOf(")"));

        String[] housePositionParams = textToParse.split(",");

        return housePositionParams;
    }

    public Drawable parseTwoArgumentalMapObject(String textToParse) {

        String[] entityPositionParams = getTwoArgumentalObjectPosition(textToParse);

        if(textToParse.contains("house")) return new House(Integer.parseInt(entityPositionParams[0]), Integer.parseInt(entityPositionParams[1]));
        else if(textToParse.contains("housegcpoint")) return new GarbageColletionField(Integer.parseInt(entityPositionParams[0]), Integer.parseInt(entityPositionParams[1]));
        else if(textToParse.contains("garbagetruck")) return new GarbageTruck(Integer.parseInt(entityPositionParams[0]), Integer.parseInt(entityPositionParams[1]));
        else return new GrassField(Integer.parseInt(entityPositionParams[0]), Integer.parseInt(entityPositionParams[1]));
    }

    public House parseHouseObject(String textToParse) {

        String[] housePositionParams = getTwoArgumentalObjectPosition(textToParse);

        return new House(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public GarbageColletionField parseHouseGCPointObject(String textToParse) {

        String[] houseCGPointPositionParams = getTwoArgumentalObjectPosition(textToParse);

        return new GarbageColletionField(Integer.parseInt(houseCGPointPositionParams[0]), Integer.parseInt(houseCGPointPositionParams[1]));
    }

    public GrassField parseGrassFieldObject(String textToParse) {

        String[] housePositionParams = getTwoArgumentalObjectPosition(textToParse);

        return new GrassField(Integer.parseInt(housePositionParams[0]), Integer.parseInt(housePositionParams[1]));
    }

    public GarbageTruck parseGarbageTruckObject(String textToParse) {

        String[] garbageTruckPositionParams = getTwoArgumentalObjectPosition(textToParse);

        return new GarbageTruck(Integer.parseInt(garbageTruckPositionParams[0]), Integer.parseInt(garbageTruckPositionParams[1]));
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

    public Road parseRoadObject(String entity) {

        entity = entity.replaceAll("\'.\'", "");

        Road road = new Road();

        entity = entity.substring(entity.indexOf("[") + 1);
        entity = entity.substring(0, entity.indexOf("]"));


        while(entity.length() > 0) {
            System.out.println(entity);
            String roadFieldPosition = entity.substring(entity.indexOf("(") + 1);
            roadFieldPosition = roadFieldPosition.substring(0, roadFieldPosition.indexOf(")"));

            entity = entity.substring(entity.indexOf(")") + 1);

            String [] splittedRoadFieldPosition = roadFieldPosition.split(",");

            RoadField roadField = new RoadField(
                    Integer.parseInt(splittedRoadFieldPosition[0]),
                    Integer.parseInt(splittedRoadFieldPosition[1]));

            road.addField(roadField);
        }

        return road;

    }
}
