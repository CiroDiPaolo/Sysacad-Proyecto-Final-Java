package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosMesaExamen;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Modelo.Materia;
import Modelo.MesaExamen;
import Modelo.Turno;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static Path.Path.*;

public class cargarMesaExamenAdministradorControl {
    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private CheckBox checkBoxApertura;

    @FXML
    private ChoiceBox<String> choiceBoxMateria;

    @FXML
    private ChoiceBox<String> choiceBoxProfesor;

    @FXML
    private ChoiceBox<String> choiceBoxTurno;

    @FXML
    private ChoiceBox<String> choiceBoxVocal1;

    @FXML
    private ChoiceBox<String> choiceBoxVocal2;

    @FXML
    private Spinner<Integer> spinnerCupos;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtAula;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtHora;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {

        if(manejoArchivos.esFormatoFechaValida(txtFecha.getText()) && manejoArchivos.esFormatoHoraValida(txtHora.getText()))
        {
            LocalDate fecha = null;
            LocalTime hora = null;
            try{
                fecha = LocalDate.parse(txtFecha.getText());
                hora = LocalTime.parse(txtHora.getText());
            }catch (DateTimeException e)
            {
                excepcionPersonalizada.excepcion("Ingresaste una fecha inválida.");
            }

            String id = MesaExamen.generarIDMesaExamen(Data.getCarrera().getId(),Materia.cortarString(choiceBoxMateria.getValue()),pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(Data.getCarrera().getId(),fecha.getYear()));
            Turno turno = null;
            try{
                turno = Turno.valueOf(choiceBoxTurno.getValue());
            }catch (NullPointerException e)
            {
                System.out.println("Ingresaste un campo vacio.");
                excepcionPersonalizada.excepcion("Ingresaste un campo vacio.");
            }
            String codigoCarrera = Data.getCarrera().getId();
            String codigoMateria = Materia.cortarString(choiceBoxMateria.getValue());
            String codigoPresidente = Materia.cortarString(choiceBoxProfesor.getValue());
            HashSet<String> vocales = new HashSet<>();
            vocales.add(Materia.cortarString(choiceBoxVocal1.getValue()));
            vocales.add(Materia.cortarString(choiceBoxVocal2.getValue()));
            int cupos = spinnerCupos.getValue();
            String aula = txtAula.getText();
            boolean apertura = checkBoxApertura.isSelected();
            boolean actividad = checkBoxActividad.isSelected();

            MesaExamen mesaExamen = new MesaExamen(id,turno,codigoCarrera,codigoMateria,codigoPresidente,vocales,fecha,hora,cupos,aula,apertura,actividad, new HashSet<>());

            try{
                if(mesaExamen.crear(pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(Data.getCarrera().getId(),fecha.getYear())))
                {
                    excepcionPersonalizada.alertaConfirmacion("¡Mesa de examen cargada exitosamente!");
                    escena.cambiarEscena(opcionEditarMesaExamenAdministrador, stage, "Configurar mesa de examen");
                }
            } catch (CamposVaciosException e) {
                e.getMessage();
            } catch (DatosIncorrectosException e) {
                e.getMessage();
            } catch (EntidadYaExistente e) {
                e.getMessage();
            }
        }else
        {
            excepcionPersonalizada.excepcion("La fecha y/o la hora tienen formato incorrecto.");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(opcionEditarMesaExamenAdministrador,stage,"Configurar mesa de examen");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        choiceBoxVocal1.setDisable(true);
        choiceBoxVocal2.setDisable(true);

        ArrayList<Profesor> profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);
        if (profesores != null) {
            choiceBoxProfesor.getItems().clear();
            for (Profesor profesor : profesores) {
                if (profesor.getActividad()) {
                    choiceBoxProfesor.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                }
            }
        }

        choiceBoxProfesor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            choiceBoxVocal1.getItems().clear();
            choiceBoxVocal2.getItems().clear();
            choiceBoxVocal1.getSelectionModel().clearSelection();
            choiceBoxVocal2.getSelectionModel().clearSelection();

            if (newValue != null) {
                choiceBoxVocal1.setDisable(false);
                String legajoSeleccionado = Materia.cortarString(newValue);
                for (Profesor profesor : profesores) {
                    if (!profesor.getLegajo().equals(legajoSeleccionado)) {
                        choiceBoxVocal1.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                    }
                }
            } else {
                choiceBoxVocal1.setDisable(true);
                choiceBoxVocal2.setDisable(true);
            }
        });

        choiceBoxVocal1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            choiceBoxVocal2.getItems().clear();
            choiceBoxVocal2.getSelectionModel().clearSelection();

            if (newValue != null) {
                choiceBoxVocal2.setDisable(false);
                String legajoSeleccionado = Materia.cortarString(choiceBoxProfesor.getValue());
                String legajoSeleccionado2 = Materia.cortarString(choiceBoxVocal1.getValue());
                for (Profesor profesor : profesores) {
                    if (!profesor.getLegajo().equals(legajoSeleccionado) && !profesor.getLegajo().equals(legajoSeleccionado2)) {
                        choiceBoxVocal2.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                    }
                }
            } else {
                choiceBoxVocal2.setDisable(true);
            }
        });

        for(Turno turno : Turno.values())
        {
            choiceBoxTurno.getItems().add(turno.toString());
        }

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,200);
        valueFactory.setValue(1);
        spinnerCupos.setValueFactory(valueFactory);

        if(!Data.getCarrera().getMaterias().entrySet().isEmpty())
        {
            for(Map.Entry<String, Materia> entry : Data.getCarrera().getMaterias().entrySet()){
                Materia materia = entry.getValue();
                if(materia.isActividad())
                {
                    choiceBoxMateria.getItems().add(materia.getId() + " - " + materia.getNombre());
                }
            }
        }

        txtFecha.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.matches("\\d{0,4}(-\\d{0,2})?(-\\d{0,2})?")) {
                if (change.isAdded()) {
                    if (text.length() == 4 || text.length() == 7) {
                        change.setText(change.getText() + "-");
                        change.selectRange(text.length() + 1, text.length() + 1);
                    }
                }
                return change;
            } else {
                return null;
            }
        }));

        txtHora.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.matches("\\d{0,2}(:\\d{0,2})?")) {
                if (change.isAdded() && text.length() == 2) {
                    change.setText(change.getText() + ":");
                    change.selectRange(text.length() + 1, text.length() + 1);
                }
                return change;
            } else {
                return null;
            }
        }));
    }
}
