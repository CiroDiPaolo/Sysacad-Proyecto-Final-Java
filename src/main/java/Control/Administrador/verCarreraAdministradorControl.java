package Control.Administrador;
import Control.EscenaControl;
import Control.InicioSesion.Data;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashSet;

import static Path.Path.configurarCarreraAdministrador;

public class verCarreraAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private TableColumn<Materia, Boolean> colActividad;

    @FXML
    private TableColumn<Materia, String> colAnio;

    @FXML
    private TableColumn<Materia, HashSet<String>> colAprobadas;

    @FXML
    private TableColumn<Materia, String> colCod;

    @FXML
    private TableColumn<Materia, String> colCuatri;

    @FXML
    private TableColumn<Materia, HashSet<String>> colCursadas;

    @FXML
    private TableColumn<Materia, String> colNombre;

    @FXML
    private TableColumn<Materia, Boolean> colSeCursa;

    @FXML
    private TableColumn<Materia, Boolean> colSeRinde;

    @FXML
    private TableView<Materia> tableViewMaterias;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPlan;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ObservableList<Materia> materiaList = FXCollections.observableArrayList(Data.getCarrera().getMaterias().values());
        tableViewMaterias.setItems(materiaList);
        // Configuración de la columna colAprobadas
        colAprobadas.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCodigoCorrelativasRendir()));
        colAprobadas.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(HashSet<String> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.join(", ", item));
                }
            }
        });

        // Configuración de la columna colCursadas
        colCursadas.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCodigoCorrelativasCursado()));
        colCursadas.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(HashSet<String> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.join(", ", item));
                }
            }
        });
        colCod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colAnio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnio()));
        colCuatri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCuatrimestre()));

        colSeCursa.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isSeCursa()));
        colSeCursa.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "No");
                }
            }
        });

        colSeRinde.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isSeRinde()));
        colSeRinde.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "No");
                }
            }
        });

        colActividad.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActividad()));
        colActividad.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Activo" : "Inactivo");
                }
            }
        });

        txtID.setText(Data.getCarrera().getId());
        txtID.setEditable(false);
        txtNombre.setText(Data.getCarrera().getNombre());
        txtNombre.setEditable(false);
        txtPlan.setText(Data.getCarrera().getPlan());
        txtPlan.setEditable(false);
        checkBoxActividad.setSelected(Data.getCarrera().isActividad());
        checkBoxActividad.setDisable(true);
    }
}


