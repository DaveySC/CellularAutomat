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

public class MainView extends VBox {

    private InfoBar infoBar;
    private Canvas canvas;
    private Affine affine;
    private int width = 10;
    private int height = 10;

    Simulation2D simulation2D;

    private int drawMode = 1;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setStyle("-fx-background-color:red;");
        this.affine = new Affine();
        affine.appendScale(400 / (double)width, 400 / (double)height);
        this.simulation2D = new Simulation2D(width, height);

        ToolBar toolBar = new ToolBar(this);
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
        double heightHBox = this.getHeight() * 0.928;

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
        this.simulation2D.setSizeOfBoard(width, height);
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

            this.simulation2D.setState(simY, simX, drawMode);

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

        for (int y = 0; y < this.simulation2D.getHeight(); y++) {
            for (int x = 0; x < this.simulation2D.getWidth(); x++) {
                if (this.simulation2D.getState(y,x) == 1) {
                    graphicsContext.fillRect(x,y,1,1);
                }
            }
        }
        graphicsContext.setStroke(Color.GREY);
        graphicsContext.setLineWidth(0.05);
        int tester = this.simulation2D.getHeight();
        for (int y = 0; y <= this.simulation2D.getHeight(); y++) {
            graphicsContext.strokeLine(0, y, width, y);
        }
        for (int x = 0; x <= this.simulation2D.getWidth(); x++) {
            graphicsContext.strokeLine(x,0, x, height);
        }
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            simulation2D.step();
            draw();
        }
    };

    public void startSimulation() {
        animationTimer.start();
    }

    public void stopSimulation() {
        animationTimer.stop();
    }

}
