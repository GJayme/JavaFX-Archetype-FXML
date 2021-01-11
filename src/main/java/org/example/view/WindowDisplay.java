package org.example.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.controller.PanelDisplayController;

import java.io.IOException;

public class WindowDisplay {

    public void show(int numberOfChars) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent graph = fxmlLoader.load(getClass().getResource("panelDisplay.fxml").openStream());
        PanelDisplayController controller = fxmlLoader.getController();
        controller.setData(numberOfChars);

        Scene scene = new Scene(graph, 300, 200);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
