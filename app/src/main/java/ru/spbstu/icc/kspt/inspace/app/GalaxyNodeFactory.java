package ru.spbstu.icc.kspt.inspace.app;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.spbstu.icc.kspt.inspace.api.Galaxy;
import ru.spbstu.icc.kspt.inspace.api.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;

class GalaxyNodeFactory {

    private Planet planet;
    private int width;
    private int padding;

    GalaxyNodeFactory(Planet planet, int width, int padding) {
        this.padding = padding;
        this.planet = planet;
        this.width = width;
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

        gridPane.add(current[0], 0, 2, 3, 1);

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
        gridPane.setVgap(15);
        Text name = new Text(planet.getName());
        name.setWrappingWidth(width * 3 / 4);
        gridPane.add(name, 0, 0, 1, 2);
        Button attack = new Button("Attack");
        attack.setOnAction(event -> {
            //TODO
        });
        Button transport = new Button("Transport");
        transport.setOnAction(event -> {
            //TODO
        });
        if (planet.getPosition().equals(this.planet.getPosition())) {
            return gridPane;
        }
        gridPane.add(attack, 1, 0);
        gridPane.add(transport, 1, 1);
        return gridPane;
    }
}
