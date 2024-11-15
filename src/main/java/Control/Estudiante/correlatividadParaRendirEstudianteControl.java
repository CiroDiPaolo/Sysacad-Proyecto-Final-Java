package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Carrera;
import Modelo.EstadoAlumnoMateria;
import Modelo.EstadoMateria;
import Modelo.Materia;
import Path.Path;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashSet;

import static Path.Path.menuPrincipalAlumnos;

public class correlatividadParaRendirEstudianteControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Materia, String> colAnio;

    @FXML
    private TableColumn<Materia, String> colCorrelatividad;

    @FXML
    private TableColumn<Materia, String> colMateria;

    @FXML
    private TableColumn<Materia, String> colPlan;

    @FXML
    private TableView<Materia> tableView;

    private Stage stage;

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
        });
        try {
            Carrera carrera = manejoArchivosCarrera.retornarCarrera(Path.pathCarreras, Data.getEstudiante().getCodigoCarrera());

            Collection<Materia> materias = carrera.getMaterias().values();

            ObservableList<Materia> materiasList = tableView.getItems();
            materiasList.clear();
            materiasList.addAll(materias);

            colAnio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
            colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

            colCorrelatividad.setCellValueFactory(cellData -> {
                HashSet<String> correlativasRendir = cellData.getValue().getCodigoCorrelativasRendir();

                for (EstadoAlumnoMateria estado : Data.getEstudiante().getMaterias()) {
                    if (cellData.getValue().getId().equals(estado.getCodigoMateria())) {
                        if (estado.getEstado().equals(EstadoMateria.APROBADA)) {
                            return new SimpleStringProperty("Aprobada");
                        }
                    }
                }

                if (correlativasRendir == null || correlativasRendir.isEmpty()) {
                    return new SimpleStringProperty("Puede cursar");
                } else {
                    String correlativasString = String.join(", ", manejoArchivosCarrera.obtenerNombresMaterias(Path.pathCarreras, correlativasRendir));
                    return new SimpleStringProperty(correlativasString);
                }
            });

            colPlan.setCellValueFactory(cellData -> {
                try {
                    return new SimpleStringProperty(manejoArchivosCarrera.retornarCarrera(Path.pathCarreras, Data.getEstudiante().getCodigoCarrera()).getPlan());
                } catch (CamposVaciosException | DatosIncorrectosException e) {
                    e.printStackTrace();
                }
                return null;
            });

            tableView.setItems(materiasList);

        } catch (CamposVaciosException | DatosIncorrectosException e) {
            e.printStackTrace();
        }
    }


}
