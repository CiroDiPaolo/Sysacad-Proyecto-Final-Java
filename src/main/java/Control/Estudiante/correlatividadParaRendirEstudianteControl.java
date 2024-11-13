package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Carrera;
import Modelo.EstadoMateria;
import Modelo.Materia;
import Path.Path;
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
    private Label tctMenuPrincipal;

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

            try {
                // Carga la carrera del estudiante y obtiene las materias
                Carrera carrera = manejoArchivosCarrera.retornarCarrera(Path.pathCarreras, Data.getEstudiante().getCodigoCarrera());

                Collection<Materia> materias = carrera.getMaterias().values();

                // Convierte la colección de materias a una lista observable para la tabla
                ObservableList<Materia> materiasList = FXCollections.observableArrayList(materias);

                // Configuración de las columnas utilizando SimpleStringProperty
                colAnio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
                colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

                // Si la materia tiene correlativas, las mostramos, si no, mostramos "Puede cursar"
                colCorrelatividad.setCellValueFactory(cellData -> {
                    HashSet<String> correlativasRendir = cellData.getValue().getCodigoCorrelativasRendir();

                    for(int i = 0 ; i < Data.getEstudiante().getMaterias().size(); i++){

                        if(cellData.getValue().getId().equals(Data.getEstudiante().getMaterias().get(i).getCodigoMateria())){

                            if(Data.getEstudiante().getMaterias().get(i).getEstado().equals(EstadoMateria.APROBADA)){

                                return new SimpleStringProperty("Aprobada");

                            }

                        }

                    }

                    if (correlativasRendir == null || correlativasRendir.isEmpty()) {
                        return new SimpleStringProperty("Puede cursar");
                    } else {
                        // Obtenemos los nombres de las materias correlativas y las unimos con coma
                        return new SimpleStringProperty(String.join(", ", manejoArchivosCarrera.obtenerNombresMaterias(Path.pathCarreras, correlativasRendir)));
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

                // Asigna la lista de materias a la TableView
                tableView.setItems(materiasList);

            } catch (CamposVaciosException | DatosIncorrectosException e) {
                // Manejo de excepciones en caso de error
                throw new RuntimeException("Error al cargar las materias: " + e.getMessage(), e);
            }
        });
    }


}
