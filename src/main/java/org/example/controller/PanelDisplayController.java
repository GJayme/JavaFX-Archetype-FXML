package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PanelDisplayController {

    @FXML
    private Button btnOk;

    @FXML
    private Label lbNumberToDisplay;

    public void setData(int number){
        lbNumberToDisplay.setText(String.valueOf(number));
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }
}
