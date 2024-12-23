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

    /**
     * Metodo que se encarga de cambiar de ventana
     * @param fxml
     * @param tituloVentana
     */
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

    /**
     * Metodo que se encarga de cambiar de escena
     * @param fxml
     * @param stage
     * @param tituloVentana
     * @return
     */
    public FXMLLoader cambiarEscena(String fxml, Stage stage, String tituloVentana) {
        {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle(tituloVentana);
                stage.setMaximized(true); // Asegura que la ventana se maximice
                stage.show();

                return loader;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}
