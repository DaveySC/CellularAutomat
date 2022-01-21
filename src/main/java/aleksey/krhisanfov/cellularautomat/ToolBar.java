package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ToolBar extends javafx.scene.control.ToolBar {
    MainView mainView;
    ToolBar (MainView mainView) {
        this.mainView = mainView;
        Button start = new Button("start");
        Button stop = new Button("stop");
        Button step = new Button("step");
        Button draw = new Button("draw");
        Button erase = new Button("erase");
        Button clear = new Button("clear");
        Button size = new Button("size");


        start.setOnAction(this::start);
        stop.setOnAction(this::stop);
        step.setOnAction(this::step);
        draw.setOnAction(this::draw);
        erase.setOnAction(this::erase);
        clear.setOnAction(this::clear);
        size.setOnAction(this::size);

        this.getItems().addAll(start, stop, step, draw, erase, clear, size);
    }



    private void start(ActionEvent actionEvent) {
        this.mainView.startSimulation();


    }
    private void stop(ActionEvent actionEvent) {
        this.mainView.stopSimulation();
    }

    private void step(ActionEvent actionEvent) {
        this.mainView.simulation.step();
        this.mainView.draw();
        stop(null);
    }

    private void draw(ActionEvent actionEvent) {
        this.mainView.setDrawMode(1);
        stop(null);
    }

    private void erase(ActionEvent actionEvent) {
        this.mainView.setDrawMode(0);
        stop(null);
    }

    private void clear(ActionEvent actionEvent) {
        this.mainView.simulation.clearBoard();
        this.mainView.draw();
        stop(null);
    }

    private void size(ActionEvent actionEvent) {
        ModalView modalView = new ModalView();
        modalView.openModalWindow(this.mainView);
        stop(null);
    }


}
