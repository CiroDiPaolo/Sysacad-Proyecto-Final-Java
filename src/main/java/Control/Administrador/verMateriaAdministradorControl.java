package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Modelo.Carrera;
import Modelo.Materia;
import Modelo.MateriaFX;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static Path.Path.configurarMateriasAdministrador;
import static Path.Path.pathCarreras;

public class verMateriaAdministradorControl {

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxSeCursa;

    @FXML
    private CheckBox checkBoxSeRinde;

    @FXML
    private ChoiceBox<String> choiceboxMaterias;

    @FXML
    private TableColumn<MateriaFX, String> colCodigo;

    @FXML
    private TableColumn<MateriaFX, String> colNombre;

    @FXML
    private TableColumn<MateriaFX, Boolean> colSeCursa;

    @FXML
    private TableColumn<MateriaFX, Boolean> colSeRinde;

    @FXML
    private Rectangle lista;

    @FXML
    private TableView<MateriaFX> tableMaterias;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtCuatrimestre;

    @FXML
    private TextField txtNombre;

    @FXML
    private CheckBox checkBoxActividad;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    private Carrera carrera = Data.getCarrera();

    private Materia materiaData = new Materia();

    private ObservableList<MateriaFX> materias = FXCollections.observableArrayList();

    HashSet<String> idsMateriasCursar = new HashSet<>();

    HashSet<String> idsMateriasRendir = new HashSet<>();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        cargarChoiceboxMaterias();
    }

    private void cargarMateriasDeCarrera() {
        materias.clear();

        idsMateriasCursar = materiaData.getCodigoCorrelativasCursado();
        idsMateriasRendir = materiaData.getCodigoCorrelativasRendir();

        for (Materia materia : carrera.getMaterias().values()) {
            if(!materia.getId().equals(materiaData.getId()))
            {
                boolean seCursaChecked = idsMateriasCursar.contains(materia.getId());
                boolean seRindeChecked = idsMateriasRendir.contains(materia.getId());

                MateriaFX materiaFX = new MateriaFX(materia.getId(), materia.getNombre(), seCursaChecked, seRindeChecked);
                materias.add(materiaFX);
            }

        }
    }

    private void cargarChoiceboxMaterias(){
        for (Map.Entry<String, Materia> entry : Data.getCarrera().getMaterias().entrySet()) {
            Materia materia = entry.getValue();
            choiceboxMaterias.getItems().add(materia.getId() + " - " + materia.getNombre());
        }
    }

    @FXML
    void clickBtnSeleccionar(ActionEvent event) {

        materiaData = manejoArchivosCarrera.obtenerMateria(pathCarreras, Materia.cortarString(choiceboxMaterias.getValue()));

        // Configuración de los TextField (no editables)
        txtCodigo.setText(materiaData.getId());
        txtCodigo.setEditable(false);

        txtNombre.setText(materiaData.getNombre());
        txtNombre.setEditable(false);

        txtAnio.setText(materiaData.getAnio());
        txtAnio.setEditable(false);

        txtCuatrimestre.setText(materiaData.getCuatrimestre());
        txtCuatrimestre.setEditable(false);

        checkBoxSeCursa.setSelected(materiaData.isSeCursa());
        checkBoxSeCursa.setDisable(true);

        checkBoxSeRinde.setSelected(materiaData.isSeRinde());
        checkBoxSeRinde.setDisable(true);

        checkBoxActividad.setSelected(materiaData.isActividad());
        checkBoxActividad.setDisable(true);

        cargarMateriasDeCarrera();
        tableMaterias.setEditable(false);

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Configurar las columnas CheckBox sin convertir a String
        colSeCursa.setCellValueFactory(cellData -> cellData.getValue().seCursaProperty());
        colSeCursa.setCellFactory(CheckBoxTableCell.forTableColumn(colSeCursa));
        colSeCursa.setEditable(false);

        colSeRinde.setCellValueFactory(cellData -> cellData.getValue().seRindeProperty());
        colSeRinde.setCellFactory(CheckBoxTableCell.forTableColumn(colSeRinde));
        colSeRinde.setEditable(false);

        tableMaterias.setItems(materias);
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(configurarMateriasAdministrador,stage,"Configurar materias");
    }

}
