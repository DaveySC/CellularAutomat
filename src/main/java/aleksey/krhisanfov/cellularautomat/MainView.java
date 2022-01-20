package aleksey.krhisanfov.cellularautomat;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Canvas canvas;
    private Button stepButton;
    private Affine affine;

    private Simulation simulation;

    private int drawMode = 1;

    public MainView() {
        this.stepButton = new Button("step");
        this.canvas = new Canvas(400, 400);
        this.affine = new Affine();
        affine.appendScale(400 / 10f, 400 / 10f);

        this.simulation = new Simulation(10, 10);
        this.getChildren().addAll(stepButton, canvas);

        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.setOnKeyPressed(this::keyPressed);


        stepButton.setOnAction(actionEvent -> {
            this.simulation.step();
            this.draw();
        });
    }

    private void keyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = 1;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = 0;
        }
    }


    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        try {
            Point2D point =  this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) point.getX();
            int simY = (int) point.getY();

            System.out.println(simX + " " + simY);

            this.simulation.setState(simY, simX, drawMode);

            draw();

        } catch (Exception exp) {
            System.out.println("couldn't reverse transform");
        }


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
