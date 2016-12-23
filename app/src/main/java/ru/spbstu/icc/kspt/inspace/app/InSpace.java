package ru.spbstu.icc.kspt.inspace.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.util.*;

public class InSpace extends Application {

    static Map<BuildingType, String> buildingTypeTable = new EnumMap<>(BuildingType.class);
    static Map<ResearchType, String> researchTypeTable = new EnumMap<>(ResearchType.class);
    static Map<ShipType, String> shipTypeTable = new EnumMap<>(ShipType.class);

    {
        buildingTypeTable.put(BuildingType.FACTORY, "Factory");
        buildingTypeTable.put(BuildingType.LABORATORY, "Laboratory");
        buildingTypeTable.put(BuildingType.SHIPYARD, "Shipyard");
        buildingTypeTable.put(BuildingType.POWER_STATION, "Power station");
        buildingTypeTable.put(BuildingType.METAL_MINE, "Metal mine");
        buildingTypeTable.put(BuildingType.CRYSTAL_MINE, "Crystal mine");
        buildingTypeTable.put(BuildingType.DEUTERIUM_MINE, "Deuterium mine");

        researchTypeTable.put(ResearchType.ENERGY, "Energy technology");
        researchTypeTable.put(ResearchType.LASER, "Laser technology");

        shipTypeTable.put(ShipType.SMALL_CARGO, "Small cargo");
        shipTypeTable.put(ShipType.FIGHTER, "Fighter");
    }

    private static final int WIDTH = 645;
    private static final int PADDING = 10;

    private Node currentNode;
    private GridPane root = new GridPane();
    private List<Button> menuButtons = new ArrayList<>();
    private Planet planet = new Planet("Planet", new Position(5, 3));

    private GalaxyNodeFactory galaxyNodeFactory = new GalaxyNodeFactory(planet, WIDTH, PADDING);
    private ObjectsNodeFactory objectsNodeFactory = new ObjectsNodeFactory(planet, WIDTH, PADDING);
    private OverviewNodeFactory overviewNodeFactory = new OverviewNodeFactory(planet, WIDTH, PADDING);

    private void createPlanets() {
        Random random = new Random(214);
        for (int i = 0; i < 30; i++) {
            int numberOfSystem;
            int numberOfPlanet;
            do {
                numberOfPlanet = random.nextInt(10);
                numberOfSystem = random.nextInt(10);
            } while (numberOfPlanet == 3 && numberOfSystem == 5);
            new Planet("planet" + random.nextInt(), new Position(numberOfSystem, numberOfPlanet));
        }
    }

    private void changeNode(Node newNode) {
        root.getChildren().remove(currentNode);
        root.add(newNode, 1, 0, 1, menuButtons.size() + 1);
        currentNode = newNode;
    }

    @Override
    public void init() throws Exception {
        super.init();
        createPlanets();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);

        Button overview = new Button("Overview");
        overview.setOnAction(event -> changeNode(overviewNodeFactory.createOverviewNode()));
        menuButtons.add(overview);

        Button buildings = new Button("Buildings");
        buildings.setOnAction(event -> changeNode(objectsNodeFactory.createBuildingsNode()));
        menuButtons.add(buildings);

        Button research = new Button("Research");
        research.setOnAction(event -> changeNode(objectsNodeFactory.createResearchNode()));
        menuButtons.add(research);

        Button fleet = new Button("Fleet");
        fleet.setOnAction(event -> changeNode(objectsNodeFactory.createShipsNode()));
        menuButtons.add(fleet);



        Button galaxy = new Button("Galaxy");
        galaxy.setOnAction(event -> changeNode(galaxyNodeFactory.getGalaxyNode()));
        menuButtons.add(galaxy);

        menuButtons.forEach(button -> button.setPrefWidth(100));


        for (int i = 0; i < menuButtons.size(); i++) {
            root.add(menuButtons.get(i), 0, i);
        }
        root.add(new VBox(1), 0, menuButtons.size());

        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("InSpace");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("STOP");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
