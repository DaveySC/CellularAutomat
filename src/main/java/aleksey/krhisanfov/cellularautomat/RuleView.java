package aleksey.krhisanfov.cellularautomat;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RuleView extends VBox {

    MainView mainView;
    TextField textField;

    public RuleView() {

        Label label = new Label("Rule");
        this.textField = new TextField();
        this.textField.setPromptText("Enter rule");

        HBox hBox = new HBox(label, this.textField);
        hBox.setMargin(label, new Insets(10));
        this.setAlignment(Pos.CENTER);
        Button randomButton = new Button("Generate Random rule");
        Button setRule = new Button("Set rule");
        VBox vBox = new VBox(setRule, randomButton);
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        setRule.setOnAction(this::set);
        vBox.setMargin(randomButton, new Insets(10));

        randomButton.setOnAction(this::random);
        this.getChildren().addAll(hBox, vBox);
        this.setMargin(hBox, new Insets(10));
        this.setMargin(vBox, new Insets(10));
    }

    private void random(ActionEvent actionEvent) {
        int random = (int)((Math.random() * 1000) % 256);
        this.textField.setText(String.valueOf(random));
    }

    private void set(ActionEvent actionEvent) {
        if (this.textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните поле");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        int random = Integer.parseInt(this.textField.getText());
        if (random > 255 || random < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Недопустимое правило");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        this.mainView.simulation1D.setRule(random);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }


    public void openRuleWindow(MainView mainView) {
        this.mainView = mainView;
        Scene scene = new Scene(this, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

}
