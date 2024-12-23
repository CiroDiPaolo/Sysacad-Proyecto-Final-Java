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
import java.util.Collection;

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
    private TableView<Materia> tableView;

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

            try {

                Carrera carrera = manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera());

                Collection<Materia> materias = carrera.getMaterias().values();

                ObservableList<Materia> materiasList = FXCollections.observableArrayList(materias);

                anio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
                dic.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCuatrimestre()));
                materia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                seCursa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isSeCursa() ? "Sí" : "No"));
                seRinde.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isSeRinde() ? "Sí" : "No"));

                tableView.setItems(materiasList);

            } catch (CamposVaciosException e) {

                throw new RuntimeException(e);

            } catch (DatosIncorrectosException e) {

                throw new RuntimeException(e);

            }

    });

 }

}