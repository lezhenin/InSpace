package ru.spbstu.icc.kspt.inspace.app;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.HashMap;
import java.util.Map;

class MissionDialogsFactory {

    private int width;
    private int height;

    MissionDialogsFactory(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //TODO тут дикий костыль, пофиксить и сделать нормальный диалог
    Stage createFleetPickerDialog(Map<ShipType, Integer> mapToFeel, boolean[] result) {
        GridPane root = new GridPane();
        Scene dialogScene = new Scene(root);
        Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.setScene(dialogScene);
        dialog.setTitle("Fleet picker");
        dialog.setHeight(height);
        dialog.setWidth(width);
        Map<ShipType, TextField> fieldMap = new HashMap<>();

        int row = 0;
        for (ShipType type: ShipType.values()) {
            GridPane gridPane = new GridPane();
            Text text = new Text(InSpace.shipTypeTable.get(type));
            text.setWrappingWidth(width / 2);
            gridPane.add(text, 0, 0);
            TextField textField = new TextField("0");
            textField.setMinWidth(width / 2);
            fieldMap.put(type, textField);
            gridPane.add(textField, 1, 0);
            root.add(gridPane, 0, row++, 2, 1);
        }

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            result[0] = false;
            dialog.close();
        });
        cancel.setMinWidth(width / 2);
        root.add(cancel, 1, row);

        Button done = new Button("Done");
        done.setOnAction(event -> {
            result[0] = true;
            fieldMap.forEach((type, textField) -> {
                int res = 0;
                try {
                    res = Integer.parseInt(textField.getText());
                }
                catch (NumberFormatException ignored) {
                }
                mapToFeel.put(type, res);
            });
            dialog.close();
        });
        done.setMinWidth(width/2);
        root.add(done, 0, row++);
        return dialog;
    }

    Stage createResourcePickerDialog(int resources[], boolean[] result) {
        GridPane root = new GridPane();
        Scene dialogScene = new Scene(root);
        Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.setScene(dialogScene);
        dialog.setTitle("Resources picker");
        dialog.setHeight(height);
        dialog.setWidth(width);
        TextField[] fields  = new TextField[3];

        int row = 0;
        Text metal = new Text("Metal");
        metal.setWrappingWidth(width / 3);
        root.add(metal, 0, row);

        Text crystals = new Text("Crystals");
        crystals.setWrappingWidth(width / 3);
        root.add(crystals, 1, row);

        Text deuterium = new Text("Deuterium");
        deuterium.setWrappingWidth(width / 3);
        root.add(deuterium, 2, row);

        row++;
        for (int i = 0; i < resources.length; i++) {
            TextField field = new TextField("0");
            field.setMinWidth(width / 3);
            root.add(field, i, row);
            fields[i] = field;
        }

        row++;
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            result[0] = false;
            dialog.close();
        });
        cancel.setMinWidth(width / 3);
        root.add(cancel, 2, row);

        Button done = new Button("Done");
        done.setOnAction(event -> {
            result[0] = true;
            for (int i = 0; i < resources.length; i++) {
                int res = 0;
                try {
                    res = Integer.parseInt(fields[i].getText());
                }
                catch (NumberFormatException ignored) {

                }
                resources[i] = res;
            }
            dialog.close();
        });
        done.setMinWidth(width/3);
        root.add(done, 0, row++);
        return dialog;
    }




}
