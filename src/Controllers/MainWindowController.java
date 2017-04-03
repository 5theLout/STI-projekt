package Controllers;

import Models.*;
import Models.MapFields.House;
import PrologIntegrations.Parsers.PrologToJavaModelParser;
import PrologIntegrations.PrologEngineResolver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    public Canvas drawingArea;

    private Environment environment;

    private Stage stage;

    private String theoryFileName;

    private PrologEngineResolver prologEngineResolver;

    private GraphicsContext gc;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gc = drawingArea.getGraphicsContext2D();
        theoryFileName = "resources/mapa.pl";
        environment = new Environment();

        redrawCanvas();
    }

    public void setStageAndItsMeasures(Stage stage) {
        this.stage = stage;
    }

    private void redrawCanvas() {
        try {
            prologEngineResolver = new PrologEngineResolver(theoryFileName);

            PrologToJavaModelParser modelParser = new PrologToJavaModelParser();

            List<String> prologFileKnowledgeBase = new ArrayList<>();

            for(int k=0; k<500; k+=50) {
                for(int l=0; l<500; l+=50) {
                    prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("house(" + k + "," + l + ")."));
                    prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("grassfield(" + k + "," + l + ")."));
                    prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("roadfield(" + k + "," + l + ")."));
                    prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("garbagetruck(" + k + "," + l + ")."));
                }
            }

            prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("bottles(X,Y)."));
            prologFileKnowledgeBase.addAll(prologEngineResolver.resolvePrologQuery("various(X,Y)."));

            for (String entity : prologFileKnowledgeBase) {

                if(entity.contains("house")) {
                    House house = modelParser.parseHouseObject(entity);
                    for(String garbageEntity : prologFileKnowledgeBase) {
                        if (garbageEntity.contains("bottles(\',\'(" + house.getXPos() + "," + house.getYPos() + "),")) {
                            house.addGarbage(modelParser.parseGarbageObject(garbageEntity));
                        }
                        else if (garbageEntity.contains("various((" + house.getXPos() + "," + house.getYPos() + "),")) {
                            house.addGarbage(modelParser.parseGarbageObject(garbageEntity));
                        }
                    }
                    environment.addMapEntity(house);
                }
                else if(entity.contains("grassfield")) environment.addMapEntity(modelParser.parseGrassFieldObject(entity));
                else if(entity.contains("roadfield")) environment.addMapEntity(modelParser.parseRoadFieldObject(entity));
                else if(entity.contains("garbagetruck")) environment.addGarbageTruck(modelParser.parseGarbageTruckObject(entity));
            }

            prologEngineResolver.resolvePrologQuery("hasgarbages(X,Y).");

        } catch (Exception e) {
            e.printStackTrace();
        }

        environment.draw(gc);
        System.out.println(prologEngineResolver.getEngine().getTheory().toJSON());
    }
}
