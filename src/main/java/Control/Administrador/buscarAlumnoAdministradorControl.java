package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.ArchivoNoEncontrado;
import Usuarios.Estudiante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static Path.Path.*;

public class buscarAlumnoAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtLegajoAlumno;

    private Stage stage;

    @FXML
    void clickBtnBuscar(ActionEvent event) {
        try {
            if (txtLegajoAlumno.getText().isEmpty()) {
                throw new ArchivoNoEncontrado("Ingrese un legajo valido");
            } else {
                if (Consultas.consultaArchivo.buscarClave(fileNameAlumnos, txtLegajoAlumno.getText(), "legajo")) {

                    Data data = new Data();
                    data.setEstudiante(Estudiante.JSONObjectAEstudiante(manejoArchivosEstudiante.retornarEstudiante(txtLegajoAlumno.getText(), fileNameAlumnos)));
                    EscenaControl escena = new EscenaControl();
                    stage = (Stage) btnVolver.getScene().getWindow();
                    escena.cambiarEscena(verEstudianteAdministrador, stage, "Ver estudiante");

                } else {
                    throw new ArchivoNoEncontrado("No existe alumno con ese legajo");
                }
            }
        } catch (ArchivoNoEncontrado e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        stage = (Stage) btnVolver.getScene().getWindow();
        escena.cambiarEscena(configurarAlumnosAdministrador,stage,"Configurar Alumno");

    }

}
