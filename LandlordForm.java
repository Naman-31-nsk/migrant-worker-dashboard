package app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LandlordForm {

    public void show() {

        Stage stage = new Stage();
        stage.setTitle("Landlord Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();
        TextField house = new TextField();
        TextField district = new TextField();
        TextField state = new TextField();
        TextField rent = new TextField();

        Button submit = new Button("Register Landlord");

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(name, 1, 0);

        grid.add(new Label("House / Property No:"), 0, 1);
        grid.add(house, 1, 1);

        grid.add(new Label("District:"), 0, 2);
        grid.add(district, 1, 2);

        grid.add(new Label("State:"), 0, 3);
        grid.add(state, 1, 3);

        grid.add(new Label("Monthly Rent:"), 0, 4);
        grid.add(rent, 1, 4);

        grid.add(submit, 1, 6);

        submit.setOnAction(e -> {
            LandlordService.registerLandlord(
                    name.getText(),
                    house.getText(),
                    district.getText(),
                    state.getText(),
                    rent.getText()
            );
            stage.close();
        });

        stage.setScene(new Scene(grid, 450, 350));
        stage.show();
    }
}
