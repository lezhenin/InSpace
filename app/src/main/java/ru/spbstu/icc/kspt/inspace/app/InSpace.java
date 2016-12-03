package ru.spbstu.icc.kspt.inspace.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;

import java.util.ArrayList;
import java.util.List;

public class InSpace extends Application {

    private List<Button> menuButtons = new ArrayList<>();

    private Planet planet = new Planet("Nibiru", new Position(5, 3));

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);

        menuButtons.add(new Button("Overview"));
        menuButtons.add(new Button("Buildings"));
        menuButtons.add(new Button("Research"));
        menuButtons.add(new Button("Fleet"));
        menuButtons.forEach(button -> button.setPrefWidth(100));

        for (int i = 0; i < menuButtons.size(); i++) {
            root.add(menuButtons.get(i), 0, i);
        }

        root.add(getOverviewPane(10, 645), 1, 0, 1, menuButtons.size());

        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("My JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane getOverviewPane(int padding, int width) {
        int realWidth = width - 2 * padding;
        GridPane overview = new GridPane();
        //overview.setGridLinesVisible(true);
        overview.setHgap(10);
        overview.setVgap(5);
        overview.setPadding(new Insets(padding));

        Text resources = new Text("Resources");
        resources.setTextAlignment(TextAlignment.CENTER);
        resources.setWrappingWidth(realWidth);

        Text properties = new Text("Properties");
        properties.setWrappingWidth(realWidth);
        properties.setTextAlignment(TextAlignment.CENTER);

        Text metal = new Text("Metal: " + planet.getResources().getMetal());
        metal.setWrappingWidth(realWidth / 3);

        Text crystals = new Text("Crystals: " + planet.getResources().getCrystals());
        crystals.setWrappingWidth(realWidth / 3);

        Text deuterium = new Text("Deuterium:  " + planet.getResources().getCrystals());
        deuterium.setWrappingWidth(realWidth / 3);

        overview.add(resources, 0, 0, 3, 1);
        overview.add(metal, 0, 1);
        overview.add(crystals, 1, 1);
        overview.add(deuterium, 2, 1);
        overview.add(properties, 0, 2, 3, 1);
        overview.add(new Text("Name:"), 0, 3);
        overview.add(new Text(planet.getName()), 1, 3);
        overview.add(new Text("Number of fields:"), 0, 4);
        overview.add(new Text(String.valueOf(planet.getNumberOfFields())), 1, 4);
        overview.add(new Text("Number of empty fields:"), 0, 5);
        overview.add(new Text(String.valueOf(planet.getNumberOfEmptyFields())), 1, 5);

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
