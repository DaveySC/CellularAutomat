package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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
        Button choose = new Button("type of simulation");
        Button rule = new Button("set rule");


        start.setOnAction(this::start);
        stop.setOnAction(this::stop);
        step.setOnAction(this::step);
        draw.setOnAction(this::draw);
        erase.setOnAction(this::erase);
        clear.setOnAction(this::clear);
        size.setOnAction(this::size);
        choose.setOnAction(this::choose);
        rule.setOnAction(this::set);

        this.getItems().addAll(start, stop, step, draw, erase, clear, size, choose, rule);
    }

    private void set(ActionEvent actionEvent) {
        if (this.mainView.get1D() == null) {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Выберите другой режим симуляции");
            alert.showAndWait();
            return;
        }
        RuleView ruleView = new RuleView();
        ruleView.openRuleWindow(this.mainView);
    }


    private void start(ActionEvent actionEvent) {
        this.mainView.startSimulation();


    }
    private void stop(ActionEvent actionEvent) {
        this.mainView.stopSimulation();
    }
//
    private void step(ActionEvent actionEvent) {
        SimulationType simulationType = this.mainView.getSimulationType();

        if (simulationType == SimulationType.D1) {
            this.mainView.simulation1D.step();
        }

        if (simulationType == SimulationType.D2) {
            this.mainView.simulation2D.step();
        }

        if (simulationType == SimulationType.COLORED) {
            //this.mainView.coloredSimulation.step();
        }

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
//
    private void clear(ActionEvent actionEvent) {

        SimulationType simulationType = this.mainView.getSimulationType();

        if (simulationType == SimulationType.D1) {
            this.mainView.simulation1D.clearBoard();
            this.mainView.simulation1D.setCurrentLine(1);
        }

        if (simulationType == SimulationType.D2) {
            this.mainView.simulation2D.clearBoard();
        }



        this.mainView.draw();
        stop(null);
    }

    private void size(ActionEvent actionEvent) {
        ModalView modalView = new ModalView();
        modalView.openModalWindow(this.mainView);
        stop(null);
    }


    private void choose(ActionEvent actionEvent) {
        ChooseView chooseView = new ChooseView();
        chooseView.openChooseWindow(this.mainView);
        stop(null);
    }


}
