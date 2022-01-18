package aleksey.krhisanfov.cellularautomat;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;

public class MainView extends VBox {

    private Canvas canvas;
    private Button stepButton;
    private Affine affine;

    private Simulation simulation;

    public MainView() {
        this.stepButton = new Button("step");
        this.canvas = new Canvas(400, 400);
        this.affine = new Affine();
        affine.appendScale(400 / 10f, 400 / 10f);

        this.simulation = new Simulation(10, 10);
        this.getChildren().addAll(stepButton, canvas);

        this.simulation.setAlive(4,5);
        this.simulation.setAlive(4,6);
        this.simulation.setAlive(4,7);


        stepButton.setOnAction(actionEvent -> {
            this.simulation.step();
            this.draw();
        });
    }


    public void draw() {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);

        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, 450, 450);


        graphicsContext.setFill(Color.BLACK);

        for (int y = 0; y < this.simulation.getHeight(); y++) {
            for (int x = 0; x < this.simulation.getWidth(); x++) {
                if (this.simulation.getState(y,x) == 1) {
                    graphicsContext.fillRect(x,y,1,1);
                }
            }
        }
        graphicsContext.setStroke(Color.GREY);
        graphicsContext.setLineWidth(0.05);
        for (int y = 0; y <= this.simulation.getHeight(); y++) {
            graphicsContext.strokeLine(0, y, 10, y);
        }
        for (int x = 0; x <= this.simulation.getWidth(); x++) {
            graphicsContext.strokeLine(x,0, x, 10);
        }
    }



}
