package Control.Estudiante;

import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import Modelo.Comision;
import Path.Path;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import Control.EscenaControl;
import static Path.Path.pathComisiones;

public class inscripcionMateriaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Comision, String> colComision;

    @FXML
    private TableColumn<Comision, String> colMateria;

    @FXML
    private TableColumn<Comision, String> colDescripcion;

    @FXML
    private TableColumn<Comision, Void> colBotones;

    @FXML
    private TableView<Comision> tableTablaMaterias;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.inscripcionCursada, stage, "Inscripcion Cursada");

    }


    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<Comision> comisiones = manejoArchivosComisiones.obtenerComisionesDeUnaMateria(pathComisiones + manejoArchivosComisiones.generarNombreArchivoComision(Data.getEstudiante().getCodigoCarrera(), LocalDate.now().getYear()), Data.getAux2());

            colComision.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            colMateria.setCellValueFactory(cellData -> new SimpleStringProperty(Data.getAux()));
            colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));

            colBotones.setCellFactory(col -> new TableCell<Comision, Void>() {
                private final Button inscribirseButton = new Button("Inscribirse");

                {
                    inscribirseButton.setOnAction(event -> {
                        Comision comision = getTableView().getItems().get(getIndex());
                        Data data = new Data();
                        data.setComision(comision);
                        EscenaControl escena = new EscenaControl();
                        escena.cambiarEscena(Path.inscripcionCompletada, stage, "Inscripcion Completada");
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

            tableTablaMaterias.getItems().addAll(comisiones);
        });
    }

}
