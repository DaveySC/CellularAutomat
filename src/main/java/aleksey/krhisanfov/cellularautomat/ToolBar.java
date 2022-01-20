package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ToolBar extends javafx.scene.control.ToolBar {
    MainView mainView;
    ToolBar (MainView mainView) {
        this.mainView = mainView;
        Button step = new Button("step");
        Button erase = new Button("erase");
        Button draw = new Button("draw");

        step.setOnAction(this::step);
        erase.setOnAction(this::erase);
        draw.setOnAction(this::draw);

        this.getItems().addAll(step, draw, erase);
    }


    private void step(ActionEvent actionEvent) {
        this.mainView.simulation.step();
        this.mainView.draw();
    }
    private void erase(ActionEvent actionEvent) {
        this.mainView.setDrawMode(0);
    }
    private void draw(ActionEvent actionEvent) {
        this.mainView.setDrawMode(1);
    }
}
