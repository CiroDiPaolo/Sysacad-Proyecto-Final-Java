package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Carrera;
import Modelo.Materia;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static Path.Path.*;

public class materiasDelPlanEstudianteControl {

    private Stage stage;

    @FXML
    private TableColumn<Materia, String> anio;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Materia, String> dic;

    @FXML
    private TableColumn<Materia, String> materia;

    @FXML
    private TableColumn<Materia, String> seCursa;

    @FXML
    private TableColumn<Materia, String> seRinde;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TableView<Materia> tableMaterias; // La tabla donde se mostrarán las materias

    /**
     * Metodo que se ejecuta al clickear el boton volver
     * @param event
     */
    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        // Obtener las materias del estudiante. Aquí deberías reemplazarlo con la lógica
        // correspondiente para obtener las materias del plan de estudio del estudiante

            try {

                 Carrera carrera = manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera());

                Collection<Materia> materias = carrera.getMaterias().values();

                ObservableList<Materia> materiasList = FXCollections.observableArrayList(materias);

                 // Configuración de las columnas de la tabla
                anio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
                dic.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCuatrimestre()));
                materia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                seCursa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isSeCursa() ? "Sí" : "No"));
                seRinde.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isSeRinde() ? "Sí" : "No"));


                // Asignamos los datos a la tabla
                tableMaterias.setItems(materiasList);

            } catch (CamposVaciosException e) {

                throw new RuntimeException(e);

            } catch (DatosIncorrectosException e) {

                throw new RuntimeException(e);

            }


    });

 }

}