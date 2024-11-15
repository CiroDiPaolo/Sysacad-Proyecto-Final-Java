package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.Materia;
import Modelo.Carrera;
import Modelo.MateriaFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.HashSet;
import javafx.stage.Stage;

import static Path.Path.configurarMateriasAdministrador;
import static Path.Path.pathCarreras;

public class cargaMateriaAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxSeCursa;

    @FXML
    private CheckBox checkBoxSeRinde;

    @FXML
    private TableColumn<MateriaFX, String> colCodigo;

    @FXML
    private TableColumn<MateriaFX, String> colNombre;

    @FXML
    private TableColumn<MateriaFX, Boolean> colSeCursa;

    @FXML
    private TableColumn<MateriaFX, Boolean> colSeRinde;

    @FXML
    private TableView<MateriaFX> tableMaterias;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtCuatrimestre;

    @FXML
    private TextField txtNombre;


    private ObservableList<MateriaFX> materias = FXCollections.observableArrayList();
    private Carrera carrera = Data.getCarrera();
    HashSet<String> idsMateriasCursar = new HashSet<>();
    HashSet<String> idsMateriasRendir = new HashSet<>();

    @FXML
    public void initialize() {

        cargarMateriasDeCarrera();
        tableMaterias.setEditable(true);


        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));


        colSeCursa.setCellValueFactory(cellData -> cellData.getValue().seCursaProperty());
        colSeCursa.setCellFactory(CheckBoxTableCell.forTableColumn(colSeCursa));
        colSeCursa.setEditable(true);
        colSeCursa.setOnEditCommit(event -> {
            MateriaFX materia = event.getRowValue();
            boolean newValue = event.getNewValue();
            materia.setSeCursa(newValue);
            System.out.println("Se cursa: " + materia.getId() + " - " + newValue);
        });

        colSeRinde.setCellValueFactory(cellData -> cellData.getValue().seRindeProperty());
        colSeRinde.setCellFactory(CheckBoxTableCell.forTableColumn(colSeRinde));
        colSeRinde.setEditable(true);
        colSeRinde.setOnEditCommit(event -> {
            MateriaFX materia = event.getRowValue();
            boolean newValue = event.getNewValue();
            materia.setSeRinde(newValue);
            System.out.println("Se rinde: " + materia.getId() + " - " + newValue);
        });

        tableMaterias.setItems(materias);
    }

    @FXML
    void clickBtnCargar(ActionEvent event) {

        idsMateriasCursar.clear();
        idsMateriasRendir.clear();

        for (MateriaFX materia : tableMaterias.getItems()) {
            if (materia.isSeCursa()) {
                idsMateriasCursar.add(materia.getId());
            }
            if (materia.isSeRinde()) {
                idsMateriasRendir.add(materia.getId());
            }
        }

        Materia materia = new Materia(txtCodigo.getText(), txtNombre.getText(), txtAnio.getText(),txtCuatrimestre.getText(),checkBoxSeCursa.isSelected(),checkBoxSeRinde.isSelected(),idsMateriasCursar,idsMateriasRendir,true);
        try{

            if(materia.crear(pathCarreras)){
                Data.getCarrera().getMaterias().put(materia.getId(),materia);
                EscenaControl escena = new EscenaControl();
                Stage stage = (Stage) btnCargar.getScene().getWindow();
                escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar Materias");
            }


        } catch (CamposVaciosException e) {
            e.getMessage();
        } catch (DatosIncorrectosException e) {
            e.getMessage();
        } catch (EntidadYaExistente e) {
            e.getMessage();
        }
    }

    private void cargarMateriasDeCarrera() {
        materias.clear();
        for (Materia materia : carrera.getMaterias().values()) {
            materias.add(new MateriaFX(materia.getId(), materia.getNombre()));
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar Materias");
    }

}