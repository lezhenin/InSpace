package ru.spbstu.icc.kspt.inspace.app;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ru.spbstu.icc.kspt.inspace.api.Construct;
import ru.spbstu.icc.kspt.inspace.api.Mission;
import ru.spbstu.icc.kspt.inspace.api.Planet;
import ru.spbstu.icc.kspt.inspace.api.Upgrade;

import java.util.Optional;

class OverviewNodeFactory {

    private Planet planet;
    private int padding;
    private int width;

    public OverviewNodeFactory(Planet planet, int width, int padding) {
        this.planet = planet;
        this.padding = padding;
        this.width = width - padding * 2 - 30;
    }

    private Node createResourceNode(){

        GridPane resourcesPane = new GridPane();
        resourcesPane.setHgap(10);
        resourcesPane.setVgap(5);
        resourcesPane.setPadding(new Insets(padding));

        int row = -1;

        Text resources = new Text("Resources");
        resources.setTextAlignment(TextAlignment.CENTER);
        resources.setWrappingWidth(width);
        resources.setFont(new Font("Arial", 25));
        resourcesPane.add(resources, 0, ++row, 3, 1);

        Text metal = new Text("Metal: " + planet.getResources().getMetal());
        metal.setWrappingWidth(width / 3);
        metal.setTextAlignment(TextAlignment.CENTER);
        resourcesPane.add(metal, 0, ++row);

        Text crystals = new Text("Crystals: " + planet.getResources().getCrystals());
        crystals.setWrappingWidth(width / 3);
        crystals.setTextAlignment(TextAlignment.CENTER);
        resourcesPane.add(crystals, 1, row);

        Text deuterium = new Text("Deuterium:  " + planet.getResources().getCrystals());
        deuterium.setWrappingWidth(width / 3);
        deuterium.setTextAlignment(TextAlignment.CENTER);
        resourcesPane.add(deuterium, 2, row);

        Text energy = new Text("Energy: " + planet.getEnergyLevel());
        energy.setWrappingWidth(width / 3);
        energy.setTextAlignment(TextAlignment.CENTER);
        resourcesPane.add(energy, 0, ++row);

        Text power = new Text("Power: " + (int)(planet.getProductionPower() * 100) + "%");
        power.setWrappingWidth(width / 3);
        power.setTextAlignment(TextAlignment.CENTER);
        resourcesPane.add(power, 1, row);

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            System.out.println("upd");
            metal.setText("Metal: " + planet.getResources().getMetal());
            crystals.setText("Crystals: " + planet.getResources().getCrystals());
            deuterium.setText("Deuterium: " + planet.getResources().getDeuterium());
        }));

        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        return resourcesPane;
    }

    private Node createPropertiesNode() {

        GridPane propertiesNode = new GridPane();
        propertiesNode.setHgap(10);
        propertiesNode.setVgap(5);
        propertiesNode.setPadding(new Insets(padding));

        int row = -1;

        Text properties = new Text("Properties");
        properties.setWrappingWidth(width);
        properties.setTextAlignment(TextAlignment.CENTER);
        properties.setFont(new Font("Arial", 25));
        propertiesNode.add(properties, 0, ++row, 3, 1);

        Text nameLabel = new Text("Name:");
        nameLabel.setWrappingWidth(width / 3);
        propertiesNode.add(nameLabel, 0, ++row);
        Text name = new Text(planet.getName());
        name.setWrappingWidth(width / 3);
        name.setTextAlignment(TextAlignment.CENTER);
        propertiesNode.add(name, 1, row);
        Button rename = new Button("Rename");
        rename.setMinWidth(width / 3);
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
        propertiesNode.add(rename, 2, row);


        propertiesNode.add(new Text("Number of fields:"), 0, ++row);
        Text numberOfFields = new Text(String.valueOf(planet.getNumberOfFields()));
        numberOfFields.setWrappingWidth(width / 3);
        numberOfFields.setTextAlignment(TextAlignment.CENTER);
        propertiesNode.add(numberOfFields, 1, row);
        propertiesNode.add(new Text("Number of empty fields:"), 0, ++row);
        numberOfFields = new Text(String.valueOf(planet.getNumberOfEmptyFields()));
        numberOfFields.setWrappingWidth(width / 3);
        numberOfFields.setTextAlignment(TextAlignment.CENTER);
        propertiesNode.add(numberOfFields, 1, row);

        return propertiesNode;
    }

    public Node createOverviewNode() {
        GridPane overview = new GridPane();
        overview.setHgap(10);
        overview.setVgap(5);
        overview.setPadding(new Insets(padding));
        overview.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        int row = -1;

        overview.add(createResourceNode(), 0, ++row, 3, 1);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        overview.add(separator, 0, ++row, 3, 1);

        overview.add(createPropertiesNode(), 0, ++row, 3, 1);

        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        Text actions = new Text("Current actions");
        actions.setTextAlignment(TextAlignment.CENTER);
        actions.setWrappingWidth(width);
        actions.setFont(new Font("Arial", 25));
        overview.add(actions, 0, ++row, 3, 1);

        Text buildingUpgrade = new Text("Building upgrade");
        buildingUpgrade.setWrappingWidth(width / 3);
        buildingUpgrade.setTextAlignment(TextAlignment.CENTER);
        overview.add(buildingUpgrade, 0, ++row);

        Text researchUpgrade = new Text("Research upgrade");
        researchUpgrade.setWrappingWidth(width / 3);
        researchUpgrade.setTextAlignment(TextAlignment.CENTER);
        overview.add(researchUpgrade, 1, row);

        Text fleetConstruction = new Text("Fleet construction");
        fleetConstruction.setWrappingWidth(width / 3);
        fleetConstruction.setTextAlignment(TextAlignment.CENTER);
        overview.add(fleetConstruction, 2, row);

        ActionNodeFactory factory = new ActionNodeFactory(width / 3);

        Optional<Upgrade> upgrade = planet.getCurrentBuildingUpgrade();
        overview.add(factory.createUpgradeNode(upgrade.isPresent() ? upgrade.get(): null), 0, ++row);
        upgrade = planet.getCurrentResearchUpgrade();
        overview.add(factory.createUpgradeNode(upgrade.isPresent() ? upgrade.get(): null), 1, row);

        Optional<Construct> construct = planet.getCurrentConstruct();
        overview.add(factory.createConstructNode(construct.isPresent() ? construct.get() : null), 2, row);

        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        Text missions = new Text("Missions");
        missions.setTextAlignment(TextAlignment.CENTER);
        missions.setWrappingWidth(width);
        missions.setFont(new Font("Arial", 25));
        overview.add(missions, 0, ++row, 3, 1);

        for (Mission mission: planet.getMissions()) {
            overview.add(factory.createMissionNode(mission), 0, ++row, 3, 1);
        }

        overview.add(new Separator(Orientation.HORIZONTAL), 0, ++row, 3, 1);

        return overview;
    }

}
