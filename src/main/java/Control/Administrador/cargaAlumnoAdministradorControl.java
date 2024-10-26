package Control.Administrador;

import Control.EscenaControl;
import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ControlArchivos.manejoArchivosCarrera;

import java.util.ArrayList;

import static Path.Path.fileNameAlumnos;
import static Path.Path.pathCarreras;

public class cargaAlumnoAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceCarrera;

    @FXML
    private Rectangle lista;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String nombreCarrera =choiceCarrera.getValue();
        try{
            String codigoCarrera = manejoArchivosCarrera.retonarCodigoCarreraPorNombre(nombreCarrera, pathCarreras, false);
            try{
                Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, dni, codigoCarrera);
                nuevoEstudiante.crear(fileNameAlumnos);
            }catch (EntidadYaExistente e)
            {
                e.getMessage();
            }catch (CamposVaciosException e)
            {
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
        escena.cambiarEscena(Path.menuPrincipalAdministrador,stage,"Menu Principal");

    }

    @FXML
    void setTxtApellido(ActionEvent event) {

    }

    @FXML
    void setTxtDni(ActionEvent event) {

    }

    @FXML
    void setTxtNombre(ActionEvent event) {

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
