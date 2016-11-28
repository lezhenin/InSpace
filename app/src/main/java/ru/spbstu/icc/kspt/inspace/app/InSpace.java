package ru.spbstu.icc.kspt.inspace.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Attack;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;

public class InSpace extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        Button overview = new Button("Overview");
        overview.setOnAction(event -> {
            System.out.println("Overview");
        });

        Button buildings = new Button("Buildings");
        buildings.setPrefWidth(100);
        Button research = new Button("Research");
        research.setPrefWidth(100);
        Button fleets = new Button("Fleets");


        GridPane root = new GridPane();
        root.setGridLinesVisible(true);
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.add(overview, 0,0);
        root.add(buildings, 0,1);
        root.add(research, 0,2);
        root.add(fleets, 0,3);
        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("My JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();

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
