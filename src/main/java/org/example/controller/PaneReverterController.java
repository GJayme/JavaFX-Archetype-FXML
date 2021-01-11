package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.view.App;
import org.example.view.WindowDisplay;

import java.io.IOException;

public class PaneReverterController {

    @FXML //Garante o binding entre o componente descrito no FXML e o objeto definidido aqui
    private TextField txtInput;

    @FXML
    private Label lbReverted;

    public void revertInput(ActionEvent actionEvent) throws IOException {
        String inputText = txtInput.getText();
        StringBuilder sb = new StringBuilder(inputText);
        String reversed = sb.reverse().toString();
        lbReverted.setText(reversed);
        txtInput.setText("");

//        App.setRoot("panelDisplay");
//        PanelDisplayController controller = (PanelDisplayController) App.getController();

        int numberOfReversedChars = reversed.length();
//        controller.setData(numberOfReversedChars);


        WindowDisplay windowDisplay = new WindowDisplay();
        windowDisplay.show(numberOfReversedChars);
    }
}
