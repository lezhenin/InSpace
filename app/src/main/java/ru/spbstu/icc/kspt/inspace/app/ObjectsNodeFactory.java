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


class ObjectsNodeFactory {

    private Planet planet;
    private int width;
    private int padding;

    public ObjectsNodeFactory(Planet planet, int width, int padding) {
        this.planet = planet;
        this.width = width;
        this.padding = padding;
    }

    public Node getBuildingsNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        Text buildings = new Text("Buildings");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = getScrollPaneWithObjects(planet.getBuildings().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        Upgrade upgrade = planet.getCurrentBuildingUpgrade().isPresent() ?
                planet.getCurrentBuildingUpgrade().get() : null;
        gridPane.add(actionNodeFactory.getUpgradeNode(upgrade), 0, 2);
        return gridPane;
    }

    //TODO избавиться от дублирования кода
    public Node getResearchNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        Text buildings = new Text("Research");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = getScrollPaneWithObjects(planet.getResearches().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        Upgrade upgrade = planet.getCurrentResearchUpgrade().isPresent() ?
                planet.getCurrentResearchUpgrade().get() : null;
        gridPane.add(actionNodeFactory.getUpgradeNode(upgrade), 0, 2);
        return gridPane;
    }


    public Node getShipsNode() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        Text buildings = new Text("Ships");
        buildings.setWrappingWidth(width);
        buildings.setTextAlignment(TextAlignment.CENTER);
        buildings.setFont(new Font("Arial", 25));
        gridPane.add(buildings, 0, 0);

        ScrollPane scrollPane = getScrollPaneWithShips(planet.getShips().values());
        gridPane.add(scrollPane, 0, 1);
        ActionNodeFactory actionNodeFactory = new ActionNodeFactory(width);
        Construct construction = planet.getCurrentConstruct().isPresent() ?
                planet.getCurrentConstruct().get() : null;
        gridPane.add(actionNodeFactory.getConstructNode(construction), 0, 2);
        return gridPane;
    }

    private ScrollPane getScrollPaneWithShips(Collection<Ship> ships) {
        GridPane innerPane = new GridPane();
        innerPane.setHgap(5);
        innerPane.setVgap(5);
        innerPane.setMinWidth(width);
        innerPane.setPadding(new Insets(padding));
        int row = 0;

        innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        for (Ship ship: ships){
            innerPane.add(createObjectNode(ship), 0, row++);
            innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        }
        ScrollPane scrollPane = new ScrollPane(innerPane);
        scrollPane.setMinWidth(width);
        return scrollPane;
    }

    private ScrollPane getScrollPaneWithObjects(Collection<? extends Upgradable> upgradables) {
        GridPane innerPane = new GridPane();
        innerPane.setHgap(5);
        innerPane.setVgap(5);
        innerPane.setMinWidth(width);
        innerPane.setPadding(new Insets(padding));
        int row = 0;

        innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        for (Upgradable upgradable: upgradables){
            innerPane.add(createObjectNode(upgradable), 0, row++);
            innerPane.add(new Separator(Orientation.HORIZONTAL), 0, row++, 2, 1);
        }
        ScrollPane scrollPane = new ScrollPane(innerPane);
        scrollPane.setMinWidth(width);
        return scrollPane;
    }

    private Node createObjectNode(Ship ship) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(100);
        gridPane.setVgap(10);

        int row = 0;
        gridPane.add(new Text("Type: " + ship.getType().toString()), 0, row++);
        gridPane.add(new Text("Number: " + planet.getFleetOnPlanet().getNumbersOfShips().get(ship.getType())), 0, row++);
        Resources cost = ship.getConstructCost();
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
                    ship.construct(number);
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
                    alert.setHeaderText("Can not construct " + ship.getType().toString());
                    alert.setContentText("It can not be construct at this moment");
                    alert.showAndWait();
                }

            });
        });
        gridPane.add(construct, 2, row++);
        Button infoButton = new Button("Information");
        infoButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ship.getType().toString());
            alert.setHeaderText("There will be information about ship");
            alert.show();
        });
        gridPane.add(infoButton, 2, row);

        return gridPane;
    }

    private Node createObjectNode(Upgradable upgradable) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(100);
        gridPane.setVgap(10);

        Enum type;
        if (upgradable instanceof Building) {
            type = ((Building)(upgradable)).getType();
        } else {
            type = ((Research)(upgradable)).getType();
        }

        int row = 0;
        gridPane.add(new Text("Type: " + type.toString()), 0, row++);
        gridPane.add(new Text("Level: " + upgradable.getLevel()), 0, row++);
        Resources cost = upgradable.getUpgradeCost();
        gridPane.add(new Text("Cost of upgrade: "
                + cost.getMetal() + " : " + cost.getCrystals() + " : " + cost.getDeuterium()), 0, row++);
        gridPane.add(new Text("Duration of upgrade: "
                + upgradable.getUpgradeDuration().getSeconds() + " second(s)"), 0, row++);

        row = 0;
        Button upgrade = new Button("Upgrade");
        upgrade.setOnAction(event -> {
            try {
                upgradable.upgrade();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Upgrade has started");
                alert.setContentText("Upgrade will be completed at "
                        + LocalDateTime.now().plus(upgradable.getUpgradeDuration()).format(DateTimeFormatter.ISO_TIME));
                alert.showAndWait();
            } catch (UpgradeException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Can not upgrade " + type.toString());
                alert.setContentText("It can not be upgraded at this moment");
                alert.showAndWait();
            }
        });
        gridPane.add(upgrade, 1, row++);
        Button infoButton = new Button("Information");
        infoButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(type.toString());
            alert.setHeaderText("There will be information about building");
            alert.show();
        });
        gridPane.add(infoButton, 1, row);
        return gridPane;
    }
}
