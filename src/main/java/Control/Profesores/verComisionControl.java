package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosComisiones;
import Excepciones.DatosIncorrectosException;
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
    private TableColumn<Estudiante, String> colParcial1;

    @FXML
    private TableColumn<Estudiante, String> colParcial2;

    @FXML
    private TableView<Estudiante> tableTablaAlumnos;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    @FXML
    void clickBtnExcel(ActionEvent event) throws DatosIncorrectosException {

        ArrayList<Estudiante> estudiantes = manejoArchivosComisiones.obtenerEstudiantesDeUnaComision(Path.fileNameAlumnos, Data.getComision().getId());
        String[] nombresAlumnos;

        if (estudiantes.size() > 0) {
            nombresAlumnos = new String[estudiantes.size()];
            for (int i = 0; i < estudiantes.size(); i++) {
                nombresAlumnos[i] = estudiantes.get(i).getApellido() + " " + estudiantes.get(i).getNombre();
            }
        } else {

            throw new DatosIncorrectosException("No hay alumnos en la comisión");

        }

        manejoArchivos.guardarAlumnosExcel(stage, nombresAlumnos);

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

            // Configuramos las columnas
            colAlumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido().concat(" ").concat(cellData.getValue().getNombre())));
            colLegajo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLegajo()));

            // Configuramos colParcial1 y colParcial2 para cada estudiante, evaluando los parciales rendidos
            colParcial1.setCellValueFactory(cellData -> {
                Estudiante estudiante = cellData.getValue();
                ArrayList<String> parciales = manejoArchivosEstudiante.filtrarParcialesPorMateria(estudiante.obtenerParcialesRendidos(), Data.getComision().getCodigoMateria());
                return new SimpleStringProperty(parciales.size() > 0 && !parciales.get(0).equals("0") ? parciales.get(0) : "-");
            });

            colParcial2.setCellValueFactory(cellData -> {
                Estudiante estudiante = cellData.getValue();
                ArrayList<String> parciales = manejoArchivosEstudiante.filtrarParcialesPorMateria(estudiante.obtenerParcialesRendidos(), Data.getComision().getCodigoMateria());
                return new SimpleStringProperty(parciales.size() > 1 && !parciales.get(1).equals("0") ? parciales.get(1) : "-");
            });

            // Configuramos colEstado solo si cumple la condición del if
            colEstado.setCellValueFactory(cellData -> {
                Estudiante estudiante = cellData.getValue();
                // Buscamos la materia que coincide con el código de la comisión y obtenemos su estado
                return estudiante.getMaterias().stream()
                        .filter(materia -> materia.getCodigoMateria().equals(Data.getComision().getCodigoMateria()))
                        .findFirst()
                        .map(materia -> new SimpleStringProperty(materia.getEstado().toString()))
                        .orElse(new SimpleStringProperty("-"));
            });

            // Cargamos la lista de estudiantes en la tabla
            tableTablaAlumnos.setItems(estudiantesList);

        });
    }

}
