package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Dashboard {

    public void show(Stage stage) {

        Label title = new Label("Migrant Worker Verification System");
        title.setFont(new Font(22));

        Button workerBtn = new Button("Register Migrant Worker");
        Button landlordBtn = new Button("Register Landlord");

        workerBtn.setPrefWidth(300);
        landlordBtn.setPrefWidth(300);

        workerBtn.setOnAction(e -> new WorkerForm().show());
        landlordBtn.setOnAction(e -> new LandlordForm().show());

        VBox layout = new VBox(20, title, workerBtn, landlordBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Admin Dashboard");
        stage.show();
    }
}
