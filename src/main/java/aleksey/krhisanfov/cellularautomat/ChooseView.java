package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseView extends GridPane {
    MainView mainView;
    Button dimensional1Simulation;
    Button dimensional2Simulation;

    public ChooseView() {
        this.dimensional1Simulation = new Button("One-dimensional Simulation");
        this.dimensional2Simulation = new Button("Two-dimensional Simulation");

        this.setAlignment(Pos.CENTER);

        dimensional1Simulation.setOnAction(this::openD1);
        dimensional2Simulation.setOnAction(this::openD2);

        this.add(dimensional1Simulation, 0,0);
        this.add(dimensional2Simulation, 0, 1);


    }

    private void openD1(ActionEvent actionEvent) {
        mainView.setSimulationType(SimulationType.D1);
        mainView.draw();
        closeWindow();
    }

    private void openD2(ActionEvent actionEvent) {
        mainView.setSimulationType(SimulationType.D2);
        mainView.draw();
        closeWindow();
    }

    public void openChooseWindow(MainView mainView) {
        this.mainView = mainView;
        Scene scene = new Scene(this, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    void closeWindow() {
        Stage stage = (Stage)dimensional1Simulation.getScene().getWindow();
        stage.close();
    }
}
