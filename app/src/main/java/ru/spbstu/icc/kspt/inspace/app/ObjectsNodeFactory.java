package ru.spbstu.icc.kspt.inspace.app;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;


class ObjectsNodeFactory {

    private APlanet planet;
    private int width;
    private int padding;

    public ObjectsNodeFactory(APlanet planet, int width, int padding) {
        this.planet = planet;
        this.width = width - padding * 2;
        this.padding = padding;
    }

    public Node createBuildingsNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(padding));
        Text buildings = new Text("Buildings");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = createScrollPaneWithObjects(planet.getBuildings().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        AUpgrade upgrade = planet.getCurrentBuildingUpgrade();
        gridPane.add(actionNodeFactory.createUpgradeNode(upgrade), 0, 2);
        return gridPane;
    }

    public Node createResearchNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(padding));
        Text buildings = new Text("Research");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = createScrollPaneWithObjects(planet.getResearches().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        AUpgrade upgrade = planet.getCurrentResearchUpgrade();
        gridPane.add(actionNodeFactory.createUpgradeNode(upgrade), 0, 2);
        return gridPane;
    }


    public Node createShipsNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(padding));
        Text buildings = new Text("Ships");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = createScrollPaneWithShips(planet.getShips().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        AConstruct construction = planet.getCurrentConstruct();
        gridPane.add(actionNodeFactory.createConstructNode(construction), 0, 2);
        return gridPane;
    }

    private ScrollPane createScrollPaneWithShips(Collection<? extends AShipModel> ships) {
        GridPane innerPane = new GridPane();
        innerPane.setHgap(10);
        innerPane.setVgap(5);
        //innerPane.setMinWidth(width);
        innerPane.setPadding(new Insets(padding));
        int row = 0;

        innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        for (AShipModel ship: ships){
            innerPane.add(createObjectNode(ship), 0, row++);
            innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        }
        ScrollPane scrollPane = new ScrollPane(innerPane);
        scrollPane.setMinWidth(width);
        return scrollPane;
    }

    private ScrollPane createScrollPaneWithObjects(Collection<? extends AUpgradable> upgradables) {
        GridPane innerPane = new GridPane();
        innerPane.setHgap(10);
        innerPane.setVgap(5);
       // innerPane.setMinWidth(width);
        innerPane.setPadding(new Insets(padding));
        int row = 0;

        innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        for (AUpgradable upgradable: upgradables){
            innerPane.add(createObjectNode(upgradable), 0, row++);
            innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        }
        ScrollPane scrollPane = new ScrollPane(innerPane);
        scrollPane.setMinWidth(width);
        return scrollPane;
    }

    private Node createObjectNode(AShipModel ship) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(50);
        gridPane.setVgap(10);

        int row = 0;
        gridPane.add(new Text("Type: " + InSpace.shipTypeTable.get(ship.getType())), 0, row++);
        gridPane.add(new Text("Number: " + planet.getFleetOnPlanet().getNumbersOfShips().get(ship.getType())), 0, row++);
        AResources cost = ship.getConstructCost();
        gridPane.add(new Text("Cost of construction: "
                + cost.getMetal() + " : " + cost.getCrystals() + " : " + cost.getDeuterium()), 0, row++);
        gridPane.add(new Text("Duration of construction: "
                + ship.getConstructDuration().getSeconds() + " second(s)"), 0, row++);

        row = 0;
        gridPane.add(new Text("Attack: " + ship.getAttack()), 1, row++);
        gridPane.add(new Text("Structure: " + ship.getStructure()), 1, row++);
        gridPane.add(new Text("Shields: " + ship.getShieldStructure()), 1, row++);
        gridPane.add(new Text("Speed: " + ship.getSpeed()), 1, row++);
        gridPane.add(new Text("Capacity: " + ship.getResourcesCapacity()), 1, row++);

        row = 0;
        Button construct = new Button("Construct");
        construct.setOnAction(event -> {
            TextInputDialog input = new TextInputDialog("1");
            input.setTitle("Construct ships");
            input.setContentText("Input number of ships");
            input.showAndWait().ifPresent(s ->
            {
                try {
                    int number = Integer.parseInt(s);
                    ship.startConstruction(number);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Construction");
                    alert.setHeaderText("Construction has started");
                    alert.setContentText("Construction will be completed at "
                         + LocalDateTime.now().plus(ship.getConstructDuration()).format(DateTimeFormatter.ISO_TIME));
                    alert.showAndWait();
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect input");
                    alert.show();
                    return;
                } catch (ConstructException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Construction");
                    alert.setHeaderText("Can not construct " + InSpace.shipTypeTable.get(ship.getType()));
                    alert.setContentText("It can not be construct at this moment");
                    alert.showAndWait();
                }

            });
        });
        gridPane.add(construct, 2, row++);
        Button infoButton = new Button("Information");
        infoButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(InSpace.shipTypeTable.get(ship.getType()));
            alert.setHeaderText("There will be information about ship");
            alert.show();
        });
        gridPane.add(infoButton, 2, row);

        return gridPane;
    }

    private Node createObjectNode(AUpgradable upgradable) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(50);
        gridPane.setVgap(10);
        Map<? extends Enum, String> table;
        Enum type;
        if (upgradable instanceof ABuilding) {
            type = ((ABuilding)(upgradable)).getType();
            table = InSpace.buildingTypeTable;
        } else {
            type = ((AResearch)(upgradable)).getType();
            table = InSpace.researchTypeTable;
        }

        int row = 0;
        gridPane.add(new Text("Type: " + table.get(type)), 0, row++);
        gridPane.add(new Text("Level: " + upgradable.getLevel()), 0, row++);
        AResources cost = upgradable.getUpgradeCost();
        gridPane.add(new Text("Cost of upgrade: "
                + cost.getMetal() + " : " + cost.getCrystals() + " : " + cost.getDeuterium()), 0, row++);
        gridPane.add(new Text("Duration of upgrade: "
                + upgradable.getUpgradeDuration().getSeconds() + " second(s)"), 0, row++);

        row = 0;
        Button upgrade = new Button("Upgrade");
        upgrade.setOnAction(event -> {
            try {
                upgradable.startUpgrade();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Upgrade has started");
                alert.setContentText("Upgrade will be completed at "
                        + LocalDateTime.now().plus(upgradable.getUpgradeDuration()).format(DateTimeFormatter.ISO_TIME));
                alert.showAndWait();
            } catch (UpgradeException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Can not upgrade " + table.get(type));
                alert.setContentText("It can not be upgraded at this moment");
                alert.showAndWait();
            }
        });
        gridPane.add(upgrade, 1, row++);
        Button infoButton = new Button("Information");
        infoButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(table.get(type));
            alert.setHeaderText("There will be information about building");
            alert.show();
        });
        gridPane.add(infoButton, 1, row);
        return gridPane;
    }
}
