package aleksey.krhisanfov.cellularautomat;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InfoBar extends HBox {
    private Label cursor;
    private Label mode;

    InfoBar() {
        this.cursor = new Label("Mouse Position: 0 0");
        this.mode = new Label("Draw mode: Draw");

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.setPadding(new Insets(5));
        this.getChildren().addAll(mode, spacer, cursor);
    }


    public void updateMode(int mode) {
        if (mode == 1) {
            this.mode.setText("Draw mode: Draw");
        } else {
            this.mode.setText("Draw mode: Erase");
        }
    }

    public void updatePosition(String pos) {
        this.cursor.setText(pos);
    }

}
