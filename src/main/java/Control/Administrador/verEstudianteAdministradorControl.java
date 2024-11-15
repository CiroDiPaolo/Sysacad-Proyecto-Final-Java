package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Modelo.EstadoAlumnoMateria;
import Modelo.EstadoAlumnoMateriaFX;
import Modelo.EstadoAlumnoMesa;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

import static Path.Path.buscarAlumnoAdministrador;

public class verEstudianteAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkActivo;

    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colCodCom;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colCodMateria;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colCodMesaExamen;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colEstado;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colFolio;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colNota;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colParcial1;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colParcial2;
    @FXML
    private TableColumn<EstadoAlumnoMateriaFX, String> colTomo;

    @FXML
    private TableView<EstadoAlumnoMateriaFX> tableView;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCodCarrera;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });
        txtNombre.setEditable(false);
        txtNombre.setText(Data.getEstudiante().getNombre());
        txtApellido.setEditable(false);
        txtApellido.setText(Data.getEstudiante().getApellido());
        txtDni.setEditable(false);
        txtDni.setText(Data.getEstudiante().getDni());
        txtCorreo.setEditable(false);
        txtCorreo.setText(Data.getEstudiante().getCorreo());
        txtContrasenia.setEditable(false);
        txtContrasenia.setText(Data.getEstudiante().getContrasenia());
        txtCodCarrera.setEditable(false);
        txtCodCarrera.setText(Data.getEstudiante().getCodigoCarrera());
        checkActivo.setDisable(true);
        checkActivo.setSelected(Data.getEstudiante().getActividad());

        // Configurar columnas (como vimos antes)
        configurarColumnas();

        // Obtener la lista de materias y cargarla en la tabla
        cargarDatosEnTabla();
    }

    private void configurarColumnas() {
        colCodCom.setCellValueFactory(new PropertyValueFactory<>("codComision"));
        colCodMateria.setCellValueFactory(new PropertyValueFactory<>("codMateria"));
        colCodMesaExamen.setCellValueFactory(new PropertyValueFactory<>("codigosMesa"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoMateria"));
        colFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("notasMesa"));
        colParcial1.setCellValueFactory(new PropertyValueFactory<>("notaParcial1"));
        colParcial2.setCellValueFactory(new PropertyValueFactory<>("notaParcial2"));
        colTomo.setCellValueFactory(new PropertyValueFactory<>("tomo"));
    }

    private void cargarDatosEnTabla() {
        ObservableList<EstadoAlumnoMateriaFX> estadoAlumnoMateriaFXList = FXCollections.observableArrayList();
        if (Data.getEstudiante() != null && Data.getEstudiante().getMaterias() != null) {
            for (EstadoAlumnoMateria estadoAlumnoMateria : Data.getEstudiante().getMaterias()) {
                EstadoAlumnoMateriaFX estadoAlumnoMateriaFX1 = new EstadoAlumnoMateriaFX();
                ArrayList<EstadoAlumnoMesa> estadoAlumnoMesas = new ArrayList<>();

                for (Map.Entry<String, EstadoAlumnoMesa> entry : estadoAlumnoMateria.getMesasExamen().entrySet()) {
                    EstadoAlumnoMesa estadoAlumnoMesa = entry.getValue();
                    estadoAlumnoMesas.add(estadoAlumnoMesa);
                }

                // Rellenar los datos del EstadoAlumnoMateriaFX
                estadoAlumnoMateriaFX1.setEstadoAlumnoMesa(estadoAlumnoMesas);
                estadoAlumnoMateriaFX1.setCodMateria(estadoAlumnoMateria.getCodigoMateria());
                estadoAlumnoMateriaFX1.setEstadoMateria(estadoAlumnoMateria.getEstado());
                estadoAlumnoMateriaFX1.setNotaParcial1(estadoAlumnoMateria.getNotas().get("primerParcial"));
                estadoAlumnoMateriaFX1.setNotaParcial2(estadoAlumnoMateria.getNotas().get("segundoParcial"));
                estadoAlumnoMateriaFX1.setTomo(estadoAlumnoMateria.getTomo());
                estadoAlumnoMateriaFX1.setFolio(estadoAlumnoMateria.getFolio());
                estadoAlumnoMateriaFX1.setCodComision(estadoAlumnoMateria.getCodigoComision());

                // Obtener códigos y notas de las mesas
                estadoAlumnoMateriaFX1.setCodigosMesa(estadoAlumnoMateriaFX1.getCodigosMesa());
                estadoAlumnoMateriaFX1.setNotasMesa(estadoAlumnoMateriaFX1.getNotasMesa());

                // Agregar a la lista
                estadoAlumnoMateriaFXList.add(estadoAlumnoMateriaFX1);


            }
        }

        // Setear los items del TableView
        tableView.setItems(estadoAlumnoMateriaFXList);
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarAlumnoAdministrador,stage,"Busqueda alumno");
    }

}
