package Control;

import App.App;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class controlMenuPrincipal {


    @FXML
    private Label tctMenuPrincipal;

    public static void mostrarMenuPrincipal(){

        try {

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Path.menuPrincipal));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Sysacad Programación");

            Control.inicioSesionController controller = fxmlLoader.getController();
            controller.setStage(stage);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
