package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Carrera;
import Modelo.Materia;
import Modelo.MateriaFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;

import static Path.Path.*;


public class editarMateriaAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxSeCursa;

    @FXML
    private CheckBox checkBoxSeRinde;

    @FXML
    private CheckBox checkBoxActividad;

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
    private Stage stage;

    private EscenaControl escena = new EscenaControl();
    private Carrera carrera = Data.getCarrera();
    private Materia materiaData = new Materia(Data.getMateria());
    private ObservableList<MateriaFX> materias = FXCollections.observableArrayList();
    HashSet<String> idsMateriasCursar = new HashSet<>();
    HashSet<String> idsMateriasRendir = new HashSet<>();

    @FXML
    public void initialize() {
        txtCodigo.setText(materiaData.getId());
        txtNombre.setText(materiaData.getNombre());
        txtAnio.setText(materiaData.getAnio());
        txtCuatrimestre.setText(materiaData.getCuatrimestre());
        checkBoxSeCursa.setSelected(materiaData.isSeCursa());
        checkBoxSeRinde.setSelected(materiaData.isSeRinde());
        checkBoxActividad.setSelected(materiaData.isActividad());
        txtCodigo.setEditable(false);
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
            MateriaFX materia =  event.getRowValue();
            boolean newValue = event.getNewValue();
            materia.setSeRinde(newValue);
            System.out.println("Se rinde: " + materia.getId() + " - " + newValue);
        });

        tableMaterias.setItems(materias);
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
        Materia m = new Materia(txtCodigo.getText(),txtNombre.getText(),txtAnio.getText(),txtCuatrimestre.getText(),checkBoxSeCursa.isSelected(),checkBoxSeRinde.isSelected(),idsMateriasCursar,idsMateriasRendir, checkBoxActividad.isSelected());
        try{
            m.actualizar(pathCarreras, m.materiaAJSONObject());
            Data.getCarrera().getMaterias().put(m.getId(),m);
            EscenaControl escena = new EscenaControl();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            escena.cambiarEscena(elegirMateriaAdministrador, stage, "Configurar materias");
        }catch (DatosIncorrectosException e)
        {
            e.getMessage();
        }catch (CamposVaciosException e)
        {
            e.getMessage();
        }



    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        escena.cambiarEscena(elegirMateriaAdministrador, stage, "Configurar Materias");
    }

}
