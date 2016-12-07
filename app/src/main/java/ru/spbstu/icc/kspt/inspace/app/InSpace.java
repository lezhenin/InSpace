package ru.spbstu.icc.kspt.inspace.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.api.Mission;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.*;

public class InSpace extends Application {

    private Node currentNode;
    private GridPane root = new GridPane();
    private List<Button> menuButtons = new ArrayList<>();

    private Planet planet = new Planet("Nibiru", new Position(5, 3));
    {
        new Planet("test", new Position(6,7));
        try {
            //planet.getBuilding(BuildingType.FACTORY).upgrade();
            planet.getShips().get(ShipType.FIGHTER).construct(2);
            try {
                Map<ShipType, Integer> fleetToAttack = new HashMap<>();
                fleetToAttack.put(ShipType.SMALL_CARGO, 1);
                fleetToAttack.put(ShipType.FIGHTER, 2);
                planet.startAttack(new Position(6,7),fleetToAttack);
            } catch (FleetDetachException e) {
                e.printStackTrace();
            }
        } catch (ConstructException e) {
            e.printStackTrace();
        }
    }

    private void changeNode(Node newNode) {
        root.getChildren().remove(currentNode);
        root.add(newNode, 1, 0, 1, menuButtons.size());
        currentNode = newNode;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);

        Button overview = new Button("Overview");
        overview.setOnAction(event -> changeNode(getOverviewNode(10, 645)));
        menuButtons.add(overview);

        Button buildings = new Button("Buildings");
        buildings.setOnAction(event -> changeNode(getBuildingsNode(10, 645)));
        menuButtons.add(buildings);
        menuButtons.add(new Button("Research"));
        menuButtons.add(new Button("Fleet"));
        menuButtons.forEach(button -> button.setPrefWidth(100));

        for (int i = 0; i < menuButtons.size(); i++) {
            root.add(menuButtons.get(i), 0, i);
        }

        //root.add(getBuildingsNode(10, 645), 1, 0, 1, menuButtons.size());


        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("My JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Node getBuildingsNode(int padding, int width) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        Text buildings = new Text("Buildings");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        GridPane innerPane = new GridPane();
        innerPane.setHgap(5);
        innerPane.setVgap(5);
        innerPane.setMinWidth(width);
        innerPane.setPadding(new Insets(padding));
        ObjectsNodeFactory factory = new ObjectsNodeFactory(this);
        int row = 0;

        innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        for (Building building: planet.getBuildings().values()){
            innerPane.add(factory.createBuildingNode(building), 0, row++);
            innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        }
        ScrollPane scrollPane = new ScrollPane(innerPane);
        scrollPane.setMinWidth(width);

        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory factory1 = new ActionNodeFactory(width);
        Upgrade upgrade = planet.getCurrentBuildingUpgrade().isPresent() ?
                planet.getCurrentBuildingUpgrade().get() : null;
        gridPane.add(factory1.getUpgradeNode(upgrade), 0, 2);
        return gridPane;
    }

