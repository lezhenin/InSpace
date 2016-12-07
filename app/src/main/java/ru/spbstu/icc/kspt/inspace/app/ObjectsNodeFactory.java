package ru.spbstu.icc.kspt.inspace.app;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ru.spbstu.icc.kspt.inspace.api.Building;
import ru.spbstu.icc.kspt.inspace.api.Resources;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class ObjectsNodeFactory {
    private InSpace inSpace;

    public ObjectsNodeFactory(InSpace inSpace) {
        this.inSpace = inSpace;
    }

    public Node createBuildingNode(Building building) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(100);
        gridPane.setVgap(10);
        int row = 0;
        gridPane.add(new Text("Type: " + building.getType().toString()), 0, row++);
        gridPane.add(new Text("Level: " + building.getLevel()), 0, row++);
        Resources cost = building.getUpgradeCost();
        gridPane.add(new Text("Cost of upgrade: "
                + cost.getMetal() + " : " + cost.getCrystals() + " : " + cost.getDeuterium()), 0, row++);
        gridPane.add(new Text("Duration of upgrade: "
                + building.getUpgradeDuration().getSeconds() + " second(s)"), 0, row++);

        row = 0;
        Button upgrade = new Button("Upgrade");
        upgrade.setOnAction(event -> {
            try {
                building.upgrade();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Upgrade has started");
                alert.setContentText("Upgrade will be completed at "
                        + LocalDateTime.now().plus(building.getUpgradeDuration()).format(DateTimeFormatter.ISO_TIME));
                alert.showAndWait();
            } catch (UpgradeException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upgrade");
                alert.setHeaderText("Can not upgrade " + building.getType().toString());
                alert.setContentText("It can not be upgraded at this moment");
                alert.showAndWait();
            }
        });
        gridPane.add(upgrade, 1, row++);
        Button infoButton = new Button("Information");
        infoButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(building.getType().toString());
            alert.setHeaderText("There will be information about building");
            alert.show();
        });
        gridPane.add(infoButton, 1, row);
        return gridPane;
    }
}
