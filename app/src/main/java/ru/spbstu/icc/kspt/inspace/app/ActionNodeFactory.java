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

    Node getUpgradeNode(Upgrade upgrade) {
        Node node;
        if (upgrade == null) {
            node = createEmptyActionNode();
        } else {
            node = createUpgradeNode(upgrade);
        }
        return node;
    }

    Node getConstructNode(Construct construct) {
        Node node;
        if (construct == null) {
            node = createEmptyActionNode();
        } else {
            node = createConstructNode(construct);
        }
        return node;
    }

    Node getMissionNode(Mission mission) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setHgap(15);
        gridPane.setMinWidth(width);
        Text destination = new Text("Destination: " +
                mission.getDestination().getName() + " " + mission.getDestination().getPosition());
        Text endTime = new Text("Finish time: " +
                mission.getEndTime().format(DateTimeFormatter.ISO_TIME));
        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), mission.getEndTime()).getSeconds() + " second(s)");
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
                    java.time.Duration.between(LocalDateTime.now(), mission.getEndTime()).getSeconds() + " second(s)");
        }));
        timeline.play();

        return gridPane;
    }

    private Node createConstructNode(Construct construct) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setMinWidth(width);

        Text name = new Text("Type: " + InSpace.shipTypeTable.get(((Ship)(construct.getConstructable())).getType()));
        gridPane.add(name, 0, 0);

        Text number = new Text("Number of units: " + construct.getNumberOfUnits());
        gridPane.add(number, 0, 1);

        Text endTime = new Text("Finish time: " + construct.getEndTime().format(DateTimeFormatter.ISO_TIME));
        gridPane.add(endTime, 0, 2);

        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), construct.getEndTime()).getSeconds() + " second(s)");
        gridPane.add(remainingTime, 0, 3);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime.setText("Remaining time: " +
                    java.time.Duration.between(LocalDateTime.now(), construct.getEndTime()).getSeconds() + " second(s)");
        }));
        timeline.play();

        return gridPane;
    }

    private Node createUpgradeNode(Upgrade upgrade) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.setMinWidth(width);
        Upgradable upgradable = upgrade.getUpgradable();

        Text name = new Text();
        if (upgradable instanceof Building) {
            name.setText("Type: " + InSpace.buildingTypeTable.get(((Building)upgradable).getType()));
        } else {
            name.setText("Type: " + InSpace.researchTypeTable.get(((Research)upgradable).getType()));
        }
        gridPane.add(name, 0, 0);

        Text endTime = new Text("Finish time: " + upgrade.getEndTime().format(DateTimeFormatter.ISO_TIME));
        gridPane.add(endTime, 0, 1);

        Text remainingTime = new Text("Remaining time: " +
                java.time.Duration.between(LocalDateTime.now(), upgrade.getEndTime()).getSeconds() + " second(s)");
        gridPane.add(remainingTime, 0, 2);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime.setText("Remaining time: " +
                    java.time.Duration.between(LocalDateTime.now(), upgrade.getEndTime()).getSeconds() + " second(s)");
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
