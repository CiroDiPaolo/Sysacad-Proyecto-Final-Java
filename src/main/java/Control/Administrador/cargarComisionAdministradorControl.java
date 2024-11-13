package Control.Administrador;

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
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Control.EscenaControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Path.Path.*;

public class cargarComisionAdministradorControl {
    @FXML
    private Button btnCargar;

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
            System.out.println("Ingresaste un campo vacio.");
            excepcionPersonalizada.excepcion("Ingresaste un campo vacio.");
        }
        int anio = choiceBoxAnio.getValue();
        int cupos = spinnerCupos.getValue();
        System.out.println(pathComisiones+manejoArchivosComisiones.generarNombreArchivoComision(codigoCarrera,anio));
        String id = Comision.generarIDComision(codigoCarrera,codigoMateria, pathComisiones+manejoArchivosComisiones.generarNombreArchivoComision(codigoCarrera,anio));
        HashSet<EstadoAlumnoComision> hashSet = new HashSet<>();
        Comision comision = new Comision(id,turno,nombre,codigoMateria,codigoCarrera,codigoProfesor,descripcion,anio,aula,cupos,apertura,actividad,hashSet);
        try{
            if(comision.crear(pathComisiones+manejoArchivosComisiones.generarNombreArchivoComision(codigoCarrera,anio))){
                txtNombre.clear();
                txtAula.clear();
                txtDescripcion.clear();
                choiceBoxTurno.setValue(null);
                choiceBoxProfesor.setValue(null);
                checkBoxActividad.setSelected(false);
                checkBoxApertura.setSelected(false);
            }
        }catch (EntidadYaExistente e)
        {
            e.getMessage();
        }catch (CamposVaciosException e)
        {
            e.getMessage();
        }catch (DatosIncorrectosException e)
        {
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

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,200);
        valueFactory.setValue(1);
        spinnerCupos.setValueFactory(valueFactory);
        ArrayList<Integer> anios = new ArrayList<>();
        anios.add(LocalDate.now().getYear());
        anios.add((LocalDate.now().getYear()+1));
        choiceBoxAnio.getItems().addAll(anios);
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

        ArrayList<Profesor> profesores;
        profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);
        if(profesores!= null)
        {
            for(Profesor profesor : profesores)
            {
                if(profesor.getActividad()){
                    choiceBoxProfesor.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
                }

            }
        }

        for(Turno turno : Turno.values())
        {
            choiceBoxTurno.getItems().add(turno.toString());
        }

    }
}
