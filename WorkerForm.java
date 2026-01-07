package app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WorkerForm {

    public void show() {

        Stage stage = new Stage();
        stage.setTitle("Migrant Worker Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();
        TextField id = new TextField();
        TextField guardian = new TextField();
        TextField district = new TextField();
        TextField stateFrom = new TextField();
        TextField stateTo = new TextField();
        TextField job = new TextField();
        TextField skills = new TextField();

        Button submit = new Button("Register Worker");

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(name, 1, 0);

        grid.add(new Label("ID / Domicile No:"), 0, 1);
        grid.add(id, 1, 1);

        grid.add(new Label("Guardian Name:"), 0, 2);
        grid.add(guardian, 1, 2);

        grid.add(new Label("District:"), 0, 3);
        grid.add(district, 1, 3);

        grid.add(new Label("Migrant State (From):"), 0, 4);
        grid.add(stateFrom, 1, 4);

        grid.add(new Label("Destination State:"), 0, 5);
        grid.add(stateTo, 1, 5);

        grid.add(new Label("Job Title:"), 0, 6);
        grid.add(job, 1, 6);

        grid.add(new Label("Skills (comma separated):"), 0, 7);
        grid.add(skills, 1, 7);

        grid.add(submit, 1, 9);

        submit.setOnAction(e -> {
            WorkerService.registerWorker(
                    name.getText(),
                    id.getText(),
                    guardian.getText(),
                    district.getText(),
                    stateFrom.getText(),
                    stateTo.getText(),
                    job.getText(),
                    skills.getText()
            );
            stage.close();
        });

        stage.setScene(new Scene(grid, 500, 450));
        stage.show();
    }
}
