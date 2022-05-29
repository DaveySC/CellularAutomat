package aleksey.krhisanfov.cellularautomat;

import javafx.animation.AnimationTimer;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;


enum SimulationType {
    D1,
    COLORED,
    D2
}

public class MainView extends VBox {
    private SimulationType simulationType;
    private InfoBar infoBar;
    private Canvas canvas;
    private Affine affine;
    private int width = 10;
    private int height = 10;

    public Simulation1D simulation1D;
    Simulation2D simulation2D;


    private int drawMode = 1;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setStyle("-fx-background-color:red;");
        this.affine = new Affine();
        affine.appendScale(400 / (double)width, 400 / (double)height);
        this.simulation1D = new Simulation1D(width, height);
        this.simulation2D = null;
        this.simulationType = SimulationType.D1;
        this.simulation1D.setRule(25);
        ToolBar toolBar = new ToolBar(this); // новый тулбар для каждой симуляции
        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);


        this.getChildren().addAll(toolBar, canvas);

        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        //this.canvas.setOnMouseMoved(this::mouseEnteredNewSquare);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setMaxWidth(Double.MAX_VALUE);
        this.heightProperty().addListener(this::resizeCanvas);
        this.widthProperty().addListener(this::resizeCanvas);

    }

    private void resizeCanvas(Observable observable) {
        double widthHBox = this.getWidth();
        double heightHBox = this.getHeight() * 0.956;

        this.canvas.setWidth(widthHBox);
        this.canvas.setHeight(heightHBox);
        this.affine = new Affine();
        affine.appendScale(this.canvas.getWidth() / (double)this.width, this.canvas.getHeight() / (double)this.height);
        draw();

    }

    private void mouseEnteredNewSquare(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        try {
            Point2D point =  this.affine.inverseTransform(mouseX, mouseY);
            int simX = (int) point.getX();
            int simY = (int) point.getY();
            this.infoBar.updatePosition("Mouse Position: " + simX + " " + simY);

        } catch (Exception exp) {
            System.out.println("couldn't reverse transform in mouseEnteredNewSquare ");
        }


    }

    public void setSizeOfBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.simulation1D.setSizeOfBoard(width, height);
        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / (double)width, this.canvas.getHeight() / (double)height);
        draw();
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        try {
            Point2D point =  this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) point.getX();
            int simY = (int) point.getY();

            System.out.println(simX + " " + simY);

            if (this.simulation1D != null) {
                this.simulation1D.setState(simY, simX, drawMode);
            }

            if (this.simulation2D != null) {
                this.simulation2D.setState(simY, simX, drawMode);
            }

            draw();

        } catch (Exception exp) {
            System.out.println("couldn't reverse transform");
        }

    }

    public void draw() {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);

        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());


        graphicsContext.setFill(Color.BLACK);

        int maxY = 0, maxX = 0;
        if (this.simulation2D != null) {
            maxY = this.simulation2D.getHeight();
            maxX = this.simulation2D.getWidth();
        }


        if (this.simulation1D != null) {
            maxY = this.simulation1D.getHeight();
            maxX = this.simulation1D.getWidth();
        }


        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (this.simulation2D != null && this.simulation2D.getState(y,x) == 1) {
                    graphicsContext.fillRect(x,y,1,1);
                }

                if (this.simulation1D != null && this.simulation1D.isAlive(y,x) == 1) {
                    graphicsContext.fillRect(x,y,1,1);
                }

                //colored simulation
            }
        }
        graphicsContext.setStroke(Color.GREY);
        graphicsContext.setLineWidth(0.05);


        for (int y = 0; y <= maxY; y++) {
            graphicsContext.strokeLine(0, y, width, y);
        }
        for (int x = 0; x <= maxX; x++) {
            graphicsContext.strokeLine(x,0, x, height);
        }
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
           if (simulation1D != null) {
               simulation1D.step();
               draw();
           }

           if (simulation2D != null) {
               simulation2D.step();
               draw();
           }

        }
    };

    public void startSimulation() {
        animationTimer.start();
    }

    public void stopSimulation() {
        animationTimer.stop();
    }


    public void setSimulationType(SimulationType simulationType) {
        this.simulationType = simulationType;

        if (simulationType == SimulationType.D1) {

            this.simulation2D = null;
            this.simulation1D = new Simulation1D(width, height);
            return;
        }

        if (simulationType == SimulationType.D2) {
            this.simulation1D = null;
            this.simulation2D = new Simulation2D(width, height);

        }
    }

    public SimulationType getSimulationType() {
        return simulationType;
    }

    public Simulation1D get1D() {
        return this.simulation1D;
    }

    public Simulation2D get2D() {
        return this.simulation2D;
    }

}
