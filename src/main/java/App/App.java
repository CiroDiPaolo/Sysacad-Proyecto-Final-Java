package App;

import Control.InicioSesion.inicioSesionControl;
import Path.Path;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import static Path.Path.icono;

public class App extends Application {

    /**
     * Metodo que inicia la aplicacion
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {

        //Carga archivo fxml
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Path.inicioSesion));
        //Representa un nodo
        Parent root = fxmlLoader.load();
        //Representa una escena
        Scene scene = new Scene(root);
        //Setea el titulo de la ventana
        stage.setTitle("Sysacad Programación");

        //Maximiza la ventana
        stage.setMaximized(true);

        //Crea el icono
        Image icon = new Image(getClass().getResourceAsStream(icono));

        //Setea el icono a la ventana
        stage.getIcons().add(icon);

        //Le pasa el control del stage a la clase inicioSesionControl
        inicioSesionControl controller = fxmlLoader.getController();
        controller.setStage(stage);

        //Setea la escena y la muestra
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

}