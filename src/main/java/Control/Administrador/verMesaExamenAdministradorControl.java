package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosMesaExamen;
import Excepciones.CamposVaciosException;
import Excepciones.excepcionPersonalizada;
import Modelo.EstadoAlumnoMesa;
import Modelo.Materia;
import Modelo.MesaExamen;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static Path.Path.opcionEditarMesaExamenAdministrador;
import static Path.Path.pathMesaExamen;

public class verMesaExamenAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private CheckBox checkBoxApertura;

    @FXML
    private ChoiceBox<Integer> choiceboxAnio;

    @FXML
    private ChoiceBox<String> choiceboxMesa;

    @FXML
    private TableColumn<EstadoAlumnoMesa, String> colLegajo;

    @FXML
    private TableColumn<EstadoAlumnoMesa, Integer> colNota;

    @FXML
    private TableColumn<EstadoAlumnoMesa, Boolean> colPresente;

    @FXML
    private TableView<EstadoAlumnoMesa> tableviewAlumnos;

    @FXML
    private TextField txtAula;

    @FXML
    private TextField txtCupos;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtMateria;

    @FXML
    private TextField txtPresidente;

    @FXML
    private TextField txtTurno;

    @FXML
    private TextField txtVocal1;

    @FXML
    private TextField txtVocal2;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        colLegajo.setCellValueFactory(new PropertyValueFactory<>("codigoMesa"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        colPresente.setCellValueFactory(new PropertyValueFactory<>("presente"));
        colPresente.setCellFactory(column -> new TableCell<EstadoAlumnoMesa, Boolean>() {
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

        cargarAnios();

        choiceboxAnio.setOnAction(event -> {
            Integer anioSeleccionado = choiceboxAnio.getValue();
            if (anioSeleccionado != null) {
                actualizarMesas(anioSeleccionado);
            }
        });

        choiceboxMesa.setOnAction(event -> {
            String mesaSeleccionada = choiceboxMesa.getValue();
            if (mesaSeleccionada != null) {
                cargarDatosMesa(mesaSeleccionada);
            }
        });
    }

    private void cargarAnios() {
        try{
            ArrayList<Integer> aniosDisponibles = manejoArchivosMesaExamen.obtenerNumerosDeArchivos("Files/MesaExamen", Data.getCarrera().getId());
            if (!aniosDisponibles.isEmpty()) {
                choiceboxAnio.getItems().addAll(aniosDisponibles);
            } else {
                excepcionPersonalizada.excepcion("No se encontraron mesas para ese año");
            }
        }catch (IOException e)
        {
            e.getMessage();
        }

    }

    private void actualizarMesas(Integer anioSeleccionado) {
        choiceboxMesa.getItems().clear();
        ArrayList<MesaExamen> mesas = manejoArchivosMesaExamen.obtenerMesaExamenPorAnio(choiceboxAnio.getValue(),Data.getCarrera().getId());
        if (!mesas.isEmpty()) {

            for(MesaExamen mesaExamen : mesas)
            {
                choiceboxMesa.getItems().add( mesaExamen.getId() + " - " + mesaExamen.getCodigoMateria() + " - " + mesaExamen.getFecha() + " - " + mesaExamen.getHora() + " - " + mesaExamen.getTurno().toString());
            }
        } else {
            excepcionPersonalizada.excepcion("No se encontraron mesas para el año seleccionado.");
        }
    }

    private void cargarDatosMesa(String mesaSeleccionada) {
        try{
            MesaExamen mesa = manejoArchivosMesaExamen.buscarMesaExamen(pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(Data.getCarrera().getId(),choiceboxAnio.getValue()),"id", Materia.cortarString(mesaSeleccionada));
            if (mesa != null) {
                txtMateria.setText(mesa.getCodigoMateria());
                txtPresidente.setText(mesa.getCodigoPresidente());
                txtAula.setText(mesa.getAula());
                txtCupos.setText(String.valueOf(mesa.getCupos()));
                txtFecha.setText(mesa.getFecha().toString());
                txtHora.setText(mesa.getHora().toString());
                txtTurno.setText(mesa.getTurno().toString());
                checkBoxActividad.setSelected(mesa.isActividad());
                checkBoxApertura.setSelected(mesa.isApertura());

                HashSet<String> vocales = mesa.getVocales();
                txtVocal1.setText(vocales.size() > 0 ? vocales.iterator().next() : "");
                txtVocal2.setText(vocales.size() > 1 ? (String) vocales.toArray()[1] : "");

                ObservableList<EstadoAlumnoMesa> estadoAlumnos = FXCollections.observableArrayList(mesa.getAlumnosInscriptos());
                tableviewAlumnos.setItems(estadoAlumnos);

                deshabilitarCampos();
            } else {
                excepcionPersonalizada.excepcion("No se pudieron cargar los datos de la mesa seleccionada.");
            }
        }catch (CamposVaciosException e) {
           e.getMessage();
        }

    }

    private void deshabilitarCampos() {
        txtMateria.setDisable(true);
        txtPresidente.setDisable(true);
        txtAula.setDisable(true);
        txtCupos.setDisable(true);
        txtFecha.setDisable(true);
        txtHora.setDisable(true);
        txtTurno.setDisable(true);
        txtVocal1.setDisable(true);
        txtVocal2.setDisable(true);
        checkBoxActividad.setDisable(true);
        checkBoxApertura.setDisable(true);
        tableviewAlumnos.setDisable(true);
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(opcionEditarMesaExamenAdministrador,stage,"Configurar mesa de examen");
    }
}
