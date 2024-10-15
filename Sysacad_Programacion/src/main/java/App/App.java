package App;

import Modelo.Comision;
import Modelo.Materia;
import Modelo.Profesor;
import Modelo.Turno;
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

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Path.inicioSesion));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Sysacad Programación");

        Image icon = new Image(getClass().getResourceAsStream(icono));

        stage.getIcons().add(icon);

        Control.inicioSesionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();
/*
        Comision comision1 = new Comision("1", Turno.Mañana);

        Profesor archuby = new Profesor("Caro","1234","oda");

        Materia programacion1 = new Materia("Programacion 1",archuby,"Laboratorio 5");

        Profesor dani = new Profesor("Dani","123","ajio");

        Materia bdd = new Materia("Base de datos 1",dani,"Laboratorio 6");

        programacion1.agregarHorarios("Lunes 13-15");

        bdd.agregarHorarios("Lunes 10-12");

        comision1.agregarMateria(programacion1);

        comision1.agregarMateria(bdd);

        System.out.println(comision1.toString());
*/
    }
}