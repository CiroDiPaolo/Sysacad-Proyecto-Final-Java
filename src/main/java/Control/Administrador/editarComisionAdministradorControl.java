package Control.Administrador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Modelo.Comision;
import Modelo.EstadoAlumnoComision;
import Modelo.Materia;
import Modelo.Turno;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import Control.EscenaControl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static Path.Path.*;

public class editarComisionAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private CheckBox checkBoxApertura;

    @FXML
    private ChoiceBox<Integer> choiceBoxAnio;

    @FXML
    private ChoiceBox<String> choiceBoxMateria;

    @FXML
    private ChoiceBox<String> choiceBoxProfesor;

    @FXML
    private ChoiceBox<String> choiceBoxTurno;

    @FXML
    private Spinner<Integer> spinnerCupos;

    @FXML
    private TextField txtAula;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String aula = txtAula.getText().trim();
        boolean apertura = checkBoxApertura.isSelected();
        boolean actividad = checkBoxActividad.isSelected();
        String codigoCarrera = Data.getCarrera().getId();
        String codigoProfesor = Materia.cortarString(choiceBoxProfesor.getValue());
        String codigoMateria = Materia.cortarString(choiceBoxMateria.getValue());
        Turno turno = null;
        try{
            turno = Turno.valueOf(choiceBoxTurno.getValue());
        }catch (NullPointerException e)
        {
            excepcionPersonalizada.excepcion("Ingresaste un campo vacio.");
        }
        int anio = choiceBoxAnio.getValue();
        int cupos = spinnerCupos.getValue();

        String id = Data.getComision().getId();
        HashSet<EstadoAlumnoComision> hashSet = Data.getComision().getEstadoAlumnoComisionHashSet();
        Comision comision = new Comision(id,turno,nombre,codigoMateria,codigoCarrera,codigoProfesor,descripcion,anio,aula,cupos,apertura,actividad,hashSet);
        try{
            if(comision.actualizar(pathComisiones+manejoArchivosComisiones.generarNombreArchivoComision(comision.getCodigoCarrera(),comision.getAnio()),comision.ComisionAJSONObject()))
            {
                manejoArchivosComisiones.actualizarEstudiantesDeUnaComision(codigoMateria);
                escena.cambiarEscena(opcionConfigurarComisionAdministrador, stage, "Configurar comisiones");
            }
        } catch (CamposVaciosException | DatosIncorrectosException e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(opcionConfigurarComisionAdministrador, stage, "Configurar comisiones");
    }

    @FXML
    protected void initialize(){
        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
        valueFactory.setValue(Data.getComision().getCupos());
        spinnerCupos.setValueFactory(valueFactory);

        choiceBoxAnio.getItems().clear();
        choiceBoxAnio.getItems().add(Data.getComision().getAnio());
        choiceBoxAnio.getSelectionModel().select(0);

        if (!Data.getCarrera().getMaterias().entrySet().isEmpty()) {
            choiceBoxMateria.getItems().clear();
            for (Map.Entry<String, Materia> entry : Data.getCarrera().getMaterias().entrySet()) {
                Materia materia = entry.getValue();
                if (materia.isActividad()) {
                    choiceBoxMateria.getItems().add(materia.getId() + " - " + materia.getNombre());
                    if(materia.getId().equals(Data.getComision().getCodigoMateria()))
                    {
                        String selectedMateria = Data.getComision().getCodigoMateria() + " - " + materia.getNombre();
                        choiceBoxMateria.getSelectionModel().select(selectedMateria);
                    }
                }
            }
        }

        ArrayList<Profesor> profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);
        if (profesores != null) {
            choiceBoxProfesor.getItems().clear();
            for (Profesor profesor : profesores) {
                if (profesor.getActividad()) {
                    choiceBoxProfesor.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                    if(profesor.getLegajo().equals(Data.getComision().getCodigoProfesor()))
                    {
                        String selectedProfesor = Data.getComision().getCodigoProfesor() + " - " + profesor.getNombre() + " " + profesor.getApellido();
                        choiceBoxProfesor.getSelectionModel().select(selectedProfesor);
                    }
                }
            }
        }

        choiceBoxTurno.getItems().clear();
        for (Turno turno : Turno.values()) {
            choiceBoxTurno.getItems().add(turno.toString());
        }
        choiceBoxTurno.getSelectionModel().select(Data.getComision().getTurno().toString());

        txtNombre.setText(Data.getComision().getNombre());
        txtAula.setText(Data.getComision().getAula());
        txtDescripcion.setText(Data.getComision().getDescripcion());
        checkBoxActividad.setSelected(Data.getComision().isActividad());
        checkBoxApertura.setSelected(Data.getComision().getApertura());

    }
}
