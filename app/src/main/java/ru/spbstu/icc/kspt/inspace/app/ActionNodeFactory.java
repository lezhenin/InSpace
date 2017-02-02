package ru.spbstu.icc.kspt.inspace.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


class ActionNodeFactory {

    private double width;
    private Timeline timeline;

    public ActionNodeFactory(double width) {
        this.width = width;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    Node createUpgradeNode(AUpgrade upgrade) {
        Node node;
        if (upgrade == null) {
            node = createEmptyActionNode();
        } else {
            node = getUpgradeNode(upgrade);
        }
        return node;
    }

    Node createConstructNode(AConstruct construct) {
        Node node;
        if (construct == null) {
            node = createEmptyActionNode();
        } else {
            node = getConstructNode(construct);
        }
        return node;
    }

    Node createMissionNode(AMission mission) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setHgap(15);
        gridPane.setMinWidth(width);
        Text destination = new Text("Destination: " +
                mission.getDestination().getName() + " " + mission.getDestination().getPosition());
        Text endTime = new Text("Finish time: " +
                mission.getTime().format(DateTimeFormatter.ISO_TIME));
        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), mission.getTime()).getSeconds() + " second(s)");
        gridPane.add(remainingTime, 0, 2);
        gridPane.add(destination, 0, 0);
        gridPane.add(endTime, 0, 1);

        gridPane.add(new Text("Fleet: "), 1, 0);
        int i = 0;
        for (Map.Entry<ShipType, Integer> number : mission.getFleet().getNumbersOfShips().entrySet()) {
            if (number.getValue() != 0) {
                gridPane.add(new Text(InSpace.shipTypeTable.get(number.getKey()) +
                        " : " + number.getValue().toString()), 1, ++i);
            }
        }

        gridPane.add(new Text("Resources: "), 2, 0);
        gridPane.add(new Text("Metal:  " + mission.getFleet().getResources().getMetal()), 2, 1);
        gridPane.add(new Text("Crystals: " + mission.getFleet().getResources().getCrystals()), 2, 2);
        gridPane.add(new Text("Deuterium:  " + mission.getFleet().getResources().getDeuterium()), 2, 3);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime.setText("Remaining time: " +
                    java.time.Duration.between(LocalDateTime.now(), mission.getTime()).getSeconds() + " second(s)");
        }));
        timeline.play();

        return gridPane;
    }

    private Node getConstructNode(AConstruct construct) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setMinWidth(width);

        Text name = new Text("Type: " + InSpace.shipTypeTable.get(((AShip)(construct.getConstructable())).getType()));
        gridPane.add(name, 0, 0);

        Text number = new Text("Number of units: " + construct.getNumberOfUnits());
        gridPane.add(number, 0, 1);

        Text endTime = new Text("Finish time: " + construct.getTime().format(DateTimeFormatter.ISO_TIME));
        gridPane.add(endTime, 0, 2);

        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), construct.getTime()).getSeconds() + " second(s)");
        gridPane.add(remainingTime, 0, 3);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime.setText("Remaining time: " +
                    java.time.Duration.between(LocalDateTime.now(), construct.getTime()).getSeconds() + " second(s)");
        }));
        timeline.play();

        return gridPane;
    }

    private Node getUpgradeNode(AUpgrade upgrade) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setMinWidth(width);
        AUpgradable upgradable = upgrade.getUpgradable();

        Text name = new Text();
        if (upgradable instanceof ABuilding) {
            name.setText("Type: " + InSpace.buildingTypeTable.get(((ABuilding)upgradable).getType()));
        } else {
            name.setText("Type: " + InSpace.researchTypeTable.get(((AResearch)upgradable).getType()));
        }
        gridPane.add(name, 0, 0);

        Text endTime = new Text("Finish time: " + upgrade.getTime().format(DateTimeFormatter.ISO_TIME));
        gridPane.add(endTime, 0, 1);

        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), upgrade.getTime()).getSeconds() + " second(s)");
        gridPane.add(remainingTime, 0, 2);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime.setText("Remaining time: " +
                    java.time.Duration.between(LocalDateTime.now(), upgrade.getTime()).getSeconds() + " second(s)");
        }));
        timeline.play();
        return gridPane;
    }

    private Node createEmptyActionNode() {
        Text message = new Text("none");
        message.setWrappingWidth(width);
        message.setTextAlignment(TextAlignment.CENTER);
        return message;
    }
}
