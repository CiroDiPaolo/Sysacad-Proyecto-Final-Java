package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMateria;
import Modelo.Materia;
import Path.Path;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Path.Path.menuPrincipalAlumnos;
import static Path.Path.pathCarreras;

public class inscripcionCursadaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TableColumn<Materia, String> colAnio;

    @FXML
    private TableColumn<Materia, Void> colBotones;

    @FXML
    private TableColumn<Materia, String> colCuatrimestre;

    @FXML
    private TableColumn<Materia, String> colMateria;

    @FXML
    private TableView<Materia> tableTablaMaterias;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            try {

                HashMap<String, Materia> materias = (manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera())).getMaterias();
                ArrayList<String> materiasEstudiante = new ArrayList<>();

                for (EstadoAlumnoMateria materia : Data.getEstudiante().getMaterias()) {
                    materiasEstudiante.add(materia.getCodigoMateria());
                }

                if (materiasEstudiante.isEmpty()) {
                    tableTablaMaterias.getItems().addAll(materias.values());
                } else {
                    for (Map.Entry<String, Materia> entry : materias.entrySet()) {
                        if (!materiasEstudiante.contains(entry.getKey())) {
                            tableTablaMaterias.getItems().add(entry.getValue());
                        }
                    }
                }

                colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                colAnio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAnio())));
                colCuatrimestre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCuatrimestre()));

                colBotones.setCellFactory(col -> new TableCell<Materia, Void>() {
                    private final Button inscribirseButton = new Button("Inscribirse");

                    {
                        inscribirseButton.setOnAction(event -> {
                            Materia materia = getTableView().getItems().get(getIndex());
                            Data.setAux(materia.getNombre());
                            Data.setAux2(materia.getId());

                            EscenaControl escena = new EscenaControl();
                            escena.cambiarEscena(Path.inscripcionMateria, stage, "Inscripcion Materia");

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            inscribirseButton.setMaxWidth(Double.MAX_VALUE);
                            setGraphic(inscribirseButton);
                        }
                    }
                });

            } catch (CamposVaciosException | DatosIncorrectosException e) {
                e.printStackTrace();
            }

        });
    }

}