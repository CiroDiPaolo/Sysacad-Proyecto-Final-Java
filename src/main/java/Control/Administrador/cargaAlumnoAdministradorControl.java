package Control.Administrador;

import Control.EscenaControl;
import ControlArchivos.manejoArchivos;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ControlArchivos.manejoArchivosCarrera;

import java.util.ArrayList;

import static Path.Path.fileNameAlumnos;
import static Path.Path.pathCarreras;

public class cargaAlumnoAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceCarrera;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String correo = txtCorreo.getText();
        String nombreCarrera =choiceCarrera.getValue();

        try{
            String codigoCarrera = manejoArchivosCarrera.retonarCodigoCarreraPorNombre(nombreCarrera, pathCarreras, false);
            try{
                Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, dni, codigoCarrera,correo);
                if(nuevoEstudiante.crear(fileNameAlumnos))
                {
                    Excepciones.excepcionPersonalizada.alertaConfirmacion("Estudiante cargado. LEGAJO: " + manejoArchivos.ultimoLegajo(fileNameAlumnos));
                    EscenaControl escena = new EscenaControl();
                    escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage,"Configurar alumno");
                }
            }catch (EntidadYaExistente e)
            {
                e.getMessage();
            }catch (CamposVaciosException e)
            {
                e.getMessage();
            } catch (DatosIncorrectosException e) {
                e.getMessage();
            }
        }catch (NullPointerException e)
        {
            excepcionPersonalizada.excepcion("No seleccionaste una carrera y/o completaste todos los datos del estudiante");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage,"Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<String> carreras = manejoArchivosCarrera.obtenerCampoEspecificoDeCarrera(pathCarreras, "nombre");

            choiceCarrera.getItems().addAll(carreras);

        });

    }

}
