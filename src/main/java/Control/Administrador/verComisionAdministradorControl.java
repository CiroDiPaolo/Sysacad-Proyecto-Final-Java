package Control.Administrador;


import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import Excepciones.CamposVaciosException;
import Excepciones.EntidadNoEncontradaException;
import Excepciones.excepcionPersonalizada;
import Modelo.Comision;
import Modelo.EstadoAlumnoComision;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.ArrayList;

import static Path.Path.opcionConfigurarComisionAdministrador;
import static Path.Path.pathComisiones;

public class verComisionAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private CheckBox checkBoxApertura;

    @FXML
    private ChoiceBox<Integer> choiceBoxAnio;

    @FXML
    private ChoiceBox<Integer> choiceBoxAnioElegir;

    @FXML
    private ChoiceBox<String> choiceBoxMateria;

    @FXML
    private ChoiceBox<String> choiceBoxProfesor;

    @FXML
    private ChoiceBox<String> choiceBoxTurno;

    @FXML
    private ChoiceBox<String> choiceboxComision;

    @FXML
    private Spinner<Integer> spinnerCupos;

    @FXML
    private TableColumn<EstadoAlumnoComision, Boolean> colEstado;

    @FXML
    private TableColumn<EstadoAlumnoComision, String> colLegajo;

    @FXML
    private TableColumn<EstadoAlumnoComision, String> colNombre;

    @FXML
    private TableView<EstadoAlumnoComision> tableViewAlumnos;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtAula;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        colLegajo.setCellValueFactory(new PropertyValueFactory<>("lejagoAlumno"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAlumno"));

        colEstado.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colEstado.setCellFactory(column -> new TableCell<EstadoAlumnoComision, Boolean>() {
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

        try {
            ArrayList<Integer> numeros = manejoArchivosComisiones.obtenerNumerosDeArchivos("E:\\Sysacad-Proyecto-Final-Java\\Files\\Comisiones", Data.getCarrera().getId());

            if (!numeros.isEmpty()) {
                for (Integer numero : numeros) {
                    choiceBoxAnioElegir.getItems().add(numero);
                }
            } else {
                throw new EntidadNoEncontradaException("No se encontraron comisiones para ese año");
            }

            choiceBoxAnioElegir.setOnAction(event -> {
                Integer anioSeleccionado = choiceBoxAnioElegir.getValue();
                if (anioSeleccionado != null) {
                    actualizarComisiones(anioSeleccionado);
                }
            });

            choiceboxComision.setOnAction(event -> {
                String comisionSeleccionada = Materia.cortarString(choiceboxComision.getValue());
                if (comisionSeleccionada != null) {
                    try {
                        Comision comision = manejoArchivosComisiones.buscarComision(pathComisiones + manejoArchivosComisiones.generarNombreArchivoComision(Data.getCarrera().getId(), choiceBoxAnioElegir.getValue()), "id", comisionSeleccionada);
                        llenarCampos(comision);
                    } catch (CamposVaciosException e) {
                        e.getMessage();
                    }
                }
            });

        } catch (IOException e) {
            excepcionPersonalizada.excepcion("Ocurrió un problema. Si el problema persiste comuníquese con su distribuidor.");
        } catch (EntidadNoEncontradaException e) {
            e.getMessage();
        }
    }

    // Método para llenar los campos con los datos de la comisión seleccionada
    private void llenarCampos(Comision comision) {
        txtNombre.setText(comision.getNombre());
        choiceBoxMateria.setValue(comision.getCodigoMateria());
        choiceBoxProfesor.setValue(comision.getCodigoProfesor());
        txtDescripcion.setText(comision.getDescripcion());
        txtAula.setText(comision.getAula());
        choiceBoxTurno.setValue(comision.getTurno().toString());
        checkBoxActividad.setSelected(comision.isActividad());
        checkBoxApertura.setSelected(comision.getApertura());
        if (spinnerCupos.getValueFactory() == null) {

            spinnerCupos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, comision.getCupos()));
        } else {

            spinnerCupos.getValueFactory().setValue(comision.getCupos());
        }
        choiceBoxAnio.getSelectionModel().select(Integer.valueOf(comision.getAnio()));
        ObservableList<EstadoAlumnoComision> estadoAlumnos = FXCollections.observableArrayList(comision.getEstadoAlumnoComisionHashSet());
        tableViewAlumnos.setItems(estadoAlumnos);
        deshabilitarCampos();
    }

    // Método para deshabilitar los campos
    private void deshabilitarCampos() {
        txtNombre.setDisable(true);
        txtDescripcion.setDisable(true);
        txtAula.setDisable(true);
        spinnerCupos.setDisable(true);
        choiceBoxAnio.setDisable(true);
        choiceBoxMateria.setDisable(true);
        choiceBoxProfesor.setDisable(true);
        choiceBoxTurno.setDisable(true);
        checkBoxActividad.setDisable(true);
        checkBoxApertura.setDisable(true);
        tableViewAlumnos.setDisable(true);
    }

    private void actualizarComisiones(Integer anioSeleccionado) {
        choiceboxComision.getItems().clear();

        ArrayList<Comision> comisiones = manejoArchivosComisiones.obtenerComisionesPorAnio(anioSeleccionado, Data.getCarrera().getId());

        if (!comisiones.isEmpty()) {
            for(Comision comision : comisiones)
            {
                choiceboxComision.getItems().add(comision.getId() + " - " + comision.getNombre() + " - " + comision.getTurno().toString());
            }
        } else {
            excepcionPersonalizada.excepcion("No se encontraron comisiones para el año seleccionado.");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(opcionConfigurarComisionAdministrador,stage,"Configurar comisión");
    }

}
