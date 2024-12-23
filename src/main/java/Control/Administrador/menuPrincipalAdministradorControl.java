package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.ArchivoNoEncontrado;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.*;

public final class  menuPrincipalAdministradorControl {

    @FXML
    private Text txtBienvenida;

    private Stage stage;

    @FXML
    void clickBtnOp1(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(busquedaCarreraAdministrador, stage, "Configurar Carrera");

    }

    @FXML
    void clickBtnOp2(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarAlumnosAdministrador,stage,"Configurar Alumno");

    }

    @FXML
    void clickBtnOp3(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarProfesorAdministrador,stage,"Configurar Profesor");

    }

    @FXML
    void clickBtnOp5(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(busquedaCarrera2Administrador, stage, "Inscripciones");

    }

    @FXML
    void clickBtnOp6(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarAvisosAdministrador, stage, "Configurar avisos");
    }


    @FXML
    void clickBtnOp8(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(inicioSesion, stage, "Inicio de Sesion");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            stage = (Stage) txtBienvenida.getScene().getWindow();
            setTxtBienvenida();
        });

    }

    protected void setTxtBienvenida() {

        String legajo = Data.getLegajo();

        try {
            txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameAdministrador, legajo));
        } catch (ArchivoNoEncontrado e) {
            e.getMessage();
        }

    }

}
