package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
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

import static Path.Path.menuPrincipalAlumnos;
import static Path.Path.pathCarreras;

public class inscripcionCursadaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TableColumn<String, String> colAnio;

    @FXML
    private TableColumn<String, Void> colBotones;

    @FXML
    private TableColumn<String, String> colComision;

    @FXML
    private TableColumn<String, String> colMateria;

    @FXML
    private TableView<String> tableTablaMaterias;

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

                HashMap<String, Materia> materias = (manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera())).getMaterias();

                for (int i = 0; i < Data.getEstudiante().getMaterias().size(); i++) {

                    if (materias.containsKey(Data.getEstudiante().getMaterias().get(i).getCodigoMateria())) {

                        tableTablaMaterias.getItems().add(materias.get(Data.getEstudiante().getMaterias().get(i).getCodigoMateria()).getNombre());

                    }

                }

                colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

                colBotones.setCellFactory(col -> new TableCell<String, Void>() {
                    private final Button inscribirseButton = new Button("Inscribirse");

                    {
                        inscribirseButton.setOnAction(event -> {
                            String materia = getTableView().getItems().get(getIndex());
                            // Lógica para inscribirse en la materia
                            inscribirseEnMateria(materia);
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

            } catch (CamposVaciosException e) {
                e.printStackTrace();
            } catch (DatosIncorrectosException e) {
                e.printStackTrace();
            }

        });

    }

    private void inscribirseEnMateria(String materia) {

        System.out.println("Inscribirse en: " + materia);

    }

}
