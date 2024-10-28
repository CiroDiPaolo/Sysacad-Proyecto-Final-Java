package Control.Administrador;

import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.Materia;
import Modelo.Carrera;
import Modelo.MateriaFX;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.scene.shape.Rectangle;

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
    private Label tctMenuPrincipal;

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
        // Cargar materias
        cargarMateriasDeCarrera();
        tableMaterias.setEditable(true); // Hacer la tabla editable

        // Configurar columnas
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Columna para "seCursa"
        colSeCursa.setCellValueFactory(cellData -> cellData.getValue().seCursaProperty());
        colSeCursa.setCellFactory(CheckBoxTableCell.forTableColumn(colSeCursa));
        colSeCursa.setEditable(true);
        colSeCursa.setOnEditCommit(event -> {
            MateriaFX materia = event.getRowValue();
            boolean newValue = event.getNewValue();
            materia.setSeCursa(newValue); // Actualizar el modelo
            System.out.println("Se cursa: " + materia.getId() + " - " + newValue);
        });

        // Columna para "seRinde"
        colSeRinde.setCellValueFactory(cellData -> cellData.getValue().seRindeProperty());
        colSeRinde.setCellFactory(CheckBoxTableCell.forTableColumn(colSeRinde));
        colSeRinde.setEditable(true);
        colSeRinde.setOnEditCommit(event -> {
            MateriaFX materia = event.getRowValue();
            boolean newValue = event.getNewValue();
            materia.setSeRinde(newValue); // Actualizar el modelo
            System.out.println("Se rinde: " + materia.getId() + " - " + newValue);
        });

        // Asignar la lista de materias al TableView
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

            materia.crear(pathCarreras);
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
            materias.add(new MateriaFX(materia.getId(), materia.getNombre())); // Cambiado a MateriaFX
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        // Implementa la lógica para regresar a la escena anterior
    }
}