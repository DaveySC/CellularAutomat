package aleksey.krhisanfov.cellularautomat;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {
    public ResizableCanvas(double v, double v1) {
        super(v, v1);
    }

    public boolean isResizable() {
        return true;
    }
}