package ru.spbstu.icc.kspt.inspace.app;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ru.spbstu.icc.kspt.inspace.api.Building;
import ru.spbstu.icc.kspt.inspace.api.Research;
import ru.spbstu.icc.kspt.inspace.api.Resources;
import ru.spbstu.icc.kspt.inspace.api.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


class ObjectsNodeFactory {

    public ScrollPane getScrollPaneWithObjects(int padding, int width, Collection<? extends Upgradable> upgradables) {
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

    public Node createObjectNode(Upgradable upgradable) {
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
