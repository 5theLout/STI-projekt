package Controllers;

import Models.*;
import Models.MapFields.*;
import ProblemResolvers.*;
import PrologIntegrations.Parsers.PrologToJavaModelParser;
import PrologIntegrations.PrologEngineResolver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MainWindowController implements Initializable {

    @FXML
    public Canvas drawingArea;
    public TextField maxPopulationSizeTextField;

    private Environment environment;

    private Stage stage;

    private String theoryFileName;

    private PrologEngineResolver prologEngineResolver;

    private GraphicsContext gc;

    private List<Node> nodeList;

    Timer timer = new Timer();

    //GeneticsAlgorithm geneticsAlgorithm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gc = drawingArea.getGraphicsContext2D();
        theoryFileName = "resources/mapa.pl";
        environment = new Environment();

        nodeList = new ArrayList<>();

        prepareEnvironment();

        environment.draw(gc);
        System.out.println(prologEngineResolver.getEngine().getTheory().toJSON());
    }

    public void setStageAndItsMeasures(Stage stage) {
        this.stage = stage;
    }

    private void prepareEnvironment() {

        try {
            prologEngineResolver = new PrologEngineResolver(theoryFileName);

            PrologToJavaModelParser modelParser = new PrologToJavaModelParser();

            List<String> prologFileKnowledgeBase = new ArrayList<>();

            for(int x=25; x<750; x+=50) {
                for(int y=25; y<500; y+=50) {
                    if (prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("house(" + x + "," + y + ")."))) {
                        environment.addMapEntity(new House(x,y));
                    } else environment.addMapEntity(new Grass(x,y));
                    if(prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("housegcpoint(" + x + "," + y + ")."))) {
                        environment.addHouseGCPointEntity(new GarbageColletionField(x,y));
                    }
                    if(prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("intersection(" + x + "," + y + ")."))) {
                        environment.addIntersectionEntity(new Intersection(x,y));
                    }
                    if(prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("garbagetruck(" + x + "," + y + ")."))) {
                        environment.addGarbageTruck(new GarbageTruck(x,y));
                    }
                }
            }

            for(String road : prologEngineResolver.solvePrologQuery("road(X).")) {
                environment.addRoadEntity(modelParser.parseRoadObject(road));
            }

            prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("road(X)."));

            //prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("housegcpoint(X,Y)."));
            //prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("road(X)."));
            //prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("garbagetruck(X,Y)."));
            //prologFileKnowledgeBase.addAll(prologEngineResolver.solvePrologQuery("intersection(X,Y)."));
