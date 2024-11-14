package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoMateria;
import Path.Path;
import Modelo.Carrera;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import java.util.Collection;
import java.util.HashSet;
import static Path.Path.menuPrincipalAlumnos;

public class correlatividadParaCursarEstudianteControl {

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

    private Stage stage;

    @FXML
    private TableView<Materia> tableView;

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();

            try {
                Carrera carrera = manejoArchivosCarrera.retornarCarrera(Path.pathCarreras, Data.getEstudiante().getCodigoCarrera());

                Collection<Materia> materias = carrera.getMaterias().values();

                ObservableList<Materia> materiasList = FXCollections.observableArrayList(materias);

                colAnio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
                colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

                colCorrelatividad.setCellValueFactory(cellData -> {
                    HashSet<String> correlativas = cellData.getValue().getCodigoCorrelativasCursado();

                    for(int i = 0 ; i < Data.getEstudiante().getMaterias().size(); i++){

                        if(cellData.getValue().getId().equals(Data.getEstudiante().getMaterias().get(i).getCodigoMateria())){

                            if(Data.getEstudiante().getMaterias().get(i).getEstado().equals(EstadoMateria.APROBADA)){

                                return new SimpleStringProperty("Aprobada");

                            }

                        }

                    }

                    if (correlativas == null || correlativas.isEmpty()) {
                        return new SimpleStringProperty("Puede cursar");
                    } else {
                        return new SimpleStringProperty(String.join(", ", manejoArchivosCarrera.obtenerNombresMaterias(Path.pathCarreras, correlativas)));
                    }
                });

                // Configuración de la columna Plan
                colPlan.setCellValueFactory(cellData -> {
                    try {
                        return new SimpleStringProperty((manejoArchivosCarrera.retornarCarrera(Path.pathCarreras, Data.getEstudiante().getCodigoCarrera())).getPlan());
                    } catch (CamposVaciosException e) {
                        throw new RuntimeException(e);
                    } catch (DatosIncorrectosException e) {
                        throw new RuntimeException(e);
                    }
                });

                tableView.setItems(materiasList);

            } catch (CamposVaciosException | DatosIncorrectosException e) {
                throw new RuntimeException("Error al cargar las materias: " + e.getMessage(), e);
            }
        });
    }


    /**
     * Método que se ejecuta al hacer clic en el botón 'Volver'
     */
    @FXML
    void clickBtnVolver(ActionEvent event) {
        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");
    }
}
