package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;

public class ModalView {
    private Button okButton;
    private TextField rowsField;
    private TextField columnsField;
    private Label invalidInput;

    MainView mainView;

    public  void openModalWindow (MainView mainView) {
        this.mainView = mainView;
        GridPane gridPane = new GridPane();
        Label rowsLabel = new Label("Rows");
        Label columnsLabel = new Label("Columns");
        invalidInput = new Label("Invalid Input");
        invalidInput.setTextFill(Color.RED);
        invalidInput.setVisible(false);

        okButton = new Button("Confirm");
        okButton.setOnAction(this::close);
        rowsField = new TextField();
        columnsField = new TextField();



        gridPane.add(rowsLabel, 0,0);
        gridPane.add(columnsLabel, 0, 1);
        gridPane.add(rowsField, 1,0 );
        gridPane.add(columnsField, 1, 1);
        gridPane.add(okButton, 1, 2);
        gridPane.add(invalidInput, 1,3);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(6);


        Scene scene = new Scene(gridPane, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    private void close(ActionEvent actionEvent) {
        Stage stage = (Stage)  okButton.getScene().getWindow();
        String textRows =  rowsField.getText();
        String  textColumns = columnsField.getText();
        if (validText(textRows) && validText(textColumns)) {
            int rows = Integer.parseInt(textRows);
            int columns = Integer.parseInt(textColumns);

             if (rows <= 0 ) {
                  invalidInput.setText("Min Value for rows : 1");
                 invalidInput.setVisible(true);
                 return;
             }
             if (rows >= 1000) {
              invalidInput.setText("Max Value for rows : 1000");
              invalidInput.setVisible(true);
              return;
             }

             if (columns <= 0 ) {
                invalidInput.setText("Min Value for columns : 1");
                invalidInput.setVisible(true);
                return;
             }

             if (columns >= 1000) {
                invalidInput.setText("Max Value for columns : 1000");
                invalidInput.setVisible(true);
                return;
             }
             mainView.setSizeOfBoard(columns, rows);
             stage.close();
        } else {
            invalidInput.setVisible(true);
            System.out.println("Invalid input");
        }



    }

    boolean validText(String text) {
        return  text != "" && text.matches("[0-9]*");
    }


}
