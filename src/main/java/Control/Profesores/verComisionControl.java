package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ControlArchivos.manejoArchivosEstudiante;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class verComisionControl {

    @FXML
    private Button btnExcel;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Estudiante, String> colAlumno;

    @FXML
    private TableColumn<Estudiante, String> colEstado;

    @FXML
    private TableColumn<Estudiante, String> colLegajo;

    @FXML
    private TableColumn<String, String> colParcial1;

    @FXML
    private TableColumn<String, String> colParcial2;

    @FXML
    private TableView<Estudiante> tableTablaAlumnos;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    @FXML
    void clickBtnExcel(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.gestionarComisionProfesor, stage, "Gestionar comision");

    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<Estudiante> estudiantes = manejoArchivosComisiones.obtenerEstudiantesDeUnaComision(Path.fileNameAlumnos, Data.getComision().getId());

            ObservableList<Estudiante> estudiantesList = FXCollections.observableArrayList(estudiantes);

            colAlumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            colLegajo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLegajo()));

           for (int i = 0; i < estudiantes.size(); i++) {

                Estudiante estudiante = estudiantes.get(i);
                ArrayList<String> parciales = manejoArchivosEstudiante.filtrarParcialesPorMateria(estudiante.obtenerParcialesRendidos(), Data.getComision().getCodigoMateria());

                colParcial1.setCellValueFactory(cellData -> new SimpleStringProperty(parciales.size() > 0 ? parciales.get(0) : "-"));
                colParcial2.setCellValueFactory(cellData -> new SimpleStringProperty(parciales.size() > 1 ? parciales.get(1) : "-"));
           }



           tableTablaAlumnos.setItems(estudiantesList);

        });
    }

}
