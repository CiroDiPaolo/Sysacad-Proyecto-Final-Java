package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.ArchivoNoEncontrado;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static Path.Path.fileNameAlumnos;

public class buscarAlumnoControl {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtNombreAlumno;

    private Stage stage;

    @FXML
    void clickBtnBuscar(ActionEvent event) {
        try {
            if (txtNombreAlumno.getText().isEmpty()) {
                throw new ArchivoNoEncontrado("Ingrese un nombre y apellido válidos");
            } else {
                String[] nombreApellido = txtNombreAlumno.getText().split(" ");
                if (nombreApellido.length < 2) {
                    throw new ArchivoNoEncontrado("Ingrese tanto el nombre como el apellido");
                }
                String nombre = nombreApellido[0];
                String apellido = nombreApellido[1];

                if (Consultas.consultaArchivo.buscarClave(fileNameAlumnos, nombre, "nombre") && Consultas.consultaArchivo.buscarClave(fileNameAlumnos, apellido, "apellido")) {


                    Data.setAux2(txtNombreAlumno.getText());
                    EscenaControl escena = new EscenaControl();
                    escena.cambiarEscena(Path.mostrarAlumnoProfesor, stage, "Editar Alumno");

                } else {
                    throw new ArchivoNoEncontrado("No existe alumno con ese nombre y apellido");
                }
            }
        } catch (ArchivoNoEncontrado e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.gestionarComisionProfesor, stage, "Gestionar comisiones");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }

}
