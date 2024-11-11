package Control.Estudiante;

import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMateria;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Path.Path.pathCarreras;

public class inscripcionMateriaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> colBotones;

    @FXML
    private TableColumn<?, ?> colComision;

    @FXML
    private TableColumn<?, ?> colDescripcion;

    @FXML
    private TableColumn<?, ?> colMateria;

    @FXML
    private TableView<?> tableTablaMaterias;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();


        });
    }

}
