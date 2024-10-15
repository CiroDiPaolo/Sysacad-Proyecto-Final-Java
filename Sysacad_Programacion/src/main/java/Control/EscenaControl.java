package Control;

import Path.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import static Path.Path.icono;

public class EscenaControl {

    // Cambiar de ventana, recibiendo el Path por parametros
    public void cambiarVentana(String fxml,String titloVentana){

        // Abrir la nueva ventana
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(titloVentana);
            newStage.show();

            Image icon = new Image(getClass().getResourceAsStream(Path.icono));

            newStage.getIcons().add(icon);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Cambiar de contenido de la ventana, recibiendo el Path por parametros y el Stage
    public void cambiarEscena(String fxml, Stage stage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }



}