//
            //for (String entity : prologFileKnowledgeBase) {
            //    if (entity.contains("housegcpoint")) environment.addHouseGCPointEntity(modelParser.parseHouseGCPointObject(entity));
            //    else if(entity.contains("house")) {
            //        House house = modelParser.parseHouseObject(entity);
            //        for(String garbageEntity : prologFileKnowledgeBase) {
            //            if (garbageEntity.contains("bottles(\',\'(" + house.getCenterPosition().getXPos() + "," + house.getCenterPosition().getYPos() + "),")) {
            //                house.addGarbage(modelParser.parseGarbageObject(garbageEntity));
            //            }
            //            else if (garbageEntity.contains("various((" + house.getCenterPosition().getXPos() + "," + house.getCenterPosition().getYPos() + "),")) {
            //                house.addGarbage(modelParser.parseGarbageObject(garbageEntity));
            //            }
            //        }
            //        environment.addMapEntity(house);
            //    }
            //    else if(entity.contains("grassfield")) environment.addMapEntity(modelParser.parseGrassFieldObject(entity));
            //    else if(entity.contains("road")) environment.addRoadEntity(modelParser.parseRoadObject(entity));
            //    else if(entity.contains("garbagetruck")) environment.addGarbageTruck(modelParser.parseGarbageTruckObject(entity));
            //    else if(entity.contains("intersection")) environment.addIntersectionEntity(modelParser.parseIntersectionObject(entity));
            //}

            prologEngineResolver.solvePrologQuery("hasgarbages(X,Y).");

            System.out.println(prologEngineResolver.solveSimpleQuery("neighbour((25,75),(25,175))."));

            nodeList.add(new Node(environment.getGarbageTruck().getCenterPosition()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doGarbageTruckJob(List<Node> goalNodes) throws Exception {

        //ZROBIĆ LISTĘ WSZYSTKICH TRAS PROWADZĄCYCH Z KAŻDEGO GOALNODE DO WSZYSTKICH POZOSTAŁYCH

        List<Node> allJobResult = new ArrayList<>();
        Node startNode = goalNodes.get(0);
        goalNodes.remove(0);

        for(Node goalNode : goalNodes) {
            AStar aStar = new AStar(goalNode,
                    startNode);

            aStar.prepareNodesForJob(environment);

            //------------------------------------------------------------------------------------------------------------------------
            //System.out.println("(" + allNodes.get(0).getPosition().getXPos() + "," + allNodes.get(0).getPosition().getYPos() + ")");

            //System.out.println("(" + allNodes.get(6).getPosition().getXPos() + "," + allNodes.get(6).getPosition().getYPos() + ")");
            //------------------------------------------------------------------------------------------------------------------------

            List<Node> result = aStar.solvePath(prologEngineResolver);

            //------------------------------------------------------------------------------------------------------------------------
            for(int i = 0; i<result.size(); i++) {
                System.out.println("(" + result.get(i).getPosition().getXPos() + "," + result.get(i).getPosition().getYPos() + ")");
            }
            //------------------------------------------------------------------------------------------------------------------------

            allJobResult.addAll(result);
            startNode = goalNode;
        }

        List<Road> roadsToGoForGarbageTruck = loadRoadsBasedOnResultController(allJobResult);

        environment.getGarbageTruck().beginJob(roadsToGoForGarbageTruck, environment);
        environment.getGarbageTruck().draw(gc);

        timer.schedule(task, 1000l,500l);
    }

    private List<Road> loadRoadsBasedOnResultController(List<Node> result) {
        List<Road> allRoads = environment.getRoads();
        List<Road> roadsToGoForGarbageTruck = new ArrayList<>();

        for(int i = 0; i < result.size() - 1; i++) {
            for(Road road : allRoads) {
                List<RoadField> roadFields = road.getRoadFields();
                if(
                        roadFields.get(0).getCenterPosition().getXPos() == result.get(i).getPosition().getXPos()
                                && roadFields.get(0).getCenterPosition().getYPos() == result.get(i).getPosition().getYPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getXPos() == result.get(i + 1).getPosition().getXPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getYPos() == result.get(i + 1).getPosition().getYPos()) {

                    roadsToGoForGarbageTruck.add(road);
                }
            }
        }

        return roadsToGoForGarbageTruck;
    }

    TimerTask task = new TimerTask()
    {
        public void run()
        {
            if(!environment.getGarbageTruck().jobDone) {
                environment.getGarbageTruck().getPreviousCorespondingRoadField().draw(gc);
                environment.getGarbageTruck().draw(gc);
                environment.getGarbageTruck().goToNextPosition(environment);
            } else {
                finishGarbageTruckJob();
                environment.getGarbageTruck().getPreviousCorespondingRoadField().draw(gc);
                environment.getGarbageTruck().draw(gc);
            }
        }

    };

    private void finishGarbageTruckJob() {
        timer.cancel();
        nodeList = new ArrayList<>();
    }

    public void onCanvasMouseClicked(MouseEvent mouseEvent) {
        int houseGCPointXPos;
        int houseGCPointYPos;

        Point2D drawingPoint = drawingArea.sceneToLocal(mouseEvent.getSceneX(), mouseEvent.getSceneY());

        int mouseXPos = (int)drawingPoint.getX();
        int mouseYPos = (int)drawingPoint.getY();

        houseGCPointXPos = mouseXPos - (mouseXPos % 50) + 25;
        houseGCPointYPos = mouseYPos - (mouseYPos % 50) + 25;

        Position position = new Position(houseGCPointXPos, houseGCPointYPos);

        GarbageColletionField garbageColletionField = environment.searchForGarbageCollectionField(position);

        if(!(garbageColletionField == null)) {
            garbageColletionField.setImage("/HouseGCPointFieldGreen.png");
            garbageColletionField.draw(gc);
            nodeList.add(new Node(position));
        }
    }

    public void goGarbageTruck(MouseEvent mouseEvent) throws Exception {
        Node startNode = nodeList.get(0);
        nodeList.remove(0);
        FitnessCalc.setEnvironment(environment, prologEngineResolver);


        List<Node> availableGenesList = new ArrayList<>();
        for(Node node : nodeList) {
            availableGenesList.add(node);
        }
        //Individual fromNodeList = new Individual(availableGenesList);

        List<List<Node>> permutationsNodeList = NodesPermutationGenerator.generatePerm(nodeList);
        List<List<Node>> finalPermutationsForOperation = new ArrayList<>();
        for(int i = 0 ; i < permutationsNodeList.size() ; i+=permutationsNodeList.size()/Integer.parseInt(maxPopulationSizeTextField.getText())+1) {
            if(i > Integer.parseInt(maxPopulationSizeTextField.getText())) break;
            finalPermutationsForOperation.add(permutationsNodeList.get(i));
            //permutationsNodeList.remove(i);
        }

        Individual[] individuals = new Individual[finalPermutationsForOperation.size()];
        List<Individual> individualList = new ArrayList<>();
        for(List<Node> permutation : finalPermutationsForOperation) {
            individualList.add(new Individual(permutation, startNode));
        }

        //List<Individual> individualList = NodesPermutationGenerator.generatePerm(fromNodeList);

        for(int i = 0; i < individuals.length; i++) {
            individuals[i] = individualList.get(i);
        }

        Population population = new Population(finalPermutationsForOperation.size(), individuals);
        int generationCount = 0;
        for(int i = 0; i < 10; i++) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
            population = Algorithm.evolvePopulation(population, availableGenesList);
        }
        //while (population.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
        //    generationCount++;
        //    System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
        //    population = Algorithm.evolvePopulation(population);
        //}
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        Individual fittest = population.getFittest();
        for(Node node : fittest.getGoalNodes()) {
            System.out.println("(" + node.getPosition().getXPos() + ", " + node.getPosition().getYPos() + ")");
            System.out.println(fittest.getGoalNodes().size());
        }
        //System.out.println(population.getFittest());
        //geneticsAlgorithm = new GeneticsAlgorithm(availableGenesList, environment, prologEngineResolver);
        //geneticsAlgorithm.printPopulationSpecimens();
        List<Node> garbageTruckJobNodesList = new ArrayList<>();
        garbageTruckJobNodesList.add(startNode);
        garbageTruckJobNodesList.addAll(population.getFittest().getGoalNodes());
        doGarbageTruckJob(garbageTruckJobNodesList);

    }
}
