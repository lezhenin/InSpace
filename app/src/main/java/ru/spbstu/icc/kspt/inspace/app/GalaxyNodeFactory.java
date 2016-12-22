package ru.spbstu.icc.kspt.inspace.app;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.spbstu.icc.kspt.inspace.api.Galaxy;
import ru.spbstu.icc.kspt.inspace.api.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.HashMap;
import java.util.Map;

class GalaxyNodeFactory {

    private Planet planet;
    private int width;
    private int padding;

    GalaxyNodeFactory(Planet planet, int width, int padding) {
        this.padding = padding;
        this.planet = planet;
        this.width = width - padding * 2;
    }

    Node getGalaxyNode() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(padding));
        gridPane.setMinWidth(width);

        final int[] numberOfSystem = {planet.getPosition().getNumberOfSystem()};
        final Node[] current = {getSystemNode(numberOfSystem[0])};

        Text title = new Text("System " + numberOfSystem[0] + ":");
        title.setWrappingWidth(width / 2);
        title.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(new Separator(Orientation.HORIZONTAL), 0, 1);

        gridPane.add(new Separator(Orientation.HORIZONTAL), 0, 2, 3, 1);
        gridPane.add(current[0], 0, 3, 3, 1);

        Button prev = new Button("Prev");
        prev.setMinWidth(width / 4);
        prev.setOnAction(event -> {
            if (numberOfSystem[0] == 0) {
                return;
            }
            numberOfSystem[0]--;
            gridPane.getChildren().remove(current[0]);
            current[0] = getSystemNode(numberOfSystem[0]);
            gridPane.add(current[0], 0, 2, 3, 1);
            title.setText("System " + numberOfSystem[0] + ":");
        });

        Button next = new Button("Next");
        next.setMinWidth(width / 4);
        next.setOnAction(event -> {
            if (numberOfSystem[0] == 9) {
                return;
            }
            numberOfSystem[0]++;
            gridPane.getChildren().remove(current[0]);
            current[0] = getSystemNode(numberOfSystem[0]);
            gridPane.add(current[0], 0, 2, 3, 1);
            title.setText("System " + numberOfSystem[0] + ":");
        });

        gridPane.add(prev, 0, 0);
        gridPane.add(title, 1, 0);
        gridPane.add(next, 2, 0);
        return gridPane;
    }

    private  Node getSystemNode(int numberOfSystem) {
        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(width);
        int row = 0;
        for (int i = 0; i < 10; i++) {
            gridPane.add(getPlanetNode(new Position(numberOfSystem, i)), 0, row++);
            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.setMinWidth(width);
            gridPane.add(separator, 0, row ++);
        }
        return gridPane;
    }

    private Node getPlanetNode(Position position) {
        Planet planet = Galaxy.getPlanet(position);
        if (planet == null) {
            return new Text("Empty");
        }
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        Text name = new Text(planet.getName());
        name.setWrappingWidth(width * 3 / 4);
        gridPane.add(name, 0, 0, 1, 2);
        Button attack = new Button("Attack");
        attack.setMinWidth(width / 4);
        attack.setOnAction(event -> {
            Map<ShipType, Integer>  map = new HashMap<>();
            boolean[] result = {false};
            new MissionDialogsFactory(400, 400).makeFleetPickerDialog(map, result).showAndWait();
            if (result[0]) {
                try {
                    this.planet.startAttack(planet.getPosition(), map);
                    showSuccessDialog();
                } catch (FleetDetachException e) {
                    showDetachErrorDialog();
                }
            }
        });
        Button transport = new Button("Transport");
        transport.setMinWidth(width / 4);
        transport.setOnAction(event -> {
            Map<ShipType, Integer>  map = new HashMap<>();
            boolean[] results = {false};
            new MissionDialogsFactory(400, 400).makeFleetPickerDialog(map, results).showAndWait();
            if (results[0]) {
                int[] resource = new int[3];
                new MissionDialogsFactory(400, 400).makeResourcePickerDialog(resource, results).showAndWait();
                if (results[0]) {
                    try {
                        this.planet.startTransportation(planet.getPosition(), map, resource[0], resource[1], resource[2]);
                        showSuccessDialog();
                    } catch (FleetDetachException e) {
                        showDetachErrorDialog();
                    } catch (CapacityExcessException e) {
                        showCapacityExcessDialog();
                    }
                }
            }
        });
        if (planet.getPosition().equals(this.planet.getPosition())) {
            return gridPane;
        }
        gridPane.add(attack, 1, 0);
        gridPane.add(transport, 1, 1);
        return gridPane;
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mission has started");
        alert.setHeaderText("Mission has started successfully");
        alert.show();
    }

    private void showDetachErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fleet detach error");
        alert.setHeaderText("Can not detach fleet");
        alert.setContentText("Check number of ships");
        alert.show();
    }

    private void showCapacityExcessDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fleet capacity excess");
        alert.setHeaderText("Can not put resources on ships");
        alert.setContentText("Check capacity of ships");
        alert.show();
    }
}
