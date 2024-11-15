package Control.Administrador;
import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosMesaExamen;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
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

public class editarMesaExamenAdministradorControl {

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
    private TextField txtAula;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtHora;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {
        if(manejoArchivos.esFormatoFechaValida(txtFecha.getText()) && manejoArchivos.esFormatoHoraValida(txtHora.getText()))
        {
            LocalDate fecha = null;
            LocalTime hora = null;
            try{
                fecha = LocalDate.parse(txtFecha.getText());
                hora = LocalTime.parse(txtHora.getText());
                String id = Data.getMesaExamen().getId();
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

                MesaExamen mesaExamen = new MesaExamen(id,turno,codigoCarrera,codigoMateria,codigoPresidente,vocales,fecha,hora,cupos,aula,apertura,actividad, Data.getMesaExamen().getAlumnosInscriptos());

                try{
                    if(mesaExamen.actualizar(pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(Data.getCarrera().getId(),fecha.getYear()),mesaExamen.toJSONObject()))
                    {
                        excepcionPersonalizada.alertaConfirmacion("¡Mesa de examen actualizada exitosamente!");
                        EscenaControl escena = new EscenaControl();
                        escena.cambiarEscena(opcionEditarMesaExamenAdministrador, stage, "Configurar mesa de examen");
                    }
                } catch (CamposVaciosException e) {
                    e.getMessage();
                } catch (DatosIncorrectosException e) {
                    e.getMessage();
                }
            }catch (DateTimeException e)
            {
                excepcionPersonalizada.excepcion("Ingresaste una hora y/o fecha inválida.");
            }
        }else
        {
            excepcionPersonalizada.excepcion("La fecha y/o la hora tienen formato incorrecto.");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarMesaExamenAdministrador,stage,"Configurar mesa de examen");
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
                    if (!profesor.getLegajo().equals(legajoSeleccionado) && profesor.getActividad()) {
                        String nombreCompleto = profesor.getNombre() + " " + profesor.getApellido();
                        choiceBoxVocal1.getItems().add(profesor.getLegajo() + " - " + nombreCompleto);
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
                String legajoPresidente = Materia.cortarString(choiceBoxProfesor.getValue());
                String legajoVocal1 = Materia.cortarString(newValue);

                for (Profesor profesor : profesores) {
                    if (!profesor.getLegajo().equals(legajoPresidente) && !profesor.getLegajo().equals(legajoVocal1) && profesor.getActividad()) {
                        String nombreCompleto = profesor.getNombre() + " " + profesor.getApellido();
                        String profesorItem = profesor.getLegajo() + " - " + nombreCompleto;
                        if (!choiceBoxVocal2.getItems().contains(profesorItem)) {
                            choiceBoxVocal2.getItems().add(profesorItem);
                        }
                    }
                }
            } else {
                choiceBoxVocal2.setDisable(true);
            }
        });

        choiceBoxVocal2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                choiceBoxVocal1.setDisable(true);
                choiceBoxVocal2.setDisable(true);
            }
        });

        for (Turno turno : Turno.values()) {
            choiceBoxTurno.getItems().add(turno.toString());
        }

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
        valueFactory.setValue(1);
        spinnerCupos.setValueFactory(valueFactory);

        if (!Data.getCarrera().getMaterias().entrySet().isEmpty()) {
            choiceBoxMateria.getItems().clear();
            for (Map.Entry<String, Materia> entry : Data.getCarrera().getMaterias().entrySet()) {
                Materia materia = entry.getValue();
                if (materia.isActividad()) {
                    choiceBoxMateria.getItems().add(materia.getId() + " - " + materia.getNombre());
                    if(materia.getId().equals(Data.getMesaExamen().getCodigoMateria()))
                    {
                        String selectedMateria = Data.getMesaExamen().getCodigoMateria() + " - " + materia.getNombre();
                        choiceBoxMateria.getSelectionModel().select(selectedMateria);
                    }
                }
            }
        }
        MesaExamen mesaExamen = Data.getMesaExamen();
        // Cargar presidente
        String codigoPresidente = mesaExamen.getCodigoPresidente();
        if (codigoPresidente != null) {
            for (Profesor profesor : profesores) {
                if (profesor.getLegajo().equals(codigoPresidente)) {
                    choiceBoxProfesor.getSelectionModel().select(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                    break;
                }
            }
        }

        HashSet<String> vocales = mesaExamen.getVocales();
        if (vocales != null) {
            for (String vocal : vocales) {
                if (choiceBoxVocal1.getSelectionModel().getSelectedItem() == null) {
                    choiceBoxVocal1.getItems().add(vocal + " - " + getProfesorByLegajo(vocal).getNombre() + " " + getProfesorByLegajo(vocal).getApellido());
                    choiceBoxVocal1.getSelectionModel().select(vocal + " - " + getProfesorByLegajo(vocal).getNombre() + " " + getProfesorByLegajo(vocal).getApellido());
                } else if (choiceBoxVocal2.getSelectionModel().getSelectedItem() == null) {
                    choiceBoxVocal2.getItems().add(vocal + " - " + getProfesorByLegajo(vocal).getNombre() + " " + getProfesorByLegajo(vocal).getApellido());
                    choiceBoxVocal2.getSelectionModel().select(vocal + " - " + getProfesorByLegajo(vocal).getNombre() + " " + getProfesorByLegajo(vocal).getApellido());
                }
            }
        }

        String turno = mesaExamen.getTurno().toString();
        if (turno != null) {
            choiceBoxTurno.getSelectionModel().select(turno);
        }

        LocalDate fecha = mesaExamen.getFecha();
        if (fecha != null) {
            txtFecha.setText(fecha.toString());
        }

        LocalTime hora = mesaExamen.getHora();
        if (hora != null) {
            txtHora.setText(hora.toString());
        }

        int cupos = mesaExamen.getCupos();
        if (cupos > 0) {
            spinnerCupos.getValueFactory().setValue(cupos);
        }

        String aula = mesaExamen.getAula();
        if (aula != null) {
            txtAula.setText(aula);
        }

        boolean apertura = mesaExamen.isApertura();
        checkBoxApertura.setSelected(apertura);

        boolean actividad = mesaExamen.isActividad();
        checkBoxActividad.setSelected(actividad);
    }

    public Profesor getProfesorByLegajo(String legajo) {
        ArrayList<Profesor> profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);
        if (profesores == null || profesores.isEmpty()) {
            return null;
        }

        for (Profesor profesor : profesores) {
            if (profesor.getLegajo().trim().equals(legajo.trim())) {
                return profesor;
            }
        }

        return null;
    }
}



