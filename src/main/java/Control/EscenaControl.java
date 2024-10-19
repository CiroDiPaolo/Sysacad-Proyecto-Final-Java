package Control;

import Control.InicioSesion.inicioSesionControl;
import Path.Path;
import Control.Estudiante.menuPrincipalEstudianteControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import static Path.Path.icono;

public final class EscenaControl {

    // Cambiar de ventana, recibiendo el Path por parametros
    public void cambiarVentana(String fxml,String tituloVentana){

        // Abrir la nueva ventana
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(tituloVentana);
            newStage.setMaximized(true);
            newStage.show();

            Image icon = new Image(getClass().getResourceAsStream(icono));
            newStage.getIcons().add(icon);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Cambiar de contenido de la ventana, recibiendo el Path por parametros y el Stage
    public FXMLLoader cambiarEscena(String fxml, Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

            return loader;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