    private Node getOverviewNode(int padding, int width) {
        int realWidth = width - 2 * padding;
        GridPane overview = new GridPane();
        overview.setHgap(10);
        overview.setVgap(5);
        overview.setPadding(new Insets(padding));

        int row = -1;

        Text resources = new Text("Resources");
        resources.setTextAlignment(TextAlignment.CENTER);
        resources.setWrappingWidth(realWidth);
        resources.setFont(new Font("Arial", 25));
        overview.add(resources, 0, ++row, 3, 1);

        Text metal = new Text("Metal: " + planet.getResources().getMetal());
        metal.setWrappingWidth(realWidth / 3);
        metal.setTextAlignment(TextAlignment.CENTER);
        overview.add(metal, 0, ++row);

        Text crystals = new Text("Crystals: " + planet.getResources().getCrystals());
        crystals.setWrappingWidth(realWidth / 3);
        crystals.setTextAlignment(TextAlignment.CENTER);
        overview.add(crystals, 1, row);

        Text deuterium = new Text("Deuterium:  " + planet.getResources().getCrystals());
        deuterium.setWrappingWidth(realWidth / 3);
        deuterium.setTextAlignment(TextAlignment.CENTER);
        overview.add(deuterium, 2, row);

        Text energy = new Text("Energy: " + planet.getEnergyLevel());
        energy.setWrappingWidth(realWidth / 3);
        energy.setTextAlignment(TextAlignment.CENTER);
        overview.add(energy, 0, ++row);

        Text power = new Text("Power: " + (int)(planet.getProductionPower() * 100) + "%");
        power.setWrappingWidth(realWidth / 3);
        power.setTextAlignment(TextAlignment.CENTER);
        overview.add(power, 1, row);


        Separator separator = new Separator(Orientation.HORIZONTAL);
        overview.add(separator, 0, ++row, 3, 1);

        Text properties = new Text("Properties");
        properties.setWrappingWidth(realWidth);
        properties.setTextAlignment(TextAlignment.CENTER);
        properties.setFont(new Font("Arial", 25));
        overview.add(properties, 0, ++row, 3, 1);

        overview.add(new Text("Name:"), 0, ++row);
        Text name = new Text(planet.getName());
        overview.add(name, 1, row);
        Button rename = new Button("Rename");
        rename.setOnAction((ActionEvent e) -> {
            TextInputDialog dialog = new TextInputDialog("New name...");
            dialog.setTitle("Rename Planet Dialog");
            dialog.setContentText("Enter new name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(r -> {
                planet.rename(result.get());
                name.setText(planet.getName());
            });

        });
        overview.add(rename, 2, row);


        overview.add(new Text("Number of fields:"), 0, ++row);
        overview.add(new Text(String.valueOf(planet.getNumberOfFields())), 1, row);
        overview.add(new Text("Number of empty fields:"), 0, ++row);
        overview.add(new Text(String.valueOf(planet.getNumberOfEmptyFields())), 1, row);
        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        Text actions = new Text("Current actions");
        actions.setTextAlignment(TextAlignment.CENTER);
        actions.setWrappingWidth(realWidth);
        actions.setFont(new Font("Arial", 25));
        overview.add(actions, 0, ++row, 3, 1);

        Text buildingUpgrade = new Text("Building upgrade");
        buildingUpgrade.setWrappingWidth(realWidth/3);
        buildingUpgrade.setTextAlignment(TextAlignment.CENTER);
        overview.add(buildingUpgrade, 0, ++row);

        Text researchUpgrade = new Text("Research upgrade");
        researchUpgrade.setWrappingWidth(realWidth/3);
        researchUpgrade.setTextAlignment(TextAlignment.CENTER);
        overview.add(researchUpgrade, 1, row);

        Text fleetConstruction = new Text("Fleet construction");
        fleetConstruction.setWrappingWidth(realWidth/3);
        fleetConstruction.setTextAlignment(TextAlignment.CENTER);
        overview.add(fleetConstruction, 2, row);

        ActionNodeFactory factory = new ActionNodeFactory(realWidth/3);

        Optional<Upgrade> upgrade = planet.getCurrentBuildingUpgrade();
        overview.add(factory.getUpgradeNode(upgrade.isPresent() ? upgrade.get(): null), 0, ++row);
        upgrade = planet.getCurrentResearchUpgrade();
        overview.add(factory.getUpgradeNode(upgrade.isPresent() ? upgrade.get(): null), 1, row);

        Optional<Construct> construct = planet.getCurrentConstruct();
        overview.add(factory.getConstructNode(construct.isPresent() ? construct.get() : null), 2, row);

        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        Text missions = new Text("Missions");
        missions.setTextAlignment(TextAlignment.CENTER);
        missions.setWrappingWidth(realWidth);
        missions.setFont(new Font("Arial", 25));
        overview.add(missions, 0, ++row, 3, 1);

        for (Mission mission: planet.getMissions()) {
            overview.add(factory.getMissionNode(mission), 0, ++row, 3, 1);
        }

        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            System.out.println("upd");
            metal.setText("Metal: " + planet.getResources().getMetal());
            crystals.setText("Crystals: " + planet.getResources().getCrystals());
            deuterium.setText("Deuterium: " + planet.getResources().getDeuterium());
        }));

        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        return overview;
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
