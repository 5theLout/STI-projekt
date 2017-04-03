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

    public void onCanvasMouseDragged(MouseEvent mouseEvent) {
    }

    public void onCanvasMousePressed(MouseEvent mouseEvent) {
    }

    public void onCanvasMouseReleased(MouseEvent mouseEvent) {
    }

    public void openFileMenuItemClicked(ActionEvent actionEvent) {
    }

    public void saveAsImageFileMenuItemClicked(ActionEvent actionEvent) {
    }

    public void closeFileMenuItemClicked(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gc = drawingArea.getGraphicsContext2D();
        theoryFileName = "resources/mapa.pl";
        environment = new Environment();

        /*environment = new Environment(new Drawable[][]{
                new Drawable[]{ new GrassField(0, 0, '*'),      new GrassField(50, 0, '*'),     new House(100, 0, '*'),         new House(150, 0, '*'),         new GrassField(200, 0, '*'),        new GrassField(250, 0, '*'),    new GrassField(300, 0, '*'),        new GrassField(350, 0, '*'),    new House(400, 0, '*'),         new House(450, 0, '*') },
                new Drawable[]{ new RoadField(0, 50, '*'),      new House(50, 50, '-'),         new RoadField(100, 50, '*'),    new RoadField(150, 50, '*'),    new RoadField(200, 50, '*'),        new RoadField(250, 50, '*'),    new RoadField(300, 50, '*'),        new RoadField(350, 50, '*'),    new RoadField(400, 50, '*'),    new House(450, 50, '*') },
                new Drawable[]{ new RoadField(0, 100, '*'),     new GrassField(50, 100, '*'),   new RoadField(100, 100, '*'),   new RoadField(150, 100, '*'),   new GrassField(200, 100, '*'),      new GrassField(250, 100, '*'),  new GrassField(300, 100, '*'),      new GrassField(350, 100, '*'),  new RoadField(400, 100, '*'),   new GrassField(450, 100, '*') },
                new Drawable[]{ new RoadField(0, 150, '*'),     new GrassField(50, 150, '*'),   new GrassField(100, 150, '*'),   new RoadField(150, 150, '*'),   new House(200, 150, '*'),     new RoadField(250, 150, '*'),   new RoadField(300, 150, '*'),       new GrassField(350, 150, '*'),  new House(400, 150, '*'),       new GrassField(450, 150, '*') },
                new Drawable[]{ new RoadField(0, 200, '*'),     new House(50, 200, '*'),        new RoadField(100, 200, '*'),   new RoadField(150, 200, '*'),   new RoadField(200, 200, '*'),       new RoadField(250, 200, '*'),   new RoadField(300, 200, '*'),       new RoadField(350, 200, '*'),   new RoadField(400, 200, '*'),   new GrassField(450, 200, '*') },
                new Drawable[]{ new RoadField(0, 250, '*'),     new GrassField(50, 250, '*'),   new RoadField(100, 250, '*'),   new GrassField(150, 250, '*'),   new RoadField(200, 250, '*'),       new GrassField(250, 250, '*'),   new House(300, 250, '*'),       new House(350, 250, '*'),         new RoadField(400, 250, '*'),   new House(450, 250, '*') },
                new Drawable[]{ new RoadField(0, 300, '*'),     new House(50, 300, '*'),        new RoadField(100, 300, '*'),   new GrassField(150, 300, '*'),   new RoadField(200, 300, '*'),       new RoadField(250, 300, '*'),   new RoadField(300, 300, '*'),       new RoadField(350, 300, '*'),  new RoadField(400, 300, '*'),   new RoadField(450, 300, '*') },
                new Drawable[]{ new RoadField(0, 350, '*'),     new House(50, 350, '*'),        new RoadField(100, 350, '*'),   new RoadField(150, 350, '*'),   new RoadField(200, 350, '*'),       new GrassField(250, 350, '*'),   new GrassField(300, 350, '*'),       new House(350, 350, '*'),     new RoadField(400, 350, '*'),   new GrassField(450, 350, '*') },
                new Drawable[]{ new RoadField(0, 400, '*'),     new RoadField(50, 400, '*'),    new RoadField(100, 400, '*'),   new RoadField(150, 400, '*'),   new House(200, 400, '*'),           new House(250, 400, '*'),       new RoadField(300, 400, '*'),       new RoadField(350, 400, '*'),   new RoadField(400, 400, '*'),   new GrassField(450, 400, '*') },
                new Drawable[]{ new GrassField(0, 450, '*'),    new GrassField(50, 450, '*'),   new GrassField(100, 450, '*'),   new RoadField(150, 450, '*'),   new RoadField(200, 450, '*'),      new RoadField(250, 450, '*'),   new RoadField(300, 450, '*'),       new GrassField(350, 450, '*'),   new GrassField(400, 450, '*'),   new GrassField(450, 450, '*') }
        }, 500, 500); // 10x10*/

        //environment.draw(gc);

        // creating a query to consult the prolog program
        //if (!Query.hasSolution("consult('D:/STUDIA/IV rok/SZI/familytree.pl').")) {
        //  System.out.println("Consult failed");
        //} else {
        //    JPLTest.test3a("ria");
        //}
        //JPLTest.test3a("ria");

        //PrologEngineResolver prologEngineResolver = new PrologEngineResolver(theoryFileName);
        //try {
        //prologEngineResolver.setEngineTheory("mapa.pl");
        //prologEngineResolver.resolvePrologQuery("m(jan).");
        //prologEngineResolver.resolvePrologQuery("ojciec(jan,anna).");
        //prologEngineResolver.resolvePrologQuery("ojciec(joanna,anna).");
//
        //prologEngineResolver.resolvePrologQuery("wsiadz(jan,autobus).");
        //prologEngineResolver.resolvePrologQuery("pojedz(autobus,lublin).");
        //prologEngineResolver.resolvePrologQuery("jest_w(autobus,lublin).");
        //prologEngineResolver.resolvePrologQuery("jest_w(autobus,poznan).");
//
        //prologEngineResolver.resolvePrologQuery("k(karolajna).");
        //prologEngineResolver.resolvePrologQuery("grassfield(0,0).");


        //    for(int k=0; k<500; k+=50) {
        //        for(int l=0; l<500; l+=50) {
        //            if(prologEngineResolver.resolvePrologQuery("house(" + k + "," + l + ").")) environment.addMapEntity(new House(k,l,'*'));
        //            else if(prologEngineResolver.resolvePrologQuery("grassfield(" + k + "," + l + ").")) environment.addMapEntity(new GrassField(k,l,'*'));
        //            else if(prologEngineResolver.resolvePrologQuery("roadfield(" + k + "," + l + ").")) environment.addMapEntity(new RoadField(k,l,'*'));
        //        }
        //    }

        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

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
